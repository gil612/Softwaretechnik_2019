package org.iMage.HDrize;

import java.util.concurrent.Executors;

import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.IHDRCombine;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImage;

/**
 * @author Gil Baram
 *
 */
public class HDRCombineParallel extends Thread implements IHDRCombine  {
	private static final int CHANNELS = 3;

	  /**
	 * @param numThreads 
	 */
	public HDRCombineParallel(int numThreads) {
		Executors.newFixedThreadPool(numThreads);
	}

	/**
	 * @param curve 
	 * @param w 
	 * @param result 
	 * @param numerator 
	 * @param denominator 
	 * @param imageList 
	 */
	
	public HDRCombineParallel(ICameraCurve curve, float[] w, float[][][] result, float[][][] numerator,
			float[][][] denominator, EnhancedImage[] imageList) {
	}

	
	
	
	/**
	 * 
	 */
	public HDRCombineParallel() {
		
	}

	@Override
	  public HDRImage createHDR(ICameraCurve curve, EnhancedImage[] imageList) {
	    int width = imageList[0].getWidth();
	    int height = imageList[0].getHeight();

	    float[][][] result = new float[][][] { //
	        new float[height][width], //
	        new float[height][width], //
	        new float[height][width]//
	    };

	    float[][][] numerator = new float[][][] { //
	        new float[height][width], //
	        new float[height][width], //
	        new float[height][width]//
	    };

	    float[][][] denominator = new float[][][] { //
	        new float[height][width], //
	        new float[height][width], //
	        new float[height][width]//
	    };

	    float[] w = this.calculateWeights();

	    
	    
	    for (int i = 0; i < imageList.length; i++) {
	    	float exposure = imageList[i].getExposureTime();
	    

	      for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	          int[] color = imageList[i].getRGB(x, y);
	          float[] resp = curve.getResponse(color);

	          for (int c = 0; c < HDRCombineParallel.CHANNELS; c++) {
	            numerator[c][y][x] += w[color[c]] * resp[c] / exposure;
	            denominator[c][y][x] += w[color[c]];
	          }

	        }
	      }

	    }

	    for (int x = 0; x < width; x++) {
	      for (int y = 0; y < height; y++) {
	        for (int c = 0; c < HDRCombineParallel.CHANNELS; c++) {
	          result[c][y][x] = numerator[c][y][x] / denominator[c][y][x];
	        }
	      }
	    }

	    return new HDRImage(result[0], result[1], result[2]);
	  }

	@Override
	 public float[] calculateWeights() {

		float[] weight = new float[256];
		weight[0] = 1f;
		weight[255] = 1f;
		
		new Thread(new MyRunnableCombine(weight)).start();
		

	    return weight;
	  }

}
