package markovchains;

/*
 * Transition Probability Matrix class
 */
public class TransitionProbabilityMatrix {

  private String[] data;

  private String[] states;

  private double[][] tpm;

  /*
   * Constructor
   */
  public TransitionProbabilityMatrix(ProcessStates states) {
    this.data = states.getTrainData();
    this.states = states.getStates();
    this.tpm = tpm();
  }

  /*
   * Method that calculates the Transition Probability Matrix(TPM) of the process
   */
  public double[][] tpm() {
    double[][] tpm = new double[states.length][states.length];
    double d;
    double n;
    for (int i = 0; i < states.length; i++) {
      for (int j = 0; j < states.length; j++) {
        d = 0;
        n = 0;
        for (int z = 0; z < data.length - 1; z++) {
          // Counts the times that the state i appears in the data
          if (data[z].equals(states[i])) {
            d = d + 1;
          }
          //Assures that all the matrix lines sum 1
          if (z + 1 == data.length - 1 && d == 0) {
            d = 1;
          }
          // Counts the number of times the state j is right after the state i
          if (data[z].equals(states[i]) && data[z + 1].equals(states[j])) {
            n = n + 1;
          }
          //Assures that all the matrix lines sum 1
          if (
            z + 1 == data.length - 1 &&
            n == 0 &&
            data[z + 1].equals(states[j]) &&
            data[z + 1].equals(states[i])
          ) {
            n = 1;
          }
        }
        tpm[i][j] = n / d;
      }
    }

    return tpm;
  }

  //getter
  public double[][] getTpm() {
    return tpm;
  }
}
