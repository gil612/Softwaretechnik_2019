package org.iMage.HDrize.matrix;


import java.util.concurrent.ForkJoinPool;

import org.iMage.HDrize.base.matrix.IMatrix;


/**
 * @author Gil Baram
 *
 */
public class MatrixCalculatorParallel extends MatrixCalculator implements Runnable {

	private int start = 0;;
	private int end = 0;
	private int numThreads;	 
	private IMatrix mtx;
	private boolean bool = true;


	
	/**
	 * @param numThreads 
	 */
	public MatrixCalculatorParallel(int numThreads) {
		this.numThreads = numThreads;
	}
	
	/**
	 * @param mtx 
	 * @param start 
	 * @param end 
	 * @param bool 
	 */
	public MatrixCalculatorParallel(IMatrix mtx, int start, int end, boolean bool) {
		this.mtx = mtx;
		this.start = start;
		this.end = end;
		this.bool = bool;
	}

	@Override
	  public Matrix inverse(Matrix mtx) {

	    double[][] inverse = this.inverse(mtx.copy());

	    if (inverse != null) {
	      return new Matrix(inverse);
	    }
	    return null;
	  }

	  private double[][] inverse(double[][] matrix) {

	    if (matrix.length != matrix[0].length) {
	      return null;
	    }
	    int n = matrix.length;
	    double[][] augmented = new double[n][n * 2];
	    for (int r = 0; r < n; r++) {
	      for (int c = 0; c < n; c++) {
	        augmented[r][c] = matrix[r][c];
	      }
	      augmented[r][r + n] = 1;
	    }
	    this.solve(augmented);
	    double[][] inv = new double[n][n];
	    for (int i = 0; i < n; i++) {
	      for (int j = 0; j < n; j++) {
	        inv[i][j] = augmented[i][j + n];
	      }
	    }


	    IMatrix product = this.multiply(new Matrix(matrix), new Matrix(inv));



	    if (!this.isIdentity(product)) {
	      return null;
	    }

	    return inv;
	  }

	  

	  private boolean isIdentity(IMatrix mtx) {
	    if (mtx.cols() != mtx.rows()) {
	      return false;
	    }
	    MatrixCalculatorParallel[] worker = new MatrixCalculatorParallel[this.numThreads];
	    Thread[] vmm = new Thread[this.numThreads];
	    for (int f = 0; f < this.numThreads;) {
	    	int n = mtx.rows();
	    	int blocksize = (int) Math.ceil((double) n / this.numThreads);
	    	int start = f * blocksize;
	    	int end =  Math.min((f + 1) * blocksize, n);
	    	worker[f] = new MatrixCalculatorParallel(mtx, start, end, bool);
	    	vmm[f] = new Thread(worker[f]);
	    	vmm[f].start();
	    	return bool;
	    	
	    }
	    return true;
	  }


	  private void solve(double[][] augmentedMatrix) {
	    int rows = augmentedMatrix.length;
	    int cols = augmentedMatrix[0].length;
	    int lead = 0;
	    for (int r = 0; r < rows; r++) {


	      if (lead >= cols) {
	        break;
	      }
	      int i = r;
	      while (Math.abs(augmentedMatrix[i][lead]) < 1E-8) {
	        if (++i == rows) {
	          i = r;
	          if (++lead == cols) {
	            return;
	          }
	        }
	      }

	      this.swap(augmentedMatrix, r, i);

	      double lv = augmentedMatrix[r][lead];
	      for (int j = 0; j < cols; j++) {
	        augmentedMatrix[r][j] /= lv;
	      }

	      for (i = 0; i < rows; i++) {
	        if (i != r) {
	          lv = augmentedMatrix[i][lead];
	          for (int j = 0; j < cols; j++) {
	            augmentedMatrix[i][j] -= lv * augmentedMatrix[r][j];
	          }
	        }
	      }
	      lead++;
	    }
	  }

	  private void swap(double[][] mtx, int i, int j) {
	    double[] tmp = mtx[i];
	    mtx[i] = mtx[j];
	    mtx[j] = tmp;
	  }

	  

	  @Override
	  public Matrix transpose(Matrix mtx) {
	    Matrix res = new Matrix(mtx.cols(), mtx.rows());
	    for (int r = 0; r < res.rows(); r++) {
	      for (int c = 0; c < res.cols(); c++) {
	        res.set(r, c, mtx.get(c, r));
	      }
	    }
	    return res;
	  }

  
	  /**
	 * @param a 
	 * @param b 
	 * @return c 
	 */
	public Matrix multiplyThread(Matrix a, Matrix b) {

		  MatrixThreadMultiplication mtm = new MatrixThreadMultiplication(a, b, numThreads);
		  
		  Matrix c = mtm.mehtod();

		  return c;

			
		}


	 
	 @Override 
	public Matrix multiply(Matrix a, Matrix b) {
	    if (a.cols() != b.rows()) {
	      return null;
	    }
		Matrix c = new Matrix(a.rows(), b.cols());
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MatrixForkMultiplication(a, b, c));
		return c;
		
	}

	@Override
	public void run() {
		for (int r = start; r <  end; r++) {
			for (int c = 0; c < mtx.cols(); c++) {
				if (r == c && Math.abs(mtx.get(r, c) - 1) >= 1E-8) {
			          bool = false;
			        } else if (r != c && Math.abs(mtx.get(r, c)) >= 1E-8) {
			          bool = false;
			        }
			}
		}
		
	}
		
	


}




