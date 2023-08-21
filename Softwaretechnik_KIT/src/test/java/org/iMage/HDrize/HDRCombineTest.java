package org.iMage.HDrize;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

/**
 * @author Gil Baram
 *
 */
public class HDRCombineTest {
	private HDRCombine hdrc = new HDRCombine();


	/**
	 * Calculate the Weight on the curve for every pixel
	 */
	@Test
	public void testCalculateWeight() {
		float[] a = hdrc.calculateWeights();
		float[] b = new float[256];
		
		b[0] = 1;
		b[255] = 1;
		for (int i = 1; i < 20; i++) {
			b[i] = (float) (b[i - 1] + 0.05);
			b[255 - i] = b[i];
		}
		for (int i = 20; i < 236; i++) {
			b[i] = 2;
		}
		assertArrayEquals(a, b, 0);
	}

}
