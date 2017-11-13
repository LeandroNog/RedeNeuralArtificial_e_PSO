/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preProcessameto;

import Dados.Atributo;
import Dados.Dados;
import Dados.Instancia;
import DadosNormalizados.AtributoNormalizado;
import DadosNormalizados.DadosNormalizados;
import DadosNormalizados.InstanciaNormalizada;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.exit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author leandro
 */
public class PreProcessaDados {
    
    
    public DadosNormalizados normalizaDados(Dados dados){
        DadosNormalizados dadosNorm = new DadosNormalizados();
       
        Instancia instNew;
        Atributo atribNew;
        AtributoNormalizado atribDia;
        ArrayList<Instancia> newListInstancias = new ArrayList();
        int i, j;
        String[] splitValues = {"",""};
        //Copia dados normais para
        
        int indice = -1;
        int k=0;
        int f;
        double valor=0.0;
          
        
       
        /*
        for(i=0;i<dados.getNumInstancias();i++){
             instNew = new Instancia();

             for(j=0;j<dados.getNumAtributos();j++){
                 atribNew = new Atributo();
                 
                 //SOMA INSTANCIAS COM TIMESTAMP IGUAIS, OU SEJA FAZ O PREVISTO DA CASA TODA descomentar se for o caso
                 if(j==1){
                     indice = -1;
                     for(k = 0; k < newListInstancias.size(); k++){
                             if(newListInstancias.get(k).getListAtributos().get(j).getValor() == dados.getListInstancias()
                                 .get(i).getListAtributos().get(j).getValor()){
                                 //System.out.println("igual");
                             indice = k;
                             break;
                         
                         
                         }
                     }
                     
                    if(indice!=-1){
                        newListInstancias.get(k).setEsperado(newListInstancias.get(k).getEsperado() + dados.getListInstancias().get(i).getEsperado());
                        
                        //Copia somando
                        for(f=0; f < dados.getNumAtributos();f++)
                            
                            if((f!=0)&&(f!=1)){

                            newListInstancias.get(k).getListAtributos().get(f).setValor(newListInstancias.get(k).getListAtributos().get(f).getValor()
                                    + dados.getListInstancias().get(i).getListAtributos().get(j).getValor());
                            
                             break;
                            }
                        

                        
                      }
                      else{
                                //add instancia
                                instNew = new Instancia();
                                atribNew = new Atributo();
                                atribNew.setValor(0.0);
                                instNew.getListAtributos().add(atribNew); //atrib 0
                                atribNew = new Atributo();
                                atribNew.setValor(dados.getListInstancias().get(i).getListAtributos().get(1).getValor());
                                instNew.getListAtributos().add(atribNew); //atrib 1
                                
                                for(f=2;f<dados.getNumAtributos();f++){
                                    atribNew = new Atributo();
                                    atribNew.setValor(dados.getListInstancias().get(i).getListAtributos().get(f).getValor());
                                    instNew.getListAtributos().add(atribNew);
                                }
                                
                                instNew.setEsperado(dados.getListInstancias().get(i).getEsperado());
                                instNew.setNome("Casa1");
                                newListInstancias.add(instNew);
                            }
                    
                    
                     
                 }
                 
             }
             //System.out.println("Somando"+ i);
        }
        */
        //exit(1);
        //atualiza lista de instancia
        //dados.setListInstancias(newListInstancias);
        //dados.setNumInstancias(newListInstancias.size());
        dadosNorm.setNumInstancias(dados.getNumInstancias());
        dadosNorm.setNumAtributos(dados.getNumAtributos()+1); //timestamp vira dois atributos
        //atualiza num instancias
        //dados.imprimeDataConsole();
        //dados.imprimeDataArquivo(dados, 25);
        
        System.out.println("Dados Normalizados!");
        
        InstanciaNormalizada instNorm;
        AtributoNormalizado atribNorm;
        
        for(i=0;i<dados.getNumInstancias();i++){

             instNorm = new InstanciaNormalizada();
             instNorm.setNome(dados.getListInstancias().get(i).getNome());
             instNorm.setEsperado(dados.getListInstancias().get(i).getEsperado());
             for(j=0;j<dados.getNumAtributos();j++){
                 atribNorm = new AtributoNormalizado();

                 if(j==1){ 
                    //Converte timestamp pra hora e dia da semana
                    String dateAsText = new SimpleDateFormat("HH:mm:ss:EEE")
                            .format(new Date( (long) (dados.getListInstancias().get(i).getListAtributos().get(j).getValor()* 1000L)) );
                               
                                splitValues = dateAsText.split(":");
                                double hour = Double.parseDouble(splitValues[0]);
                                
                                double hourNorm = 1.0/23.0 * hour;

                                //Coloca a hora na posicao 2 msm
                                atribNorm.setValor(hourNorm);
                                instNorm.getListAtributos().add(atribNorm);
                                
                             
                             

                 }
                 else{
                    atribNorm.setValor(dados.getListInstancias().get(i).getListAtributos().get(j).getValor());
                    instNorm.getListAtributos().add(atribNorm);
                 }
  
                 
             }     
             double valorAtrib = 0.0;
             atribDia = new AtributoNormalizado();
                        
             if(splitValues[3].equals("Dom")){
                valorAtrib = 0.0;
             }
             else if(splitValues[3].equals("Seg")){

                valorAtrib = 0.16;
             }
             else if(splitValues[3].equals("Ter")){
                valorAtrib = 0.32;
             }
             else if(splitValues[3].equals("Qua")){
                valorAtrib = 0.48;
             }
             else if(splitValues[3].equals("Qui")){
                valorAtrib = 0.64;
             }
             else if(splitValues[3].equals("Sex")){
                valorAtrib = 0.8;
             }
             else if(splitValues[3].equals("Sab")){
                valorAtrib = 0.96;
             }

             atribDia.setValor(valorAtrib);
             instNorm.getListAtributos().add(atribDia);
             dadosNorm.getListInstancias().add(instNorm);
  
        
        }
        
        //dados.imprimeDataConsole();
       

        
         //Consegue maior atributo 2
               
               int l, m;
               double maior1 = 0.0;
               double maior2 = 0.0;
               for(l=0;l<dadosNorm.getNumInstancias();l++){
                   if(dadosNorm.getListInstancias().get(l).getListAtributos().get(2).getValor() > maior1){
                       maior1 = dadosNorm.getListInstancias().get(l).getListAtributos().get(2).getValor();
                   }
               }
                for(l=0;l<dadosNorm.getNumInstancias();l++){
                   if(dadosNorm.getListInstancias().get(l).getEsperado() > maior2){
                       maior2 = dadosNorm.getListInstancias().get(l).getEsperado();
                   }
               }
            
               
               DadosNormalizados dataNormalizado = new DadosNormalizados();
               dataNormalizado.setNumAtributos(dadosNorm.getNumAtributos());
               dataNormalizado.setNumInstancias(dadosNorm.getNumInstancias());
               
               
               
                for(l=0;l<dadosNorm.getNumInstancias();l++){
                    
                    instNorm = new InstanciaNormalizada();
                    
                    instNorm.setNome(dadosNorm.getListInstancias().get(l).getNome());
                    instNorm.setEsperado(dadosNorm.getListInstancias().get(l).getEsperado()/maior2);
                       for(m=0;m<dadosNorm.getNumAtributos();m++){ 
                           atribNorm = new AtributoNormalizado();
                           if(m==2){
                                atribNorm.setValor(dadosNorm.getListInstancias().get(l).getListAtributos().get(m).getValor()/maior1);
                           }
                           else{
                                atribNorm.setValor(dadosNorm.getListInstancias().get(l).getListAtributos().get(m).getValor());
                           }
                           instNorm.getListAtributos().add(atribNorm);
                           
                       }
                        dataNormalizado.getListInstancias().add(instNorm);
                   
               }
              
    
        //dataNormalizado.imprimeDataConsole
     
        //dadosNorm.imprimeDataConsole();
        
        return dataNormalizado;
        
        
    }
    
}
    
    
    

