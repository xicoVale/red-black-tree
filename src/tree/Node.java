package tree;

public class Node {
	private int value;
	private int id;
	private Node left = null;
	private Node right = null;
	private Node parent = null;
	
	private String colour;
	
	public Node (){}
	
	// To be used when creating the root
	public Node (int value){
		setValue(value);
		setId(0);
	}
	
	// To be used when creating non root nodes
	public Node (int value, int id, Node parent) {
		setValue(value);
		setId(id);
		setParent(parent);
		setRed();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public String getColour() {
		return colour;
	}

	public void setRed(){
		this.colour = "red";
	}
	
	public void setBlack(){
		this.colour = "black";
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
	/**
	 * Returns a node's sibling or null if none exists
	 * 
	 * @param node
	 * @return
	 */
	public Node getSibling() {
		Node parent = getParent();
		
		if (parent.getLeft() == null) {
			return null ;
		}
		
		else if (parent.getLeft().getValue() == getValue()) {
			if (parent.getRight() == null) {
				return null;
			}
			else {
				return parent.getRight();
			}
		}
		
		return parent.getLeft();
	}
	/**
	 * Check a nodes colour
	 * 
	 * @return True if the node is black or false otherwise
	 */
	public boolean isBlack() {
		if (getColour().equals("black")){
			return true;
		}
		return false;
	}
	
	/**
	 * Two nodes are equal they have the same id.
	 */
	public boolean equals(Node node){
		if (getValue() == node.getValue()) {
			if (getParent().equals(node.getParent())){
				return true;
			}
		}
		
		return false;
	}
	

}
