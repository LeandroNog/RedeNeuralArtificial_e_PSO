package PSO;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leandro
 */
public class Particula {
    
    ArrayList<Integer> numNeuroniosCamada;

    double taxaAprendizado;
    ArrayList<Double> velocidade;
    //Fixar numero de camadas fica mais simples, ai eh só testar o PSO com 1, 2, 3, 4 e 5 camadas
    double numCamadas;
    int numeroDeCiclos;
    
    double bestFun=100.0;
    double fun = 0.0;

    public ArrayList<Integer> getNumNeuroniosCamada() {
        return numNeuroniosCamada;
    }

    public void setNumNeuroniosCamada(ArrayList<Integer> numNeuroniosCamada) {
        this.numNeuroniosCamada = numNeuroniosCamada;
    }

 

    public double getTaxaAprendizado() {
        return taxaAprendizado;
    }

    public void setTaxaAprendizado(double taxaAprendizado) {
        this.taxaAprendizado = taxaAprendizado;
    }

    public double getNumCamadas() {
        return numCamadas;
    }

    public void setNumCamadas(double numCamadas) {
        this.numCamadas = numCamadas;
    }

    public int getNumeroDeCiclos() {
        return numeroDeCiclos;
    }

    public void setNumeroDeCiclos(int numeroDeCiclos) {
        this.numeroDeCiclos = numeroDeCiclos;
    }

    public ArrayList<Double> getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(ArrayList<Double> velocidade) {
        this.velocidade = velocidade;
    }

    public double getBestFun() {
        return bestFun;
    }

    public void setBestFun(double bestFun) {
        this.bestFun = bestFun;
    }

    public double getFun() {
        return fun;
    }

    public void setFun(double fun) {
        this.fun = fun;
    }
      
    public Particula copiaParticula(){
        Particula copy = new Particula();
        copy.setBestFun(this.getBestFun());
        copy.setFun(this.getFun());
        copy.setNumCamadas(this.getNumCamadas());
        copy.setNumNeuroniosCamada(this.getNumNeuroniosCamada());
        copy.setNumeroDeCiclos(this.getNumeroDeCiclos());
        copy.setTaxaAprendizado(this.getTaxaAprendizado());
        copy.setVelocidade(this.getVelocidade());
        return copy;
            
    }
    
    
}
