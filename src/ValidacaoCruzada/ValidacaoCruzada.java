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
import RedeNeural.Rede;
import file.GravaArquivo;
import java.util.Collections;
import file.LeituraArquivo;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
        
        
        int i, k=10;
       
        //Carrego os dados
        String pathArquivo = "smarthome_2012-May-7.csv";
        LeituraArquivo leituraArquivo = new LeituraArquivo();
        
        //Carrega dados sem normalizar
        Dados dados = leituraArquivo.carregaDados(pathArquivo);
//        dados.imprimeDataConsole();
        
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
        ArrayList <DadosNormalizados> gruposValidacaoCruzada = v.separaTreinamento(testeTreinamento.get(1), k);
        
        //Imprime grupo da validação cruzada
        // gruposValidacaoCruzada.get(9).imprimeDataConsole();
        
        
        //Realiza Validação cruzada
        
        for (i = 0; i<k;i++){ 
            //Executa aprendizado com grupos exceto grupo i (grupo de validação)
            //Testa com grupo i (grupo de validação)
            
        }
            
        ArrayList<Integer> numNeuronioCamada=new ArrayList<>();
        numNeuronioCamada.add(2);
        numNeuronioCamada.add(1);
        
        Rede rede = new Rede(2,numNeuronioCamada,4, 10000);
        rede.inicializaRede();
       
              
        
        
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
       
       int numInstancias = dados.getNumInstancias()/k;
       
       DadosNormalizados dadosNorm;
       InstanciaNormalizada instNorm;
       AtributoNormalizado atribNorm;
       
       for (g = 0; g<k;g++){

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
    
   
 
}
