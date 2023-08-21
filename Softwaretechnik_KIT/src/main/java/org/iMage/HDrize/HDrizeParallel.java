package org.iMage.HDrize;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.apache.commons.imaging.ImageReadException;
import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.IHDrizeParallel;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImage;
import org.iMage.HDrize.base.images.HDRImageIO;
import org.iMage.HDrize.base.images.HDRImageIO.ToneMapping;
import org.iMage.HDrize.matrix.MatrixCalculator;
import org.iMage.HDrize.matrix.MatrixCalculatorParallel;

/**
 * @author Gil Baram
 *
 */
public class HDrizeParallel implements IHDrizeParallel {
	private HDRCombineParallel combine;
	private int numThreads;
	private HDrize seq;
	
	/**
	 * Constructor for HDrizeParallel without a given Thread number 
	 */
	public HDrizeParallel() {
		 this(Runtime.getRuntime().availableProcessors());
	}
	
	/**
	 * Constructor
	 * @param numThreads Number of Thread
	 */
	public HDrizeParallel(int numThreads) {
	    this.numThreads = numThreads;
	    this.combine = new HDRCombineParallel(numThreads);
	  }

	@Override
	public BufferedImage createRGB(InputStream[] images, int samples, double lambda)  {

		EnhancedImage[] result = toEnhancedImages(images);
		ToneMapping mapping = ToneMapping.SRGBGamma;
		if (numThreads == 1) {
		      this.seq = new HDrize();
		      return seq.createRGB(result, samples, lambda, new MatrixCalculator(), mapping);
		} else {
			MatrixCalculator mc = new MatrixCalculatorParallel(numThreads);
			this.seq = null;
			return this.createRGB(result, samples, lambda, mc, mapping);
		}
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

	/**
	 * @param enhancedImages images
	 * @param samples samples
	 * @param lambda lambda
	 * @param mtxCalc mtxCalc
	 * @param mapping mapping variable
	 * @return an image
	 */
	private BufferedImage createRGB(EnhancedImage[] enhancedImages, int samples,
			double lambda, MatrixCalculator mtxCalc,
			ToneMapping mapping) {
		 return HDRImageIO.createRGB(//
			        this.createHDR(enhancedImages, samples, lambda, mtxCalc), //
			        Objects.requireNonNull(mapping, "mapping cannot be null") //
			    );
	}

	private HDRImage createHDR(EnhancedImage[] images, int samples, double lambda, MatrixCalculator mtxCalc) {
		ICameraCurve curve = this.createCurve(//
		        Objects.requireNonNull(images, "images cannot be null"), //
		        samples, lambda, //
		        Objects.requireNonNull(mtxCalc, "mtxCalc cannot be null") //
		    );

		    if (curve == null) {
		      return null;
		    }
		    return this.combine.createHDR(curve, images);
	  }

	private ICameraCurve createCurve(EnhancedImage[] images, int samples, double lambda,
		      MatrixCalculator mtxCalc) {
		if (lambda <= 0 || lambda > 100) {
		      throw new IllegalArgumentException("Lambda has to be in (0,100]");
		    }
		    if (samples < 1 || samples > 1000) {
		      throw new IllegalArgumentException("samples has to be in [1,1000]");
		    }

		    CameraCurveParallel cc = new CameraCurveParallel(images, samples, lambda, mtxCalc);
		    //CameraCurve cc = new CameraCurve(images,samples,lambda,mtxCalc);
		    cc.calculate();

		   
		    return cc.isCalculated() ? cc : null;
		  }



}
