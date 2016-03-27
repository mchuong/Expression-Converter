import java.util.Scanner;
import java.util.Stack;

/**
 * CS 240-01: Data Structures and Algorithms I
 * Professor: Dr. Fang Tang
 *
 * Programming Project #3
 *
 * Stack application that converts Infix expression to Postfix and/or Prefix.
 *
 * Michelle Chuong
 */

/**
 * @author Michelle Chuong
 */
public class Project3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		start();
	}
	
	public static void start() {
	    Scanner keyboard = new Scanner(System.in);
		
		System.out.print("Enter an expression in the Infix form: ");
		String expression = keyboard.nextLine();
		
		System.out.println();
		System.out.println("[1] Convert to Postfix");
		System.out.println("[2] Convert to Prefix");

		System.out.print("Command? ");
		int input = keyboard.nextInt();
		
		System.out.println();
		
		switch (input) {
			case 1: 
				infixToPostfix(expression);
				System.out.print("[Y/N] Convert same expression to Prefix? ");
				String a1 = keyboard.next();
				convertPrefix(expression, a1);
				break;
			case 2: 
				infixToPrefix(expression);
				System.out.print("[Y/N] Convert same expression to Postfix? ");
				String a2 = keyboard.next();
				convertPostfix(expression, a2);
				break;
			default:
				System.exit(0);
		}
		
		keyboard.close();
	}
	
	public static void convertPostfix(String expression, String repeat) {
		if (repeat.equalsIgnoreCase("Y")) {
			infixToPostfix(expression);
			newExpression();
		}
		else
			newExpression();
	}
	
	public static void convertPrefix(String expression, String repeat) {		
		if (repeat.equalsIgnoreCase("Y")) {
			infixToPrefix(expression);
			newExpression();
		}
		else
			newExpression();
	}
	
	public static void newExpression() {
		Scanner keyboard = new Scanner(System.in);
	
		System.out.print("[Y/N] Would you like to convert another expression? ");
		String repeat = keyboard.next();
		System.out.println();
		if (repeat.equalsIgnoreCase("Y"))
			start();
		else {
			System.out.println("Thank you for using the expression converter!");
			System.exit(0);
		}
		keyboard.close();
	}
	
	public static void infixToPostfix(String a) {
		String infix = a;
		String postfix = "";
		String str = "";
		String stackTop = "";
		Stack<String> stack =  new Stack<String>();
		
		for (int i = 0; i < infix.length(); i++) {
			str = infix.substring(i, i+1);
			if (str.matches("[a-zA-Z]"))	//is a Letter
				postfix += str;
			else if (str.equals("("))	//is left parenthesis
				stack.push(str);
			else if (str.equals(")")) {	//is right parenthesis
				while ((!(stack.peek().equals("("))) && (!(stack.isEmpty()))) {
					postfix += stack.pop();		//output everything until left parenthesis
				}
				if (stack.isEmpty()) {
					System.out.println("Mismatched parenthesis!");
					System.exit(0);
				}
				else
					stack.pop();	//pop left parenthesis
			}
			else if (str.equals(" ")) {
				
			}
			else {
				if (stack.isEmpty() && isOperator(str) == true)
					stack.push(str);
				else if ((stack.peek().equals("(")) && isOperator(str) == true)
					stack.push(str);
				else {
					stackTop = stack.peek();
					if (getPrecedence(stackTop, str).equals(stackTop)) {	//top of stack is > than str
						postfix += stack.pop();		//add top of stack to print string
					}
					stack.push(str);	//push str to stack
				}
			}
		}
		while (!(stack.isEmpty())) {	//if stack is not empty, add leftovers to print
			stackTop = stack.peek();
			if (stackTop.equals("(")) {		//missing right parenthesis
				System.out.println("Mismatched parenthesis!");
				System.exit(0);
			}
			else
				postfix += stack.pop();
		}
		
		System.out.println("Postfix: " + postfix);
		
	}
	
	public static boolean isOperator(String ch){
		String operators = "*/%+-";
		
		if (operators.indexOf(ch) != -1)
			return true;
		else
			return false;
	}
	
	public static String getPrecedence(String op1, String op2) {
		String multiOps = "*/%";
		String addOps = "+-";
		String leftParen = "(";
		    
		if ((multiOps.indexOf(op1) != -1) && (addOps.indexOf(op2) != -1))
			return op1;
		else if ((multiOps.indexOf(op1) != -1) && (multiOps.indexOf(op2) != -1))
			return op1;		
		else if ((multiOps.indexOf(op1) != -1) && (leftParen.indexOf(op2) != -1))
			return op1;
		
		else if ((leftParen.indexOf(op1) != -1) && (multiOps.indexOf(op2) != -1))
			return op2;
		else if ((leftParen.indexOf(op1) != -1) && (addOps.indexOf(op2) != -1))
			return op2;
		else if ((leftParen.indexOf(op1) != -1) && (leftParen.indexOf(op2) != -1))
			return op1;
		
		else if ((addOps.indexOf(op1) != -1) && (multiOps.indexOf(op2) != -1))
			return op2;
		else if ((addOps.indexOf(op1) != -1) && (addOps.indexOf(op2) != -1))
			return op1;
		else if ((addOps.indexOf(op1) != -1) && (leftParen.indexOf(op2) != -1))
			return op1;
		else
			return "-1";
	}
	
	public static void infixToPrefix(String a) {
		String infix = a;
		String prefix = "";
		String str = "";
		String stackTop = "";
		String op = "";
		String rightOperand = "";
		String leftOperand = "";
		String nexpress = "";
		Stack<String> operStack =  new Stack<String>();
		Stack<String> opanStack = new Stack<String>();
		
		for (int i = 0; i < infix.length(); i++) {
			str = infix.substring(i, i+1);
			if (str.matches("[a-zA-Z]"))	//is a Letter
				opanStack.push(str);
			else if (str.equals("("))	//is left parenthesis
				operStack.push(str);
			else if (str.equals(")")) {	//is right parenthesis
				while ((!(operStack.peek().equals("("))) && (!(operStack.isEmpty()))) {
					op = operStack.pop();
					rightOperand = opanStack.pop();
					leftOperand = opanStack.pop();
					nexpress = op.concat(leftOperand).concat(rightOperand);
					opanStack.push(nexpress);
				}
				if (operStack.isEmpty()) {
					System.out.println("Mismatched parenthesis!");
					System.exit(0);
				}
				else
					operStack.pop();	//pop left parenthesis
			}
			else if (str.equals(" ")) {
			
			}	
			else {
				if (operStack.isEmpty() && isOperator(str) == true)
					operStack.push(str);
				else if ((operStack.peek().equals("(")) && isOperator(str) == true)
					operStack.push(str);
				else {
					stackTop = operStack.peek();
					if (getPrecedence(stackTop, str).equals(stackTop)) {	//top of stack is > than str
						op = operStack.pop();
						rightOperand = opanStack.pop();
						leftOperand = opanStack.pop();
						nexpress = op.concat(leftOperand).concat(rightOperand);
						opanStack.push(nexpress);
					}
					operStack.push(str);	//push str to stack
				}
			}
		}
		while (!(operStack.isEmpty())) {	//if stack is not empty, add leftovers to print
			stackTop = operStack.peek();
			if (stackTop.equals("(")) {
				System.out.println("Mismatched parenthesis!");
				System.exit(0);
			}
			else {
				op = operStack.pop();
				rightOperand = opanStack.pop();
				leftOperand = opanStack.pop();
				nexpress = op.concat(leftOperand).concat(rightOperand);
				opanStack.push(nexpress);
			}
		}
		
		prefix += opanStack.pop();
		
		System.out.println("Prefix: " + prefix);
		
	}

}
