/**
 * 	Solver -- Mersenne.java
 *
 *  Created on: June 03, 2011
 *      Author: David Jolly 
 *      		<jollyd@onid.oregonstate.edu>
 *
 * 	Based off of the Mersenne Twist algorithm.  See M. Matsumoto and T. Nishimura,
 * 	"Mersenne Twister: A 623-Dimensionally Equidistributed Uniform Pseudo-Random
 * 	Number Generator", ACM Transactions on Modeling and Computer Simulation, Vol. 8,
 * 	No. 1, January 1998, pp 3--30.
 */

package com.majestic.solver.util;

import java.util.Random;

public class Mersenne {

	private static final int M = 624;
	private static final int N = 397;
	
	private int index;
	private long value;
	private long[] mt;
	
	public Mersenne() {
		this(new Random().nextLong());
	}
	
	/**
	 * Mersenne Twister constructor
	 * @param seed used to initialize the mersenne twister
	 */
	public Mersenne(long seed) {
		index = 0;
		value = 0;
		mt = new long[M];
		mt[0] = seed;
		for(int i = 1; i < M; i++)
			mt[i] = 0xffffffffL & (0x6c078965L * (mt[i - 1] ^ (mt[i - 1] >> 30)) + i);
		generate();
	}
	
	/**
	 * Generates a table of random values
	 */
	private void generate() {
		for(int i = 0; i < M; i++) {
			value = (0x80000000L & mt[i]) + (0x7fffffffL & mt[(i + 1) % M]);
			mt[i] = mt[(i + N) % M] ^ (value >> 1);
			if((value & 1) == 1)
				mt[i] ^= 0x9908b0dfL;
		}
	}
	
	/**
	 * Returns a pseudo-random long value
	 * @return a pseudo-random long value
	 */
	public long nextLong() {
		if(index == 0)
			generate();
		value = mt[index];
		value ^= (value >> 11);
		value ^= (value << 7) & 0x9d2c5680L;
		value ^= (value << 15) & 0xefc60000L;
		value ^= (value >> 18);
		index = (index + 1) % M;
		return value;
	}
	
	/**
	 * Returns a pseudo-random integer value
	 * @return a pseudo-random integer value
	 */
	public int nextInt() {
		return (int) (nextLong() >> 16);
	}
	
	/**
	 * Returns a pseudo-random integer value (n inclusive)
	 * @param n 0 to n inclusive
	 * @return a pseudo-random integer value (n inclusive)
	 */
	public int nextInt(int n) {
		return nextInt() % n;
	}
}
