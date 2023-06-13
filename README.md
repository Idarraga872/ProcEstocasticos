# Markov Chains Project
## About this repository
This repository provides the necessary code to perform a Markov Chains project. The user only requires a txt file with the data. This repository includes classes and functions for the process states, the transition probability matrix in time, long term probabilities, etc.

### File Reader
In this repository, a File Reader class is included to read txt files with the data. The data should consist of the states of the process, and it should be organized in a 1 column structure. Multiple examples are given.

### Markov Chain class
This class includes functions for calculating the Transition Probability Matrix in time, give the predicted states for future periods of time, calculates the long term probabilities of the process states, calculates the average time of first step of the different states and the average time of first return of the states.
In this class a lot of Linear Algebra is performed such as matrix multiplication, Gauss Jordan method, calculation of inverse matrices and Gauss jordan but according to the laws of inverting matrices.

### Process States class
This class retrieves the different process states from the txt file, and separates the data in Train data and test data. Also, it determines the initial states for the simulation.

### Solution class
The Solution class prints out all the results of the Markov Chains problem, including the Transition Probability Matrix in time, the predicted states for future periods of time, the long term probabilities of the process states, the average time of first step of the different states and the average time of first return of the states.
