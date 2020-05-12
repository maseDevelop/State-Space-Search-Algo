/* 
Adapted from https://www.geeksforgeeks.org/expression-evaluation/
A Java program to evaluate a given expression where tokens are separated by space. 	 
*/


import java.util.*;

public class EvaluateString {

	private static HashMap<Character, Integer> precedence = new HashMap<>();

	public static Double evaluate(String expression) {
		
		try {
		
		final char[] tokens = expression.toCharArray();

		// Stack to store operators and values
		Deque<Double> values = new LinkedList<>();
		Deque<Character> ops = new LinkedList<>();

		// StringBuilder to create numbers
		StringBuilder sbuf = new StringBuilder();

		char newChar;
		int nextcharindex;
		boolean OneDecimalPoint;

		// Creating Precedence Dictionary
		precedence.put('(', 0);
		precedence.put(')', 0);
		precedence.put('^', 3);
		precedence.put('*', 2);
		precedence.put('/', 2);
		precedence.put('+', 1);
		precedence.put('-', 1);

			for (int i = 0; i < tokens.length; i++) {

				// Current token is a whitespace, skip it
				if (tokens[i] == ' ')
					continue;

				OneDecimalPoint = false; // making sure thier is only one decimal in the number 

				// Current token is a number, push it to stack for numbers
				if (tokens[i] >= '0' && tokens[i] <= '9') {
					// There may be more than one digits in number
					while (i < tokens.length && (tokens[i] >= '0' && tokens[i] <= '9') || (tokens[i] == '.') && (!OneDecimalPoint)){
						
						sbuf.append(tokens[i++]);
						if (i >= tokens.length)
							break;
						//Making sure there is only one decimal point in the term
						if(tokens[i] == '.')
							OneDecimalPoint = true; 
					}

					values.addFirst(Double.parseDouble(sbuf.toString()));
					// Clearing String Builder
					sbuf.setLength(0);
				}

				// Current token is an opening brace, push it to 'ops'
				else if (tokens[i] == '(')
					ops.addFirst(tokens[i]);

				// Closing brace encountered, solve entire brace
				else if (tokens[i] == ')') {
					while (ops.peekFirst() != '(')
						values.addFirst(applyOp(ops.removeFirst(), values.removeFirst(), values.removeFirst()));
					ops.removeFirst();
				}

				// Current token is an operator.
				else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {

					nextcharindex = i + 1;
					if (nextcharindex < tokens.length && tokens[i] == '*' && tokens[nextcharindex] == '*') {
						newChar = '^';
						i = i + 1;
					} else {
						newChar = tokens[i];
					}

					// While top of 'ops' has same or greater precedence to current
					// token, which is an operator. Apply operator on top of 'ops'
					// to top two elements in values stack

					while ((ops.peekFirst() != null) && hasPrecedence(newChar, ops.peekFirst()))
						values.addFirst(applyOp(ops.removeFirst(), values.removeFirst(), values.removeFirst()));

					// Push current token to 'ops'.
					ops.addFirst(newChar);
				}
			}

			// Entire expression has been parsed at this point, apply remaining
			// ops to remaining values
			while ((ops.peekFirst() != null))
				values.addFirst(applyOp(ops.removeFirst(), values.removeFirst(), values.removeFirst()));

			// Top of 'values' contains result, return it
			return values.removeFirst();

		} catch (Exception e) {
			System.out.println(e);
			return -0.0; 
		}
	}

	

	// Returns true if 'op2' has higher or same precedence as 'op1',
	// otherwise returns false.
	private static boolean hasPrecedence(char op1, char op2) {
		return (precedence.get(op1) < precedence.get(op2));
	}

	// A utility method to apply an operator 'op' on operands 'a'
	// and 'b'. Return the result.
	private static Double applyOp(char op, Double b, Double a) {
		switch (op) {
			case '+':
				return a + b;
			case '-':
				return a - b;
			case '*':
				return a * b;
			case '/':
				if (b == 0)
					throw new UnsupportedOperationException("Cannot divide by zero");
				return a / b;
			case '^':
				return (Double) Math.pow(a, b);
			default:
				return 0.0;
		}

	}

	public static void main(String[] args) {
		
		System.out.println(EvaluateString.evaluate("9 ( 8 + 1 )"));
		System.out.println(EvaluateString.evaluate("(44 / 4) - 44"));
		//System.out.println(EvaluateString.evaluate(" ( 4 * * 2  + 2 ) "));
		//System.out.println(EvaluateString.evaluate(" 44 * * 2.2  + 2"));
		//System.out.println(EvaluateString.evaluate("( 3.6 * 6 * 6 ) + 2"));
		//System.out.println(EvaluateString.evaluate("100 * 2 + 12"));
		//System.out.println(EvaluateString.evaluate("100 * ( 2 + 12 )"));
		//System.out.println(EvaluateString.evaluate("100 * ( 2 + 12 ) / 14"));
	
	}

	
}
