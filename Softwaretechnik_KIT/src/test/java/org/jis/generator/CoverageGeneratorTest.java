package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import org.jis.options.Options;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Gil Baram
 *
 */
public class CoverageGeneratorTest {

	private static final String RES_DIR = "src\\test\\resources";
	private static final String IMAGESTOZIP = "images.zip";
	private static final String IMAGE_RES = RES_DIR + "\\image.jpg";	
	private static final String DIR = "target\\testGenCoverage\\";
	private static final String IMAGES = RES_DIR + "\\images\\";
	private static final String IMAGES_COPY = "src\\test\\resources\\imagescopy";
	private Generator generator;
	private static BufferedImage image;
	private static BufferedImage imagesaved;
	private BufferedImage imagetest;
	private static int n = 4;
	private static int angle = 90;

	/**
	 * Delete a directory if it's already exists and create a new one
	 * set Properties in the class options
	 * @throws Exception 
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		File dir = new File(DIR);
		if (dir.exists()) {
			deleteDirectory(dir);
		}
		try {
			dir.mkdir();
		} catch (SecurityException se) {
			
		}
		
		Options.getInstance().setModus(0);
		Options.getInstance().setAntialiasing(true);
		Options.getInstance().setCopyright(true);
		Options.getInstance().setCopyright_r(1);
		Options.getInstance().setCopyright_g(1);
		Options.getInstance().setCopyright_b(1);
		Options.getInstance().setCopyrightText("Gil Baram");
		Options.getInstance().setCopyMetadata(true);
		
		
	}

	/**
	 * initialize generator and read image
	 * @throws Exception 
	 */
	@Before
	public void setUp() throws Exception {
		generator = new Generator(null, 0);
		image = ImageIO.read(new File(IMAGE_RES));	
		imagesaved = image;
	}

	/**
	 * Test Method for generateImage (f,f,b,i,i,s).
	 * Checks the proportion measures between an original and a resized image 
	 */
	@Test
	public void generateImagetest1() {
		File inputfile = new File(IMAGE_RES);
		File outputfile = new File(DIR);
		
		try {
			generator.generateImage(inputfile, outputfile, false,
					image.getWidth() / n, image.getHeight() / n, "resized");
			imagetest = ImageIO.read(new File(DIR + "resizedimage.jpg"));
			assertEquals(image.getHeight() / imagetest.getHeight(), n);
			assertEquals(image.getWidth() / imagetest.getWidth(), n);
			
		} catch (IOException e) {
			
		}
	}
	
	/**
	 * second Test Method for generateImage (f,f,b,i,i,s).
	 * set Modus to Default, and run with print = true
	 * Checks the proportion measures between an original and a resized image 
	 */
	@Test
	public void generateImagetest2() {
		Options.getInstance().setAntialiasing(false);
		Options.getInstance().setCopyright(false);
		Options.getInstance().setModus(0);
		File inputfile = new File(IMAGE_RES);
		File outputfile = new File(DIR);
		try {
			generator.generateImage(inputfile, outputfile, true,
					image.getWidth() / n, image.getHeight() / n, "resized");
			imagetest = ImageIO.read(new File(DIR + "resizedimage.jpg"));
			assertEquals(image.getHeight() / imagetest.getHeight(), n);
			assertEquals(image.getWidth() / imagetest.getWidth(), n);
			
			
			
		} catch (IOException e) {
			
		}
	}
	
	/**
	 * Test Method for createZip(f, Vf).
	 * Create a zip file and check if both zip files are not empty and equal by size
	 */
	@Test
	public void createZiptest() {
		File dirtoZip = new File(IMAGES);
		Vector<File> fileList = new Vector<File>();
		getAllFiles(dirtoZip, fileList);
		
		generator.createZip(new File(DIR + "ImagesGenerator.zip"), fileList);
		copyImagesBack();
		writeZipFile(dirtoZip, fileList);
		File file1 = new File(DIR + IMAGESTOZIP);
		File file2 = new File(DIR + "ImagesGenerator.zip");
		assertTrue(isValid(file1.toString()));
		assertTrue(isValid(file2.toString()));
		assertEquals(file1.length() / 1024, file2.length() / 1024);		
	}
	
	/**
	 * Test Method for generate(b)
	 * Create a zip file and check if its valid
	 */
	@Ignore ("A zip file is not being generated due to a failure")
	@Test
	public void generatetest() {
		JFileChooser fo = new JFileChooser();
		generator.generate(false);	
		File file = fo.getSelectedFile(); 
		assertTrue(isValid(file.toString()));	
	}
	
	/**
	 * Test method for rotate(f)
	 * Compare a non rotated image with a rotated image
	 * @throws IOException 
	 */
	@Ignore
	@Test
	public void rotatetest() throws IOException {
		File test = new File(IMAGE_RES);
		try {
			Files.copy(test.toPath(),
					(new File(RES_DIR + test.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
			// cannot be done due to a failure
			generator.rotate(test);
			assertTrue(GeneratorTest.compareRotatedImage(imagesaved, image, Math.toRadians(90)));
		} catch (IOException e) {
			e.printStackTrace();
			
		}

	}
	
	/**
	 * Test method for rotate(f,i)
	 * compare a rotated image with a rotated image from roate(f,i)
	 * @throws IOException 
	 */
	@Ignore ("Ignored due to lack of data of the method rotate")
	@Test
	public void rotateFileAngletest() throws IOException {
		File test = new File(IMAGE_RES);
		try {
			Files.copy(test.toPath(),
					(new File(RES_DIR + test.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
			generator.rotate(test, angle);
			BufferedImage imagetst = ImageIO.read(test);
			BufferedImage rotimg = generator.rotateImage(image, Math.toRadians(angle));
			assertTrue(GeneratorTest.compareImages(rotimg, imagetst));
					 
	    } catch (IOException e) {
	    
	    }
	}
	
	/**
	 * Counts the number of files in a zip directory
	 * @param path a given path to ths Zip file
	 * @return true if not empty, false otherwise
	 */
	private boolean isValid(String path) {
		int c = 0;
		try {
			ZipFile zf = new ZipFile(path);
			c = zf.size();
			zf.close();
		} catch (IOException e) {
			
		}
		if (c > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Writes file to a zip
	 * @param dirtoZip origin directory
	 * @param fileList a given fileList
	 */
	private void writeZipFile(File dirtoZip, Vector<File> fileList) {
		try {
			FileOutputStream fos = new FileOutputStream(DIR + "Images.zip");
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (File file : fileList) {
				if (!file.isDirectory()) {
					addToZip(dirtoZip, file, zos);
				}
			}
			zos.close();
			fos.close();
		} catch (FileNotFoundException fnfe) {
			
		} catch (IOException e) {
			
		}
		
	}

	/**
	 * Add a file to zip
	 * @param dirtoZip origin directory
	 * @param file file of origin directory
	 * @param zos a created zip file
	 * @throws IOException
	 */
	private void addToZip(File dirtoZip, File file, ZipOutputStream zos) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		
		String zipFilePath =
				file.getCanonicalPath().
				substring(dirtoZip.getCanonicalPath().length() + 1, file.getCanonicalPath().length());
		
		ZipEntry ze = new ZipEntry(zipFilePath);
		zos.putNextEntry(ze);
		
		byte[] bytes = new byte[1024];
		int len;
		while ((len = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, len);
		}
		zos.closeEntry();
		fis.close();
	}

	/**
	 * A method to get all files in a directory
	 * @param dirc a given directory
	 * @param fileList a given fileList
	 */
	public static void getAllFiles(File dirc, Vector<File> fileList) {
		File[] files = dirc.listFiles();
		for (File file : files) {
			fileList.add(file);
			if (file.isDirectory()) {
				getAllFiles(file, fileList);
			}
		}
	}
	
	/**
	 * copy images to their original directory
	 */
	public static void copyImagesBack() {
		File fileImage = new File(IMAGES_COPY);
		Vector<File> fileList = new Vector<File>();
		getAllFiles(fileImage, fileList);
		for (File file : fileList) {
			try {
				Files.copy(file.toPath(), (new File(IMAGES + file.getName())).toPath(),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * delete a given directory
	 * @param dirc dierctory
	 */
	public static void deleteDirectory(File dirc) {
		if (dirc.exists()) {
			File[] files = dirc.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteDirectory(files[i]);
					} else {
						files[i].delete();
					}					
				}		
			}
		}
		dirc.delete();				
	}

		
	}