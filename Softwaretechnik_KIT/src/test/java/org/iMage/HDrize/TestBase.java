package org.iMage.HDrize;

import java.awt.image.BufferedImage;

import org.iMage.HDrize.base.matrix.IMatrix;
import org.iMage.HDrize.matrix.Matrix;
import org.junit.Assert;

/**
 * @author Gil Baram
 *
 */
public class TestBase {

	 protected static final double EPS = 1E-8;

	  /**
	   * Check whether two matrices are equal.
	   *
	   * @param expected
	   *          the expected matrix
	   * @param actual
	   *          the given matrix
	   */
	  protected final void assertEquals(double[][] expected, Matrix actual) {
	    Assert.assertEquals(expected.length, actual.rows());
	    Assert.assertEquals(expected[0].length, actual.cols());

	    for (int r = 0; r < actual.rows(); r++) {
	      for (int c = 0; c < actual.cols(); c++) {
	        Assert.assertEquals("Matrix differ at " + r + "," + c, //
	            expected[r][c], actual.get(r, c), TestBase.EPS);
	      }
	    }

	  }
	  
	  /**
	 * @param mtx 
	 * @return true if identity otherwise false
	 */
	protected final boolean isIdentity(IMatrix mtx) {
		    if (mtx.cols() != mtx.rows()) {
		      return false;
		    }

		    for (int r = 0; r < mtx.rows(); r++) {
		      for (int c = 0; c < mtx.cols(); c++) {
		        if (r == c && Math.abs(mtx.get(r, c) - 1) >= TestBase.EPS) {
		          return false;
		        } else if (r != c && Math.abs(mtx.get(r, c)) >= TestBase.EPS) {
		          return false;
		        }
		      }
		    }
		    return true;
		  }
	
	/**
	 * compare between two images
	 * @param img1  
	 * @param img2 
	 * @return true or false
	 */
	protected final boolean compareImages(BufferedImage img1, BufferedImage img2) {
		if ((img1.getHeight() == img2.getHeight()) && (img1.getWidth() == img2.getWidth())) {
			for (int x = 0; x < img1.getWidth(); x++) {
				for (int y = 0; y < img1.getHeight(); y++) {
					if (img1.getRGB(x, y) != img2.getRGB(x, y))  {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

}
