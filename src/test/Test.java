package test;

import java.util.Scanner;

import tree.RBTree;

public class Test {
	private static Scanner in = new Scanner(System.in);
	private static RBTree tree;
	
	public static void main(String[] args) {
		int [] input = readInput();
		
		populateTree(input);
		
		tree.print();
		
		tree.max();
		
		tree.min();
		
		System.out.println(tree.contains(5));
		System.out.println(tree.contains(6));
	}
	
	/**
	 * 
	 * Process input. The first value should be the number of nodes in the tree.
	 * This should be followed by the values that will be in the tree.
	 * 
	 * @return And int array with the input values.
	 */
	private static int [] readInput(){
		int length = in.nextInt();
		
		int [] input = new int [length];
		
		for (int i = 0; i < length; i++){
			input[i] = in.nextInt();
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
				System.out.println(String.format("Inserting value %s as root... ", input[i]));
				tree = new RBTree(input[i]);
			}
			else {
				System.out.println(String.format("Inserting value %s... ", input[i]));
				tree.insert(input[i]);
			}
		}
	}

}
