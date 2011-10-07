/**
 * 	Solver -- SudokuSolver.java
 *
 *  Created on: Sep 30, 2011
 *      Author: David Jolly 
 *      		[jollyd@onid.oregonstate.edu]
 *
 */

package com.majestic.solver;

import java.util.ArrayList;

import com.majestic.solver.matrix.Matrix;
import com.majestic.solver.matrix.MatrixBuilder;
import com.majestic.solver.matrix.node.Node;

public class SudokuSolver {

	/**
	 * Puzzle parameters
	 */
	public static int LEN = 9;
	public static int BLOCK_LEN = 3;
	public static int CONSTRAINTS = 4;
	public static int ROWS = LEN * LEN * LEN;
	public static int COLS = LEN * LEN * CONSTRAINTS;
	
	private int known;
	private int[] offset;
	private int[][] board;
	private int[][] matrix;
	private Matrix ans;
	
	/**
	 * Sudoku puzzle solver
	 * @param board a solvable sudoku puzzle
	 */
	public SudokuSolver(int[][] board) {
		this.board = board;
		matrix = new int[COLS][ROWS];
		offset = new int[CONSTRAINTS - 1];
		for(int i = 1; i < CONSTRAINTS; i++)
			offset[i - 1] = i * LEN * LEN;
		known = 0;
		for(int i = 0; i < LEN; i++)
			for(int j = 0; j < LEN; j++)
				if(board[i][j] != 0)
					known++;
		convertBoard();
	}
	
	/**
	 * Add known positions to the exact cover matrix
	 */
	private void addKnown() {
		int index;
		Node rowNode;
		for(int i = 0; i < LEN; i++)
			for(int j = 0; j < LEN; j++)
				if(board[i][j] != 0) {
					index = (board[i][j] - 1) * LEN * LEN + i * LEN + j;
					rowNode = ans.getRowNode(index);
					ans.cover(rowNode.getRoot());
					for(Node rightNode = rowNode.getRight(); rightNode != rowNode; rightNode = rightNode.getRight())
						ans.cover(rightNode.getRoot());
				}
	}
	
	/**
	 * Converts a solvable sudoku board to exact cover
	 */
	private void convertBoard() {
		int index;
		for(int i = 0; i < LEN; i++)
			for(int j = 0; j < LEN; j++)
				for(int k = 0; k < LEN; k++) {
					index = k * LEN * LEN + i * LEN + j;
					matrix[getRowIndex(index) * LEN + getColumnIndex(index)][index] = 1;
					matrix[getNumberIndex(index) * LEN + getRowIndex(index) + offset[0]][index] = 1;
					matrix[getNumberIndex(index) * LEN + getColumnIndex(index) + offset[1]][index] = 1;
					matrix[getNumberIndex(index) * LEN + getBlockIndex(index) + offset[2]][index] = 1;
				}
	}
	
	/**
	 * Converts a given row index to its respective block
	 * @param index a given row index
	 * @return a block index
	 */
	public static int getBlockIndex(int index) { 
		return ((getRowIndex(index) / BLOCK_LEN) * BLOCK_LEN) + (getColumnIndex(index) / BLOCK_LEN); 
	}
	
	/**
	 * Returns a sudoku board
	 * @return a sudoku board
	 */
	public int[][] getBoard() { 
		return board; 
	}
	
	/**
	 * Converts a given row index to its respective column
	 * @param index a given row index
	 * @return a column index
	 */
	public static int getColumnIndex(int index) { 
		return index % LEN; 
	}
	
	/**
	 * Converts a given row index to its respective number
	 * @param index a given row index
	 * @return a number
	 */
	public static int getNumberIndex(int index) { 
		return index / (LEN * LEN); 
	}
	
	/**
	 * Converts a given row index to its respective row
	 * @param index a given row index
	 * @return a row index
	 */
	public static int getRowIndex(int index) { 
		return (index / LEN) % LEN; 
	}
	
	/**
	 * Solves a given sudoku puzzle
	 */
	public void solve() {
		ArrayList<Node> results;
		MatrixBuilder builder = new MatrixBuilder(matrix, known, LEN * LEN);
		ans = builder.build();
		addKnown();
		results = ans.solve();
		for(Node n: results)
			board[SudokuSolver.getRowIndex(n.getRow())][SudokuSolver.getColumnIndex(n.getRow())] 
			                                       = SudokuSolver.getNumberIndex(n.getRow()) + 1;
	}
}
