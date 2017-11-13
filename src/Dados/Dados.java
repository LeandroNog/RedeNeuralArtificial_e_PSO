/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dados;

import java.awt.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import file.LeituraArquivoUI;

/**
 *
 * @author leandro
 */
public class Dados {
    private ArrayList<Instancia> listInstancias ;
    private int numInstancias;
    private int numAtributos;

    public Dados() {
        listInstancias = new ArrayList<Instancia>();
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
    
    public ArrayList<Instancia> getListInstancias() {
        return listInstancias;
    }

    public void setListInstancias(ArrayList<Instancia> listInstancias) {
        this.listInstancias = listInstancias;
    }
    public void imprimeDataConsole(){
        
        String linha;
         for(int i = 0; i < this.numInstancias; i++){
             linha = this.getListInstancias().get(i).getNome() + ", ";
             
            for(int j = 0; j < this.numAtributos; j++){
                 linha = linha + String.valueOf(this.getListInstancias().get(i).getListAtributos().get(j).getValor()) + " , ";
            }
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
    
    public void imprimeDataArquivo(Dados data, int iHora){
        FileWriter arq;
        String nome = "hora" + String.valueOf(iHora);
        
        try {
            arq = new FileWriter(nome + ".txt", false);
            PrintWriter gravarArq = new PrintWriter(arq);
            
            
            String linha;
            for(int i = 0; i < this.numInstancias; i++){
                linha = this.getListInstancias().get(i).getNome() + ", ";
             
                for(int j = 0; j < this.numAtributos ; j++){
                   
                       linha = linha + String.valueOf(this.getListInstancias().get(i).getListAtributos().get(j).getValor()) + ","; 
                   
                }
                linha = linha + String.valueOf(this.getListInstancias().get(i).getEsperado());
                gravarArq.printf(linha+"\n");
        
            }
            arq.close();
        } catch (IOException ex) {
            Logger.getLogger(Dados.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Dados.class.getName()).log(Level.SEVERE, null, ex);
        }
       

    }
    
    
    
    public Dados dadosDispositivo(Dados data, String nome){
        Dados dataDisp = new Dados();
        int numInsntaciasDisp = 0;
        int numAtributos = data.getNumAtributos();
        Instancia nInstancia;
        System.out.println("Buscando...");
        for(int i= 0; i < data.numInstancias-1;i++ ){
            if(data.getListInstancias().get(i).getNome().equals(nome)){
                nInstancia = data.getListInstancias().get(i);
                dataDisp.getListInstancias().add(nInstancia);
                numInsntaciasDisp++;
            }
        }
        System.out.println("Concluído");
        dataDisp.setNumAtributos(data.numAtributos);
        dataDisp.setNumInstancias(numInsntaciasDisp);
        return dataDisp;
    }
    
    
    public Dados dadosPorHora(Dados data, double horaInicial){
        Dados dataDisp = new Dados();
        int numInsntaciasDisp = 0;
        int numAtributos = data.getNumAtributos();
        Instancia nInstancia;
        System.out.println("Buscando...");
        for(int i= 0; i < data.numInstancias-1;i++ ){
            if((data.getListInstancias().get(i).getListAtributos().get(1).getValor() >=  horaInicial)&&(data.getListInstancias().get(i).getListAtributos().get(1).getValor() <  horaInicial + 3600)){
                nInstancia = data.getListInstancias().get(i);
                dataDisp.getListInstancias().add(nInstancia);
                numInsntaciasDisp++;
            }
        }
        System.out.println("Concluído");
        dataDisp.setNumAtributos(data.numAtributos);
        dataDisp.setNumInstancias(numInsntaciasDisp);
        return dataDisp;
    }
    
    
    public void clear(){
        this.listInstancias.clear();
        this.numAtributos = 0;
        this.numInstancias = 0;
        
        
    }
    
    
    
}
