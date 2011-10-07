/**
 * 	Solver -- Matrix.java
 *
 *  Created on: Sep 30, 2011
 *      Author: David Jolly 
 *      		[jollyd@onid.oregonstate.edu]
 *
 */

package com.majestic.solver.matrix;

import java.util.ArrayList;

import com.majestic.solver.matrix.node.HeadNode;
import com.majestic.solver.matrix.node.Node;

public class Matrix {
	
	private int max;
	private int known;
	private boolean active;
	private HeadNode root;
	private ArrayList<Node> results;
	private ArrayList<Node> rowNode;
	private ArrayList<Node> out;
	
	/**
	 * Exact cover matrix
	 * @param root The root node of a toroidal doubly-linked list
	 * @param rowNode The list of row header nodes
	 * @param known The number of known positions
	 * @param max The maximum number of solvable positions
	 */
	public Matrix(HeadNode root, ArrayList<Node> rowNode, int known, int max) {
		this.root = root;
		this.rowNode = rowNode;
		this.known = known;
		this.max = max;
		results = new ArrayList<Node>();
		out = new ArrayList<Node>();
	}
	
	/**
	 * Performs a cover operation on a given column node
	 * @param col A given column node
	 */
	public void cover(Node col) {
		col.getLeft().setRight(col.getRight());
		col.getRight().setLeft(col.getLeft());
		for(Node rowNode = col.getDown(); rowNode != col; rowNode = rowNode.getDown())
			for(Node rightNode = rowNode.getRight(); rightNode != rowNode; rightNode = rightNode.getRight()) {
				rightNode.getUp().setDown(rightNode.getDown());
				rightNode.getDown().setUp(rightNode.getUp());
			}
	}
	
	/**
	 * Returns the row header node at a given index
	 * @param index The index of the row header node
	 * @return The row header node at a given index
	 */
	public Node getRowNode(int index) {
		return rowNode.get(index);
	}

	/**
	 * Performs a search operation on the given toroidal doubly-linked list
	 * @param k The current iteration of the search operation
	 */
	private void search(int k) {
		if(k == 0) {
			results.clear();
			active = true;
		}
		if(k == max - known || (root.getLeft() == root && root.getRight() == root)) {
			active = false;
			for(Node n: results)
				out.add(new Node(n.getRow(), n.getRoot()));
			return;
		}
		Node col = root.getRight();
		cover(col);
		for(Node rowNode = col.getDown(); rowNode != col && active; rowNode = rowNode.getDown()) {
			results.add(rowNode);
			for(Node rightNode = rowNode.getRight(); rightNode != rowNode; rightNode = rightNode.getRight())
				cover(rightNode.getRoot());
			search(k + 1);
			for(Node rightNode = rowNode.getRight(); rightNode != rowNode; rightNode = rightNode.getRight())
				uncover(rightNode.getRoot());
			results.remove(rowNode);
		}
		uncover(col);
	}
	
	/**
	 * Runs the search operation and returns a series of nodes corrisponding to solution
	 * @return A series of nodes corrisponding to solution
	 */
	public ArrayList<Node> solve() {
		search(0);
		return out;
	}
	
	/**
	 * Performs an ucover operation on a given column node
	 * @param col A given column node
	 */
	public void uncover(Node col) {
		for(Node rowNode = col.getUp(); rowNode != col; rowNode = rowNode.getUp())
			for(Node leftNode = rowNode.getLeft(); leftNode != rowNode; leftNode = leftNode.getLeft()) {
				leftNode.getUp().setDown(leftNode);
				leftNode.getDown().setUp(leftNode);
			}
		col.getLeft().setRight(col);
		col.getRight().setLeft(col);
	}
}
