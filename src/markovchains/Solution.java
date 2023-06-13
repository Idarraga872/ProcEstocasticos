package markovchains;

import java.io.IOException;
import javax.swing.JOptionPane;
/*
 * Class to print out the solution
 */
public class Solution {

  public static void main(String[] args) throws IOException {
    // TODO Auto-generated method stub

    FileReaderMC fr = new FileReaderMC();
    String[] model = fr.readFile();
    ProcessStates ps = new ProcessStates(model);
    TransitionProbabilityMatrix tpm = new TransitionProbabilityMatrix(ps);
    MarkovChain mc = new MarkovChain(ps, tpm);

    String[] processStates = ps.getStates();
    double[][] tPm = tpm.getTpm();
    double[] longTP = mc.longTermProbs();
    double[] steps = mc.getSteps();

    System.out.println("The process states are:");
    for (int i = 0; i < processStates.length; i++) {
      System.out.println(processStates[i]);
    }
    System.out.println();

    System.out.println("The transition probability matrix is: ");

    for (double[] fila : tPm) {
      for (double i : fila) {
        System.out.print(i + "       ");
      }
      System.out.println();
    }

    System.out.println();
    System.out.println(
      "The long term probabilities of the states are: "
    );
    for (int i = 0; i < processStates.length; i++) {
      System.out.println(processStates[i] + " " + longTP[i]);
    }
    System.out.println();
    System.out.println(
      "The average time of first return of the states are: "
    );
    for (int i = 0; i < processStates.length; i++) {
      System.out.println(processStates[i] + " " + steps[i]);
    }

    System.out.println();

    for (int i = 0; i < processStates.length / 6; i++) {
      double[] firstStep = mc.getFirstStep(i);
      System.out.println(
        "The average time of first step for the state " +
        processStates[i] +
        " is: "
      );
      for (int j = 0; j < processStates.length; j++) {
        if (j != i) {
          System.out.print(processStates[j] + "  ");
        }
        if (j < i) {
          System.out.println(firstStep[j]);
        } else if (j == i) {} else {
          System.out.println(firstStep[j - 1]);
        }
      }
    }

    System.out.println();

    String[] testdata = ps.getTestData();
    double n;
    double esperado = 0;
    double p = 0;
    double promedio = 0;
    for (int i = 0; i < processStates.length; i++) {
      n = 0;
      for (int j = 0; j < testdata.length; j++) {
        if (testdata[j].equals(processStates[i])) {
          n = n + 1;
        }
      }
      double a = (double) testdata.length;
      esperado = n / a;
      p = Math.abs((esperado - longTP[i]) / longTP[i]);
      promedio = promedio + p;
    }
    System.out.print("The long-term success rate is ");
    System.out.println((1 - (promedio / processStates.length)) * 100);
    System.out.println();
    System.out.println("Prediction ");
    System.out.println();
    String[] prediction = mc.predictedStates();
    double h = 0;
    for (int i = 0; i < testdata.length; i++) {
      if (prediction[i].equals(testdata[i])) {
        h = h + 1;
      }
    }
    for (int i = 0; i < prediction.length / 6; i++) {
      System.out.println(testdata[i]);
    }
    double z = (double) testdata.length;
    double m = h / z;
    System.out.println();
    System.out.print("The success rate of the simulation is ");
    System.out.println(m * 100);
    /*
		String n=JOptionPane.showInputDialog("Introduce el numero de estados");
		int m=Integer.parseInt(n);
		
		String[] states=new String[m];
		for(int i=0;i<m;i++) {
			states[i]=JOptionPane.showInputDialog("Introduce el estado " + i);
		}
		
		double [][] tpm=new double[m][m];
		
		for(int i=0;i<m;i++) {
			for(int j=0;j<m;j++) {
				tpm[i][j]=Double.parseDouble(JOptionPane.showInputDialog("Introduce la probabilidad de pasar del estado " + states[i] + 
						" al estado " + states[j]));
			}
		}
		
		MarkovChain mc=new MarkovChain(states,tpm);
		double[] longTP=mc.longTermProbs();
		double[] steps=mc.getSteps();
		
		System.out.println("Los estados del proceso son:");
		for(int i=0;i<states.length;i++) {
			System.out.println(states[i]);
		}
		System.out.println();
		
		System.out.println("La matriz de probabilidades de transici�n es: ");
		
		for(double[]fila:tpm) {
 			
 			for(double i:fila) {
 				
 				System.out.print(i + "       ");
 			}
 			System.out.println();
		}
		
		System.out.println();
		System.out.println("Las probabilidades de largo plazo de los estados son: ");
		for(int i=0;i<states.length;i++) {
			System.out.println(states[i] + " " + longTP[i]);
		}
		System.out.println();
		System.out.println("El tiempo promedio de primer retorno de los estados es: ");
		for(int i=0;i<states.length;i++) {
			System.out.println(states[i] + " " + steps[i]);
		}
		System.out.println();
		
		for(int i=0;i<states.length;i++) {
			double []firstStep=mc.getFirstStep(i);
			System.out.println("El tiempo promedio de primer paso para el estado " + states[i] + " es: ");
			for(int j=0;j<states.length;j++) {
				if(j!=i) {
					System.out.print(states[j] + "  ");
				}
				if(j<i) {
					System.out.println(firstStep[j]);
				}else if(j==i) {
					
				} else {
					System.out.println(firstStep[j-1]);
				}
			}
		}*/
  }
}
