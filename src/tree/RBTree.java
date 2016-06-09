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
	 * @param parent Where to start browsing the tree from
	 */
	private void insert(int value, Node parent) {
		System.out.println(String.format("Inserting value %s on node %s with id %s...", value, parent.getValue(), parent.getId()));
		// Should we insert on the right? 
		if (value > parent.getValue()) {
			System.out.printf("Right side of %s \n", parent.getValue());
			// Is the right a leaf?
			if (parent.getRight() == null){
				System.out.println("Is Right leaf");
				// Create a new node
				maxId++;
				Node node = new Node (value, maxId, parent);
				
				parent.setRight(node);
				fixTree(node);
				return ;
			}
			
			// Right is not a leaf, we must descend
			else {
				System.out.println("Descending");
				insert (value, parent.getRight());
				return ;
			}
		}
		
		// Should we insert on the left?
		if (value <= parent.getValue()) {
			System.out.printf("Left side of %s \n", parent.getValue());
			// Is the left a leaf?
			if (parent.getLeft() == null){
				System.out.println("Is left leaf");
				// Create a new node
				maxId++;
				Node node = new Node (value, maxId, parent);
				
				parent.setLeft(node);
				fixTree(node);
				return ;
			}
			
			else {
				System.out.println("Descending");
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
	
	/**
	 * 
	 * Check node's sub tree. Fix if it isn't Red-Black Tree compliant
	 * 
	 * @param node
	 */
	private void fixTree(Node node) {
		Node parent = node.getParent();
		// All is well, return
		if (parent.isBlack()){
			return ;
		}
		 
		else if (!parent.isBlack()) {
			Node grandparent = parent.getParent();
			Node sibling = parent.getSibling() ;
			
			if(sibling == null){
				// If parent is the left hand son of grandparent
				if(grandparent.getLeft() != null) {
					fixLeft(node);
					
				}
				// If parent is the right hand son of grandparent
				else {
					fixRight(node);
				}
			}
			
			else if (sibling.isBlack()){
				// If parent is the left hand son of grandparent
				if(grandparent.getLeft().equals(parent)){
					fixLeft(node);
				}
				// If parent is the right hand son of grandparent
				else{
					fixRight(node);
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
	
	/**
	 * 
	 * Make the left hand side of a node obey 
	 * the properties of Red-Black Trees
	 * 
	 * @param node
	 */
	private void fixLeft(Node node){
		Node parent = node.getParent();
		Node grandparent = parent.getParent();
		
		// If the node is the left hand son
		if(parent.getLeft() != null) {
			if(parent.getLeft().equals(node)) {
				// Fix colours
				parent.setBlack();
				grandparent.setRed();
				// Fix relations
				grandparent.setLeft(parent.getRight());
				grandparent.getLeft().setParent(grandparent);
				
				parent.setRight(grandparent);
				// Check for possible new conflicts
				fixTree(parent);
				return ;
			}	
		}
		
		// If the node is the right hand child
		else {
			// Fix colours
			node.setBlack();
			grandparent.setRed();
			
			// Fix relations
			node.setLeft(parent);
			parent.setRight(null);
			
			node.setRight(grandparent);
			grandparent.setLeft(null);
			
			// Check for possible new conflicts
			fixTree(node);
			return ;
		}
	}
	
	/**
	 * 
	 * Make the right hand side of the tree obey
	 *  the properties of Red-Black Trees
	 * 
	 * @param node
	 */
	private void fixRight(Node node){
		Node parent = node.getParent();
		Node grandparent = parent.getParent();
		Node greatgrandparent = grandparent.getParent();
		
		// If node is the right hand son
		if(parent.getRight()!= null) {
			if (parent.getRight().equals(node)) {
				// Fix colours
				parent.setBlack();
				grandparent.setRed();
				// Fix relations
				// Save parent's left hand tree
				Node left = parent.getLeft();
				Node gp = parent.getParent();
				if (greatgrandparent != null) {
					if (greatgrandparent.getRight().equals(grandparent)) {
						greatgrandparent.setRight(parent);
					}
					else if (greatgrandparent.getLeft().equals(grandparent)) {
						greatgrandparent.setLeft(parent);
					}
				}
				parent.setLeft(gp);
				grandparent.setRight(left);	
				// Check for possible new conflicts
				fixTree(parent);
				return ;
			}
		}
		
		else {
			// Fix colours
			node.setBlack();
			grandparent.setRed();

			// Fix relations
			if (greatgrandparent != null) {
				if (greatgrandparent.getLeft().equals(grandparent)) {
					greatgrandparent.setLeft(node);
				}
				else if (greatgrandparent.getRight().equals(grandparent)){
					greatgrandparent.setRight(node);
				}
			}
			node.setRight(parent);
			parent.setLeft(null);
			
			node.setLeft(grandparent);
			
			// Check for possible new conflicts
			fixTree(node);
			return ;
		}
	}
	
	public boolean contains(int value) {
		return true;
	}
	
	/**
	 * Find the tree's maximum value.
	 */
	public void max() {
		int max = max(getRoot());
		
		System.out.printf("Maximum value is %s\n", max);
	}
	
	private int max(Node root) {
		if(root.getRight() == null) {
			return root.getValue();
		}
		else {
			return max(root.getRight());
		}
	}
	
	/**
	 * Find the tree's minimum value.
	 */
	public void min(){
		int min = min(getRoot());
		
		System.out.printf("Minimum value is %s\n", min);
	}
	
	private int min(Node root) {
		if (root.getLeft() == null) {
			return root.getValue() ;
		}
		else {
			return max(root.getLeft()) ;
		}
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
		node.print();
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
