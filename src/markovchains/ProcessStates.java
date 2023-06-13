package markovchains;

import java.util.Stack;

public class ProcessStates {

  //Matrix of data
  private String[] data;

  //Matrix of the posible states of the process
  private String[] states;

  //Data used for training
  private String[] trainData;

  //Data used for testing
  private String[] testData;

  //Initial State
  private double[] initialState;

  /*
   * Creates an instance of the ProcessStates class
   */
  public ProcessStates(String[] data) {
    this.data = data;
    this.states = determineStates();
    this.trainData = trainData();
    this.testData = testData();
    this.initialState = initialState();
  }

  /*
   * Method to determine the states of the process
   */
  public String[] determineStates() {
    String[] sortedData = sortedData(data);
    int n = numberOfStates(sortedData);
    String[] states = new String[n];
    String m = sortedData[0];
    states[0] = m;
    int h = 0;

    for (int i = 1; i < n; i++) {
      for (int j = h; j < sortedData.length; j++) {
        if (!m.equals(sortedData[j])) {
          m = sortedData[j];
          h = j;
          j = sortedData.length;
        }
      }
      states[i] = m;
    }
    return states;
  }

  /*
   *Method that sorts the data
   */
  public String[] sortedData(String[] data) {
    Stack<String> s = new Stack<>();
    String[] sortedData = new String[data.length];

    int z = 0;
    boolean stop = true;
    String m;
    boolean already = true;
    int h = 0;
    while (stop) {
      m = data[z];
      already = true;

      //It determinates if the process m has been already included in the matrix of sorted data
      for (int j = 0; j < sortedData.length; j++) {
        if (m.equals(sortedData[j])) {
          already = false;
        }
      }

      //fill the stack with the process m
      for (int i = 0; i < data.length; i++) {
        if (m.equals(data[i]) && already) {
          s.push(data[i]);
        }
      }
      int n = s.size();

      //fill the matrix of sorted data with the elements of the stack
      for (int i = h; i < n + h; i++) {
        sortedData[i] = s.pop();
      }

      h = h + n;
      z = z + 1;
      if (z >= data.length) {
        stop = false;
      }
    }
    return sortedData;
  }

  /*
   * Method to determine the number of possible states of the process
   */
  public int numberOfStates(String[] sortedData) {
    String[] oData = sortedData;
    int n = 1;
    for (int i = 0; i < oData.length - 1; i++) {
      if (!oData[i].equals(oData[i + 1])) {
        n = n + 1;
      }
    }
    return n;
  }

  /*
   * Method to separate the data that will be used for training
   */
  public String[] trainData() {
    int totalData = data.length;
    float trainData = (float) (totalData);
    int trainingData = Math.round(trainData);
    String[] dataForTraining = new String[trainingData];

    for (int i = 0; i < trainingData; i++) {
      dataForTraining[i] = data[i];
    }

    return dataForTraining;
  }

  /*
   * Method to separate the data that will be used for testing
   */
  public String[] testData() {
    int totalData = data.length;
    float testData = (float) (totalData * 0.2);
    int testingData = Math.round(testData);
    String[] dataForTest = new String[testingData];

    for (int i = 0; i < testingData; i++) {
      dataForTest[i] = data[i + testingData];
    }

    return dataForTest;
  }

  /*
   * Method that determines the initial state of our process to test our Markov Chain
   */
  public double[] initialState() {
    double[] initialState = new double[states.length];

    for (int i = 0; i < states.length; i++) {
      if (states[i].equals(trainData[trainData.length - 1])) {
        initialState[i] = 1;
      } else {
        initialState[i] = 0;
      }
    }
    return initialState;
  }

  /*
   * Getters
   */
  public String[] getStates() {
    return states;
  }

  public String[] getData() {
    return data;
  }

  public String[] getTrainData() {
    return trainData;
  }

  public String[] getTestData() {
    return testData;
  }

  public double[] getInitialState() {
    return initialState;
  }
}
