package org.iMage.HDrize.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Gil Baram
 *
 */
public class MatrixTest {
	private static int n = 3;
	private static double[][] mtx = new double[n][n];
	private static Matrix mat1;
		
	/**
	 * Initialize a double 2D array as a sampler for the test Methods
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		mtx[0][0] = 2; mtx[0][1] = 1; mtx[0][2] = 1;
		mtx[1][0] = 3; mtx[1][1] = 2; mtx[1][2] = 1;
		mtx[2][0] = 2; mtx[2][1] = 1; mtx[2][2] = 2;
		
	}

	/**
	 * Test the Constructor of Matrix, Methods rows() and cols()  
	 */
	@Test
	public void testMatinit() {
		
		Matrix mat1 = new Matrix(n, n);
		assertEquals(mat1.rows(), n);
		assertTrue(mat1.cols() == mat1.rows());
		for (int i = 0; i < mat1.rows(); i++) {
			for (int j = 0; j < mat1.cols(); j++) {
				assertTrue(mat1.get(i, j) == 0);
			}
		}
	}
	
	/**
	 * Test the method Matrix(double[][] mtx)
	 */
	@Test
	public void testConstructor() {
		Matrix mat2 = new Matrix(mtx);
		for (int i = 0; i < mat2.rows(); i++) {
			for (int j = 0; j < mat2.cols(); j++) {
				assertTrue(mat2.get(i, j) == mtx[i][j]);
			}
		}
	}
	
	/**
	 * Test Copy() Method
	 */
	@Test
	public void testCopy() {
		Matrix mat2 = new Matrix(mtx);
		double[][] mtx2 = mat2.copy();
		assertTrue(mtx.length == mtx2.length);
		for (int i = 0; i < mat2.rows(); i++) {
			for (int j = 0; j < mat2.cols(); j++) {
				assertTrue(mtx[i][j] == mtx2[i][j]);
			}
		}
	}

}
