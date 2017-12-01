
package PSO;

import Dados.Dados;
import DadosNormalizados.DadosNormalizados;
import Erros.Erro;
import RedeNeural.Rede;
import file.LeituraArquivo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.log;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import preProcessameto.PreProcessaDados;

/**
 *
 * @author leandro
 */
public class PSO {
    
    ArrayList<Particula> listParticulas = new ArrayList();;
    ArrayList<Particula> listPBestParticulas = new ArrayList();;
    Particula gBest = new Particula(); 
    int numCiclosPSO = 2;
    int minNumNeuronio=2;
    int maxNumNeuronio=15;
    int minTaxaAprendizado;
    int maxTaxaAprendizado;
    int minNumCamadas=3;
    int maxNumCamadas=3;
    int numParticulas = 10;
    
    Rede bestRede;

    public ArrayList<Particula> getListParticulas() {
        return listParticulas;
    }

    public void setListParticulas(ArrayList<Particula> listParticulas) {
        this.listParticulas = listParticulas;
    }

    public int getNumCiclosPSO() {
        return numCiclosPSO;
    }

    public void setNumCiclosPSO(int numCilos) {
        this.numCiclosPSO = numCilos;
    }
    
    public Rede executaPSO(DadosNormalizados dadosNormalizadoTrei,DadosNormalizados dadosNormalizadoValid,DadosNormalizados dadosNormalizadoTest, int num){
        System.out.println("PSO execução número: " + String.valueOf(num));
        Random random = new Random();
        double x;
        Particula part;
        int numCiclosRede=10;
      
        for(int i=0;i<numParticulas;i++){
            part = new Particula();
            listParticulas.add(part);
            part = new Particula();
            listPBestParticulas.add(part);
        }
        
        Rede redePart;
        
        
         double w = 1/(2*log(2)); //eh a ponderacao da inehrcia;
         double c_1 = 0.5 + log(2); //representa o parâmetro cognitivo;
         double c_2 = c_1; //representa o parâmetro social.
        
        
        //CRIAR EXAME....EXAME = NEW EXAME
        
        // ===============
        // Passo 1: Iniciar as variáveis do enxame de particulas. Onde: - ok
        // ===============
        // - Definir os elementos de composicao de cada particula (X). Exemplos:
        //	- número de camadas escondidas;
        //	- número de neurônios na camada escondida;
        //	- funcao de ativacao {sigmoide, degrau, base radial, seno, base triangular};
        //	- pesos dos neuronios;
        //	- bias dos neuronios.
        //
        // - Definir os limites de cada elemento de composicao da particula. Ex.: -ok
        //	- numeros minimo e maximo de camadas escondidas;
        //	- numeros minimo e maximo de neuronios em cada camada;
        //	- dimensão máxima das particulas;
        //
        int numCamadas = 3;
        ArrayList<Integer> numNeuronios = new ArrayList();
        ArrayList<Double> velocidade = new ArrayList();
        // - Inicializar os valores das particulas (i = particula, j = elemento da particula):
        for(int i = 0; i < listParticulas.size(); i++){
            numNeuronios = new ArrayList();
            velocidade = new ArrayList();
            for(int j = 0; j < numCamadas-1; j++){
                x =  random.nextDouble(); 
                numNeuronios.add((this.getMinNumNeuronio()) + (int)((this.getMaxNumNeuronio()-this.getMinNumNeuronio()) * x));
            
            }   
              for(int j = 0; j < numCamadas-1; j++){
                x = random.nextDouble(); 
                velocidade.add((this.getMinNumNeuronio() + (this.getMaxNumNeuronio()-this.getMinNumNeuronio()) * x));
            }
          numNeuronios.add(1);
          
          //.out.println(numNeuronios);
          listParticulas.get(i).setNumNeuroniosCamada(numNeuronios);
          listParticulas.get(i).setVelocidade(velocidade);
          
          listPBestParticulas.set(i,listParticulas.get(i).copiaParticula()); //Funciona como copia?
        }
        

        Erro erroTrei = new Erro();
        Erro erroValid = new Erro();
        Erro erroTest = new Erro();
        //X[i][j] = j_min + ((j_max - j_min) * <sorteio de um valor aleatorio>) para toda particula;
        //
        // - Inicializar a velocidade da particula:
        //	- V[i][j] = ((j_max - j_min) * <sorteio de um valor aleatorio>) - X[i][j], para toda particula;
        //
        // - Definir outras variavies:
        //	- Pbest[i] (inicialmente 0) vetor que representa a melhor localizacao da particula i; -ok
        //	- Pbestfun[i] (inicialmente 0) representa o valor da funcao objetivo da particula i; -
        //	- Gbest (inicialmente 0) vetor que representa a melhor localizacao da melhor particula;
        //	- Gbestfun (inicialmente 0) representa o valor da funcao objetivo da melhor particula;
        //	- N eh o tamanho do enxame (numero de particulas);
        //	- w = 1/(2*log(2)) eh a ponderacao da inehrcia;
        //	- c_1 = 0.5 + log(2) representa o parâmetro cognitivo;
        //	- c_2 = c_1 representa o parâmetro social. ----- ok
        //
        // ===============
        // Passo 2: Calcular o valor da funcao-objetivo f(X) para todas as particulas.
        // ===============
        //	- O vetor Pbest[i] recebe a posicao atual de cada particula;
        //	- O vetor Pbestfun[i] recebe o valor da funcao-objetivo da particula;
        //INICIALIZANDO...
        for(int i = 0;i<listParticulas.size();i++){
            redePart = new Rede(3,listParticulas.get(i).getNumNeuroniosCamada(),dadosNormalizadoTrei.getNumAtributos(),  numCiclosRede);
            redePart.inicializaRede();
            
            erroTrei = redePart.executaAprendizadoBackPropagation(dadosNormalizadoTrei);
            erroValid = redePart.executaTeste(dadosNormalizadoValid);
            listParticulas.get(i).setFun(erroTrei.getErroMedio());
            listParticulas.get(i).setBestFun(erroTrei.getErroMedio());
            
            listPBestParticulas.set(i, listParticulas.get(i).copiaParticula());
            
            if(erroTrei.getErroMedio() < gBest.getBestFun()){
                gBest = listParticulas.get(i).copiaParticula();
                gBest.setBestFun(listPBestParticulas.get(i).getBestFun());
                System.out.println("GBest atualizado!: " +gBest.getBestFun());
                System.out.println("\t NumNeuronios Primeira Camada:" + gBest.getNumNeuroniosCamada().get(0));
                System.out.println("\t NumNeuronios Segunda Camada:" + gBest.getNumNeuroniosCamada().get(1));
                bestRede = redePart.copiaRede();
            }
        }
        
        for(int l = 0; l<numCiclosPSO ; l++){
            
        
        ArrayList newNumNerouniosCamadas = new ArrayList();
        ArrayList<Double> newVelocidades = new ArrayList();
        Double aleatorio1 = random.nextDouble();
        Double aleatorio2 = random.nextDouble();
        
        //Cria novos numeros
         for(int i = 0;i<listParticulas.size();i++){

             
            //Atualiza velocidade
            for(int j = 0; j<numCamadas-1;j++ ){
                newNumNerouniosCamadas =new ArrayList();
                newVelocidades = new ArrayList();
              
               //System.out.println(gBest.getNumNeuroniosCamada().get(j)  ) ;
              
                newVelocidades.add(((w*listParticulas.get(i).getVelocidade().get(j)) 
                + (c_1 * aleatorio1) * listPBestParticulas.get(i).getNumNeuroniosCamada().get(j) 
                        - listParticulas.get(i).getNumNeuroniosCamada().get(j) 
                        
                        + c_2 * aleatorio2 * (gBest.getNumNeuroniosCamada().get(j) 
                        - listParticulas.get(i).getNumNeuroniosCamada().get(j))));
                
                
                
                
            }
                 for(int j = 0; j<numCamadas-1;j++ ){
                 listParticulas.get(i);
                 Double nn =(listParticulas.get(i).getNumNeuroniosCamada().get(j) +listParticulas.get(i).getVelocidade().get(j));
                 if(nn<this.getMinNumCamadas()){
                     nn = Double.valueOf(this.getMinNumCamadas());
                 }
                 if(nn>this.getMaxNumNeuronio()){
                     nn = Double.valueOf(this.getMaxNumCamadas());
                 }
                 newNumNerouniosCamadas.add(nn.intValue());
             }
                 newNumNerouniosCamadas.add(1);
             listParticulas.get(i).setNumNeuroniosCamada(newNumNerouniosCamadas);

         }
         
         
         for(int i = 0;i<listParticulas.size();i++){
            redePart = new Rede(3,listParticulas.get(i).getNumNeuroniosCamada(),dadosNormalizadoTrei.getNumAtributos(),  numCiclosRede);
            redePart.inicializaRede();
            erroTrei = redePart.executaAprendizadoBackPropagation(dadosNormalizadoTrei);
            erroValid = redePart.executaTeste(dadosNormalizadoValid);
            //Executar teste com conjunto de validacao aqui
            
            listParticulas.get(i).setFun(erroTrei.getErroMedio());
            if(listParticulas.get(i).getFun() < listParticulas.get(i).getBestFun()){
                listParticulas.get(i).setBestFun(listParticulas.get(i).getFun() );
            }
            
           
            if(listParticulas.get(i).getFun() < gBest.getBestFun()){
                gBest = listParticulas.get(i).copiaParticula();
                System.out.println("GBest atualziado!: " +gBest.getBestFun());
                System.out.println("\t NumNeuronios Primeira Camada:" + gBest.getNumNeuroniosCamada().get(0));
                System.out.println("\t NumNeuronios Segunda Camada:" + gBest.getNumNeuroniosCamada().get(1));
                bestRede = redePart.copiaRede();
            }
        }
         
      

        }
        
        return bestRede;
    }

    public int getMinNumNeuronio() {
        return minNumNeuronio;
    }

    public void setMinNumNeuronio(int minNumNeuronio) {
        this.minNumNeuronio = minNumNeuronio;
    }

    public int getMaxNumNeuronio() {
        return maxNumNeuronio;
    }

    public void setMaxNumNeuronio(int maxNumNeuronio) {
        this.maxNumNeuronio = maxNumNeuronio;
    }

    public int getMinTaxaAprendizado() {
        return minTaxaAprendizado;
    }

    public void setMinTaxaAprendizado(int minTaxaAprendizado) {
        this.minTaxaAprendizado = minTaxaAprendizado;
    }

    public int getMaxTaxaAprendizado() {
        return maxTaxaAprendizado;
    }

    public void setMaxTaxaAprendizado(int maxTaxaAprendizado) {
        this.maxTaxaAprendizado = maxTaxaAprendizado;
    }

    public int getMinNumCamadas() {
        return minNumCamadas;
    }

    public void setMinNumCamadas(int minNumCamadas) {
        this.minNumCamadas = minNumCamadas;
    }

    public int getMaxNumCamadas() {
        return maxNumCamadas;
    }

    public void setMaxNumCamadas(int maxNumCamadas) {
        this.maxNumCamadas = maxNumCamadas;
    }
    
    void salvaRede(Rede rede){
        
    }
  
    
    
    
    
}
