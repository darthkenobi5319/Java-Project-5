package project5;

/**
 * The class provides a recursive implementation for a binary search tree.
 *
 * @author Zhenghan Zhang
 *
 * @param <T> generic type of data that is stored in nodes of the tree; needs to
 *            implement Comparable<T> interface
 */
public class AVLTree<T extends Comparable<T>> {

	// root of the tree
	protected Node<T> root;
	// current number of nodes in the tree
	protected int numOfElements;
	//helper variable used by the remove methods
	private boolean found;
	
	//determines whether this tree is empty
	public boolean isEmpty() {
        return root == null;
    }

	/**
	 * Default constructor that creates an empty tree.
	 */
	public AVLTree() {
		this.root = null;
		this.numOfElements = 0;
	}
	/**
	 * This method returns the height of a given node; returns -1 when null;
	 * @param node
	 * @return
	 */
	private int height(Node<T> node) {
		if (node == null) {
			return 0;
		}
		return node.height;
	}
	
	/**
	 * Add the given data item to the tree. If item is null, the tree does not
	 * change. If item already exists, the tree does not change.
	 *
	 * @param item the new element to be added to the tree
	 */
	public void add(T item) {
		if (item == null)
			return;
		root = recAdd (root, item);
		this.numOfElements ++;

	}
	
	
	
	

	/*
	 * Actual recursive implementation of add.
	 *
	 * @param item the new element to be added to the tree
	 */
	private Node<T> recAdd(Node<T> node, T item) {
		if (node == null) {
            return new Node<T>(item);
        }
        int i = item.compareTo(node.data);
        if (i < 0) {
            node.left = recAdd(node.left, item);
        } else if (i > 0) {
            node.right = recAdd(node.right, item);
        }
        
        return this.balance(node);
	}

	/**
	 * Remove the item from the tree. If item is null the tree remains unchanged. If
	 * item is not found in the tree, the tree remains unchanged.
	 *
	 * @param target the item to be removed from this tree
	 */
	public boolean remove(T target)
	{
		root = recRemove(target, root);
		if (found) numOfElements--; 
		return found;
	}


	/*
	 * Actual recursive implementation of remove method: find the node to remove.
	 *
	 * @param target the item to be removed from this tree
	 */
	private Node<T> recRemove(T target, Node<T> node)
	{
		if (node == null) {
			found = false;
			return null;
		}
		if (target.compareTo(node.data) < 0)
			node.left = recRemove(target, node.left);
		else if (target.compareTo(node.data) > 0)
			node.right = recRemove(target, node.right );
		else {
			node = removeNode(node);
			found = true;
		}
		if (node==null) return null;
		return this.balance(node);
	}

	/*
	 * Actual recursive implementation of remove method: perform the removal.
	 *
	 * @param target the item to be removed from this tree
	 * @return a reference to the node itself, or to the modified subtree
	 */
	private Node<T> removeNode(Node<T> node)
	{
		T data;
		if (node.left == null)
			return node.right ;
		else if (node.right  == null)
			return node.left;
		else {
			data = getPredecessor(node.left);
			node.data = data;
			node.left = recRemove(data, node.left);
			return node;
		}
	}

	

	/*
	 * Returns the information held in the rightmost node of subtree
	 *
	 * @param subtree root of the subtree within which to search for the rightmost node
	 * @return returns data stored in the rightmost node of subtree
	 */
	private T getPredecessor(Node<T> subtree)
	{
		if (subtree==null) throw new NullPointerException("getPredecessor called with an empty subtree");
		Node<T> temp = subtree;
		while (temp.right  != null)
			temp = temp.right ;
		return temp.data;
	}
	
	/**
	 * This method includes the four balancing methods. This should be called whenever an add or remove is called
	 * @param n the node
	 * @return	the balanced node
	 */
	private  Node<T> balance(Node<T> n) {
		if (n == null) {
	        return n;
	    }
	    if (this.balanceFactor(n) < -1) {
	        if (this.height(n.left.left) >= this.height(n.left.right)) {

	            n = this.balanceLL(n);
	        } else {

	            n = this.balanceLR(n);

	        }
	    } else if (this.balanceFactor(n) > 1) {
	        if (this.height(n.right.right) >= this.height(n.right.left)) {

	            n = this.balanceRR(n);
	        } else {

	            n = this.balanceRL(n);
	        }
	    }
	    	n.height = this.max(this.height(n.left), this.height(n.right)) + 1;
	    	return n;
		}
	/**
	 * The method that updates the height of the current node
	 * @param n the input node
	 */
	void updateHeight(Node<T> n) {
		if (this.height(n) == 1) {
			n.height = 1;
		}else if(n.left == null) {
			n.height = this.height(n.right) + 1;
		}else if (n.right == null) {
			n.height = this.height(n.left) + 1;
		}else {
			n.height = max(this.height(n.left) , this.height(n.right)) + 1;
		}
	}

	/**
	 * Determines the number of elements stored in this BST.
	 *
	 * @return number of elements in this BST
	 */
	public int size() {
		return numOfElements;
	}

	/**
	 * Returns a string representation of this tree using an inorder traversal .
	 * @see java.lang.Object#toString()
	 * @return string representation of this tree
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		inOrderPrint(root, s);
		return s.toString();
	}
	/**
	 * This is an auxiliary method, which is used for simple mathematical comparison
	 * @param a an integer a
	 * @param b an integer b
	 * @return the bigger number between a and b
	 */
	public int max(int a,int b) {
		if(a>=b) {
			return a;
		}else {
			return b;
		}
	}
	/**
	 * This method returns the balance factor, which decides whether to use a rotation or not
	 * @param n the node
	 * @return an identification number that decides whether to use a balance method;
	 */
	int balanceFactor ( Node<T> n ) {
	if ( n.right == null ) {
		return -(this.height(n));
		} 
	if ( n.left == null ) {
		return this.height(n); 
	}
	return (this.height(n.right) - this.height(n.left));
	}
	
	// These blocks are balance methods which should be called when adding or deleting nodes
	private Node<T> balanceLL(Node<T> A){
		Node<T> B = A.left;
		A.left = B.right;
		B.right = A;
		
		updateHeight(A);
		updateHeight(B);
		return B;
		
	}
	private Node<T> balanceRR(Node<T> A){
		Node<T> B = A.right;
		A.right = B.left;
		B.left = A;
		
		updateHeight(A);
		updateHeight(B);
		return B;
	}
	
	private Node<T> balanceLR(Node<T> A){
		Node<T> B = A.left;
		Node<T> C = B.right;
		
		A.left =  C.right;
		B.right = C.left;
		C.left = B;
		C.right = A;
		
		updateHeight(A);
		updateHeight(B);
		updateHeight(C);
		
		return C;
	}
	
	private Node<T> balanceRL(Node<T> A){
		Node<T> B = A.right;
		Node<T> C = B.left;
		
		A.right =  C.left;
		B.left = C.right;
		C.left = A;
		C.right = B;
		
		updateHeight(A);
		updateHeight(B);
		updateHeight(C);
		
		return C;
	}
	

	/*
	 * Actual recursive implementation of inorder traversal to produce string
	 * representation of this tree.
	 *
	 * @param tree the root of the current subtree
	 * @param s the string that accumulated the string representation of this BST
	 */
	private void inOrderPrint(Node<T> tree, StringBuilder s) {
		if (tree != null) {
			inOrderPrint(tree.left, s);
			s.append(tree.data.toString() + "  ");
			inOrderPrint(tree.right , s);
		}
	}

	/**
	 * DO NOT MOFIFY THIS METHOD.
	 * INCLUDE IT AS-IS IN YOUR CODE.
	 *
	 * Produces tree like string representation of this BST.
	 * @return string containing tree-like representation of this BST.
	 */
	public String toStringTreeFormat() {

		StringBuilder s = new StringBuilder();

		preOrderPrint(root, 0, s);
		return s.toString();
	}

	/*
	 * DO NOT MOFIFY THIS METHOD.
	 * INCLUDE IT AS-IS IN YOUR CODE.
	 *
	 * Actual recursive implementation of preorder traversal to produce tree-like string
	 * representation of this tree.
	 *
	 * @param tree the root of the current subtree
	 * @param level level (depth) of the current recursive call in the tree to
	 *   determine the indentation of each item
	 * @param output the string that accumulated the string representation of this
	 *   BST
	 */
	private void preOrderPrint(Node<T> tree, int level, StringBuilder output) {
		if (tree != null) {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append(this.height(tree));
			output.append(tree.data);
			preOrderPrint(tree.left, level + 1, output);
			preOrderPrint(tree.right , level + 1, output);
			
		}
		// uncomment the part below to show "null children" in the output
		else {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append("null");
			
		}
	}
	
	


	/**
	 * Node class is used to represent nodes in a binary search tree.
	 * It contains a data item that has to implement Comparable interface
	 * and references to left and right subtrees.
	 *
	 * @author Joanna Klukowska
	 *
	 * @param <T> a reference type that implements Comparable<T> interface
	 */
	static class Node <T extends Comparable <T>>	implements Comparable <Node <T> > {


		protected Node <T> left;  //reference to the left subtree
		protected Node <T> right; //reference to the right subtree
		protected T data;            //data item stored in the node

		public int height;
		protected int desc; 		//num of descendants


		/**
		 * Constructs a BSTNode initializing the data part
		 * according to the parameter and setting both
		 * references to subtrees to null.
		 * @param data
		 *    data to be stored in the node
		 */
		protected Node(T data) {
			this.data = data;
			left = null;
			right = null;
			height = 1;
			desc = 0;
		}
		
		protected Node() {
			this.data = null;
			left = null;
			right = null;
			height = 1;
			desc = 0;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(Node<T> other) {
			return this.data.compareTo(other.data);
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return data.toString();
		}



	}


}