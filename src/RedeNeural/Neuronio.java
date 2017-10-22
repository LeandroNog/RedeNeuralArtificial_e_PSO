/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RedeNeural;

import java.util.ArrayList;

/**
 *
 * @author leandro
 */
public class Neuronio {
   
    private ArrayList<Double> pesos;
    private ArrayList<Double> deltaPesos;
    
    private double pesoBias;
    private double bias = -1;
    private double saida;
    private double erro;
    private double derivadaErro;

    public Neuronio() {
        pesos = new ArrayList<Double>();
        deltaPesos = new ArrayList<Double>();
    }

    public ArrayList<Double> getPesos() {
        return pesos;
    }

    public void setPesos(ArrayList<Double> pesos) {
        this.pesos = pesos;
    }

    public ArrayList<Double> getDeltaPesos() {
        return deltaPesos;
    }

    public void setDeltaPesos(ArrayList<Double> deltaPesos) {
        this.deltaPesos = deltaPesos;
    }

    public double getPesoBias() {
        return pesoBias;
    }

    public void setPesoBias(double pesoBias) {
        this.pesoBias = pesoBias;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public double getSaida() {
        return saida;
    }

    public void setSaida(double saida) {
        this.saida = saida;
    }

    public double getErro() {
        return erro;
    }

    public void setErro(double erro) {
        this.erro = erro;
    }

    public double getDerivadaErro() {
        return derivadaErro;
    }

    public void setDerivadaErro(double derivadaErro) {
        this.derivadaErro = derivadaErro;
    }
    
    
    
}
