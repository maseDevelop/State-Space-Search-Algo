import java.util.*;

public class Lab2 {

	private static Queue<String> queueFrontier = new LinkedList<>();
	private static String startState = "4";

	public static void main(String[] args) {

		try {

			String currState;
			double startNum = 7;
			double returnedNum;

			// Getting the start state ready
			queueFrontier.add(startState);

			while (queueFrontier.peek() != null) {

				// Getting the current State
				currState = queueFrontier.remove();

				if (currState != null) {

					// Check if the equation is viable then print it out as a success
					returnedNum = EvaluateString.evaluate(currState);
					//System.out.println(returnedNum);
					System.out.println(currState);

					if(Double.compare(startNum,returnedNum) == 0){
						System.out.println("FOUND SOLUTION: " + currState);
						System.exit(0);
					}
					else if(returnedNum != -0.0){		
						// Expand the state space
						expand(currState);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error" + e);
		}
	}

	private static void expand(String CurrentState) {
		String[] returnValues = new String[8];

		// Concating operators
		returnValues[0] = CurrentState + " + 4";
		returnValues[1] = CurrentState + " - 4";
		returnValues[2] = CurrentState + " * 4";
		returnValues[3] = CurrentState + " / 4";
		returnValues[4] = CurrentState + " ** 4";
		returnValues[5] = CurrentState + "4";
		returnValues[6] = CurrentState + ".4";
		returnValues[7] = " ( " + CurrentState + " ) ";

		// Putting new states on the frontier
		for (int i = 0; i < returnValues.length; i++) {
			queueFrontier.add(returnValues[i]);
			returnValues[i] = null;
		}
	}
}
