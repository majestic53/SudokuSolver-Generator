/**
 * 	Solver -- MatrixBuilder.java
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

public class MatrixBuilder {

	private int rows;
	private int cols;
	private int max;
	private int known;
	private int[][] matrix;
	private ArrayList<Node> rowNode;
	
	/**
	 * Builds a matrix object
	 * @param matrix an integer representation of a matrix
	 * @param known The number of known positions
	 * @param max The maximum number of solvable positions
	 */
	public MatrixBuilder(int[][] matrix, int known, int max) {
		this.matrix = matrix;
		this.known = known;
		this.max = max;
		rowNode = new ArrayList<Node>();
		cols = matrix.length;
		rows = 0;
		if(cols > 0)
			rows = matrix[0].length;
	}
	
	/**
	 * Builds a matrix based off the input integer representation
	 * @return A matrix
	 */
	public Matrix build() {
		ArrayList<Node> rowNodes = new ArrayList<Node>();
		HeadNode root = new HeadNode("H");
		Node curr = root;
		for(int i = 0; i < cols; i++, curr = curr.getRight()) {
			HeadNode node = new HeadNode("C" + String.valueOf(i));
			node.setLeft(curr);
			curr.setRight(node);
		}
		curr.setRight(root);
		root.setLeft(curr);
		Node currRow = root.getRight();
		Node currCol = root.getRight();
		for(int i = 0; i < cols; i++, currCol = currRow = currCol.getRight()) {
			for(int j = 0; j < rows; j++) {
				if(matrix[i][j] == 0)
					continue;
				Node node = new Node(j, currCol);
				node.setUp(currRow);
				currRow.setDown(node);
				currRow = currRow.getDown();
				((HeadNode) currCol).setSize(((HeadNode) currCol).getSize() + 1);
			}
			currRow.setDown(currCol);
			currCol.setUp(currRow);
		}
		Node start, rowPrev;
		currCol = root.getRight();
		Node[] row = new Node[cols];
		for(int i = 0; i < cols; i++, currCol = currCol.getRight())
			row[i] = currCol.getDown();
		currCol = root.getRight();
		for(int i = 0; i < rows; i++) {
			rowNodes.clear();
			for(int j = 0; j < cols; j++, currCol = currCol.getRight())
				if(matrix[j][i] != 0) {
					rowNodes.add(row[j]);
					row[j] = row[j].getDown();
				}
			if(!rowNodes.isEmpty()) {
				start = rowNodes.get(0);
				rowPrev = rowNodes.get(0);
				rowNode.add(rowNodes.get(0));
				for(Node node: rowNodes) {
					if(node == start)
						continue;
					node.setLeft(rowPrev);
					rowPrev.setRight(node);
					rowPrev = node;
				}
				rowPrev.setRight(start);
				start.setLeft(rowPrev);
			} else
				rowNode.add(null);
		}
		return new Matrix(root, rowNode, known, max);
	}
}
