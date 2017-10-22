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
import java.util.Date;

/**
 *
 * @author leandro
 */
public class PreProcessaDados {
    
    
    public DadosNormalizados normalizaDados(Dados dados){
        DadosNormalizados dadosNorm = new DadosNormalizados();
        InstanciaNormalizada instNorm;
        AtributoNormalizado atribNorm;
        AtributoNormalizado atribDia;
        int i, j;
        String[] splitValues = {"",""};
        //Copia dados normais para
        
        dadosNorm.setNumInstancias(dados.getNumInstancias());
        dadosNorm.setNumAtributos(dados.getNumAtributos()+1); //timestamp vira dois atributos
        
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
        
        //dadosNorm.imprimeDataConsole();

        
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
              
    
                //dataNormalizado.imprimeDataConsole();
        return dataNormalizado;
        
        
    }
    
}
    
    
    

