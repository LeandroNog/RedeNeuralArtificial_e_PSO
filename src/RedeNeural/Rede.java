/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RedeNeural;
import DadosNormalizados.DadosNormalizados;
import DadosNormalizados.InstanciaNormalizada;
import Erros.Erro;
import Matriz.Matriz;
import Matriz.MatrizDouble;
import static java.lang.Math.abs;
import static java.lang.Math.exp;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author leandro
 */
public class Rede {
    
    private int numCamadas;
    private ArrayList<Camada> camadas;
    private ArrayList<Integer> numNeuronioCamada;
    private int numCiclos;
    private int numEntradas;
    private double taxaDeAprendizado;

    public Rede(int numCamadas,ArrayList<Integer> numNeuronioCamada,int numEntradas,  int numCiclos) {
        this.numCamadas = numCamadas;
        this.numCiclos = numCiclos;
        this.camadas = new ArrayList<Camada>();
        this.numNeuronioCamada = numNeuronioCamada; 
        this.numEntradas = numEntradas;
        this.taxaDeAprendizado = 0.6;

    }

    public Rede() {
    }
    
    //verificar se tá correto
    public void inicializaRede(){
        //Inicia rede com pesos aleatórios
        Random random = new Random();
        double x;
        int i, j, l;
        int numeroPesos;
        ArrayList<Double> pesos = new ArrayList<>();
        ArrayList<Double> dw = new ArrayList<>();
        ArrayList<Neuronio> listNeuronio = new ArrayList<>();
         
        Camada camada;
        Neuronio neuronio;
        ArrayList<Camada> camadas = new ArrayList<>();
        for(i=0;i<this.numCamadas;i++){
            //System.out.println("Camada:" + i);
            camada = new Camada();
            camada.setNumNeuronios(this.numNeuronioCamada.get(i));
            //System.out.println("aqui" + camada.getNumNeuronios());
            listNeuronio = new ArrayList<>();
            for(j=0;j<camada.getNumNeuronios();j++){
                neuronio = new Neuronio();
                pesos = new ArrayList<>();
                  if(i==0){
                     numeroPesos = this.getNumEntradas();
                     //System.out.println("camada1" + this.getCamadas().get(0).getNumNeuronios());
                  }
                  else{
                     numeroPesos = this.getCamadas().get(i-1).getNumNeuronios();
                     //System.out.println(this.getCamadas().get(i-1).getNumNeuronios());
                  }
                  for(l=0;l<numeroPesos;l++){
                       x = 0.5;
                       pesos.add(x);
                       dw.add(0.0);
                       
                  }
                neuronio.setPesos(pesos);
                neuronio.setDw(dw);
                neuronio.setBias(random.nextDouble());
                listNeuronio.add(neuronio);
 
            }
                camada.setListNeuronios(listNeuronio); 
                camadas.add(camada);
                this.setCamadas(camadas);
        }
        
          
    }
    
    
    public double propagaSinal(InstanciaNormalizada instancia){
        double saida=0.0;
        int i, j, l, c;
        
        //Primeira camada
        for(i = 0 ; i<this.getNumNeuronioCamada().get(0) ; i++){
            saida=0.0;
            for(j = 0; j < this.getNumEntradas(); j++){
                saida = saida + this.getCamadas().get(0).getListNeuronios().get(i).getPesos().get(j)*instancia.getListAtributos().get(j).getValor();
                
            }
            saida = saida + this.getCamadas().get(0).getListNeuronios().get(i).getBias();
            this.getCamadas().get(0).getListNeuronios().get(i).setSaida(this.fncIA_MLP_sigmoide(saida));
        }
        //Outras camadas
        for(c = 1; c<this.getNumCamadas();c++){
            for(i = 0 ; i<this.getNumNeuronioCamada().get(c) ; i++){
                saida=0.0;
                for(j = 0; j < this.getNumNeuronioCamada().get(c-1); j++){

                    saida = saida + this.getCamadas().get(c).getListNeuronios().get(i).getPesos().get(j)*this.getCamadas().get(c-1).getListNeuronios().get(j).getSaida();
                   
                }
                
                saida = saida + this.getCamadas().get(c).getListNeuronios().get(i).getBias();
                this.getCamadas().get(c).getListNeuronios().get(i).setSaida(this.fncIA_MLP_sigmoide(saida));
        
           
            }
        
        }

        return saida;
    }
    
    
    
    public Erro executaTeste(DadosNormalizados dados){
        ArrayList<Double> saida = new ArrayList<>();
        ArrayList<Double> esperado = new ArrayList<>();
        Double respostaRede;
        int i, j;
        
        for(i=0;i<dados.getNumInstancias();i++){
            //Propaga instancia
            this.propagaSinal(dados.getListInstancias().get(i));
            respostaRede = this.getCamadas().get(this.getNumCamadas()-1).getListNeuronios().get(this.getNumNeuronioCamada().get(this.getNumCamadas()-1)-1).getSaida();
            saida.add(respostaRede);
            esperado.add(dados.getListInstancias().get(i).getEsperado());
        }
       
        
        Erro erroTeste = new Erro(esperado, saida);

        return erroTeste;
        
    }
    
    
    public Erro executaAlgoritmodeAprendizado(DadosNormalizados dados){
        
        
        //dados.imprimeDataConsole();
        
        
        ArrayList<Double> saida = new ArrayList<>();
        ArrayList<Double> esperado = new ArrayList<>();
       
        // Calcula o numero de pesos da rede neural.
        
        int num_Pesos = (dados.getNumAtributos() * this.getCamadas().get(0).getNumNeuronios());
	    for (int i = 0; i < this.getNumCamadas()-1; i++){
                num_Pesos = num_Pesos + (this.getCamadas().get(i).getNumNeuronios() * this.getCamadas().get(i+1).getNumNeuronios());
        }
	
	// Cria a matriz jacobiana [instancias X pesos]
        Matriz Jacobiana = new Matriz(dados.getNumInstancias(), num_Pesos);
        double JacobianaTransposta[][];
        double JacobianaTranspostax2[][];
        double Gradiente[][];
        double Hessiana[][];
	// Cria a matriz de erros [instancias X neuronios na saida]
	Matriz erros = new Matriz(dados.getNumInstancias(), this.getCamadas().get(this.getNumCamadas()-1).getNumNeuronios());
	double[][] erros1 =
                new double[dados.getNumInstancias()][this.getCamadas().get(this.getNumCamadas()-1).getNumNeuronios()];
        // Cria as matrizes:
	// - B [pesos X neuronios na saida]
        Matriz B = new Matriz(num_Pesos, this.getCamadas().get(this.getNumCamadas()-1).getNumNeuronios());
	// - L e U [pesos X pesos]
	Matriz L = new Matriz(num_Pesos, num_Pesos);
	Matriz U = new Matriz(num_Pesos, num_Pesos);
	//Cria matriz identidade [pesos X pesos]
        
	Matriz identidade = new Matriz(num_Pesos, num_Pesos);
        identidade.criaIdentidade();
        Erro taxa_ErroAntes;
        
        Matriz matriz = new Matriz(num_Pesos, num_Pesos);
        
        double b1[][] = new double[num_Pesos][this.getCamadas().get(this.getNumCamadas()-1).getNumNeuronios()];
        int count_Peso=0;
        int ciclos = 0;
        int i, j, k, l;
        double diferenca = 0.0;
        boolean fimCiclo=false;
        double detHessiana=0.0;
        
        
        Random r = new Random();
        double randomValue;
        
        Erro bestErro = new Erro();
        
        double lambda = 0.00001;
        double maxLambda = 10;
        double v = 1.2;
        double identidadexLambda[][];
        double ajusteHessiana[][];
        double solucao[];
        Rede redeBackup;
        Erro taxa_ErroPosAjuste;
        int c;
	// Ateh o criterio de parada ser satisfeito [numero de ciclos]
	while(ciclos<this.getNumCiclos()) {
		lambda = 0.000001;
	    	// Calculo da matriz Jacobiana.
    		for (i=0;i<dados.getNumInstancias();i++) {
			// Propaga o sinal pela rede ateh a camada de saida.
                        this.propagaSinal(dados.getListInstancias().get(i));
                        
                        
			
			// Calcula o erro para cada neuronio de saida.
			for (j=0;j<this.getCamadas().get(this.getNumCamadas()-1).getNumNeuronios();j++) {
				
                                // Calcula a diferenca entre a saida esperada e saida encontrada.
				diferenca = abs(dados.getListInstancias().get(i).getEsperado() 
                                        - this.getCamadas().get(this.getNumCamadas()-1).getListNeuronios().get(j).getSaida() );
				
                                // Adiciona o erro do neuronio na matriz de erros.
                                erros1[i][j] = diferenca;
                                this.getCamadas().get(this.getNumCamadas()-1).getListNeuronios().get(j).setErro(diferenca);
                                
                                

			}
                        
                        this.fncIA_MLP_RetropropagaErro();
                        
                        
                        erros.setMatriz(erros1);
			
			// Retropropaga o erro
	    		// IMPORTANTE: Este calculo nao deve ser implementado agora !!!
			
			// Calcula a matriz Jacobiana
			count_Peso = 0;
			for (j=0;j<this.getNumCamadas();j++) {
				for (k=0;k<this.getNumNeuronioCamada().get(j);k++) {
					//Caso for a primeira camada, o numero de pesos de cada neuronio sera o numero de atributos
					if (j ==0){
                                          for (l=0; l<this.getNumEntradas(); l++){
						// IMPORTANTE: Este calculo nao deve ser implementado agora !!!
						Jacobiana.getMatriz()[i][count_Peso] = r.nextDouble();
                                                Jacobiana.getMatriz()[i][count_Peso] = dados.getListInstancias().get(i).getListAtributos().get(l).getValor()*
                                                        this.getCamadas().get(j).getListNeuronios().get(k).getPesos().get(l)*
                                                        this.getCamadas().get(j).getListNeuronios().get(k).getDelta();
                                                        
                                                        
                                                        
                                                //System.out.println(Jacobiana.getMatriz()[i][count_Peso]);
						count_Peso++;
					}  
                                        }
                                        else{
                                             for (l=0; l<this.getNumNeuronioCamada().get(j-1); l++){
						// IMPORTANTE: Este calculo nao deve ser implementado agora !!!
						Jacobiana.getMatriz()[i][count_Peso] = r.nextDouble();
                                                Jacobiana.getMatriz()[i][count_Peso] = this.getCamadas().get(j-1).getListNeuronios().get(l).getSaida()*
                                                        this.getCamadas().get(j).getListNeuronios().get(k).getPesos().get(l)*
                                                        this.getCamadas().get(j).getListNeuronios().get(k).getDelta();
						count_Peso++;
					}  
                                        }
                                      
				}
			}
                        //Capturo os valores esperados
                        esperado.add(dados.getListInstancias().get(i).getEsperado());
		}//Fim da criacao da matriz Jacobiana.

		//Calcula erro da tecnica ate o momento.
		taxa_ErroAntes = this.executaTeste(dados);
		
		//Calcula matriz transposta da Jacobiana
		JacobianaTransposta = Jacobiana.tadMatriz_Transposta();
               
		//Calcula matriz Jacobiana * 2
		JacobianaTranspostax2 = matriz.tadMatriz_MultiplicaConstante(Jacobiana.getC(),Jacobiana.getL(), JacobianaTransposta, 2);
		
		//Calcula matriz Gradiente do erro (Gradiente = JacobianaTransposta X matriz de erros na saida)
		Gradiente = matriz.tadMatriz_Multiplica(Jacobiana.getC(), Jacobiana.getL(), erros.getC(), JacobianaTransposta, erros.getMatriz());
		//Gradiente = tadMatriz_Multiplica(JacobianaTranspostax2, matrizDeErros);
		//Calcula matriz Hessiana (H = JacobianaTransposta x Jacobiana)
		Hessiana = matriz.tadMatriz_Multiplica(Jacobiana.getC(), Jacobiana.getL(), Jacobiana.getC(), JacobianaTransposta, Jacobiana.getMatriz());
		//Hessiana = tadMatriz_Multiplica(JacobianaTranspostax2, Jacobiana);
		
		//Copia Gradiente para matriz B que serah usada na resolucao do sistema linear.
		for (j = 0; j <num_Pesos;j++) {
			for(k = 0;k<this.getNumNeuronioCamada().get(this.getNumCamadas()-1);k++) {
	   			b1[j][k] = Gradiente[j][k];
			}
		}
                B.setMatriz(b1);

   		fimCiclo = false;
   		// Executa aproximacao ate que seja encontrado um erro menor que o obtido no ciclo anterior
   		lambda = 0.000001;
                while ((!fimCiclo) && (lambda < maxLambda)) {
                    
			//Calcula matriz Identidade x Lambda
			identidadexLambda = matriz.tadMatriz_MultiplicaConstante(num_Pesos, num_Pesos,identidade.getMatriz(), lambda);
			//Calcula matriz Hessiana, ja acrescentando o Lambda na diagonal da matriz.
			ajusteHessiana = matriz.tadMatriz_Soma(num_Pesos, num_Pesos,Hessiana, identidadexLambda);
			
			//Faz a decomposicao LU da matriz Hessiana.
			matriz.tadMatriz_DecomposicaoLU(num_Pesos, ajusteHessiana, L, U);
                        //System.out.println("AQUI - " + num_Pesos);
			
			//Calcula o determinante da matriz U.
   			detHessiana = matriz.tadMatriz_Det_U(num_Pesos, U.getMatriz());
 			
   			//Verifica se matriz Hessiana eh singular
                        detHessiana = 2;
   			if (detHessiana > 0.0) {
                             //System.out.println("Err" );
				//Resolve o sistema (Hessiana + Lambda x Identidade) * Solucao = B
				//A matriz solucao eh composta pelos novos pesos.
                                //B.getMatriz()[0] POIS HA SOMENTE UM NEURONIO, NAO SEI COMO SERIA EM OUTROS CASOS
				solucao = matriz.tadMatriz_ResolveDecomposicaoLU(num_Pesos,num_Pesos, L.getMatriz(), U.getMatriz(), B.getMatriz());
				
				
                                //Faz backup dos pesos pois, caso o resultado seja pior que o anterior
				//os pesos gerados sao descartados.
                                redeBackup = this.copiaRede();
				
				//Atualiza pesos com a solucao gerada.
				// IMPORTANTE: Este calculo nao deve ser implementado agora !!!
                                
                                
                                
                                //Atualiza pesos com a solucao gerada.
				//Primeira camada
                                for(i = 0 ; i<this.getNumNeuronioCamada().get(0) ; i++){
                                    for(j = 0; j < this.getNumEntradas(); j++){
                                        this.getCamadas().get(0).getListNeuronios().get(i).getPesos().set(j, this.getCamadas().get(0).getListNeuronios().get(i).getPesos().get(j) - solucao[j]);
                                    }
                                    
                                }
                                //Outras camadas
                                for(c = 1; c<this.getNumCamadas();c++){
                                    for(i = 0 ; i<this.getNumNeuronioCamada().get(c) ; i++){
                                        for(j = 0; j < this.getNumNeuronioCamada().get(c-1); j++){
                                            this.getCamadas().get(c).getListNeuronios().get(i).getPesos().set(j, this.getCamadas().get(c).getListNeuronios().get(i).getPesos().get(j) - solucao[j]);
                                    
                                        }
                                   
                                    }

                                }

				//Refaz teste sobre o conjunto
				 taxa_ErroPosAjuste = this.executaTeste(dados);
                                 //System.out.println("Erro Apos: " + taxa_ErroPosAjuste.getErroMedio() );
					
				
				//Verifica se pesos encontrados sao melhores que anteriores
				if (taxa_ErroAntes.getErroMedio() <= taxa_ErroPosAjuste.getErroMedio()) {
					//Desfaz atualizacao dos pesos.
                                        this.setCamadas(redeBackup.getCamadas());
                                        //System.out.println(this.getCamadas().get(0).getListNeuronios().get(0).getPesos().get(0));
    
                                        bestErro = taxa_ErroAntes;
					//Incrementa Lambda e refaz calculo de pesos
					lambda = lambda * v;
					fimCiclo = false;
                                       // System.out.println("Lambda: " + lambda );
                                        
				}
				else {
                                        bestErro = taxa_ErroPosAjuste;
                                        //System.out.println("Erro Antes: " + taxa_ErroAntes.getErroMedio() );
					
                                        System.out.println("Erro Melhorado: " + bestErro.getErroMedio() );
					//Caso os pesos sao melhores, decrementa lambda (amortece o aprendizado) e inicia proximo ciclo.
					lambda = lambda / v;
					fimCiclo = true;
				}
                               
				
	
	   		}
	   		//se hessiana for singular
	   		else {
	   			fimCiclo = true;
	   		}

	   	
		}
                
                System.out.println("Erro: " + bestErro.getErroMedio() );
	ciclos++;
	}
        
       
        return bestErro;
    }
    
            
        
// Executa o algoritmo de treinamento Backpropagation.
    public Erro executaAprendizadoBackPropagation(DadosNormalizados dados){
        Erro erro=null;

	int c, n, i, j;
	int count = 0;
	double erro_Medio = 0.0;
	boolean bit;
        double erroSaida=0.0;
	int pos=0;
        double entradas[] = new double[dados.getNumAtributos()];
	double varianciaErro = -1.0;
	do {
            c = this.getNumCamadas()-1;
		
		// Submete a rede ao aprendizado.
		erro_Medio = 0.0;
		for (i=0; i<dados.getNumInstancias(); i++) {
			// Pondera entradas pelo peso de cada atributo.
				for (j = 0; j <dados.getNumAtributos(); j++) {
					entradas[j] = dados.getListInstancias().get(i).getListAtributos().get(j).getValor();
				}
			

			// Propaga o sinal pela rede ateh a camada de saida.
			propagaSinal( dados.getListInstancias().get(i));
			
                        ///CALCULA ERROS
                        for(j = 0; j<this.getNumNeuronioCamada().get(c) ; j++){
                            erroSaida = ( dados.getListInstancias().get(i).getEsperado() - this.getCamadas().get(c).getListNeuronios().get(j).getSaida());
                           this.getCamadas().get(c).getListNeuronios().get(j).setErro(erroSaida);
                          //if (i==0)System.out.println("erro:" + erroSaida);
                        }
                        
			// Retropropaga (backpropagation) o erro calculado.
			fncIA_MLP_RetropropagaErro();
			
			// Ajusta os pesos dos neuronios.
			ajustaPesos(entradas);
		}
		
		// Atualiza condicoes de parada.
		erro_Medio = (float) erro_Medio / (this.getNumNeuronioCamada().get(c) * dados.getNumInstancias());
		
		
		count++;
                
                 erro = this.executaTeste(dados);
                 
                // System.out.println("Erro back" + erro.getRaizQuadradaErroMedioQuadratico());
		
		// Verifica condicoes de parada.
                 //System.out.println(this.getCamadas().get(1).getListNeuronios().get(0).getPesos().get(0));
                
                
	} while (count < this.getNumCiclos());
	
 
        

       
        return erro;
}
    
    
   // Retropropaga o erro, ja calculado, pela rede.
void fncIA_MLP_RetropropagaErro() {
	int c, n, e;
	double erro;

	// Calcula o delta da camada de saida.
	c = this.getNumCamadas()-1;
	for(n=0; n<this.getNumNeuronioCamada().get(c); n++) {
		this.getCamadas().get(c).getListNeuronios().get(n).setDelta(this.getCamadas().get(c).getListNeuronios().get(n).getErro() *this.fncIA_MLP_derivadaSigmoide(this.getCamadas().get(c).getListNeuronios().get(n).getSaida()));
                
        }
        		
	for (c=(this.getNumCamadas()-2); c>=0; c--) {
            
		for (n=0; n<this.getNumNeuronioCamada().get(c); n++) {
			erro = 0.0;
			for (e=0; e<this.getNumNeuronioCamada().get(c+1); e++) {
                            
				erro += this.getCamadas().get(c+1).getListNeuronios().get(e).getPesos().get(n) *  this.getCamadas().get(c+1).getListNeuronios().get(e).getDelta();
                        //System.out.println(erro);      
                        }
                        this.getCamadas().get(c).getListNeuronios().get(n).setErro(erro);
                        
                        this.getCamadas().get(c).getListNeuronios().get(n).setDelta(erro * this.fncIA_MLP_derivadaSigmoide(this.getCamadas().get(c).getListNeuronios().get(n).getSaida()));
		
                }
                
	}
}


// Ajusta os pesos dos neuronios.
public void ajustaPesos(double entradas[]) {
	int c, n, e;
	double ajuste;

	// Ajusta os pesos dos neuronios da camada de entrada.
	for (n=0; n<this.getNumNeuronioCamada().get(0); n++) {
		for (e=0; e<this.getNumEntradas(); e++) {
                    
			ajuste = (this.taxaDeAprendizado * this.getCamadas().get(0).getListNeuronios().get(n).getDelta() * entradas[e]);
                        this.getCamadas().get(0).getListNeuronios().get(n).getPesos().set(e, 
                               this.getCamadas().get(0).getListNeuronios().get(n).getPesos().get(e) + ajuste + this.getCamadas().get(0).getListNeuronios().get(n).getDw().get(e) );
			//System.out.println("p"+ajuste);
                        this.getCamadas().get(0).getListNeuronios().get(n).getDw().set(e, ajuste);
                        
                        //System.out.println("t"+this.getCamadas().get(0).getListNeuronios().get(n).getDelta());
		}
		ajuste = (this.getTaxaDeAprendizado() * this.getCamadas().get(0).getListNeuronios().get(n).getDelta());
		//System.out.println("aj"+this.getCamadas().get(0).getListNeuronios().get(n).getDelta());
                this.getCamadas().get(0).getListNeuronios().get(n).setBias(this.getCamadas().get(0).getListNeuronios().get(n).getBias() + ajuste + this.getCamadas().get(0).getListNeuronios().get(n).getdBias() );
                this.getCamadas().get(0).getListNeuronios().get(n).setdBias(ajuste);
	}

	// Ajusta os pesos dos neuronios das demais camadas.
	for (c=1; c<this.getNumCamadas(); c++) {
		// Ajusta os pesos dos neuronios da camada de entrada.
            for (n=0; n<this.getNumNeuronioCamada().get(c); n++) {
                //System.out.println(this.getNumNeuronioCamada().get(c));
                //System.out.println(this.getCamadas().get(c).getListNeuronios().get(n).getPesos().get(2));
              //  exit(1);
                    for (e=0; e<this.getNumNeuronioCamada().get(c-1); e++) {
                            ajuste = (this.taxaDeAprendizado * this.getCamadas().get(c).getListNeuronios().get(n).getDelta() * this.getCamadas().get(c-1).getListNeuronios().get(e).getSaida());
                            this.getCamadas().get(c).getListNeuronios().get(n).getPesos().set(e, 
                                   this.getCamadas().get(c).getListNeuronios().get(n).getPesos().get(e) + ajuste + this.getCamadas().get(c).getListNeuronios().get(n).getDw().get(e) );
                            this.getCamadas().get(c).getListNeuronios().get(n).getDw().set(e, ajuste);
                            //System.out.println("ajuste"+ajuste);
                    }
                    ajuste = (this.getTaxaDeAprendizado() * this.getCamadas().get(c).getListNeuronios().get(n).getDelta());
                    //System.out.println("Ajuste = " +this.getCamadas().get(c).getListNeuronios().get(n).getDelta());
                    this.getCamadas().get(c).getListNeuronios().get(n).setBias(this.getCamadas().get(c).getListNeuronios().get(n).getBias() + ajuste + this.getCamadas().get(c).getListNeuronios().get(n).getdBias() );
                    this.getCamadas().get(c).getListNeuronios().get(n).setdBias(ajuste);
            }
    }
}



    
    public Rede copiaRede(){
        // Rede(int numCamadas,ArrayList<Integer> numNeuronioCamada,int numEntradas,  int numCiclos)
        
        Rede redeCopia = new Rede(this.getNumCamadas(), this.getNumNeuronioCamada(), this.getNumEntradas(), this.getNumCiclos());
        //Inicia rede com pesos aleatórios

        int i, j, l;
        int numeroPesos;
        ArrayList<Double> pesos = new ArrayList<>();
        ArrayList<Neuronio> listNeuronio = new ArrayList<>();
         
        Camada camada;
        Neuronio neuronio;
        ArrayList<Camada> camadas = new ArrayList<>();
        for(i=0;i<this.numCamadas;i++){
            camada = new Camada();
            camada.setNumNeuronios(this.numNeuronioCamada.get(i));
            listNeuronio = new ArrayList<>();
            for(j=0;j<camada.getNumNeuronios();j++){
                
                neuronio = new Neuronio();
                pesos = new ArrayList<>();
                  if(i==0){
                     numeroPesos = this.numEntradas;
                  }
                  else{
                     numeroPesos = this.camadas.get(i-1).getNumNeuronios();
                  }
                  for(l=0;l<numeroPesos;l++){
                       pesos.add(this.getCamadas().get(i).getListNeuronios().get(j).getPesos().get(l));
                      // System.out.println(x);
                  }
                
                neuronio.setPesos(pesos);
                neuronio.setBias(this.getCamadas().get(i).getListNeuronios().get(j).getBias());
                listNeuronio.add(neuronio);
 
            }
                camada.setListNeuronios(listNeuronio); 
                camadas.add(camada);
                redeCopia.setCamadas(camadas);

        }
        return redeCopia;
    }

    public int getNumCamadas() {
        return numCamadas;
    }

    public void setNumCamadas(int numCamadas) {
        this.numCamadas = numCamadas;
    }

    public ArrayList<Camada> getCamadas() {
        return camadas;
    }

    public void setCamadas(ArrayList<Camada> camadas) {
        this.camadas = camadas;
    }

    public ArrayList<Integer> getNumNeuronioCamada() {
        return numNeuronioCamada;
    }

    public void setNumNeuronioCamada(ArrayList<Integer> numNeuronioCamada) {
        this.numNeuronioCamada = numNeuronioCamada;
    }

    public int getNumCiclos() {
        return numCiclos;
    }

    public void setNumCiclos(int numCiclos) {
        this.numCiclos = numCiclos;
    }

    public int getNumEntradas() {
        return numEntradas;
    }

    public void setNumEntradas(int numEntradas) {
        this.numEntradas = numEntradas;
    }   
    
    
// Funcao sigmoide
double fncIA_MLP_sigmoide(double valor) {
	return (1.0 / (1.0 + exp((-1.0) * valor)));
}

// Derivada da funcao sigmoide
double fncIA_MLP_derivadaSigmoide(double valor) {
	return (valor * (1 - valor));
}

public double getTaxaDeAprendizado() {
        return taxaDeAprendizado;
    }

public void setTaxaDeAprendizado(double taxaDeAprendizado) {
        this.taxaDeAprendizado = taxaDeAprendizado;
    }

    
}


