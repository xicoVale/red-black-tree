package test;

import java.util.Scanner;

import tree.RBTree;

public class Test {
	private final static Scanner IN = new Scanner(System.in);
	private static RBTree tree;
	
	public static void main(String[] args) {
		System.out.print("$ Scale of test? [10 or 10000]: ");
		
		int scale = IN.nextInt();
		System.out.print("$ In reverse order? [y/n]: ");
		
		String rev = IN.next();
		boolean reverse = false;
		if (rev.equals("y") || rev.equals("yes")) {
			reverse = true;
		}
		
		int [] input;
		if (scale == 10){
			input = smallScale(reverse);
		}
		else {
			input = largeScale(reverse);
		}
		System.out.print("Seeding tree... ");
		populateTree(input);
		System.out.println(" Done.");
		
		while(true) {
			System.out.print("$");
			String command = IN.next();
			
			if (command.equals("help")) {
				help();
			}
			else if (command.equals("print")) {
				tree.print();
			}
			else if (command.equals("insert")) {
				int value = IN.nextInt();
				tree.insert(value);
			}
			else if (command.equals("delete")) {
				int value = IN.nextInt();
				tree.delete(value);
			}
			else if (command.equals("contains")) {
				int value = IN.nextInt();
				tree.contains(value);
			}
			else if (command.equals("find")) {
				int value = IN.nextInt();
				tree.find(value);
			}
			else if (command.equals("min")) {
				tree.min();
			}
			else if (command.equals("max")) {
				tree.max();
			}
		}
		
		
	}
	
	private static int [] smallScale(boolean reverse) {
		int [] input = new int [10];
		if (reverse) {
			for (int i = 10; i > 0; i--) {
				input[i] = i;
			}
		}
		else {
			for (int i = 0; i < 10; i++) {
				input[i] = i + 1;
			}
		}
		return input;
	}
	
	private static int [] largeScale(boolean reverse) {
		int [] input = new int [10000];
		if (reverse) {
			for (int i = 10000; i > 0; i--) {
				input[i] = i;
			}
		}
		else {
			for (int i = 0; i < 10000; i++) {
				input[i] = i + 1;
			}
		}
		return input;
	}
	
	/**
	 * Inserts the input into the tree, one value at a time.
	 * 
	 * @param input An int array with the input values.
	 */
	private static void populateTree(int[] input){
		for(int i = 0; i < input.length; i++) {
			if(i == 0) {
				tree = new RBTree(input[i]);
			}
			else {
				tree.insert(input[i]);
			}
		}
	}
	
	private static void help() {
		System.out.println("help - Prints list of commands.");
		System.out.println("print- Prints values in tree.");
		System.out.println("insert 'x' - Inserts value x into the tree.");
		System.out.println("delete 'x' - Deletes value x from the tree.");
		System.out.println("contains 'x' - True if value x is in the tree or false otherwise.");
		System.out.println("find 'x' - If x exists in the tree print it's value, it's parent and it's children if they exist.");
		System.out.println("min - Finds the minimum value in the tree");
		System.out.println("max - Finds the maximum value in the tree");
		System.out.println("stats 'c' - print costs of running command X");
	}

}
