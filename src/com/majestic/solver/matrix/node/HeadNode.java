/**
 * 	Solver -- HeadNode.java
 *
 *  Created on: Sep 30, 2011
 *      Author: David Jolly 
 *      		[jollyd@onid.oregonstate.edu]
 *
 */

package com.majestic.solver.matrix.node;

public class HeadNode extends Node {

	private int size;
	private String name;
	
	/**
	 * Column Head Node
	 * @param name A node name
	 */
	public HeadNode(String name) {
		super(0, null);
		this.name = name;
		size = 0;
	}
	
	/**
	 * Column Head Node
	 * @param name A node name
	 * @param size Number of nodes in column
	 * @param up Node above
	 * @param down Node below
	 * @param left Node to the left
	 * @param right Node to the right
	 */
	public HeadNode(String name, int size, Node up, Node down, Node left, Node right) {
		super(0, null, up, down, left, right);
		this.name = name;
		this.size = size;
	}
	
	/**
	 * Returns the game of the node
	 * @return The name of the node
	 */
	public String getName() { 
		return name; 
	}
	
	/**
	 * Returns the column size
	 * @return The column size
	 */
	public int getSize() { 
		return size; 
	}
	
	/**
	 * Sets the node name
	 * @param name A node name
	 */
	public void setName(String name) { 
		this.name = name; 
	}
	
	/**
	 * Sets the column size
	 * @param size The column size
	 */
	public void setSize(int size) { 
		this.size = size; 
	}
	
	/**
	 * Returns a string representation of the node
	 */
	public String toString() { 
		return name; 
	}
}
