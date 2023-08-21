package iMage.filter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

/**
 * An interface for a couple images
 * @author Gil Baram
 *
 * @param <T> represesnt a generic parameter of type  BufferedImage
 */
public interface filterImages<T> {
	/**
	 * gather all images to a vector
	 * @param input The Source file of the images
	 * @param vb the List parameter 
	 */
	void getAllImages(File input, Vector<BufferedImage> vb);
	
	/**
	 * Process images from a vector
	 * @param vb vector of images
	 * @param output The output which all images should be saved
	 * @return a processed image
	 */
	Vector<BufferedImage> imagesProcess(Vector<BufferedImage> vb, File output);
	
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
	
	/**
	 * get an ordinary image from a vector
	 * @param vb vector of images
	 * @return an ordinary image
	 */
	BufferedImage getImage(Vector<BufferedImage> vb);

}
