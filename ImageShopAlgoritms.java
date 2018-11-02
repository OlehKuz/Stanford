/* 
 * Note: these methods are public in order for them to be used by other files
 * in this assignment; DO NOT change them to private.  You may add additional
 * private methods to implement required functionality if you would like.
 * 
 * You should remove the stub lines from each method and replace them with your
 * implementation that returns an updated image.
 */

// TODO: comment this file explaining its behavior

import java.util.*;
import acm.graphics.*;

public class ImageShopAlgorithms implements ImageShopAlgorithmsInterface {

	public GImage flipHorizontal(GImage source) {
		int[][] pixels = source.getPixelArray();
		int[][] converted = new int[pixels.length][pixels[0].length];
		for(int row = 0; row < pixels.length; row++) {
			for(int col = 0; col < pixels[0].length; col++) {
				converted[row][converted[0].length - 1 - col]= pixels[row][col];
			}
		}
		GImage image = new GImage(converted);			
		return image;
	}

	public GImage rotateLeft(GImage source) {
		int[][] pixels = source.getPixelArray();
		int[][] converted = new int[pixels[0].length][pixels.length];
		for(int row = 0; row < pixels.length; row++) {
			for(int col = 0; col < pixels[0].length; col++) {
				converted[converted.length - col - 1][row]= pixels[row][col];
			}
		}
		GImage image = new GImage(converted);			
		return image;
	}

	public GImage rotateRight(GImage source) {
		int[][] pixels = source.getPixelArray();
		int[][] converted = new int[pixels[0].length][pixels.length];
		for(int row = 0; row < pixels.length; row++) {
			for(int col = 0; col < pixels[0].length; col++) {
				converted[col][row]= pixels[row][col];
			}
		}
		GImage image = new GImage(converted);			
		return image;
	}

	public GImage greenScreen(GImage source) {
		int[][] pixels = source.getPixelArray();
		int[][] converted = new int[pixels.length][pixels[0].length];
		for(int row = 0; row < pixels.length; row++) {
			for(int col = 0; col < pixels[0].length; col++) {				
				int red = GImage.getRed(pixels[row][col]);
				int green = GImage.getGreen(pixels[row][col]);
				int blue = GImage.getBlue(pixels[row][col]);
				int bigger = Math.max(red, blue);
				if(green > 2*bigger) {
					// if i set red, green, blue to 1, as in example, i have a black background after a transparent one
					converted[row][col] = GImage.createRGBPixel(red, green, blue, 0);
				}
				else {
					converted[row][col] = GImage.createRGBPixel(red, green, blue);
				}				
			}
		}
		GImage image = new GImage(converted);	
		
		return image;
	}

	public GImage equalize(GImage source) {
		int[][] pixels = source.getPixelArray();
		int[] luminoscityHistogram = new int[256];
		int[][] pixelLuminoscity = new int[pixels.length][pixels[0].length];
		computeLuminoscityPixels(pixels, luminoscityHistogram,pixelLuminoscity);
		double[] cumulLum = new double[256];		
		computeCumulativeLumin(luminoscityHistogram, cumulLum);
		equalizeImage(pixelLuminoscity, cumulLum);		
		source.setPixelArray(pixelLuminoscity);
		return source;
	}
	
	public void equalizeImage(int[][] eachPixelLum, double[] cumulativeNPixels) {
		int numberPixels = eachPixelLum.length*eachPixelLum[0].length;
		for(int row = 0; row < eachPixelLum.length; row++) {
			for(int col = 0; col < eachPixelLum[0].length; col++) {
				int pixelLum = eachPixelLum[row][col];
				double newLumin = 255 * cumulativeNPixels[pixelLum]/numberPixels;
				int newLuminInt = (int)newLumin;
				eachPixelLum[row][col] = GImage.createRGBPixel(newLuminInt, newLuminInt, newLuminInt);
			}
		}
		
	}
	
	public void computeLuminoscityPixels(int[][] sourceArray, int[] luminoscity, int[][] pixelLum) {
		for(int row = 0; row < sourceArray.length; row++) {
			for(int col = 0; col < sourceArray[0].length; col++) {
				int red = GImage.getRed(sourceArray[row][col]);
				int green = GImage.getGreen(sourceArray[row][col]);
				int blue = GImage.getBlue(sourceArray[row][col]);
				int lum = computeLuminosity(red, green, blue);
				pixelLum[row][col] = lum;				
				luminoscity[lum]++;				
			}
		}	
	}
	
	public void computeCumulativeLumin(int[] luminHisto, double[] cumulativeLum) {
		int sumPreviousCumul = 0;
		for(int i = 0; i < luminHisto.length; i++) {
			cumulativeLum[i] = luminHisto[i] + sumPreviousCumul;
			sumPreviousCumul+=luminHisto[i];
		}
	}

	public GImage negative(GImage source) {
		int[][] pixels = source.getPixelArray();
		int[][] converted = new int[pixels.length][pixels[0].length];
		for(int row = 0; row < pixels.length; row++) {
			for(int col = 0; col < pixels[0].length; col++) {				
				int red = GImage.getRed(pixels[row][col]);
				int green = GImage.getGreen(pixels[row][col]);
				int blue = GImage.getBlue(pixels[row][col]);
				int newRed = 255 - red;
				int newGreen = 255 - green;
				int newBlue = 255 - blue;
				converted[row][col] = GImage.createRGBPixel(newRed, newGreen, newBlue);
			}
		}
		GImage image = new GImage(converted);	
		return image;
	}

	public GImage translate(GImage source, int dx, int dy) {
		int[][] pixels = source.getPixelArray();
		int[][] converted = new int[pixels.length][pixels[0].length];
		for(int row = 0; row < pixels.length; row++) {
			for(int col = 0; col < pixels[0].length; col++) {
				/* Math.abs(row - dy) if we give a negative value for y , we will start from some y coordinate
				 * closer to the bottom of the figure. We need Math.abs() to always get a legitible coordinate value*/
				converted[row][col]= pixels[Math.abs(row - dy)%pixels.length][Math.abs(col-dx)%pixels[0].length];
			}
		}
		GImage image = new GImage(converted);			
		return image;
	}

	public GImage blur(GImage source) {
		int[][] pixels = source.getPixelArray();
		int[][] converted = new int[pixels.length][pixels[0].length];
		for(int row = 0; row < pixels.length; row++) {
			for(int col = 0; col < pixels[0].length; col++) {
				int count = 0;
				int red = 0;
				int green = 0;
				int blue = 0;
				for(int k = row - 1; k < row + 1; k++) {
					if(k >= 0 && k < pixels.length) {
						for(int l = col - 1; l < col + 1; l++) {
							if(l >= 0 && l < pixels[0].length) {
								red += GImage.getRed(pixels[k][l]);
								green += GImage.getGreen(pixels[k][l]);
								blue += GImage.getBlue(pixels[k][l]);
								count++;
							}
						}
					}
				}
				converted[row][col] = GImage.createRGBPixel(red/count, green/count, blue/count);
			}
		}
		GImage image = new GImage(converted);			
		return image;
	}
}
