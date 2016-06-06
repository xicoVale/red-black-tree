package tree;

public class RBTree {
	private Node root;
	private int maxId = 1;
	
	public RBTree () {}
	
	public RBTree (int value) {
		Node root = new Node (value);
		root.setBlack();
		setRoot(root);
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
	
	/**
	 * 
	 * Insert a value into the tree
	 * 
	 * @param value The value to be inserted
	 */
	public void insert(int value) {
		insert(value, root);
	}
	
	/**
	 * Insert a value into the tree
	 * 
	 * @param value The value to insert
	 * @param parent Where to start browsing the tree from, should be the root
	 */
	private void insert(int value, Node parent) {
		System.out.println(String.format("Inserting value %s on node %s with id %s...", value, parent.getValue(), parent.getId()));
		// Should we insert on the right? 
		if (value > parent.getValue()) {
			// Is the right a leaf?
			if (parent.getRight() == null){
				// Create a new node
				maxId++;
				Node node = new Node (value, maxId, parent);
				
				parent.setRight(node);
				fixTree(node);
				return ;
			}
			
			// Right is not a leaf, we must descend
			else {
				insert (value, parent.getRight());
				return ;
			}
		}
		
		// Should we insert on the left?
		if (value < parent.getValue()) {
			// Is the left a leaf?
			if (parent.getLeft() == null){
				// Create a new node
				maxId++;
				Node node = new Node (value, maxId, parent);
				
				parent.setLeft(node);
				fixTree(node);
				return ;
			}
			
			else {
				insert (value, parent.getLeft());
				return ;
			}
		}
	}
	
	public void delete(int value) {
		delete(root);
	}
	
	private void delete(Node node) {
		
	}
	
	private void fixTree(Node node) {
		Node parent = node.getParent();
		// All is well, return
		if (parent.isBlack()){
			return ;
		}
		
		else {
			Node grandparent = parent.getParent();
			Node sibling = parent.getSibling() ;
			
			if(sibling == null || sibling.isBlack()) {
				// If parent is the left hand son of grandparent
				if(sibling == null || parent.getValue() < sibling.getValue()) {
					if(parent.getLeft() != null && parent.getLeft().equals(node)){
						// Fix colours
						parent.setBlack();
						grandparent.setRed();
						// Fix relations
						grandparent.setLeft(parent.getRight());
						grandparent.getLeft().setParent(grandparent);
						
						parent.setRight(grandparent);
						grandparent.setParent(parent);
						// Check for possible new conflicts
						fixTree(parent);
						return ;
					}
					
					else {
						// Fix colours
						node.setBlack();
						grandparent.setRed();
						
						// Fix relations
						node.setLeft(parent);
						parent.setParent(node);
						parent.setRight(null);
						
						node.setRight(grandparent);
						grandparent.setParent(node);
						
						// Check for possible new conflicts
						fixTree(node);
						return ;
					}
					
				}
				// If parent is the right hand son of grandparent
				else {
					if(parent.getRight().equals(node)){
						// Fix colours
						parent.setBlack();
						grandparent.setRed();
						// Fix relations
						grandparent.setRight(parent.getLeft());
						grandparent.getLeft().setParent(grandparent);
						
						parent.setLeft(grandparent);
						grandparent.setParent(parent);
						// Check for possible new conflicts
						fixTree(parent);
						return ;
					}
					
					else {
						// Fix colours
						node.setBlack();
						grandparent.setRed();
						
						// Fix relations
						node.setRight(parent);
						parent.setParent(node);
						parent.setLeft(null);
						
						node.setLeft(grandparent);
						grandparent.setParent(node);
						
						// Check for possible new conflicts
						fixTree(node);
						return ;
					}
				}
				
			}
			
			else {
				parent.setBlack();
				sibling.setBlack();
				
				if (!grandparent.equals(getRoot())){
					grandparent.setRed();
					fixTree(grandparent);
				}
				
				return ;
			}
		}
		
	}
	
	public boolean contains(int value) {
		return true;
	}
	
	public int max() {
		return 0;
	}
	
	public int min(){
		return 0;
	}
	
	/**
	 * Prints the tree's values and node ids in order.
	 */
	public void print() {
		System.out.println("Tree values:");
		print(root);
		
		System.out.println("\nNode ids:");
		printIds(root);
	}
	
	private void print(Node node){
		if (node == null) {
			return ;
		}
		
		print(node.getLeft());
		System.out.print(node.getValue() + " ");
		print(node.getRight());
	}
	
	private void printIds(Node node){
		if (node == null) {
			return ;
		}
		
		print(node.getLeft());
		System.out.print(node.getId() + " ");
		print(node.getRight());
	}

}
