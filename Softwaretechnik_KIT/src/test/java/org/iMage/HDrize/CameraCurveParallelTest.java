package org.iMage.HDrize;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.imaging.ImageReadException;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.matrix.IMatrixCalculator;
import org.iMage.HDrize.matrix.Matrix;
import org.iMage.HDrize.matrix.MatrixCalculatorParallel;
import org.junit.Test;


/**
 * @author Gil Baram
 *
 */
public class CameraCurveParallelTest {
	
	private CameraCurveParallel ccp;
	private CameraCurve cc;
	private EnhancedImage[] enhancedImages;
	private IMatrixCalculator<Matrix> mtxCalc;
	private IMatrixCalculator<Matrix> mtxCalc2;
	private int samples = 82;
	private double lambda = 30;
	
	


	
	/**
	 * 
	 */
	@Test
	public void test() {
		
		File[] files = new File[3];
		files[0] = new File("src\\test\\Resources\\image\\input_1_10.jpg");
		files[1] = new File("src\\test\\Resources\\image\\input_1_25.jpg");
		files[2] = new File("src\\test\\Resources\\image\\input_1_80.jpg");
		InputStream[] images = toInputStream(files);
		enhancedImages = toEnhancedImages(images);
		mtxCalc = new MatrixCalculatorParallel(8);
	
		ccp = new CameraCurveParallel(enhancedImages, samples, lambda, mtxCalc);
		ccp.calculate();

		InputStream[] images2 = toInputStream(files);
		EnhancedImage[] enhancedImages2 = toEnhancedImages(images2);
		mtxCalc2 = new MatrixCalculatorParallel(8);
		final long start = System.nanoTime();
		long time = 0;
		
		cc = new CameraCurve(enhancedImages, samples, lambda, mtxCalc);
		cc.calculate();
		
		final long stop = System.nanoTime();
	    time += stop - start;
		System.out.println(time);

		
		ccp = new CameraCurveParallel(enhancedImages2, samples, lambda, mtxCalc2);
		ccp.calculate();

		
		assertEquals(cc.isCalculated(), ccp.isCalculated());
;
		
		
	}
	
	
	private static InputStream[] toInputStream(File[] input) {
		  InputStream[] result = new InputStream[input.length];
		    for (int i = 0; i < result.length; i++) {
		      try {
		        result[i] = new BufferedInputStream(new FileInputStream(input[i]));
		      } catch (IOException e) {
		        System.err.println(e.getMessage());
		        System.exit(1);
		      }
		    }

		    return result;
		  }
	
	private EnhancedImage[] toEnhancedImages(InputStream[] images) {
	    EnhancedImage[] result = new EnhancedImage[images.length];
	    for (int i = 0; i < result.length; i++) {
	      try {
	        result[i] = new EnhancedImage(images[i]);
	      } catch (ImageReadException | IOException e) {
	        System.err.println(e.getMessage());
	        System.exit(1);
	      }
	    }

	    return result;
	  }

}
