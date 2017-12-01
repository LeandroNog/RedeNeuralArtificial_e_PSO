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

        int l, k=1;
       
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
        k = 10;
       //rede.executaAprendizadoBackPropagation(v.separaTreinamentoGrupo(dadosNormalizado, 0).get(1));
       
       
       
        PSO pso = new PSO();
        
        
        
        Rede bestRede, r2;
        int bestRedeIndice;

        ArrayList<DadosNormalizados> testeTrein = new ArrayList<DadosNormalizados>();
        ArrayList<DadosNormalizados> treinValid = new ArrayList<DadosNormalizados>();
        
        testeTrein = v.separaTesteTreinamento(dadosNormalizado);

        Erro bestErro;
        Erro erroTest;
        int indicep = 0;
        Erro erro;
        bestRedeIndice = 0;
        for (l = 0; l<k;l++){ 
            if(l == 0){
                r2 = pso.executaPSO(v.separaTreinamentoGrupo(testeTrein.get(1), 0).get(1),v.separaTreinamentoGrupo(testeTrein.get(1), 0).get(0),testeTrein.get(0), 0);
                erro = r2.executaTeste(v.separaTreinamentoGrupo(testeTrein.get(1), 0).get(0));
            }
            
            
            
            
            
            
            r2 = pso.executaPSO(v.separaTreinamentoGrupo(testeTrein.get(1), 0).get(1),v.separaTreinamentoGrupo(testeTrein.get(1), 0).get(0),testeTrein.get(0), l);      
            erro =  r2.executaTeste(v.separaTreinamentoGrupo(testeTrein.get(1), l).get(0));
            
            
        //Salva rede
        
        FileWriter arq;
       
     
        //Salva melhor rede em arquivo
        try {
            arq = new FileWriter("Rede"+String.valueOf(l)+".txt", false);
            PrintWriter gravarArq = new PrintWriter(arq);
            
            
            String linha;
        
                linha = String.valueOf("NumCamadas: " + bestRede.getNumCamadas() +
                        "," + "NumCiclos: "+bestRede.getNumCiclos() + 
                        "," + "NumEntradas: " + bestRede.getCamadas().get(0).getListNeuronios().get(0).getPesos().size()+
                        "," + "Taxa de Aprendizado: " + bestRede.getTaxaDeAprendizado());

                linha = linha + "\n" +"Numero de neuronios por camada: "+ bestRede.getNumNeuronioCamada().toString();
                
                
                //Primeira camada
         
        for(int i = 0 ; i<r2.getNumNeuronioCamada().get(0) ; i++){
            linha = linha + "\n";
            for(int j = 0; j < dadosNormalizado.getNumAtributos(); j++){
                linha = linha + String.valueOf(bestRede.getCamadas().get(0).getListNeuronios().get(i).getPesos().get(j)) + ",";
                
            }
      }
        //Outras camadas
        for(int c = 1; c<r2.getNumCamadas();c++){
           for(int i = 0 ; i<bestRede.getNumNeuronioCamada().get(c) ; i++){
            linha = linha + "\n";
            for(int j = 0; j < bestRede.getNumNeuronioCamada().get(c-1); j++){
                linha = linha + String.valueOf(bestRede.getCamadas().get(c).getListNeuronios().get(i).getPesos().get(j)) + ",";
                
            }
        }
        }
                gravarArq.printf(linha+"\n");
        
            
            arq.close();
        } catch (IOException ex) {
            Logger.getLogger(DadosNormalizados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        FileWriter arqLog;
   
     
        //Salva log em arquivo
        try {
            arqLog = new FileWriter("logBestRede"+String.valueOf(l)+".txt", false);
            PrintWriter gravarArq = new PrintWriter(arqLog);
            
            
            String linha;
            erroTrei = r2.executaTeste( v.separaTreinamentoGrupo(testeTrein.get(1), 0).get(1));
            erroTest = r2.executaTeste(dadosNormalizado);
            linha = String.valueOf("*************************REDE NEURAL MULTI-LAYER PERCEPTRON*************************\n");
            linha = linha + String.valueOf("Teste no Treinamento:\n");
            linha = linha + String.valueOf("Erro médio: " + erroTrei.getErroMedio() + "\n"
                                    + "Desvio padrão: " + erroTrei.getDesvioPadrao() + "\n"
                                    +  "Coeficiente de Determinação: " + erroTrei.getCoeficienteDeDeterminacao()  + "\n"
                                    + "Raiz quadrada do erro médio quadrático: " + erroTrei.getRaizQuadradaErroMedioQuadratico() + "\n"
                                    + "Variância: " + erroTrei.getVariancia() + "\n" + "\n");
            
            linha = linha  + String.valueOf("Teste na Validação:\n");
            linha = linha + String.valueOf("Erro médio: " + erroValid.getErroMedio() + "\n"
                                    + "Desvio padrão: " + erroValid.getDesvioPadrao() + "\n"
                                    + "Coeficiente de Determinação: " + erroValid.getCoeficienteDeDeterminacao()  + "\n"
                                    + "Raiz quadrada do erro médio quadrático: " + erroValid.getRaizQuadradaErroMedioQuadratico() + "\n"
                                    + "Variância: " + erroValid.getVariancia() + "\n"+ "\n");
           
            linha = linha + String.valueOf("Teste no Teste:\n");
            linha = linha + String.valueOf("Erro médio: " + erroTest.getErroMedio() + "\n"
                                    + "Desvio padrão: " + erroTest.getDesvioPadrao() + "\n"
                                    + "Coeficiente de Determinação: " + erroTest.getCoeficienteDeDeterminacao()  + "\n"
                                    + "Raiz quadrada do erro médio quadrático: " + erroTest.getRaizQuadradaErroMedioQuadratico() + "\n"
                                    + "Variância: " + erroTest.getVariancia() );
            
        
        
         gravarArq.printf(linha+"\n");
        
            
            arqLog.close();
        } catch (IOException ex) {
            Logger.getLogger(DadosNormalizados.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        
        
        
        
       
        
            
            
            
            
            //Define melhor rede
            if(bestErro.getErroMedio()>erro.getErroMedio()){
                bestRede = r2;
                bestRedeIndice = l;
            }
        }
        
        
        
        //Fazer teste e gravar melhor rede
        System.out.println("Melhor rede: " +  String.valueOf(bestRedeIndice));
        
        
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
