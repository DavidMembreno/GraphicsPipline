package Tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * a class to provide image I/O operations
 */
public class ReadWriteImage {
	
	/**
	 * a color look-up table that can be applied to a 3D image
	 */
	public static int[][] LUT = new int[256][3];
	
	/**
	 * set the color look-up table values to the desired "look and feel"
	 * this can be any values that suit you
	 */
	public static void setLUT() {
		for (int i = 0; i < LUT.length; ++i) {
			LUT[i][0] = (int)(255 * Math.random()) % 256;
			LUT[i][1] = (int)(255 * Math.random()) % 256;
			LUT[i][2] = (int)(255 * Math.random()) % 256;
		}
		
	}

	/**
	 * Convert a 3 dimensional array (RGB image) to a BufferedImage
	 * @param img the 3 dimensional array holding image pixels
	 * @return the RGB BufferedImage
	 */
	public static BufferedImage toBI(int[][][] img) {
		// -- from array to BufferedImage
		BufferedImage bi = new BufferedImage(img[0][0].length, img[0].length, BufferedImage.TYPE_INT_RGB);
    	for (int i = 0; i < bi.getHeight(); ++i) {
    	    for (int j = 0; j < bi.getWidth(); ++j) {
    			int pixel =	(img[0][i][j] << 16) | (img[1][i][j] << 8) | (img[2][i][j]);
    			bi.setRGB(j, i, pixel);
    		}
    	}
		return bi;
	}

	/**
	 * Write a 3D array (assumed to be RGB) to a PNG file
	 * @param img the 3D array RGB image
	 * @param filename the filename to be written (.PNG)
	 * @throws IOException if the file cannot be written
	 */
	public static void writeImage(int[][][] img, String filename) throws IOException {
		try {
			// -- from array to BufferedImage
			BufferedImage bi = new BufferedImage(img[0][0].length, img[0].length, BufferedImage.TYPE_INT_RGB);
	    	for (int i = 0; i < bi.getHeight(); ++i) {
	    	    for (int j = 0; j < bi.getWidth(); ++j) {
	    			int pixel =	(img[0][i][j] << 16) | (img[1][i][j] << 8) | (img[2][i][j]);
	    			bi.setRGB(j, i, pixel);
	    		}
	    	}
			ImageIO.write(bi, "PNG", new File(filename));
		}
		catch (IOException e) {
			throw e;
		}

	}
	
	/**
	 * Read an image into 3D array (assumed to be RGB)
	 * @param filename the filename to be read (.PNG)
	 * @throws IOException if the file cannot be read
	 * @return the 3D array of pixel values [3][height][width]
	 */
	public static int[][][] readImage(String filename) throws IOException {
		try {
			BufferedImage bi = ImageIO.read(new File(filename));
			// -- from BufferedImage to array
			int[][][] img = new int[3][bi.getHeight()][bi.getWidth()];
	    	for (int i = 0; i < bi.getHeight(); ++i) {
	    	    for (int j = 0; j < bi.getWidth(); ++j) {
	    	    	img[0][i][j] = (bi.getRGB(j, i) >> 16) & 0xFF;
	    	    	img[1][i][j] = (bi.getRGB(j, i) >> 8) & 0xFF;
	    	    	img[2][i][j] = (bi.getRGB(j, i)) & 0xFF;
	    		}
	    	}
	    	return img;
		}
		catch (IOException e) {
			throw e;
		}
	
	}

}
