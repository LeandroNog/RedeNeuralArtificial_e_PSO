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
public class Camada {
    
    private ArrayList<Neuronio> listNeuronios;
    private int numNeuronios;

    public Camada() {
       listNeuronios = new ArrayList<Neuronio>();
    }

    public ArrayList<Neuronio> getListNeuronios() {
        return listNeuronios;
    }

    public void setListNeuronios(ArrayList<Neuronio> listNeuronios) {
        this.listNeuronios = listNeuronios;
    }

    public int getNumNeuronios() {
        return numNeuronios;
    }

    public void setNumNeuronios(int numNeuronios) {
        this.numNeuronios = numNeuronios;
    }
    
    
    
}
