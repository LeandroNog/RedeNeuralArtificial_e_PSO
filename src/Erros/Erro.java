/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Erros;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import java.util.ArrayList;

/**
 *
 * @author leandro
 */
public class Erro {
    
    private double erroMedio;
    private double raizQuadradaErroMedioQuadratico;
    private double variancia;
    private double desvioPadrao;
    //Analise de correlação - real x estimado
    private double coeficienteDeDeterminacao;
    
    public Erro(){
        
    }
    
    public Erro(ArrayList<Double> valorEsperado, ArrayList<Double> respostas){
        double soma = 0.0;
        double media = 0.0;
        int i, j;
        double MARGEM =0.2;
       //Calcula todos erros
       
       //ErroMedio
       for(i=0;i<valorEsperado.size();i++){

          soma = soma + abs(valorEsperado.get(i) - respostas.get(i));
         
         /* if((respostas.get(i) >=valorEsperado.get(i) - MARGEM)&&(respostas.get(i)<=valorEsperado.get(i) + MARGEM )){
              //soma++;
          }else{
              soma++;
          }
      */
       }
       this.erroMedio = soma/valorEsperado.size();
     /*  for(int f = 0;f<10;f++){
           
       */
       
       //Raiz do erro medio quadratico
       soma = 0.0;
       for(i=0;i<valorEsperado.size();i++){
           soma = soma + pow(valorEsperado.get(i) - respostas.get(i), 2);
       }
       soma = soma/valorEsperado.size();
        this.raizQuadradaErroMedioQuadratico = pow(soma, 0.5);
       
       
       //Raiz do erro medio quadratico
       soma = 0.0;
       for(i=0;i<valorEsperado.size();i++){
           soma = soma + pow(valorEsperado.get(i) - respostas.get(i), 2);
       }
       
       //Variancia 
        soma = 0.0;
       for(i=0;i<valorEsperado.size();i++){
           soma = soma + pow(abs(valorEsperado.get(i) - respostas.get(i)) - erroMedio  , 2);
       }
       soma = soma/valorEsperado.size();
        this.variancia = pow(soma, 0.5);
       
       
       //Desvio padrao
        this.desvioPadrao = pow(variancia, 0.5);
       
       
       //CoeficienteDeDeterminacao = 1 - raizquadr....sem a raiz e a variancia
        this.coeficienteDeDeterminacao = 1.0 - pow(raizQuadradaErroMedioQuadratico, 2)/variancia;
      
        
    }

    public double getErroMedio() {
        return erroMedio;
    }

    public void setErroMedio(double erroMedio) {
        this.erroMedio = erroMedio;
    }

    public double getRaizQuadradaErroMedioQuadratico() {
        return raizQuadradaErroMedioQuadratico;
    }

    public void setRaizQuadradaErroMedioQuadratico(double raizQuadradaErroMedioQuadratico) {
        this.raizQuadradaErroMedioQuadratico = raizQuadradaErroMedioQuadratico;
    }

   
    public double getVariancia() {
        return variancia;
    }

    public void setVariancia(double variancia) {
        this.variancia = variancia;
    }

    public double getDesvioPadrao() {
        return desvioPadrao;
    }

    public void setDesvioPadrao(double desvioPadrao) {
        this.desvioPadrao = desvioPadrao;
    }

    public double getCoeficienteDeDeterminacao() {
        return coeficienteDeDeterminacao;
    }

    public void setCoeficienteDeDeterminacao(double coeficienteDeDeterminacao) {
        this.coeficienteDeDeterminacao = coeficienteDeDeterminacao;
    }
    

    
}
