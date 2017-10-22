/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DadosNormalizados;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import file.LeituraArquivoUI;


public class DadosNormalizados {
    private ArrayList<InstanciaNormalizada> listInstancias;
    private int numInstancias;
    private int numAtributos;

    public DadosNormalizados() {
        listInstancias = new ArrayList<InstanciaNormalizada>();
    }

    public int getNumInstancias() {
        return numInstancias;
    }

    public void setNumInstancias(int numInstancias) {
        this.numInstancias = numInstancias;
    }

    public int getNumAtributos() {
        return numAtributos;
    }

    public void setNumAtributos(int numAtributos) {
        this.numAtributos = numAtributos;
    }
    
    public ArrayList<InstanciaNormalizada> getListInstancias() {
        return listInstancias;
    }

    public void setListInstancias(ArrayList<InstanciaNormalizada> listInstancias) {
        this.listInstancias = listInstancias;
    }
    public void imprimeDataConsole(){
        String linha;
         for(int i = 0; i < this.numInstancias; i++){
             linha = this.getListInstancias().get(i).getNome() + ", ";
             
            for(int j = 0; j < this.numAtributos; j++){
                 linha = linha + String.valueOf(this.getListInstancias().get(i).getListAtributos().get(j).getValor()) + " , ";
            }
             linha = linha + String.valueOf(this.getListInstancias().get(i).getEsperado());
             System.out.println(linha);
        
           }
    }
     
    public void imprimeDataLista( LeituraArquivoUI telaImprime){
      DefaultListModel lista = new DefaultListModel();
      JList list;
      String linha;
      
      for(int i = 0; i < this.numInstancias-1;i++){
          linha = this.getListInstancias().get(i).getNome() + ", ";
          for(int j = 0; j< this.numAtributos-1;j++){
                linha = linha + String.valueOf(this.getListInstancias().get(i).getListAtributos().get(j).getValor()) + " , ";
               
          }
          
         
          lista.addElement(linha);
           
      }
           list = telaImprime.getjList1();
           list.setModel(lista);
           telaImprime.setjList1(list);
                
      
    }
    
    public void imprimeDataArquivo(DadosNormalizados data, int iHora){
        FileWriter arq;
        String nome = "hora" + String.valueOf(iHora);
        
        try {
            arq = new FileWriter(nome + ".txt", false);
            PrintWriter gravarArq = new PrintWriter(arq);
            
            
            String linha;
            for(int i = 0; i < this.numInstancias-1; i++){
                linha = this.getListInstancias().get(i).getNome() + ", ";
             
                for(int j = 0; j < this.numAtributos - 1; j++){
                    if(j==(this.numAtributos-2)){
                       linha = linha + String.valueOf(this.getListInstancias().get(i).getListAtributos().get(j).getValor()); 
                    }
                    else{
                        linha = linha + String.valueOf(this.getListInstancias().get(i).getListAtributos().get(j).getValor()) + ",";
                    }
                }
                gravarArq.printf(linha+"\n");
        
            }
            arq.close();
        } catch (IOException ex) {
            Logger.getLogger(DadosNormalizados.class.getName()).log(Level.SEVERE, null, ex);
        }
       

    }
    
    public void imprimeDataArquivo2(){
        FileWriter arq;
        String nome = "Disp1";
        
        try {
            arq = new FileWriter(nome + ".txt", false);
            PrintWriter gravarArq = new PrintWriter(arq);
            
            
            String linha;
            for(int i = 0; i < this.numInstancias-1; i++){
                linha = String.valueOf(i) + " ";
               // linha = this.getListInstancias().get(i).getNome() + ", ";
             
               
                    linha = linha + String.valueOf(this.getListInstancias().get(i).getListAtributos().get(2).getValor()) + ",";
                    
                   /* if(j==(this.numAtributos-2)){
                       linha = linha + String.valueOf(this.getListInstancias().get(i).getListAtributos().get(j).getValor()); 
                    }
                    else{
                        linha = linha + String.valueOf(this.getListInstancias().get(i).getListAtributos().get(j).getValor()) + ",";
                    }*/
              
                gravarArq.printf(linha+"\n");
        
            }
            arq.close();
        } catch (IOException ex) {
            Logger.getLogger(DadosNormalizados.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
   
    
    public void clear(){
        this.listInstancias.clear();
        this.numAtributos = 0;
        this.numInstancias = 0;
        
        
    }

  
}
