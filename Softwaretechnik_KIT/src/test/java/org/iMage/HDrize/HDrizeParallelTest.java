package org.iMage.HDrize;

import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Gil Baram
 *
 */
public class HDrizeParallelTest {

	private static File[] files = new File[3];

	
	/**
	 * load images before every test
	 * @throws Exception  
	 */
	@Before
	public void setUpBefore() throws Exception {
		files[0] = new File("image\\input_1_10.jpg");
		files[1] = new File("image\\input_1_25.jpg");
		files[2] = new File("image\\input_1_80.jpg");
	}
	  

	/**
	 * this test compares between two images, one sequential variant and the is with thread number
	 */
	@Test
	public void test() {
		
		
		int samples = 82;
		double lambda = 30;

		InputStream[] images = toInputStream(files);
		
		HDrizeParallel hdp = new HDrizeParallel(2);
		BufferedImage hdr = hdp.createRGB(images, samples, lambda);
		File output = new File("target\\output.jpg");
		
			saveImage(hdr, output);
			images = toInputStream(files);
			HDrizeParallel hdp2 = new HDrizeParallel(8);
			BufferedImage hdr2 = hdp2.createRGB(images, samples, lambda);
			
			 File output2 = new File("target\\output2.jpg");
			 saveImage(hdr2, output2);

			 if (hdr2 == null) {
			      System.err.println("Some error occurred while creating hdr image");
			      System.exit(1);
			    }

			  
			    try {
			     
			      ImageIO.write(hdr, "png", output2);
			    } catch (IOException e) {
			      System.err.println("Could not save image: " + e.getMessage());
			      System.exit(1);
			    }

				BufferedImage img = null;
				try {
				    img = ImageIO.read(new File("target\\output.jpg"));
				} catch (IOException e) {
				}
				
				
				BufferedImage img2 = null;
				try {
				    img2 = ImageIO.read(new File("target\\output2.jpg"));
				} catch (IOException e) {
				}
				
				
				
				assertTrue(compareImages(img, img2));

	
	}
	
	/**
	 *  samples = 180
	 */
	@Test
	public void test2() {
		
		
		int samples = 180;
		double lambda = 30;

		InputStream[] images = toInputStream(files);
		
		HDrizeParallel hdp = new HDrizeParallel(1);
		BufferedImage hdr = hdp.createRGB(images, samples, lambda);
		File output = new File("target\\output3.jpg");
		
		saveImage(hdr, output);

			images = toInputStream(files);
			HDrizeParallel hdp2 = new HDrizeParallel(8);
			BufferedImage hdr2 = hdp2.createRGB(images, samples, lambda);
			 File output2 = new File("target\\output4.jpg");

			 if (hdr2 == null) {
			      System.err.println("Some error occurred while creating hdr image");
			      System.exit(1);
			    }

			  
			    try {
			     
			      ImageIO.write(hdr, "png", output2);
			    } catch (IOException e) {
			      System.err.println("Could not save image: " + e.getMessage());
			      System.exit(1);
			    }
			    
				BufferedImage img = null;
				try {
				    img = ImageIO.read(new File("target\\output.jpg"));
				} catch (IOException e) {
				}
				
				
				BufferedImage img2 = null;
				try {
				    img2 = ImageIO.read(new File("target\\output2.jpg"));
				} catch (IOException e) {
				}
				
				assertTrue(compareImages(img, img2));			
				
				
				
	
	}



	private void saveImage(BufferedImage hdr, File file) {
		 if (hdr == null) {
		      System.err.println("Some error occurred while creating hdr image");
		      System.exit(1);
		    }

		  
		    try {
		     
		      ImageIO.write(hdr, "png", file);
		    } catch (IOException e) {
		      System.err.println("Could not save image: " + e.getMessage());
		      System.exit(1);
		    }
		    
		    
		    
		
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
	
	

	
	/**
	 * compare between two images
	 * @param img1  
	 * @param img2 
	 * @return true or false
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
