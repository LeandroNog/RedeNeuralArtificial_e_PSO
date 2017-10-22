/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preProcGrafico;

import Dados.Atributo;
import Dados.Dados;
import Dados.Instancia;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author leandro
 */
public class preProc {
    /*
    public static void main(String[] args){
        String linha="";
        String string[];
        Instancia inst;
        Atributo atrib;
        Dados data = new Dados();
        int numInstancias=0;
        
         try { 
           //PEDIR ARQUIVO COM INTERFACE GRAFICA
           File arquivo = new File("DispositivoBedroomOutlets.txt"); 
           FileReader fr = new FileReader(arquivo);  
           BufferedReader br = new BufferedReader(fr);

            while (linha != null ) 
            {  
                string = linha.split(",");
                
                inst = new Instancia();
                inst.setNome(string[0]);
               
                
                 for(int i=1; i < data.getNumAtributos(); i++){ //- 1 - classe, peso 
                     atrib = new Atributo();
                     atrib.setValor(Double.parseDouble(string[i]));
                     inst.getListAtributos().add(atrib);
                  }
                 /*inst.setEsperado(Double.valueOf(string[data.getNumAtributos()-1]));
                 inst.setPeso(Double.valueOf(string[data.getNumAtributos()+1]));*/
                /*
                data.getListInstancias().add(inst);
                numInstancias++;
               
           
            
                
                linha = br.readLine();  //lê a proxima linha
                              
                 
            }
            data.setNumInstancias(numInstancias);
            
           
            br.close(); 
            data.imprimeDataArquivo2();
           
            
            
        } 
       catch (IOException ex)
       {  
             System.out.println("nop");
        
       }
       
        
        
        
    }
    
    
    
    */
    
    
    
}
