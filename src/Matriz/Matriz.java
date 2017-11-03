package Matriz;


import static java.lang.Math.pow;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leandro
 */
public class Matriz {
    //VERIFICAR SE PASSAGEM DAS MATRIZES ESTA SENDO POR REFERENCIA OU COPIA
    
    double[][] matriz;
    int l, c;
    
    public Matriz(int l, int c){
        matriz = new double[l][c];
        this.l = l;
        this.c=c;
    }

    public double[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(double[][] matriz) {
        this.matriz = matriz;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
    
    
    
    

    public void tadMatriz_DecomposicaoLU(int n, double A[][], Matriz l, Matriz u) {
        int i, j, k;
        double[][] a = new double[n][n];
        double[][] lCop = new double[n][n];
        double[][] uCop = new double[n][n];
        double num = 0.0;
        for (i = 0; i < n; i++){
            for (j = 0; j < n; j++){
                a[i][j] =  A[i][j];
            }
        }
        for (j = 0; j < n; ++j) {
            // Elementos diagonal superior.
            for (i = 0; i <= j; ++i) {
              for (k = 0; k < i; ++k) {
                a[i][j] -= a[i][k] * a[k][j];
              }
            }
            // Elementos diagonal inferior.
            for (i = j + 1; i < n; ++i) {
              for (k = 0; k < j; ++k) {
                a[i][j] -= a[i][k] * a[k][j];
              }
            }
        // Determinar o elemento pivô para a coluna atual e reorganizar as
        // linhas.
        // Divide os elementos diagonais inferiores pelo valor diagonal.
        for (i = j + 1; i < n; ++i) {
          a[i][j] /= a[j][j];
        }
        
        
        
        for (j=0; j<n; ++j) {
          for(i=0; i<=j; ++i) {
            uCop[i][j] = a[i][j];
          }
        }

        for (i=0; i<n; i++) {
          for(j=0; j<=i; j++) {
            if(i==j) {
                num = 1;
            }
            else{
                num = a[i][j];
            }
            lCop[i][j] = num;
            System.out.println(l.getMatriz()[i][j]);
          }
        }
        



    }
    l.setMatriz(lCop);
    u.setMatriz(uCop);
        
    
    }
public double tadMatriz_Det_U(int n, double U[][]) {
    int i;
    double det = 1.0;
    for (i=0; i<n; i++) {
      det = det * U[i][i];
    }
    return det;
}


// ??? Pra que serve ???
public double[] tadMatriz_ResolveDecomposicaoLU(int n, int m, double L[][], double U[][], double B[]){
  int i, j, k;
  
  double []X = new double[n];
  double []Y = new double[n];
  /*for (i=0; i<n; i++) {
    X[i] = (double*)calloc(m, sizeof(double));  
    Y[i] = (double*)calloc(m, sizeof(double));  
  }*/
  
  for (i=0; i<n; i++) {
      Y[i]= B[i];
      for (k=0; k<i; k++) {
        Y[i] -= L[i][k]*Y[k];
      }
    
  }
  for (i=n-1; i>=0; i--) {
      X[i] = Y[i];
      for(k=i+1; k<n; k++) {
        X[i] -= U[i][k]*X[k];
      }
      X[i]/= U[i][i];
    
  }
 
  
  return X;
}


public double[][] tadMatriz_Transposta() {
  int i, j, aux;
  double[][] matriz2 = new double[this.getL()][this.getC()];
  
  for (i=0; i<l; i++) {
    for (j=0; j<c; j++) {
      matriz2[j][i] = this.getMatriz()[i][j];
    }
  }
  
  return matriz2;
}

// Cria a matriz identidade de tamanho [NxN].
public double[][] tadMatriz_Identidade(int n) {
  double[][] b = new double[n][n];
  
  for (int i=0; i<n; i++){
    for (int j=0; j<n; j++){
      if (i == j) {
        b[i][j] = 1.0;
      }
      else {
        b[i][j] = 0.0;
      }
    }
  }
  
  return b;
}


// Efetua o calculo da matriz resultante da soma de duas matrizes.
public double[][] tadMatriz_Soma(int l, int c,double matriz1[][], double matriz2[][]) {
  int i, j;
  
   double[][] matriz3 = new double[l][c];
   for(i=0;i<l;i++){
    for (j=0; j<c; j++) {
      matriz3[i][j] = matriz1[i][j] + matriz2[i][j];
    }
  }

  return matriz3;
}



// Efetua a multiplicacao de duas matrizes, retornando uma terceria matriz como resultado.
public double[][] tadMatriz_Multiplica(int numLinhasM1,int numColunasM1, int numColunasM2, double matriz1[][], double matriz2[][]){
 
  double[][] matriz3 = new double[numLinhasM1][numColunasM2];
/*
  printf("MATRIZ1\n");
  tadMatriz_ImprimeMatriz(2, 2, matriz1);
  printf("MATRIZ2\n");
  tadMatriz_ImprimeMatriz(2, 2, matriz2);
*/
  int i, j, k;
  double somaprod;
  for (i=0; i<numLinhasM1; i++) {
    for (j=0; j<numColunasM2; j++) {
      somaprod = 0.0;
      for (k=0; k<numColunasM1; k++) {
        somaprod = somaprod + (matriz1[i][k] * matriz2[k][j]); 

      }
      //printf("%f\n", somaprod);
      matriz3[i][j] = somaprod;
    }
  }
  return matriz3;
}


// Efetua a multiplicacao de uma matriz por uma constante, retornando uma nova matriz como resultado.
public double[][] tadMatriz_MultiplicaConstante(int numLinhas, int numColunas, double matriz1[][], double constante) {
  double[][] matriz3 = new double[numLinhas][numColunas];
  for (int i=0; i<numLinhas; i++){
    for (int j=0; j<numColunas; j++){
      matriz3[i][j] = matriz1[i][j] * constante;
    }
  }
  
  return matriz3;
}



// Calcula a matriz inversa, retornando o resultado em outra matriz.
double[][] tadMatriz_Inversa(int numLinhas, int numColunas, double matriz[][]){
  double deter;

  int num_Pesos = numLinhas;
 
  

  Matriz L = new Matriz(num_Pesos, num_Pesos);
  Matriz U = new Matriz(num_Pesos, num_Pesos);
  double[][] matriz3 = new double[num_Pesos][num_Pesos];

  this.tadMatriz_DecomposicaoLU(numLinhas, matriz, L, U);
  /*printf("MATRIZ l\n");
  tadMatriz_ImprimeMatriz(num_Pesos, num_Pesos, U);*/
        
    double detHessiana = tadMatriz_Det_U(num_Pesos,U.getMatriz());
    //printf("%.20f\n", detHessiana);



    
  //deter = tadMatriz_Det(matriz, numLinhas);

  
  tadMatriz_Inverse(matriz, matriz3, numLinhas, detHessiana);
  //tadMatriz_ImprimeMatriz(numLinhas,numLinhas, matriz3);
  
  return matriz3;
}

Double tadMatriz_Det(double a[][], int n) {
  int i,j,j1,j2;
  double det = 0;
  double [][]m = new double[n-1][n-1];

  if (n < 1) { /* Error */
    System.out.println("ERRO: Calculo do Determinante de uma matriz com numero de linhas menor que 1!\n");
    //exit();
  } else if (n == 1) { /* Shouldn't get used */
    det = a[0][0];
  } else if (n == 2) {
    det = a[0][0] * a[1][1] - a[1][0] * a[0][1];
  } else {
    det = 0;
    for (j1=0; j1<n; j1++) {
        m = new double[n-1][n-1];
      
      for (i=1; i<n; i++) {
        j2 = 0;
        for (j=0; j<n; j++) {
          if (j != j1) {
            m[i-1][j2] = a[i][j];
            j2++;
          }
        }
      }
      det += pow(-1.0,j1+2.0) * a[0][j1] * tadMatriz_Det(m, n-1);
      
      for (i=0; i<(n-1); i++) {
        //free(m[i]);
      }
      //free(m);
    }
  }
  return(det);
}

// Calcula matriz inversa
void tadMatriz_Inverse(double a[][], double d[][], int n, double det){
  if (det == 0.0) {
    //printf("\nNao existe matriz inversa\n");
  }
  else if (n == 1) {
    d[0][0] = 1;
  }
  else {
    tadMatriz_Cofactor(a, d, n, det);
  }
}


//Calcula cofator da matriz
public void tadMatriz_Cofactor(double a[][], double d[][], double n, double determinte){
    double [][]b = new double[(int)n][(int)n];
    double [][]c = new double[(int)n][(int)n];
  
  int l, h, m, k, i, j;
  for (h=0; h<n; h++) {
    for (l=0; l<n; l++) {
      m = 0;
      k = 0;
      for (i=0; i<n; i++) {
        for (j=0; j<n; j++) {
          if (i != h && j != l){
            b[m][k] = a[i][j];
            if (k < (n-2)) {
              k++;
            }
            else {
              k = 0;
              m++;
            }
          }
        }
      }
      c[h][l] = pow(-1,(h+l)) * tadMatriz_Det(b, (int)(n-1)); // c = cofactor da matriz
    }
  }
  tadMatriz_Transpose(c, d, n, determinte);
}

public void tadMatriz_ImprimeMatriz(int numLinhas, int numColunas, double matriz[][]){
  for (int i=0; i<numLinhas; i++) {
    for (int j=0; j<numColunas; j++) {
      System.out.print(matriz[i][j]);
    }
    System.out.print("\n");
  }
}

//  Calcula transposta da matriz e resulta na inversa
public void tadMatriz_Transpose(double c[][], double d[][], double n, double det){
  int i,j;
  double[][] b = new double[100][100];
  
  for (i=0; i<n; i++) {
    for (j=0; j<n; j++) {
      b[i][j] = c[j][i];
    }
  }
  for (i=0; i<n; i++) {
    for (j=0; j<n; j++) {
      d[i][j] = b[i][j]/det;  // array d[][] = matriz inversa
    }
  }
}

public boolean tadMatriz_Singular(double a[][], int n) {
  return true;
}

public void criaIdentidade(){
    int n = this.getC();
    int i, j;
    for(i=0;i<n;i++){
      for(j=0;j<n;j++){
          if(i==j)this.matriz[i][j] = 1;
          else this.matriz[i][j] = 0;
      }  
    }
    
    
}


}
