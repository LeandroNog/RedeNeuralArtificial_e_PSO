/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RedeNeural;

import DadosNormalizados.DadosNormalizados;
import DadosNormalizados.InstanciaNormalizada;
import Erros.Erro;
import static java.lang.Math.exp;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author leandro
 */
public class Rede {
    
    private int numCamadas;
    private ArrayList<Camada> camadas;
    private ArrayList<Integer> numNeuronioCamada;
    private int numCiclos;
    private int numEntradas;

    public Rede(int numCamadas,ArrayList<Integer> numNeuronioCamada,int numEntradas,  int numCiclos) {
        this.numCamadas = numCamadas;
        this.numCiclos = numCiclos;
        this.camadas = new ArrayList<Camada>();
        this.numNeuronioCamada = numNeuronioCamada; 
        this.numEntradas = numEntradas;
    }
    
    
    
    public void inicializaRede(){
        //Inicia rede com pesos aleatórios
        Random random = new Random();
        double x;
        int i, j, l;
        int numeroPesos;
        ArrayList<Double> pesos = new ArrayList<>();
        ArrayList<Neuronio> listNeuronio = new ArrayList<>();
         
        Camada camada;
        Neuronio neuronio;
        ArrayList<Camada> camadas = new ArrayList<>();
        for(i=0;i<this.numCamadas;i++){
            camada = new Camada();
            camada.setNumNeuronios(this.numNeuronioCamada.get(i));
            listNeuronio = new ArrayList<>();
            for(j=0;j<camada.getNumNeuronios();j++){
                neuronio = new Neuronio();
                pesos = new ArrayList<>();
                  if(i==0){
                     numeroPesos = this.getNumEntradas();
                  }
                  else{
                     numeroPesos = this.getCamadas().get(i-1).getNumNeuronios();
                  }
                  for(l=0;l<numeroPesos;l++){
                       x = random.nextDouble();
                       pesos.add(x);
                      // System.out.println(x);
                  }
                neuronio.setPesos(pesos);
                neuronio.setBias(random.nextDouble());
                listNeuronio.add(neuronio);
 
            }
                camada.setListNeuronios(listNeuronio); 
                camadas.add(camada);
                this.setCamadas(camadas);
        }
        
          
    }
    
    
    public double propagaSinal(InstanciaNormalizada instancia){
        double saida=0.0;
        int i, j, l, c;
        
        //Primeira camada
        for(i = 0 ; i<this.getNumNeuronioCamada().get(0) ; i++){
            for(j = 0; j < this.getNumEntradas(); j++){
                
                saida = saida + this.getCamadas().get(0).getListNeuronios().get(i).getPesos().get(j)*instancia.getListAtributos().get(j).getValor();
                saida = fncIA_MLP_sigmoide(saida);
                
            }
            this.getCamadas().get(0).getListNeuronios().get(i).setSaida(saida);
        }
        //Outras camadas
        for(c = 1; c<this.getNumCamadas();c++){
            for(i = 0 ; i<this.getNumNeuronioCamada().get(c) ; i++){
                for(j = 0; j < this.getNumNeuronioCamada().get(c-1); j++){
                    saida = saida + this.getCamadas().get(0).getListNeuronios().get(i).getPesos().get(j)*this.getCamadas().get(c-1).getListNeuronios().get(j).getSaida();
                    saida = fncIA_MLP_sigmoide(saida);
                }
            this.getCamadas().get(i).getListNeuronios().get(i).setSaida(saida);
            }
        
        }
        
        
        
        
        
        return saida;
    }
    
    
    
    public Erro executaTeste(DadosNormalizados dados){
        ArrayList<Double> saida = new ArrayList<>();
        ArrayList<Double> esperado = new ArrayList<>();
        Double respostaRede;
        int i, j;
        
        for(i=0;i<dados.getNumInstancias();i++){
            //Propaga instancia
            this.propagaSinal(dados.getListInstancias().get(i));
            respostaRede = this.getCamadas().get(this.getNumCamadas()-1).getListNeuronios().get(this.getNumNeuronioCamada().get(this.getNumCamadas())).getSaida();
            saida.add(respostaRede);
            esperado.add(dados.getListInstancias().get(i).getEsperado());
            
            
            
        }
  
        
        Erro erroTeste = new Erro(esperado, saida);
        

        
        return erroTeste;
        
    }
    
    
    public Erro executaAlgoritmodeAprendizado(DadosNormalizados dados){
        ArrayList<Double> saida = new ArrayList<>();
        ArrayList<Double> esperado = new ArrayList<>();
        
        
        
        
        
        
        
        
        Erro erroTreinamento = new Erro(esperado, saida);
        return erroTreinamento;
    }
    
    public Rede copiaRede(){
        // Rede(int numCamadas,ArrayList<Integer> numNeuronioCamada,int numEntradas,  int numCiclos)
        
        Rede redeCopia = new Rede(this.getNumCamadas(), this.getNumNeuronioCamada(), this.getNumEntradas(), this.getNumCiclos());
        //Inicia rede com pesos aleatórios

        int i, j, l;
        int numeroPesos;
        ArrayList<Double> pesos = new ArrayList<>();
        ArrayList<Neuronio> listNeuronio = new ArrayList<>();
         
        Camada camada;
        Neuronio neuronio;
        ArrayList<Camada> camadas = new ArrayList<>();
        for(i=0;i<this.numCamadas;i++){
            camada = new Camada();
            camada.setNumNeuronios(this.numNeuronioCamada.get(i));
            listNeuronio = new ArrayList<>();
            for(j=0;j<camada.getNumNeuronios();j++){
                neuronio = new Neuronio();
                pesos = new ArrayList<>();
                  if(i==0){
                     numeroPesos = this.numEntradas;
                  }
                  else{
                     numeroPesos = this.camadas.get(i-1).getNumNeuronios();
                  }
                  for(l=0;l<numeroPesos;l++){
                       pesos.add(this.getCamadas().get(i).getListNeuronios().get(j).getPesos().get(l));
                      // System.out.println(x);
                  }

                neuronio.setPesos(pesos);
                neuronio.setBias(this.getCamadas().get(i).getListNeuronios().get(j).getBias());
                listNeuronio.add(neuronio);
 
            }
                camada.setListNeuronios(listNeuronio); 
                camadas.add(camada);
                redeCopia.setCamadas(camadas);

        }
        return redeCopia;
    }

    public int getNumCamadas() {
        return numCamadas;
    }

    public void setNumCamadas(int numCamadas) {
        this.numCamadas = numCamadas;
    }

    public ArrayList<Camada> getCamadas() {
        return camadas;
    }

    public void setCamadas(ArrayList<Camada> camadas) {
        this.camadas = camadas;
    }

    public ArrayList<Integer> getNumNeuronioCamada() {
        return numNeuronioCamada;
    }

    public void setNumNeuronioCamada(ArrayList<Integer> numNeuronioCamada) {
        this.numNeuronioCamada = numNeuronioCamada;
    }

    public int getNumCiclos() {
        return numCiclos;
    }

    public void setNumCiclos(int numCiclos) {
        this.numCiclos = numCiclos;
    }

    public int getNumEntradas() {
        return numEntradas;
    }

    public void setNumEntradas(int numEntradas) {
        this.numEntradas = numEntradas;
    }   
    
    
// Funcao sigmoide
double fncIA_MLP_sigmoide(double valor) {
	return (1.0 / (1.0 + exp((-1.0) * valor)));
}

// Derivada da funcao sigmoide
double fncIA_MLP_derivadaSigmoide(double valor) {
	return (valor * (1 - valor));
}

    
    
    
    
    
    
    
    
    
}


