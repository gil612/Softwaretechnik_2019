package org.iMage.HDrize;

import org.iMage.HDrize.matrix.Matrix;
import org.iMage.HDrize.matrix.MatrixCalculatorParallel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Gil Baram
 *
 */
public class MatrixCalculatorParallelTest extends TestBase {

	private MatrixCalculatorParallel mcp;
	protected static final double EPS = 1E-8;
	
	  /**
	 * 
	 */
	@Before
	  public void setup() {
		  mcp = new MatrixCalculatorParallel(8);
	  
	  }
	  
	  
	  /**
	 * 
	 */
	@Test
	  public void testMultiply() {
		  Matrix a = new Matrix(new double[][] { { 1, 2, 3 }, { 5, 6, 7 } });
		    Matrix b = new Matrix(new double[][] { { 1, 2, 3 }, { 5, 6, 7 }, { 8, 9, 10 } });

		    Matrix res = new Matrix(new double[][] { { 35.0, 41.0, 47.0 }, { 91.0, 109.0, 127.0 } });
		    Matrix mat3 = this.mcp.multiply(a, b);
		    this.assertEquals(res, mat3);
		    display(mat3);
		    display(res);
	  }
	  
	  
	  
	  /**
	 * 
	 */
	@Test
	  public void testMultiplyWrongDims() {
	    Matrix a = new Matrix(new double[][] { { 1, 2, 3 }, { 5, 6, 7 } });
	    Matrix b = new Matrix(new double[][] { { 1, 2, 3 }, { 1, 2, 3 } });
	    // Check whether calculation is not possible
	    Assert.assertNull(this.mcp.multiply(a, b));
	  }
	  
	
	/**
	 * @param expected 
	 * @param actual 
	 */
	protected final void assertEquals(Matrix expected, Matrix actual) {
    Assert.assertEquals(expected.rows(), actual.rows());
    Assert.assertEquals(expected.cols(), actual.cols());

    for (int r = 0; r < actual.rows(); r++) {
      for (int c = 0; c < actual.cols(); c++) {
        Assert.assertEquals("Matrix differ at " + r + "," + c, //
            expected.get(r, c), actual.get(r, c), EPS);
      }
    }
  }


	
	/**
	 * 
	 */
	@Test
	public void test() {
		int n = 2;
		
		double[][] m1 = new double[][] {
			{1, 2},
			{3, 4}
		};
		Matrix mat1 = new Matrix(m1);
		double[][] m2 = new double[][] {
			{2, 1},
			{1, 1}
		};
		Matrix mat2 = new Matrix(m2);
		Matrix mat3 = new Matrix(n, n);

		mat3 = mcp.multiply(mat1, mat2);

		display(mat3);
	}
	
	
	
	/**
	 * 
	 */
	@Test
	  public void testInverseWrongDim() {
	    Matrix a = new Matrix(new double[][] { { 1, 2, 3 }, { 5, 6, 7 } });
	    Assert.assertNull(this.mcp.inverse(a));
	  }
	
	
//	@Test
//	  public void testInverseWrongRank() {
//	    Matrix a = new Matrix(new double[][] { { 1, 2, 3 }, { 5, 6, 7 }, { 10, 12, 14 } });
//	    Assert.assertNull(this.mcp.inverse(a));
//	  }
	
	 /**
	 * 
	 */
	@Test
	  public void testSimpleInverse() {
	    Matrix a = new Matrix(new double[][] { { 1, 0, 0 }, { 0, 2, 0 }, { 0, 0, 3 } });
	    Matrix inverse = new Matrix(new double[][] { { 1, 0, 0 }, { 0, .5, 0 }, { 0, 0, 1.0 / 3 } });
	    this.assertEquals(inverse, this.mcp.inverse(a));
	  }
	 
	 /**
	 * 
	 */
	@Test
	  public void testNotLeadingInverse() {
	    Matrix a = new Matrix(new double[][] { { 0, 2, 0 }, { 1, 0, 0 }, { 0, 0, 3 } });
	    Matrix inverse = new Matrix(new double[][] { { 0, 1, 0 }, { 0.5, 0, 0 }, { 0, 0, 1.0 / 3 } });
	    this.assertEquals(inverse, this.mcp.inverse(a));
	  }
	 
	 /**
	 * 
	 */
	@Test
	  public void testNotLeadingInverse2() {
	    Matrix a = new Matrix(new double[][] { { 0, 2, 3 }, { 1, 3, 0 }, { 4, 0, 3 } });
	    Matrix inverse = new Matrix(new double[][] { //
	        { -3.0 / 14, 1.0 / 7, 3.0 / 14 }, //
	        { 1.0 / 14, 2.0 / 7, -1.0 / 14 }, //
	        { 2.0 / 7, -4.0 / 21, 1.0 / 21 } //
	    });
	    this.assertEquals(inverse, this.mcp.inverse(a));
	  }
	 
	 
	 /**
	 * 
	 */
	@Test
	  public void testInverseCopy() {
	    Matrix a = new Matrix(new double[][] { { 0, 2, 3 }, { 1, 3, 0 }, { 4, 0, 3 } });
	    Matrix i = this.mcp.inverse(a);
	    Assert.assertNotSame(a, i);
	    this.assertEquals(new double[][] { { 0, 2, 3 }, { 1, 3, 0 }, { 4, 0, 3 } }, a);
	  }
	 

	private void display(Matrix mat3) {			
		
		for (int row = 0; row < mat3.rows(); row++) {
			
			for (int aj = 0; aj < mat3.cols(); aj++) {
				System.out.printf("%.2f ", mat3.get(row, aj));
			}
			System.out.println();
		}	
		System.out.println();	
}

}
