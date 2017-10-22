/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dados;

import java.util.ArrayList;

/**
 *
 * @author leandro
 */
public class Instancia implements Comparable<Instancia> {
    private ArrayList<Atributo> listAtributos;
    private double esperado;
    private double peso;
    private String nome;

    public Instancia() {
        listAtributos = new ArrayList<Atributo>();
    }
    
    public ArrayList<Atributo> getListAtributos() {
        return listAtributos;
    }

    public void setListAtributos(ArrayList<Atributo> listAtributos) {
        this.listAtributos = listAtributos;
    }

    public double getEsperado() {
        return esperado;
    }

    public void setEsperado(double esperado) {
        this.esperado = esperado;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int compareTo(Instancia t) {
        return this.getNome().compareTo(t.getNome());
    }
    
}
