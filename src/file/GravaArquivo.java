/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import DadosNormalizados.DadosNormalizados;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author leandro
 */
public class GravaArquivo {
    
    
    public void gravaTesteTreinamento(DadosNormalizados dadosNormalizado){
        //Grava em arquivo 10% de teste e em outro 90% treinamento
        int i, j;
        String linha;
        
        FileWriter arq_teste;
        FileWriter arq_treinamento;
        try {
            arq_teste = new FileWriter("dados_teste.txt");
            arq_treinamento = new FileWriter("dados_treinamento.txt");
            PrintWriter gravarArq_teste = new PrintWriter(arq_teste);
            PrintWriter gravarArq_treinamento = new PrintWriter(arq_treinamento);
            for (i=0; i<(dadosNormalizado.getNumInstancias())/10; i++) {
                 linha = dadosNormalizado.getListInstancias().get(i).getNome() + ", ";
                  for( j = 0; j < dadosNormalizado.getNumAtributos(); j++){
                        linha = linha + String.valueOf(dadosNormalizado.getListInstancias().get(i).getListAtributos().get(j).getValor()) + ", ";
                   }
                   linha = linha + String.valueOf(dadosNormalizado.getListInstancias().get(i).getEsperado());

              gravarArq_teste.printf(linha + "\n");
            }
            
            
             for (i=(dadosNormalizado.getNumInstancias())/10; i<(dadosNormalizado.getNumInstancias()); i++) {
                 linha = dadosNormalizado.getListInstancias().get(i).getNome() + ", ";
                 
                  for( j = 0; j < dadosNormalizado.getNumAtributos(); j++){
                        linha = linha + String.valueOf(dadosNormalizado.getListInstancias().get(i).getListAtributos().get(j).getValor()) + ", ";
                   }
                   linha = linha + String.valueOf(dadosNormalizado.getListInstancias().get(i).getEsperado());

              gravarArq_treinamento.printf(linha + "\n");
            }
      
            arq_teste.close();
            arq_treinamento.close();
        
       
        } catch (IOException ex) {
            System.out.println("Erro ao criar o arquivo");
        }

    }
    
}
