package org.iMage.HDrize.matrix;

import java.util.Random;

/**
 * @author Gil Baram
 *
 */
public class MyRunnable implements Runnable {

	private int[] x;
	private int[] y;
	private int i;
	private int imageWidth;
	private int imageHeight;
	private final Random random = new Random(42);

	
    /**
     * @param i 
     * @param x 
     * @param y 
     * @param imageWidth 
     * @param imageHeight  
     */
    public MyRunnable(int i, int[] x, int[] y, int imageWidth, int imageHeight) {
        this.i = i;
        this.x = x;
        this.y = y;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
       
    }
    


		
	

	
	/**
	 *
	 */
	public void run() {  
		x[i] = this.random.nextInt(imageWidth);
	    y[i] = this.random.nextInt(imageHeight);
	    
		
		
    }
        

}
        


