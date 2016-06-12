package tree;

public class RBTree {
	private Node root;
	private int maxId = 0;
	
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
	
	public boolean isRoot(Node node) {
		if (node == null) {
			return false;
		}
		
		else if (getRoot().equals(node)) {
			return true;
		}
		return false;
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
			if (parent.getRight() != null){
				System.out.println("Descending");
				insert (value, parent.getRight());
				return ;
			}
			
			// Right is not a leaf, we must descend
			else {
				System.out.println("Is Right leaf");
				// Create a new node
				maxId++;
				Node node = new Node (value, maxId, parent);
				
				parent.setRight(node);
				fixTreeInsert(node);
				return ;
			}
		}
		
		// Should we insert on the left?
		if (value <= parent.getValue()) {
			System.out.printf("Left side of %s \n", parent.getValue());
			// Is the left a leaf?
			if (parent.getLeft() != null){
				System.out.println("Descending");
				insert (value, parent.getLeft());
				return ;
			}
			
			else {
				System.out.println("Is left leaf");
				// Create a new node
				maxId++;
				Node node = new Node (value, maxId, parent);
				
				parent.setLeft(node);
				fixTree(node);
				return ;
			}
		}
	}
	
	public void delete(int value) {
		delete(value, root);
	}
	
	private void delete(int value, Node node) {
		if (contains(value)) {
			if (node.getValue() == value) {
				if (node.getLeft() == null && node.getRight() == null) {
					Node parent = node.getParent();
					if(parent.getLeft().equals(node)) {
						parent.setLeft(null);
					}
					else {
						parent.setRight(null);
					}
				}
				if (node.isBlack()) {
					if (node.getLeft() != null || node.getRight() != null) {
						if (node.getLeft() != null) {
							if (node.getLeft().isRed()) {
								node.getLeft().setBlack();
								node = node.getLeft();
							}
						}
					}
				}
			}
			else if (node.getValue() < value) {
				delete(value, node.getLeft());
			}
			else if (node.getValue() > value) {
				delete(value, node.getRight());
			}
		}
	}
	
	/**
	 * 
	 * Check node's sub tree. Fix if it isn't Red-Black Tree compliant
	 * 
	 * @param node
	 */
	private void fixTreeInsert(Node node) {
		//System.out.printf("Fixing from node %s, id %s\n", node.getValue(), node.getId());
		Node parent = node.getParent();
		// If parent is black there's nothing to do
		if(parent.isBlack()) {
			return ;
		}
		else if(node.isRed()){
			Node sibling = parent.getSibling();
			// If sibling is null or black
			if (sibling == null || sibling.isBlack()) {
				Node grandparent = parent.getParent();
				// If node is the left hand son of parent
				if (grandparent.getLeft() != null) {
					if (grandparent.getLeft().equals(parent)) {
						// Perform left handed trinode restructuring
						fixLeft(node);
					}
				}
				else if (grandparent.getRight() != null) {
					// If node is the right hand son of parent
					if (grandparent.getRight().equals(parent)){
						fixRight(node);
					}
				}
				
			}
			// If sibling is neither black nor null
			else {
				// Change parent's colour to black
				parent.setBlack();
				// Change sibling's colour to black, if it exists
				if (sibling != null) {
					sibling.setBlack();
				}
				// Change grandparent's color to red if it isn't the root
				Node grandparent = parent.getParent();
				if (!isRoot(grandparent)){
					grandparent.setRed();
				}
				return ;
			}
		}
	}
	
	/**
	 * Make the left hand side of a node obey 
	 * the properties of Red-Black Trees
	 * 
	 * @param node
	 */
	private void fixLeft(Node node){
		Node parent = node.getParent();
		Node grandparent = parent.getParent();
		
		if (parent.getLeft().equals(node)) {
			// Fix colors
			parent.setBlack();
			grandparent.setRed();
			// Fix relations
			if (isRoot(grandparent)) {
				setRoot(parent);
			}
			else {
				Node greatgrandparent = grandparent.getParent();
				if (greatgrandparent.getLeft() != null) {
					if (greatgrandparent.getLeft().equals(grandparent)) {
						greatgrandparent.setLeft(parent);
					}
				}
				else {
					greatgrandparent.setRight(parent);
				}
			}
			Node save = parent.getRight();
			grandparent.setParent(parent);
			parent.setRight(grandparent);
			grandparent.setLeft(save);
			
			// Check for possible new errors
			fixTree(parent);
			return ;
		}
		else if (parent.getRight().equals(node)) {
			// Fix colors
			node.setBlack();
			grandparent.setRed();
			// Fix relations
			if (isRoot(grandparent)) {
				setRoot(node);
			}
			else {
				Node greatgrandparent = grandparent.getParent();
				if (greatgrandparent.getLeft() != null) {
					if (greatgrandparent.getLeft().equals(grandparent)) {
						greatgrandparent.setLeft(node);
					}
				}
				else {
					greatgrandparent.setRight(node);
				}
			}
			parent.setParent(node);
			parent.setRight(null);
			node.setRight(grandparent);
			grandparent.setLeft(null);
			
			// Check for possible new errors
			fixTree(node);
			return ;
		}
	}
	
	
	/**
	 * Make the right hand side of the tree obey
	 * the properties of Red-Black Trees
	 * 
	 * @param node
	 */
	private void fixRight(Node node){
		Node parent = node.getParent();
		Node grandparent = parent.getParent();
		
		if (parent.getRight().equals(node)) {
			// Fix colors
			parent.setBlack();
			grandparent.setRed();
			// Fix relations
			if (isRoot(grandparent)) {
				setRoot(parent);
			}
			else {
				Node greatgrandparent = grandparent.getParent();
				if (greatgrandparent.getLeft() != null) {
					if (greatgrandparent.getLeft().equals(grandparent)) {
						greatgrandparent.setLeft(parent);
					}
					else {
						greatgrandparent.setRight(parent);
					}
				}
				else {
					greatgrandparent.setRight(parent);
				}
			}
			Node save = parent.getLeft();
			grandparent.setParent(parent);
			parent.setLeft(grandparent);
			grandparent.setRight(save);
			
			// Check for possible new errors
			fixTree(parent);
			return ;
		}
		else if (parent.getLeft().equals(node)) {
			// Fix colors
			node.setBlack();
			grandparent.setRed();
			// Fix relations
			if (isRoot(grandparent)) {
				setRoot(node);
			}
			else {
				Node greatgrandparent = grandparent.getParent();
				if (greatgrandparent.getLeft() != null) {
					if (greatgrandparent.getLeft().equals(grandparent)) {
						greatgrandparent.setLeft(node);
					}
				}
				else {
					greatgrandparent.setRight(node);
				}
			}
			parent.setParent(node);
			parent.setLeft(null);
			node.setRight(grandparent);
			grandparent.setRight(null);
			
			// Check for possible new errors
			fixTree(node);
			return ;
		}
		
	}
	/**
	 * Checks if a value exists in the tree.
	 * 
	 * @param value 
	 * @return True if the value exists in the tree or false otherwise
	 */
	public boolean contains(int value) {
		return contains(value, root);
	}
	
	private boolean contains(int value, Node node) {
		if (node == null) {
			return false;
		}
		if (value == node.getValue()) {
			return true;
		}
		else if (value < node.getValue()) {
			return contains(value, node.getLeft());
		}
		else if (value > node.getValue()) {
			return contains(value, node.getRight());
		}
		
		return false;
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
	
	private int min(Node node) {	
		if (node.getLeft() == null) {
			return node.getValue() ;
		}
		else {
			return min(node.getLeft()) ;
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
		
		System.out.println();
		printColours(root);
		System.out.println();
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
		
		printIds(node.getLeft());
		System.out.print(node.getId() + " ");
		printIds(node.getRight());
	}
	private void printColours(Node node) {
		if (node == null) {
			return ;
		}
		
		printColours(node.getLeft());
		System.out.print(node.getColour() + " ");
		printColours(node.getRight());
	}

}
