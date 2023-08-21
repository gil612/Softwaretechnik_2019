package org.iMage.HDrize.matrix;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Gil Baram
 */
public class MatrixThreadMultiplication implements Runnable {
	private Matrix a;
	private Matrix b;
	private static Matrix c;
	private int start = 0;
	private int end = 0;
	private int numThreads;
	
	private static CyclicBarrier barriere = null;
	
	private MatrixThreadMultiplication(Matrix a,  Matrix b, Matrix c, int start, int end) {

		this.a = a;
		this.b = b;

		this.start = start;
		this.end = end;

		
	}
	/**
	 * 
	 */
	public MatrixThreadMultiplication() {
	}

	/**
	 * @param a 
	 * @param b 
	 * @param numThreads 
	 */
	public MatrixThreadMultiplication(Matrix a, Matrix b, int numThreads) {
		this.a = a;
		this.b = b;
		this.numThreads = numThreads;
	}
	private void multi(Matrix a, Matrix b, int t) {
		
		MatrixThreadMultiplication[] v1 = new MatrixThreadMultiplication[t];
		Thread[] v2 = new Thread[t];
		barriere = new CyclicBarrier(t, new Runnable() {
			
			public void run() { }
		}
	);
		c = new Matrix(a.rows(), b.cols());
		start = 0;
		end = 0;
		int numRows = (int) Math.ceil((double) a.rows() / t);
		
		for (int f = 0; f < t; f++) {
			start = f * numRows;
			
			end = Math.min((f + 1) * numRows, a.rows());


				
			v1[f] = new MatrixThreadMultiplication(a, b, c, start, end);
			v2[f] = new Thread(v1[f]);
			v2[f].start();
	
			
			
		}

		
		
	}




	@Override
	public void run() {
		
		for (int i = start; i < end; i++) {
			for (int j = 0; j < b.cols(); j++) {
			for (int k = 0; k < a.cols(); k++) {
				
				double aik = a.get(i, k);
				double bkj = b.get(k, j);
				double cij = c.get(i, j);
				
				c.set(i, j, cij + aik * bkj);
			}
			}
		}
		try {
			barriere.await();
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
			}
		
	}
		
	

	/**
	 * @return c
	 */
	public Matrix mehtod() {
		MatrixThreadMultiplication mvm = new MatrixThreadMultiplication();
		mvm.multi(a, b, this.numThreads);


//		double[][] dc = c.copy();
//		
//        try (
//                PrintStream output = new PrintStream(new File("outputc.txt"));
//            ){
//
//        	for (int  i = 0; i < dc.length ; i++) {
//        		for (int  j = 0; j < dc[0].length ; j++) {
//        			output.print(dc[i][j] + " ");
//        		}
//        		output.println();
//        	}
//            output.close();
//
//        } catch (FileNotFoundException e) {
//
//            e.printStackTrace();
//        }

		
		return c;
	}
}


