/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ValidacaoCruzada;

import Dados.Dados;
import DadosNormalizados.AtributoNormalizado;
import DadosNormalizados.DadosNormalizados;
import DadosNormalizados.InstanciaNormalizada;
import Erros.Erro;
import PSO.PSO;
import PSO.Particula;
import RedeNeural.Rede;
import file.GravaArquivo;
import java.util.Collections;
import file.LeituraArquivo;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import preProcessameto.PreProcessaDados;

/**
 *
 * @author leandro
 */
public class ValidacaoCruzada {
   
    public static void main(String[] args){

        int i, k=1;
       
        //Carrego os dados
        String pathArquivo = "hora100.txt";
        LeituraArquivo leituraArquivo = new LeituraArquivo();
        
        //Carrega dados sem normalizar
        Dados dados = leituraArquivo.carregaDados(pathArquivo);
        //dados.imprimeDataConsole();
       //exit(1);
        
        PreProcessaDados preProcessaDados = new PreProcessaDados();
        DadosNormalizados dadosNormalizado = preProcessaDados.normalizaDados(dados);
        //dadosNormalizado.imprimeDataConsole();

        //Embaralha instâncias aleatoriamente
        Collections.shuffle(dadosNormalizado.getListInstancias());
        
       // dadosNormalizado.imprimeDataConsole();
       
        //Separa arquivos de teste e treinamento
        GravaArquivo grava = new GravaArquivo();
        grava.gravaTesteTreinamento(dadosNormalizado);
        
        ValidacaoCruzada v = new ValidacaoCruzada();
        //Separar em memoria principal, teste e treinamento
        ArrayList <DadosNormalizados> testeTreinamento = v.separaTesteTreinamento(dadosNormalizado);
        
        //Retorna uma lista com 10 grupos - Treinamento esta no testeTreinamento[1]
        //ArrayList <DadosNormalizados> gruposValidacaoCruzada = v.separaTreinamento(testeTreinamento.get(1), k);
        
        //Imprime grupo da validação cruzada
        // gruposValidacaoCruzada.get(9).imprimeDataConsole();
        
        
        
        ArrayList<Integer> numNeuronioCamada=new ArrayList<>();
        numNeuronioCamada.add(8);
        numNeuronioCamada.add(4);
        numNeuronioCamada.add(1);
        //Rede rede = new Rede(3,numNeuronioCamada,4, 10000);
        //rede.inicializaRede();
        Erro er;
        Erro valid;
        Erro test;
       // Rede bestRede = rede.copiaRede();
        Erro bestTeste;
        Erro bestValid = null, bestTrein = null;
        k = 1;
       //rede.executaAprendizadoBackPropagation(v.separaTreinamentoGrupo(dadosNormalizado, 0).get(1));
       
       
       
        PSO pso = new PSO();
        Particula p = pso.executaPSO();
        System.out.println("Menor erro:" + p.getBestFun());
        

      
        
        
        
        
        
        
        
        
        /*exit(1);
        //rede = new Rede(3,numNeuronioCamada,4, 1000);
        //er = rede.executaAlgoritmodeAprendizado(v.separaTreinamentoGrupo(dadosNormalizado, 0).get(1));
        ArrayList<DadosNormalizados> treinTeste = new ArrayList<DadosNormalizados>();
        ArrayList<DadosNormalizados> treinValid = new ArrayList<DadosNormalizados>();
        treinTeste = v.separaTesteTreinamento(dadosNormalizado);
        //er = rede.executaAprendizadoBackPropagation(v.separaTreinamentoGrupo(treinTeste.get(1), 0).get(1));
        er = rede.executaAlgoritmodeAprendizado(v.separaTreinamentoGrupo(treinTeste.get(1), 0).get(1));
        valid = rede.executaTeste(v.separaTreinamentoGrupo(treinTeste.get(1), 0).get(0));
        test = rede.executaTeste(treinTeste.get(0));
        bestTrein = er;
        bestValid = valid;
        bestTeste = test;
        System.out.println("Erro médio no teste: " + test.getErroMedio()+ "trein:" + er.getErroMedio());
        if(bestTeste.getErroMedio()> test.getErroMedio()){
               bestTeste = test;
               bestValid = valid;
               bestTrein = er;
               bestRede = rede;
        }
        
       
        
        for (i = 1; i<k;i++){ 
            rede = new Rede(3,numNeuronioCamada,4, 1000);
            rede.inicializaRede();
            //er = rede.executaAprendizadoBackPropagation(v.separaTreinamentoGrupo(treinTeste.get(1), i).get(1));
            er = rede.executaAlgoritmodeAprendizado(v.separaTreinamentoGrupo(treinTeste.get(1), i).get(1));
        
            valid = rede.executaTeste(v.separaTreinamentoGrupo(treinTeste.get(1), i).get(0));
            test = rede.executaTeste(treinTeste.get(0));
        
            if(bestTeste.getErroMedio()> test.getErroMedio()){
                   bestValid = valid;
                   bestTrein = er;
                   bestRede = rede;
            }

            
        }
        
        System.out.println("BestTrein" + String.valueOf(bestTrein.getErroMedio()) +"BestValid" + String.valueOf(bestValid.getErroMedio()) +"BestTeste" + String.valueOf(bestTeste.getErroMedio()) );

        */
    }
    
    
    
     //Separa dados em Treinamento e Teste
    public ArrayList<DadosNormalizados> separaTesteTreinamento(DadosNormalizados dadosNormalizado){
       ArrayList<DadosNormalizados> listDados = new ArrayList<DadosNormalizados>();
       int i, j;
       
       DadosNormalizados dadosNorm;
       InstanciaNormalizada instNorm;
       AtributoNormalizado atribNorm;
        
       
       //Separa conjunto de teste
        dadosNorm = new DadosNormalizados();
        dadosNorm.setNumInstancias((dadosNormalizado.getNumInstancias())/10);
        dadosNorm.setNumAtributos(dadosNormalizado.getNumAtributos());
        
        for (i=0; i<(dadosNormalizado.getNumInstancias())/10; i++) {
           instNorm = new InstanciaNormalizada();
           instNorm.setNome(dadosNormalizado.getListInstancias().get(i).getNome());
           instNorm.setEsperado(dadosNormalizado.getListInstancias().get(i).getEsperado());
           
                  for( j = 0; j < dadosNormalizado.getNumAtributos(); j++){
                        atribNorm = new AtributoNormalizado();
                        atribNorm.setValor(dadosNormalizado.getListInstancias().get(i).getListAtributos().get(j).getValor());
                        instNorm.getListAtributos().add(atribNorm);
                  }
              dadosNorm.getListInstancias().add(instNorm);
            }
        
        listDados.add(dadosNorm);
        
        
        
        //Separa conjunto de treinamento
        dadosNorm = new DadosNormalizados();
        dadosNorm.setNumInstancias(dadosNormalizado.getNumInstancias() - (dadosNormalizado.getNumInstancias())/10);
        dadosNorm.setNumAtributos(dadosNormalizado.getNumAtributos());
        
        for (i=dadosNormalizado.getNumInstancias()/10; i<(dadosNormalizado.getNumInstancias()); i++) {
           instNorm = new InstanciaNormalizada();
           instNorm.setNome(dadosNormalizado.getListInstancias().get(i).getNome());
           instNorm.setEsperado(dadosNormalizado.getListInstancias().get(i).getEsperado());
                  for( j = 0; j < dadosNormalizado.getNumAtributos(); j++){
                        atribNorm = new AtributoNormalizado();
                        atribNorm.setValor(dadosNormalizado.getListInstancias().get(i).getListAtributos().get(j).getValor());
                        instNorm.getListAtributos().add(atribNorm);
                  }
              dadosNorm.getListInstancias().add(instNorm);
            }
        
        listDados.add(dadosNorm);
        

        
        return listDados;

    }
    
  
   
    //Separa dados em k grupos
    public ArrayList<DadosNormalizados> separaTreinamento(DadosNormalizados dados, int k){
       ArrayList<DadosNormalizados> listDados = new ArrayList<DadosNormalizados>();
        
   
       int i, j, g;
       
       int numInstancias = dados.getNumInstancias()/10;
       
       DadosNormalizados dadosNorm;
       InstanciaNormalizada instNorm;
       AtributoNormalizado atribNorm;
       
       for (g = 0; g<2;g++){

        //Separa conjunto de teste
        dadosNorm = new DadosNormalizados();
        dadosNorm.setNumInstancias(numInstancias);
        dadosNorm.setNumAtributos(dados.getNumAtributos());
        for (i= numInstancias * g; i < numInstancias*(g+1); i++) {
           instNorm = new InstanciaNormalizada();
           instNorm.setNome(dados.getListInstancias().get(i).getNome());
           instNorm.setEsperado(dados.getListInstancias().get(i).getEsperado());
                  for( j = 0; j < dados.getNumAtributos(); j++){
                        atribNorm = new AtributoNormalizado();
                        atribNorm.setValor(dados.getListInstancias().get(i).getListAtributos().get(j).getValor());
                        instNorm.getListAtributos().add(atribNorm);
                  }
              dadosNorm.getListInstancias().add(instNorm);
            }
        
        listDados.add(dadosNorm);
       }
       

        return listDados;

        
        
    }
    
    //Separa grupo k
    public ArrayList<DadosNormalizados> separaTreinamentoGrupo(DadosNormalizados dadosNormalizado, int k){
       ArrayList<DadosNormalizados> listDados = new ArrayList<DadosNormalizados>();
       int i, j;
       
       DadosNormalizados dadosNorm;
       InstanciaNormalizada instNorm;
       AtributoNormalizado atribNorm;
        
       
       //Separa conjunto de teste
        dadosNorm = new DadosNormalizados();
        dadosNorm.setNumInstancias((dadosNormalizado.getNumInstancias())/10);
        dadosNorm.setNumAtributos(dadosNormalizado.getNumAtributos());
        
        for (i=k*(dadosNormalizado.getNumInstancias()/10); i<k*(dadosNormalizado.getNumInstancias()/10) +(dadosNormalizado.getNumInstancias()/10) ; i++) {
           instNorm = new InstanciaNormalizada();
           instNorm.setNome(dadosNormalizado.getListInstancias().get(i).getNome());
           instNorm.setEsperado(dadosNormalizado.getListInstancias().get(i).getEsperado());
           
                  for( j = 0; j < dadosNormalizado.getNumAtributos(); j++){
                        atribNorm = new AtributoNormalizado();
                        atribNorm.setValor(dadosNormalizado.getListInstancias().get(i).getListAtributos().get(j).getValor());
                        instNorm.getListAtributos().add(atribNorm);
                  }
              dadosNorm.getListInstancias().add(instNorm);
            }
        
        listDados.add(dadosNorm);
        
        
        
        //Separa conjunto de treinamento
        dadosNorm = new DadosNormalizados();
        dadosNorm.setNumInstancias(dadosNormalizado.getNumInstancias() - (dadosNormalizado.getNumInstancias())/10);
        dadosNorm.setNumAtributos(dadosNormalizado.getNumAtributos());
        
        for (i=0; i<(dadosNormalizado.getNumInstancias()); i++) {
            if(!((i>k*dadosNormalizado.getNumInstancias()/10)&&(i<k*dadosNormalizado.getNumInstancias()/10 + dadosNormalizado.getNumInstancias()/10))){
            instNorm = new InstanciaNormalizada();
            instNorm.setNome(dadosNormalizado.getListInstancias().get(i).getNome());
            instNorm.setEsperado(dadosNormalizado.getListInstancias().get(i).getEsperado());
                  for( j = 0; j < dadosNormalizado.getNumAtributos(); j++){
                        atribNorm = new AtributoNormalizado();
                        atribNorm.setValor(dadosNormalizado.getListInstancias().get(i).getListAtributos().get(j).getValor());
                        instNorm.getListAtributos().add(atribNorm);
                  }
              dadosNorm.getListInstancias().add(instNorm);
            }
        }
           
        
        listDados.add(dadosNorm);
        

        
        return listDados;

        
    }
    
    
    
    
    
    
   
 
}
