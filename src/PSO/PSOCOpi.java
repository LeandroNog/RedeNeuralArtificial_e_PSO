
package PSO;

public class PSOCOpi {
    /* 
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
 
public class PSO {
    
    ArrayList<Particula> listParticulas = new ArrayList();;
    ArrayList<Particula> listPBestParticulas = new ArrayList();;
    Particula gBest = new Particula(); 
    int numCiclosPSO = 1000;
    double minPeso = -1;
    double maxPeso = 1;
    
    

    int numParticulas = 20;


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
    
    public Particula executaPSO(){
        
        gBest = new Particula();
        listParticulas = new ArrayList();;
        listPBestParticulas = new ArrayList();;
        int NUMREDES = 3;
         int limite = 5; 
         int limiteUns = 80; 
        Random random = new Random();
        double x;
        Particula part;
        int menorTeste = 9999999;
        Particula gBestTeste = new Particula();
      for(int c = 0; c<numCiclosPSO ; c++){
          
      
        for(int i=0;i<numParticulas;i++){
            part = new Particula();
            listParticulas.add(part);
            part = new Particula();
            listPBestParticulas.add(part);
        }
 
        
         double w = 0.1; //eh a ponderacao da inehrcia; 0.4; //
         double c_1 = 0.5 + log(2); //representa o parâmetro cognitivo;
         double c_2 = c_1; //representa o parâmetro social.
         double n=0.0;
            

        ArrayList<Double> pesos = new ArrayList();
        ArrayList<Double> velocidade = new ArrayList();
        // - Inicializar os valores das particulas (i = particula, j = elemento da particula):
        for(int i = 0; i < listParticulas.size(); i++){
           
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
        

         //Carrego os dados
        String pathArquivo1 = "dados1820.txt";
        String pathArquivo2 = "dados1821.txt";
        String pathArquivo3 = "dados1822.txt";
        String pathArquivo4 = "dados1823.txt";
        String pathArquivo5 = "dados1824.txt";
        String pathArquivo6 = "dados1825.txt";
        String pathArquivo7 = "dados1826.txt";
        String pathArquivo8 = "dados1829.txt";
        
        LeituraArquivo leituraArquivo = new LeituraArquivo();
        
        //Carrega dados sem normalizar
        Dados dados1 = leituraArquivo.carregaDados(pathArquivo1);
        Dados dados2 = leituraArquivo.carregaDados(pathArquivo2);
        Dados dados3 = leituraArquivo.carregaDados(pathArquivo3);
        Dados dados4 = leituraArquivo.carregaDados(pathArquivo4);
        Dados dados5 = leituraArquivo.carregaDados(pathArquivo5);
        Dados dados6 = leituraArquivo.carregaDados(pathArquivo6);
        Dados dados7 = leituraArquivo.carregaDados(pathArquivo7);
        Dados teste = leituraArquivo.carregaDados(pathArquivo8);
       
       
        int numErroMaster = 0;
        for(int i = 0;i<listParticulas.size();i++){
            
//atualiza medias de todos dados
            double media = 0.0;
            for(int j=0;j<25;j++){
                media = 0.0;
                
                for(int l = 0; l<NUMREDES ; l++){
                    listParticulas.get(i).getPesoRede();
                    
                        media = media + listParticulas.get(i).getPesoRede().get(l)*
                            dados1.getListInstancias().get(j).getListAtributos().get(l).getValor();
                    
                    /*media = media + listParticulas.get(i).getPesoRede().get(l)*
                            dados1.getListInstancias().get(j).getListAtributos().get(l).getValor();
                }
                //media = media/5;
                Atributo atrib = new Atributo();
                atrib.setValor(media);
                dados1.getListInstancias().get(j).getListAtributos().set(NUMREDES, atrib);
            }
            
            media = 0.0;
            //System.out.println("peso" + listParticulas.get(i).getPesoRede().get(0));
            //exit(1);
            for(int j=0;j<25;j++){
                media = 0.0;
                
                for(int l = 0; l<NUMREDES ; l++){
                    
                        media = media + listParticulas.get(i).getPesoRede().get(l)*
                            dados2.getListInstancias().get(j).getListAtributos().get(l).getValor();
                    
                }
                //media = media/5;
                Atributo atrib = new Atributo();
                atrib.setValor(media);
                dados2.getListInstancias().get(j).getListAtributos().set(NUMREDES, atrib);
            }
            media = 0.0;
            //System.out.println("peso" + listParticulas.get(i).getPesoRede().get(0));
            //exit(1);
            for(int j=0;j<25;j++){
                media = 0.0;
                
                for(int l = 0; l<NUMREDES ; l++){
                  
                    media = media + listParticulas.get(i).getPesoRede().get(l)*
                            dados3.getListInstancias().get(j).getListAtributos().get(l).getValor();
                     
                }
                //media = media/5;
                Atributo atrib = new Atributo();
                atrib.setValor(media);
                
                dados3.getListInstancias().get(j).getListAtributos().set(NUMREDES, atrib);
            }
            
            
                        
//atualiza medias de todos dados
            media = 0.0;
            for(int j=0;j<25;j++){
                media = 0.0;
                
                for(int l = 0; l<NUMREDES ; l++){
                    listParticulas.get(i).getPesoRede();
                    
                        media = media + listParticulas.get(i).getPesoRede().get(l)*
                            dados4.getListInstancias().get(j).getListAtributos().get(l).getValor();
                    
                    /*media = media + listParticulas.get(i).getPesoRede().get(l)*
                            dados1.getListInstancias().get(j).getListAtributos().get(l).getValor();
                }
                //media = media/5;
                Atributo atrib = new Atributo();
                atrib.setValor(media);
                dados4.getListInstancias().get(j).getListAtributos().set(NUMREDES, atrib);
            }
            

            
                        
//atualiza medias de todos dados
            media = 0.0;
            for(int j=0;j<25;j++){
                media = 0.0;
                
                for(int l = 0; l<NUMREDES ; l++){
                    listParticulas.get(i).getPesoRede();
                    
                        media = media + listParticulas.get(i).getPesoRede().get(l)*
                            dados5.getListInstancias().get(j).getListAtributos().get(l).getValor();
                    
                    /*media = media + listParticulas.get(i).getPesoRede().get(l)*
                            dados1.getListInstancias().get(j).getListAtributos().get(l).getValor();
                }
                //media = media/5;
                Atributo atrib = new Atributo();
                atrib.setValor(media);
                dados5.getListInstancias().get(j).getListAtributos().set(NUMREDES, atrib);
            }
            

            
                        
//atualiza medias de todos dados
            media = 0.0;
            for(int j=0;j<25;j++){
                media = 0.0;
                
                for(int l = 0; l<NUMREDES ; l++){
                    listParticulas.get(i).getPesoRede();
                    
                        media = media + listParticulas.get(i).getPesoRede().get(l)*
                            dados6.getListInstancias().get(j).getListAtributos().get(l).getValor();
                    
                    /*media = media + listParticulas.get(i).getPesoRede().get(l)*
                            dados1.getListInstancias().get(j).getListAtributos().get(l).getValor();
                }
                //media = media/5;
                Atributo atrib = new Atributo();
                atrib.setValor(media);
                dados6.getListInstancias().get(j).getListAtributos().set(NUMREDES, atrib);
            }
            
  
//atualiza medias de todos dados
            media = 0.0;
            for(int j=0;j<25;j++){
                media = 0.0;
                
                for(int l = 0; l<NUMREDES ; l++){
                    listParticulas.get(i).getPesoRede();
                    
                        media = media + listParticulas.get(i).getPesoRede().get(l)*
                            dados7.getListInstancias().get(j).getListAtributos().get(l).getValor();
                    
                    /*media = media + listParticulas.get(i).getPesoRede().get(l)*
                            dados1.getListInstancias().get(j).getListAtributos().get(l).getValor();
                }
                //media = media/5;
                Atributo atrib = new Atributo();
                atrib.setValor(media);
                dados7.getListInstancias().get(j).getListAtributos().set(NUMREDES, atrib);
            }

            media = 0.0;
            for(int j=0;j<25;j++){
                media = 0.0;
                
                for(int l = 0; l<NUMREDES ; l++){
                    listParticulas.get(i).getPesoRede();
                    
                        media = media + listParticulas.get(i).getPesoRede().get(l)*
                            teste.getListInstancias().get(j).getListAtributos().get(l).getValor();
                    
                    /*media = media + listParticulas.get(i).getPesoRede().get(l)*
                            dados1.getListInstancias().get(j).getListAtributos().get(l).getValor();
                }
                //media = media/5;
                Atributo atrib = new Atributo();
                atrib.setValor(media);
                teste.getListInstancias().get(j).getListAtributos().set(NUMREDES, atrib);
            }

          
           // dados1.imprimeDataConsole();
            // Sorting
              //ordena
            Collections.sort(dados1.getListInstancias(), new Comparator<Instancia>() {
                    @Override
                    public int compare(Instancia c1, Instancia c2) {
                        return Double.compare(c1.getListAtributos().get(NUMREDES).getValor(),c2.getListAtributos().get(NUMREDES).getValor());
                    }
                });

            Collections.sort(dados2.getListInstancias(), new Comparator<Instancia>() {
                    @Override
                    public int compare(Instancia c1, Instancia c2) {
                        return Double.compare(c1.getListAtributos().get(NUMREDES).getValor(),c2.getListAtributos().get(NUMREDES).getValor());
                    }
                });
            
               Collections.sort(dados3.getListInstancias(), new Comparator<Instancia>() {
                    @Override
                    public int compare(Instancia c1, Instancia c2) {
                        return Double.compare(c1.getListAtributos().get(NUMREDES).getValor(),c2.getListAtributos().get(NUMREDES).getValor());
                    }
                });
                   Collections.sort(dados4.getListInstancias(), new Comparator<Instancia>() {
                    @Override
                    public int compare(Instancia c1, Instancia c2) {
                        return Double.compare(c1.getListAtributos().get(NUMREDES).getValor(),c2.getListAtributos().get(NUMREDES).getValor());
                    }
                });
                    Collections.sort(dados5.getListInstancias(), new Comparator<Instancia>() {
                    @Override
                    public int compare(Instancia c1, Instancia c2) {
                        return Double.compare(c1.getListAtributos().get(NUMREDES).getValor(),c2.getListAtributos().get(NUMREDES).getValor());
                    }
                });
                Collections.sort(dados6.getListInstancias(), new Comparator<Instancia>() {
                    @Override
                    public int compare(Instancia c1, Instancia c2) {
                        return Double.compare(c1.getListAtributos().get(NUMREDES).getValor(),c2.getListAtributos().get(NUMREDES).getValor());
                    }
                });
                
                Collections.sort(dados7.getListInstancias(), new Comparator<Instancia>() {
                    @Override
                    public int compare(Instancia c1, Instancia c2) {
                        return Double.compare(c1.getListAtributos().get(NUMREDES).getValor(),c2.getListAtributos().get(NUMREDES).getValor());
                    }
                });
                Collections.sort(teste.getListInstancias(), new Comparator<Instancia>() {
                    @Override
                    public int compare(Instancia c1, Instancia c2) {
                        return Double.compare(c1.getListAtributos().get(NUMREDES).getValor(),c2.getListAtributos().get(NUMREDES).getValor());
                    }
                });
               
             int erroC = 0, erroB = 0;
            
            //Avalia quantos 1 tem ate o limite
            int numErrod1 = 0;
            int numErrod2 = 0;
            int numErrod3 = 0;
            int numErrod4 = 0;
            int numErrod5 = 0;
            int numErrod6 = 0;
            int numErrod7 = 0;
            int numErrod8 = 0;
            numErroMaster = 0;
            for(int m = 30; m > 10; m--){
                if(dados1.getListInstancias().get(m).getEsperado()!=1){
                    //numErrod1 = numErrod1 + (100-m);
                    numErrod1++;
                    erroC++;
                }
            }
            if(numErrod1>14){
                numErroMaster++;
            }
             for(int m = 30; m > 10; m--){
                if(dados2.getListInstancias().get(m).getEsperado()!=1){
                   //numErrod2 = numErrod2 + (100-m);
                   numErrod2++;
                    erroC++;
                }
            }
              if(numErrod2>14){
                numErroMaster++;
            }
            for(int m = 30; m > 10; m--){
                if(dados3.getListInstancias().get(m).getEsperado()!=1){
                   numErrod3++;
                    erroC++;
                }
            }
            if(numErrod3>14){
                numErroMaster++;
            }
             for(int m = 30; m > 10; m--){
                if(dados4.getListInstancias().get(m).getEsperado()!=1){
                   numErrod4++;
                    erroC++;
                }
            }
             if(numErrod4>14){
                numErroMaster++;
            }
                for(int m = 30; m > 10; m--){
                if(dados5.getListInstancias().get(m).getEsperado()!=1){
                   numErrod5++;
                    erroC++;
                }
            }
               if(numErrod5>14){
                numErroMaster++;
            }
               for(int m = 30; m > 10; m--){
                if(dados6.getListInstancias().get(m).getEsperado()!=1){
                   numErrod6++;
                    erroC++;
                }
            }
           if(numErrod6>14){
                numErroMaster++;
            }
               for(int m = 30; m > 10; m--){
                if(dados7.getListInstancias().get(m).getEsperado()!=1){
                   numErrod7++;
                    erroC++;
                }
            }
            if(numErrod7>14){
                numErroMaster++;
            }
           for(int m = 30; m > 10; m--){
                if(teste.getListInstancias().get(m).getEsperado()!=1){
                    numErrod8++;
                   //numErrod2++;
                    
                }
            }
          
           
           
           
            /*listParticulas.get(i).setFun(numErrod1 + numErrod2 +numErrod3 +numErrod4+
           numErrod5 + numErrod6+ numErrod7);
            listParticulas.get(i).setFun(numErroMaster);
            if(listParticulas.get(i).getFun() < listParticulas.get(i).getBestFun()){
                listParticulas.get(i).setBestFun(listParticulas.get(i).getFun());
                listPBestParticulas.set(i,listParticulas.get(i).copiaParticula()); 
            }
            if(listParticulas.get(i).getFun() < gBest.getBestFun() && ((c==0) ||numErrod8<14)){
                System.out.println("GBest atualizado, de " + gBest.getBestFun()+ "para"+ listParticulas.get(i).getFun());
                
                gBest = listParticulas.get(i).copiaParticula();
                System.out.println("Atualizado");
                System.out.println("erroB:" + erroB);
                System.out.println("Erro no teste:" + numErrod8);

                for(int mm = 0; mm <NUMREDES;mm++){
                    
                     System.out.println(gBest.getPesoRede().get(mm));
                     
                
                }
               if(numErrod8<menorTeste){
                   gBestTeste = listParticulas.get(i).copiaParticula();
                   menorTeste = numErrod8;
               }
                
            }

               ArrayList<Double> newVelocidades = new ArrayList();
               ArrayList<Double> newPesos= new ArrayList();
                Double aleatorio1 = random.nextDouble();
                Double aleatorio2 = random.nextDouble();
     
            //Atualiza velocidade
            for(int l = 0; l<NUMREDES;l++ ){
                
                newVelocidades = new ArrayList();
              
               //System.out.println(gBest.getNumNeuroniosCamada().get(j)  ) ;
              listPBestParticulas.get(i).getPesoRede().get(0);
              
                newVelocidades.add(((w*listParticulas.get(i).getVelocidade().get(l)) 
                + (c_1 * aleatorio1) * listPBestParticulas.get(i).getPesoRede().get(l) 
                        - listParticulas.get(i).getPesoRede().get(l) 
                        
                        + c_2 * aleatorio2 * (gBest.getPesoRede().get(l) 
                        - listParticulas.get(i).getPesoRede().get(l))));
            }
                
                for(int l = 0; l<NUMREDES;l++ ){
                    listParticulas.get(i);
                    Double nn =(listParticulas.get(i).getPesoRede().get(l) +listParticulas.get(i).getVelocidade().get(l));
                    if(nn<this.minPeso){
                        nn = Double.valueOf(this.minPeso);
                    }
                    if(nn>maxPeso){
                        nn = Double.valueOf(maxPeso);
                    }
                   
                     newPesos.add(nn);
                    
                }
                   
                listParticulas.get(i).setPesoRede(newPesos);
                
            

                
            }
            
         }
        
         System.out.println("Melhor no teste");
         for(int mm = 0; mm <NUMREDES;mm++){
                    
           System.out.println(gBestTeste.getPesoRede().get(mm));
                     
                
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
    
    

 
    
}*/
}
