
package PSO;

import Dados.Atributo;
import Dados.Dados;
import Dados.Instancia;
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
import java.util.Comparator;
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
    int numCiclosPSO = 10;
    double minPeso=0;
    double maxPeso=10000;

    int numParticulas = 10;


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
    
    public Particula executaPSO(DadosNormalizados dadosNormalizadoTrei,DadosNormalizados dadosNormalizadoValid,DadosNormalizados dadosNormalizadoTest){
        
        gBest = new Particula();
        listParticulas = new ArrayList();;
        listPBestParticulas = new ArrayList();;
        
        Random random = new Random();
        double x;
        Particula part;
        int numCiclosRede=100000;
      
        for(int i=0;i<numParticulas;i++){
            part = new Particula();
            listParticulas.add(part);
            part = new Particula();
            listPBestParticulas.add(part);
        }
 
        
         double w = 0.4; //eh a ponderacao da inehrcia; 0.4; //
         double c_1 = 0.5 + log(2); //representa o parâmetro cognitivo;
         double c_2 = c_1; //representa o parâmetro social.
         double n=0.0;
         int NUMREDES = 5;
         
         //-------------- ok

        ArrayList<Double> pesos = new ArrayList();
        ArrayList<Double> velocidade = new ArrayList();
        // - Inicializar os valores das particulas (i = particula, j = elemento da particula):
        for(int i = 0; i < numParticulas; i++){
            pesos = new ArrayList();
            velocidade = new ArrayList();
            for(int j = 0; j < NUMREDES; j++){
                x =  random.nextDouble(); 
                n=((this.getMinPeso()) + ((this.getMaxPeso()-this.getMinPeso()) * x));
            if(n<this.getMinPeso()){
                     n = (this.getMinPeso());
                 }
                 if(n>this.getMaxPeso()){
                     n = (this.getMaxPeso());
                 }
                 pesos.add(n);
            }   
              for(int j = 0; j < NUMREDES; j++){
                x = random.nextDouble(); 
                velocidade.add((this.getMinPeso()+ (this.getMaxPeso()-this.getMinPeso()) * x));
            }
              //era da ultima camada
         /// numNeuronios.add(1);
          
          //.out.println(numNeuronios);
          listParticulas.get(i).setPesoRede(pesos);
          listParticulas.get(i).setVelocidade(velocidade);
          
          listPBestParticulas.set(i,listParticulas.get(i).copiaParticula()); //Funciona como copia?
        }
        

        Erro erroTrei = new Erro();
        Erro erroValid = new Erro();
        Erro erroTest = new Erro();
        
         //Carrego os dados
        String pathArquivo1 = "dados1819.txt";
        String pathArquivo2 = "dados1820.txt";
        LeituraArquivo leituraArquivo = new LeituraArquivo();
        
        //Carrega dados sem normalizar
        Dados dados1 = leituraArquivo.carregaDados(pathArquivo1);
        Dados dados2 = leituraArquivo.carregaDados(pathArquivo2);
        dados1.imprimeDataConsole();
        dados2.imprimeDataConsole();
       //exit(1);
       
        for(int i = 0;i<listParticulas.size();i++){
            /*redePart = new Rede(numCamadas,listParticulas.get(i).getNumNeuroniosCamada(),dadosNormalizadoTrei.getNumAtributos(),  numCiclosRede);
            redePart.inicializaRede();
            System.out.println("Particula: " + i + " Numneuro: " + redePart.getNumNeuronioCamada());
            erroTrei = redePart.executaAprendizadoBackPropagation(dadosNormalizadoTrei);
            erroValid = redePart.executaTeste(dadosNormalizadoValid);*/
            int acertosArq = 0;
            int acertosAr2 = 0;
            int limite = 70;
            
            //Calcula média dado1 separado de dado2
            double media = 0.0;
            for(int j=0;j<100;j++){
                for(int l = 0; l<5 ; l++){
                    media = media + listParticulas.get(i).getPesoRede().get(l)*
                            dados1.getListInstancias().get(j).getListAtributos().get(l).getValor();
                }
                //media = media/5;
                Atributo atrib = new Atributo();
                atrib.setValor(media);
                dados1.getListInstancias().get(j).getListAtributos().set(5, atrib);
            }
            
            media = 0.0;
            for(int j=0;j<100;j++){
                for(int l = 0; l<5 ; l++){
                    media = media + listParticulas.get(i).getPesoRede().get(l)*
                            dados2.getListInstancias().get(j).getListAtributos().get(l).getValor();
                }
                //media = media/5;
                Atributo atrib = new Atributo();
                atrib.setValor(media);
                dados2.getListInstancias().get(j).getListAtributos().set(5, atrib);
            }
            
            double mediaFun = 0.0;
            
            // Sorting
              //ordena
            Collections.sort(dados1.getListInstancias(), new Comparator<Instancia>() {
                    @Override
                    public int compare(Instancia c1, Instancia c2) {
                        return Double.compare(c1.getListAtributos().get(5).getValor(),c2.getListAtributos().get(5).getValor());
                    }
                });
            
            //PRINT NUMEROS
          /*
            
            
            //Avalia
       
        
       

            
                
            //Avalia formula
            
            
            listParticulas.get(i).setFun();
            listParticulas.get(i).setBestFun();
            
            listPBestParticulas.set(i, listParticulas.get(i).copiaParticula());
            
            if(erroTrei.getErroMedio() < gBest.getBestFun()){
                gBest = listParticulas.get(i).copiaParticula();
                gBest.setBestFun(listPBestParticulas.get(i).getBestFun());
                System.out.println("GBest atualizado!: " +gBest.getBestFun());
                System.out.println("\t NumNeuronios Primeira Camada:" + gBest.getNumNeuroniosCamada().get(0));
                System.out.println("\t NumNeuronios Segunda Camada:" + gBest.getNumNeuroniosCamada().get(1));
                bestRede = redePart.copiaRede();
            }
            else{
                System.out.println("Nao eh melhor: Erro particula: " + erroTrei.getErroMedio() + "Gbest: " + gBest.getBestFun());
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
            redePart = new Rede(numCamadas,listParticulas.get(i).getNumNeuroniosCamada(),dadosNormalizadoTrei.getNumAtributos(),  numCiclosRede);
            redePart.inicializaRede();
            System.out.println("Particula: " + i + " Numneuro: " + redePart.getNumNeuronioCamada());
            
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
            else{
                System.out.println("Nao eh melhor: Erro particula: " + erroTrei.getErroMedio() + "Gbest: " + gBest.getBestFun());
            }
        }
         
      
*/
        }
        
        return gBest;
    }

    public ArrayList<Particula> getListPBestParticulas() {
        return listPBestParticulas;
    }

    public void setListPBestParticulas(ArrayList<Particula> listPBestParticulas) {
        this.listPBestParticulas = listPBestParticulas;
    }

    public Particula getgBest() {
        return gBest;
    }

    public void setgBest(Particula gBest) {
        this.gBest = gBest;
    }

    public double getMinPeso() {
        return minPeso;
    }

    public void setMinPeso(double minPeso) {
        this.minPeso = minPeso;
    }

    public double getMaxPeso() {
        return maxPeso;
    }

    public void setMaxPeso(double maxPeso) {
        this.maxPeso = maxPeso;
    }

    public int getNumParticulas() {
        return numParticulas;
    }

    public void setNumParticulas(int numParticulas) {
        this.numParticulas = numParticulas;
    }
    
    

 
    
}