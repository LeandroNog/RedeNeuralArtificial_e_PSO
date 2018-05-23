/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import Dados.Atributo;
import Dados.Dados;
import Dados.Instancia;
import DadosNormalizados.AtributoNormalizado;
import DadosNormalizados.DadosNormalizados;
import DadosNormalizados.InstanciaNormalizada;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.exit;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * 
 *
 * @author leandro
 */
public class LeituraArquivo {


   /* public static void main(String[] args) {
        LeituraArquivoUI telaPrincipal = new LeituraArquivoUI();
        telaPrincipal.setLocationRelativeTo(null);
        //ABRE INTERFACE GRAFICA
        telaPrincipal.setVisible(true);
        Dados data = new Dados();
        
        
        
       
    }**/
    
    
    public Dados carregaDados(String pathArquivo){
        
       
        String linha;
        String string[];
        Instancia inst;
        Atributo atrib;
        Dados data = new Dados();

       
        int numInstancias=0;
      
        
        double hour;
        double hourNorm;
               
        try { 

            //PEDIR ARQUIVO COM INTERFACE GRAFICA
            File arquivo = new File(pathArquivo); 
            FileReader fr = new FileReader(arquivo);  
            BufferedReader br = new BufferedReader(fr);

            linha = br.readLine();  //lê a proxima linha
  
                  
            while (linha != null ) {  
                //System.out.println(linha);
                string = linha.split(",");
               
                data.setNumAtributos(string.length-1); // - nome
               
                inst = new Instancia();
                //System.out.println(string[string.length - 1]);
                for(int i=0; i < string.length; i++){ //- 1 - classe, peso 
                    if(i==(string.length - 1)){
                         inst.setEsperado(Double.parseDouble(string[i]));
                    }
                    else{
                        atrib = new Atributo();
                        atrib.setValor(Double.parseDouble(string[i]));
                        inst.getListAtributos().add(atrib);
                    }
                    
                }      
                       data.getListInstancias().add(inst);
                       numInstancias++;

                       linha = br.readLine();  //lê a proxima linha


                   }
                   data.setNumInstancias(numInstancias);
                   br.close(); 
                   
               
               } 
              catch (IOException ex)
              {  
                    System.out.println("Falha ao tentar abrir arquivo");

              }
        //data.imprimeDataConsole();
       
        return data;
    }
        
     
}
