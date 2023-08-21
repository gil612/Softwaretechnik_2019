package org.iMage.HDrize.matrix;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Gil Baram
 *
 */
public class MatrixCalculatorTest {
	private static int len = 3;
	private static Matrix mat1;
	private static Matrix mat2;
	private MatrixCalculator mc = new MatrixCalculator();
	
	/**
	 * Initialize a Matrix as sampler for the test Methods 
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		mat1 = new Matrix(len, len);
		mat2 = new Matrix(len, len);
		
		mat1.set(0, 0, 1); mat1.set(0, 1, 2); mat1.set(0, 2, -1);
		mat1.set(1, 0, 0); mat1.set(1, 1, 1); mat1.set(1, 2, -1);
		mat1.set(2, 0, 2); mat1.set(2, 1, 2); mat1.set(2, 2, 1);
	}


	/**
	 * Test the method multiply
	 */
	@Test
	public void testMultiply() {
		mat2 = mat1.copy(mat1);
		Matrix mat3 = new Matrix(len, len);
		mat3 = mc.multiply(mat1, mat2);
		
		double[][] a = mat1.copy();
		double[][] b = mat2.copy();
		double[][] c = new double[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				c[i][j] = 0;
				for (int k = 0; k < len; k++) {
					c[i][j] += a[i][k] * b[k][j];
				}	
			}
		}	
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				assertTrue(c[i][j] == mat3.get(i, j));
				}	
			}
		}
	
	/**
	 * test the transpose method
	 */
	@Test
	public void testTranspose() {
		mat2 = mc.transpose(mat1);
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				assertTrue(mat1.get(i, j) ==  mat2.get(j, i));
			}
		}
	}
	
	/**
	 * test the method inverse 
	 */
	@Test
	public void testInverse() {
		mat2 = mat1.copy(mat1);
		mat1 = mc.inverse(mat1);
		Matrix mat3 = mc.multiply(mat1, mat2);
		assertTrue(isIdentity(mat3));
	}
	
	
	/**
	 * Checks the Identity of a given matrix
	 * @param mat a given Matrix
	 * @return true if Identity, otherwise falls
	 */
	private boolean isIdentity(Matrix mat) {
		for (int i = 0; i < mat.rows(); i++) {
			for (int j = 0; j < mat.cols(); j++) {
				double v = (double) Math.round(mat.get(i, j) * 100000d) / 100000d;
				if (i == j) {
					if (v != 1) {
						return false;
					}
				} else if (v != 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	/**
	 * display a double 2D array
	 * @param dismat a givek 2D array
	 */
	private static void displayMat(double[][] dismat) {
		int r = dismat.length;
		int c = r;
		  for (int i = 0; i < r; i++) {
			  for (int j = 0; j < c; j++) {
				  double value = dismat[i][j];
				  System.out.printf("%.2f" + " ", value);
				  
			  }
			  System.out.println();
		  }
	}
	
	 /**
	  * display a Matrix
	 * @param a a given matrix
	 */
	private void displaymat(Matrix a) {			
			for (int row = 0; row < len; row++) {
				for (int aj = 0; aj < len; aj++) {
					System.out.printf("%.2f ", a.get(row, aj));
				}
				System.out.println();
			}	
			System.out.println();	
	}
	

}
