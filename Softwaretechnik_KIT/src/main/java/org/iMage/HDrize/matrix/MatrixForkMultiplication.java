package org.iMage.HDrize.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * @author Gil Baram
 *
 */
public class MatrixForkMultiplication extends RecursiveAction implements Runnable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Matrix a;
	private Matrix b;
	private Matrix c;

    private int i;



    
    /**
     * @param a 
     * @param b 
     * @param c 
     */
    MatrixForkMultiplication(Matrix a, Matrix b, Matrix c) {
        this(a, b, c, -1);
    }

    /**
     * @param a 
     * @param b 
     * @param c 
     * @param row 
     */
    MatrixForkMultiplication(Matrix a, Matrix b, Matrix c, int row) {
        if (a.cols() != b.rows()) {
            throw new IllegalArgumentException("Cannot multiply. Number of columns of the"//
            		+ "left Matrix is not equal to the rows of the right matrix ");
        }
        this.a = a;
        this.b = b;
        this.c = c;
        this.i = row;
    } 


	@Override
    public void compute() {
        if (i == -1) {
            List<MatrixForkMultiplication> tasks = new ArrayList<>();
            for (int i = 0; i < a.rows(); i++) {
                tasks.add(new MatrixForkMultiplication(a, b, c, i));
            }
            invokeAll(tasks);
        } else {
            multiply(a, b, c, i);
        }
    }

    /**
     * @param a 
     * @param b 
     * @param c 
     * @param i 
     */
    public void multiply(Matrix a, Matrix b, Matrix c, int i) {
        for (int j = 0; j < b.cols(); j++) {
            for (int k = 0; k < a.cols(); k++) {
            	
				double cij = c.get(i, j);
				double aik = a.get(i, k);
				double bkj = b.get(k, j);
				c.set(i, j, cij + aik  * bkj);
            }
        }
    }

	@Override
	public void run() {

		
	}
}