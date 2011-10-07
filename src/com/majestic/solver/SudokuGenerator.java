/**
 * 	Solver -- SudokuGenerator.java
 *
 *  Created on: Sep 30, 2011
 *      Author: David Jolly 
 *      		[jollyd@onid.oregonstate.edu]
 *
 */

package com.majestic.solver;

import com.majestic.solver.util.Mersenne;

public class SudokuGenerator {

	public static final int LEN = 9;
	public static final int BLOCK_LEN = 3;
	public static final int MAX_UNKNOWN = 64;
	
	/**
	 * Sudoku puzzle generator
	 * @param unknown Number of unknowns in generated puzzle
	 * @return An unsolved puzzle
	 */
	public static int[][] generate(int unknown) {
		int value;
		int offsetX;
		int offsetY;
		int rowDelta;
		int colDelta;
		SudokuSolver solver;
		Mersenne rand = new Mersenne();
		boolean[] used = new boolean[LEN];
		int[][] board = new int[LEN][LEN];
		for(int i = 0; i < LEN; i++) {
			do {
				value = Math.abs(rand.nextInt(LEN));
			} while(used[value]);
			used[value] = true;
			offsetX = (i / BLOCK_LEN) * 3;
			offsetY = (i % BLOCK_LEN) * 3;
			rowDelta = Math.abs(rand.nextInt(BLOCK_LEN));
			colDelta = Math.abs(rand.nextInt(BLOCK_LEN));
			board[offsetX + rowDelta][offsetY + colDelta] = value + 1;
		}
		solver = new SudokuSolver(board);
		solver.solve();
		board = solver.getBoard();
		if(unknown > MAX_UNKNOWN)
			unknown = MAX_UNKNOWN;
		for(int i = 0; i < unknown; i++) {
			do {
				rowDelta = Math.abs(rand.nextInt(LEN));
				colDelta = Math.abs(rand.nextInt(LEN));
			} while(board[rowDelta][colDelta] == 0);
			board[rowDelta][colDelta] = 0;
		}
		return board;
	}
}
