package org.iMage.HDrize;

/**
 * @author Gil Baram
 *
 */
public class MyRunnableCombine implements Runnable {
	

	private float[] weight;



	/**
	 * @param weight 
	 */
	public MyRunnableCombine(float[] weight) {
		this.weight = weight;
	}

	@Override
	public void run() {
		final int offset = 20;

	    for (int a = 1; a < offset; a++) {
	    	float f = (float) (weight[a - 1] + (1.0 / offset));
	      weight[a] = f;
	      weight[256 - a - 1] = f;
	    }
	    for (int a = offset; a < (256 - offset); a++) {
	      weight[a] = 2;
	    }

	}

}
