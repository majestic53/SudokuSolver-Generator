/**
 * 	Solver -- Node.java
 *
 *  Created on: Sep 30, 2011
 *      Author: David Jolly 
 *      		[jollyd@onid.oregonstate.edu]
 *
 */

package com.majestic.solver.matrix.node;

public class Node {
	
	private int row;
	private Node root;
	private Node up;
	private Node down;
	private Node left;
	private Node right;
	
	/**
	 * Node
	 * @param row The associated row
	 * @param root The associated column node
	 */
	public Node(int row, Node root) {
		this.row = row;
		this.root = root;
		up = down = left = right = null;
	}
	
	/**
	 * Node
	 * @param row The associated row
	 * @param root The associated column node
	 * @param up Node above
	 * @param down Node below
	 * @param left Node to the left
	 * @param right Node to the right
	 */
	public Node(int row, Node root, Node up, Node down, Node left, Node right) {
		this.row = row;
		this.root = root;
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Returns the node below
	 * @return The node below
	 */
	public Node getDown() { 
		return down; 
	}
	
	/**
	 * Returns the node to the left
	 * @return The node to the left
	 */
	public Node getLeft() { 
		return left; 
	}
	
	/**
	 * Returns the node to the right
	 * @return The node to the right
	 */
	public Node getRight() { 
		return right; 
	}
	
	/**
	 * Returns an associated column node
	 * @return An associated column node
	 */
	public Node getRoot() { 
		return root; 
	}
	
	/**
	 * Returns an associated row node
	 * @return An associated row node
	 */
	public int getRow() { 
		return row; 
	}
	
	/**
	 * Returns the node above
	 * @return The node above
	 */
	public Node getUp() { 
		return up; 
	}
	
	/**
	 * Sets the node below
	 * @param down The node below
	 */
	public void setDown(Node down) { 
		this.down = down; 
	}
	
	/**
	 * Sets the node to the left
	 * @param left The node to the left
	 */
	public void setLeft(Node left) { 
		this.left = left; 
	}
	
	/**
	 * Sets the node to the right
	 * @param right The node to the right
	 */
	public void setRight(Node right) { 
		this.right = right; 
	}
	
	/**
	 * Sets the associated column node
	 * @param root The associated column node
	 */
	public void setRoot(Node root) { 
		this.root = root; 
	}
	
	/**
	 * Sets the node above
	 * @param up The node above
	 */
	public void setUp(Node up) { 
		this.up = up; 
	}
	
	/**
	 * Returns a string representation of the node
	 */
	public String toString() { 
		return String.valueOf(row); 
	}
}
