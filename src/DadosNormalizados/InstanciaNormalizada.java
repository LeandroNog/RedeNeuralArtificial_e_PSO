/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DadosNormalizados;

import java.util.ArrayList;


public class InstanciaNormalizada implements Comparable<InstanciaNormalizada> {
    private ArrayList<AtributoNormalizado> listAtributos;
    private double esperado;
    private double peso;
    private String nome;

    public InstanciaNormalizada() {
        listAtributos = new ArrayList<AtributoNormalizado>();
    }
    
    public ArrayList<AtributoNormalizado> getListAtributos() {
        return listAtributos;
    }

    public void setListAtributos(ArrayList<AtributoNormalizado> listAtributos) {
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
    public int compareTo(InstanciaNormalizada t) {
        return this.getNome().compareTo(t.getNome());
    }
    
}
