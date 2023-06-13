package markovchains;

/*
 * Markov Chain class to perform all the calculations
 */
public class MarkovChain {

  private double[][] tpm;

  private String[] states;

  private String[] testData;

  private double[] initialState;

  //private String[] predictedStates;

  //Constructor 1
  public MarkovChain(ProcessStates states, TransitionProbabilityMatrix tpm) {
    this.tpm = tpm.getTpm();
    this.states = states.getStates();
    this.testData = states.getTestData();
    this.initialState = states.getInitialState();
  }

  // Constructor 2
  public MarkovChain(String[] states, double[][] tpm) {
    this.states = states;
    this.tpm = tpm;
  }

  /*
   * Method to calculate the Transition Probability Matrix (TPM) in time
   */
  public double[][] TpmInTime(int n) {
    double[][] tpmIT = new double[tpm.length][tpm[0].length];
    //Create a auxiliary matrix for the following calculations
    double[][] tpmT = new double[tpm.length][tpm[0].length];
    //fill the matrix with the tpm
    for (int i = 0; i < tpm.length; i++) {
      for (int j = 0; j < tpm[0].length; j++) {
        tpmT[i][j] = tpm[i][j];
      }
    }

    double suma;

    if (n == 1) {
      tpmIT = tpm;
    }
    //Calculates the multiplication between Arrays
    else {
      for (int z = 1; z < n; z++) {
        for (int i = 0; i < tpm.length; i++) {
          for (int j = 0; j < tpm[0].length; j++) {
            suma = 0;
            for (int k = 0; k < tpm[0].length; k++) {
              //Here we multiply the matrix
              suma += tpmT[i][k] * tpm[k][j];
            }
            tpmIT[i][j] = suma;
          }
        }

        for (int i = 0; i < tpm.length; i++) {
          for (int j = 0; j < tpm[0].length; j++) {
            //Here we update the auxiliary matrix
            tpmT[i][j] = tpmIT[i][j];
          }
        }
      }
    }
    return tpmIT;
  }

  /*
   * Method that simulates and predicts
   */
  public String[] predictedStates() {
    double[][] tpmIT;
    String[] predictedStates = new String[testData.length];
    double[] x = new double[initialState.length];
    double max;
    int maxi = 0;
    boolean finish = true;
    double suma;
    //fill the predictedStates array
    for (int i = 0; i < predictedStates.length; i++) {
      max = Math.random();
      tpmIT = TpmInTime(i + 1);
      finish = true;
      suma = 0;
      for (int h = 0; h < x.length; h++) {
        //find the predicted state using a random number and the TPM in time
        if (initialState[h] == 1) {
          for (int k = 0; k < tpm[0].length; k++) {
            suma = suma + tpmIT[h][k];
            if (max < suma && finish) {
              finish = false;
              maxi = k;
              break;
            }
          }
        }
      }
      //fill the array
      predictedStates[i] = states[maxi];

      //change the initial state for the next prediction
      for (int h = 0; h < initialState.length; h++) {
        if (states[h].equals(predictedStates[i])) {
          initialState[h] = 1;
        } else {
          initialState[h] = 0;
        }
      }
    }

    return predictedStates;
  }

  /*
   * Method to fill the array that will be used to calculate the long term probabilities
   */
  public double[][] longTermProbsMatrix() {
    double[][] longTermProbsMatrix = new double[tpm.length + 1][tpm.length + 1];

    for (int i = 0; i < longTermProbsMatrix.length; i++) {
      for (int j = 0; j < longTermProbsMatrix.length; j++) {
        if (i == 0) {
          longTermProbsMatrix[i][j] = 1;
        } else if (i == j + 1 && j != longTermProbsMatrix.length - 1) {
          longTermProbsMatrix[i][j] = tpm[i - 1][j] - 1;
        } else if (j != longTermProbsMatrix.length - 1) {
          longTermProbsMatrix[i][j] = tpm[j][i - 1];
        } else {
          longTermProbsMatrix[i][j] = 0;
        }
      }
    }
    return longTermProbsMatrix;
  }

  /*
   * Method to calculate the long term probs
   */
  public double[] longTermProbs() {
    double[][] longTermProbsMatrix = longTermProbsMatrix();
    double[] longTermProbs = new double[longTermProbsMatrix.length - 1];
    double m = 0;
    double n = 0;
    double h = 0;
    double a = 0;
    //fill the array longTermProbs
    for (int i = 0; i < longTermProbs.length; i++) {
      //Gauss jordan of the longTermProbsMatrix Matrix
      for (int j = 0; j < longTermProbsMatrix.length - 1; j++) {
        n = longTermProbsMatrix[j + 1][i];
        if (i == j) {
          a = longTermProbsMatrix[j - i][i];
        }

        for (int k = 0; k < longTermProbsMatrix.length; k++) {
          //Diagonal of ones
          if (i == k) {
            m = longTermProbsMatrix[i][k];
            longTermProbsMatrix[i][k] = m / m;
            //Divides each element of the line between the element of the diagonal
            for (int z = k + 1; z < longTermProbsMatrix.length; z++) {
              longTermProbsMatrix[i][z] = longTermProbsMatrix[i][z] / m;
            }
          }
          //reduce the array
          if (i != j + 1) {
            h = longTermProbsMatrix[j + 1][k];
            longTermProbsMatrix[j + 1][k] = h - (n * longTermProbsMatrix[i][k]);
          }
          if (longTermProbsMatrix[i][i] == 1 && j == i && k != 0 && i > 0) {
            h = longTermProbsMatrix[j - i][k];
            longTermProbsMatrix[j - i][k] = h - (a * longTermProbsMatrix[i][k]);
          }
        }
      }
    }
    //fill the array
    for (int i = 0; i < longTermProbs.length; i++) {
      longTermProbs[i] = longTermProbsMatrix[i][longTermProbsMatrix.length - 1];
    }

    return longTermProbs;
  }

  /*
   * Returns the average time of first return of the states
   */
  public double[] getSteps() {
    double[] longTermProbs = longTermProbs();
    double[] steps = new double[longTermProbs.length];

    for (int i = 0; i < longTermProbs.length; i++) {
      steps[i] = 1 / longTermProbs[i];
    }

    return steps;
  }

  /*
   * Calculates the average time of first step of the steps
   */
  public double[] getFirstStep(int g) {
    double[] firstStep = new double[states.length - 1];
    double[][] N = new double[tpm.length - 1][tpm.length - 1];
    double[][] A = new double[N.length][N.length];
    boolean mayor;
    boolean mayor2 = true;
    //fill the N array ignoring the state g
    for (int j = 0; j < N.length; j++) {
      mayor = true;
      if (j != g && mayor2) {
        for (int k = 0; k < N.length; k++) {
          if (k != g && mayor) {
            N[j][k] = tpm[j][k];
          } else {
            N[j][k] = tpm[j][k + 1];
            mayor = false;
          }
        }
      } else {
        mayor2 = false;
        for (int k = 0; k < N.length; k++) {
          if (k != g && mayor) {
            N[j][k] = tpm[j + 1][k];
          } else {
            N[j][k] = tpm[j + 1][k + 1];
            mayor = false;
          }
        }
      }
    }

    double[][] ones = new double[N.length][N.length];
    //Fill the array of ones
    for (int j = 0; j < ones.length; j++) {
      for (int k = 0; k < ones.length; k++) {
        if (j == k) {
          ones[j][k] = 1;
        } else {
          ones[j][k] = 0;
        }
      }
    }
    //Calculates the array A that will be inverted
    for (int j = 0; j < ones.length; j++) {
      for (int k = 0; k < ones.length; k++) {
        A[j][k] = ones[j][k] - N[j][k];
      }
    }
    //Fills the array that will be used to invert the array A
    double[][] inv = new double[N.length][2 * N.length];
    for (int j = 0; j < inv.length; j++) {
      int z = 0;
      for (int k = 0; k < inv[0].length; k++) {
        if (k > (inv[0].length / 2) - 1) {
          inv[j][k] = ones[j][z];
          z = z + 1;
        } else {
          inv[j][k] = A[j][k];
        }
      }
    }
    System.out.println();
    double m = 0;
    double n = 0;
    double h = 0;
    double a = 0;
    //Gauss jordan but according to the laws of inverting arrays (Linear Algebra)
    for (int i = 0; i < inv.length; i++) {
      for (int j = 0; j < inv.length - 1; j++) {
        n = inv[j + 1][i];
        if (i == j) {
          a = inv[j - i][i];
        }

        for (int k = 0; k < inv[0].length; k++) {
          if (i == k) {
            m = inv[i][k];
            inv[i][k] = m / m;
            for (int z = k + 1; z < inv[0].length; z++) {
              inv[i][z] = inv[i][z] / m;
            }
          }

          if (i != j + 1) {
            h = inv[j + 1][k];
            inv[j + 1][k] = h - (n * inv[i][k]);
          }
          if (inv[i][i] == 1 && j == i && k != 0 && i > 0) {
            h = inv[j - i][k];
            inv[j - i][k] = h - (a * inv[i][k]);
          }
          if (inv[i][i] == 1 && k == i && i == inv.length - 1) {
            a = inv[0][i];
          }
          if (inv[i][i] == 1 && k != 0 && i == inv.length - 1) {
            h = inv[0][k];
            inv[0][k] = h - (a * inv[i][k]);
          }
        }
      }
    }
    //fill the Array A now inverted
    for (int j = 0; j < inv.length; j++) {
      int z = 0;
      for (int k = 0; k < inv[0].length; k++) {
        if (k > (inv[0].length / 2) - 1) {
          A[j][z] = inv[j][k];
          z = z + 1;
        }
      }
    }
    //Fill the array of ones
    double[] one = new double[A.length];
    for (int i = 0; i < one.length; i++) {
      one[i] = 1;
    }
    double suma;
    //Multiplication of the A array and a vector of ones
    for (int j = 0; j < A[0].length; j++) {
      suma = 0;
      for (int k = 0; k < A[0].length; k++) {
        // Here we multiply the matrix
        suma += A[j][k] * one[k];
      }
      firstStep[j] = suma;
    }

    return firstStep;
  }
}
