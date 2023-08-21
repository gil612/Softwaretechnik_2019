package org.jis.generator;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Gil Baram
 *
 */
public class GeneratorTest {
	
	private Generator generator;
	private static BufferedImage image;
	private static BufferedImage rotateimg;
	
	private static final String IMAGENAME = "image";
	private static final String IMAGERES = "src\\test\\resources\\image.jpg";
	private static final String DIR = "target\\test\\";
	

	/**
	 * Initialize generator and read an image
	 * @throws Exception 
	 */
	@Before
	public void setUp() throws Exception {
		generator = new Generator(null, 0);
		image = ImageIO.read(new File(IMAGERES));
	}

	/**
	 * runs after every testmethod. It opens a new / used directory
	 * to save the image with an actual time and date-string.
	 * @throws Exception 
	 */
	@After
	public void tearDown() throws Exception {
		File dir = new File(DIR);
		if (!dir.exists()) {
			try {
				dir.mkdir();
			} catch (SecurityException se) {

			}
		}
		SimpleDateFormat date = new SimpleDateFormat("MM-dd_HH.mm.ss.SSS");
	    String timestr = date.format(new Date());
	    File outputfile = new File(DIR + IMAGENAME + "_rotated_" + timestr + ".jpg");
	    try {
	    	ImageIO.write(rotateimg, "jpg", outputfile);
	    } catch (IllegalArgumentException iae) {
	    	
	    }
	}

	/**
	 * checks for any instance of generator if it's legal
	 * to call rotateImage(..) with 0
	 */
	@Test
	public void rotatezero() {
		rotateimg = generator.rotateImage(image, 0.0);
		assertTrue(compareImages(rotateimg, image));
	}
	
	/**
	 * Checks if it's legal to call rotateimage(..)
	 * with a null generator and 0
	 */
	@Test
	public void rotateNull() {
		rotateimg = generator.rotateImage(null, 0.0);
		assertNull(rotateimg);
	}
	
	
	/**
	 * Checks if it's legal to call rotateimage(..)
	 * with a angle, which is not multiply of 90.
	 * Expecting an {@link IllegalArgumentException}.
	 */
	@Test 
	public void exceptionTest() {
		try {
			rotateimg = generator.rotateImage(image, 0.42);
		} catch (IllegalArgumentException e) {
			System.err.println("IllegalArgumentException");
		}
	}
	
	/**
	 * Rotate the image 90°
	 */
	@Test 
	public void rotate90() {
		rotateimg = generator.rotateImage(image, Math.toRadians(90));
		assertTrue(compareRotatedImage(image, rotateimg, Math.toRadians(90)));
	}
	
	
	/**
	 * Rotate the image 270°  
	 */
	@Test 
	public void rotate270() {
		rotateimg = generator.rotateImage(image, Math.toRadians(90));
		assertTrue(compareRotatedImage(image, rotateimg, Math.toRadians(90)));
	}
	
	/**
	 * Rotate the image -90°
	 */
	@Test 
	public void rotateneg90() {
		rotateimg = generator.rotateImage(image, Generator.ROTATE_neg90);
		assertTrue(compareRotatedImage(image, rotateimg, Generator.ROTATE_neg90));
	}
	
	/**
	 * Rotate the image -270°
	 */
	@Test 
	public void rotateneg270() {
		rotateimg = generator.rotateImage(image, Generator.ROTATE_neg270);
		assertTrue(compareRotatedImage(image, rotateimg, Generator.ROTATE_neg270));
	}
	
	
	

	/**
	 * Receives two images and an angle and check if they are rotated to each other
	 * @param img1 image
	 * @param img2 rotateimg
	 * @param angle - the requested angle to rotate the image
	 * @return return true if equal, otherwise falls
	 */
	public static boolean compareRotatedImage(BufferedImage img1, BufferedImage img2, double angle) {
		assertEquals(img1.getHeight() == img2.getWidth(), img1.getWidth() == img2.getHeight());
		
		if (angle == Math.toRadians(90) || angle == Math.toRadians(-270)) {
			for (int y = 0; y < img1.getHeight(); y++) {
				for (int x = 0; x < img1.getWidth(); x++) {
					assertTrue(img1.getRGB(x, y) == (img2.getRGB(img1.getHeight() - 1 - y, x)));
				}
			}
			return true;
			
		} else {
			if (angle == Math.toRadians(270) || angle == Math.toRadians(-90)) {
				for (int y = 0; y < img1.getHeight(); y++) {
					for (int x = 0; x < img1.getWidth(); x++) {
						assertTrue((img1.getRGB(x, y) == (img2.getRGB(y, img1.getWidth() - x - 1))));
					}
				}
				return true;
			}
		}
		return true;
	}
	
	/**
	 * Receives 2 Images of BuffereImage and checks
	 * if the are equal
	 * @param img1 image
	 * @param img2 rotateimg
	 * @return true if equal, otherwise falls
	 */
	public static boolean compareImages(BufferedImage img1, BufferedImage img2) {
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
