package iMage.filter;

import java.awt.image.BufferedImage;

/**
 * An Interface for a single image
 * @author Gil Baram
 *
 */
public interface filter extends filterImages<BufferedImage> {
	
	/**
	 * create a copy of an image
	 * @param image the image we want to copy
	 * @return a copy of an image
	 */
	BufferedImage createCopy(BufferedImage image);
	/**
	 * process an image
	 * @param image the image we want to process
	 * @return a processed image
	 */
	BufferedImage imageProcess(BufferedImage image);
	
	}

