package org.iMage.HDrize;

import java.awt.image.BufferedImage;
import java.util.Objects;

import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImage;
import org.iMage.HDrize.base.images.HDRImageIO;
import org.iMage.HDrize.base.images.HDRImageIO.ToneMapping;
import org.iMage.HDrize.matrix.MatrixCalculator;

/**
 * @author Gil Baram
 *
 */

public class MyRunnableHDrize implements Runnable {

	private EnhancedImage[] result;
	private int samples;
	private double lambda;
	private MatrixCalculator mc;
	private ToneMapping mapping;
	private HDRCombineParallel combine;

		
	

	/**
	 * @param bi 
	 * @param combine 
	 * @param result 
	 * @param samples 
	 * @param lambda 
	 * @param mc 
	 * @param mapping 
	 */
	public MyRunnableHDrize(BufferedImage bi, HDRCombineParallel combine,
			 EnhancedImage[] result, int samples, double lambda, MatrixCalculator mc,
			ToneMapping mapping) {
		this.combine = combine;
		this.result = result;
		this.samples = samples;
		this.lambda = lambda;
		this.mc = mc;
		this.mapping = mapping;
		
	}

	@Override
	public void run() {
		
		HDRImageIO.createRGB(//
		        this.createHDR(result, samples, lambda, mc), //
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
