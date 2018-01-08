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
    ArrayList<Double> velocidade;
    ArrayList<Double> pesoRede;
   
    
    double bestFun= 0.0;
    double fun = 0.0;

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
        copy.setVelocidade(this.getVelocidade());
        return copy; 
    }

    public ArrayList<Double> getPesoRede() {
        return pesoRede;
    }

    public void setPesoRede(ArrayList<Double> pesoRede) {
        this.pesoRede = pesoRede;
    }
    
    
}
