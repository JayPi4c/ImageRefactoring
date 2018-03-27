package com.JayPi4c;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import net.coobird.thumbnailator.makers.FixedSizeThumbnailMaker;
import net.coobird.thumbnailator.resizers.DefaultResizerFactory;
import net.coobird.thumbnailator.resizers.Resizer;

public class Editor {
	public static final int TOP = 0;
	public static final int BOTTOM = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;

	public int treshhold = 15;

	public static final int EDGESPACER = 7;

	private BufferedImage img;
	private Boundaries bounds;

	public Editor(BufferedImage img, int tresh) {
		this.img = img;
		this.treshhold = tresh;
	}

	public BufferedImage edit() {
		findEdges();
		BufferedImage img2 = cutImage();
		BufferedImage img3 = scaleThumbnailator(img2, 50, 50); // resizeImage(img2,
																// BufferedImage.TYPE_INT_RGB,
																// 50,50);
		return img3;
	}

	private void findEdges() {
		int leftEdge = 0;
		int rightEdge = 0;
		int topEdge = 0;
		int bottomEdge = 0;

		// find edge of coin
		// LEFT
		int y = (img.getHeight() / 2);
		int x;
		for (x = 1; x < img.getWidth(); x++) {
			if (isEdge(img, LEFT, y, x - 1, x)) {
				System.out.println("Found a Edge at x: " + x + ", y: " + y);
				leftEdge = x - EDGESPACER;
				break;
			}
		}

		// RIGHT
		for (x = img.getWidth() - 2; x > 0; x--) {
			if (isEdge(img, RIGHT, y, x + 1, x)) {
				System.out.println("Found a Edge at x: " + x + ", y: " + y);
				rightEdge = x + EDGESPACER;
				break;
			}
		}

		// TOP
		x = img.getWidth() / 2;
		for (y = 1; y < img.getHeight(); y++) {
			if (isEdge(img, TOP, x, y - 1, y)) {
				System.out.println("Found a Edge at x: " + x + ", y: " + y);
				topEdge = y - EDGESPACER;
				break;
			}
		}

		// BOTTOM
		for (y = img.getHeight() - 2; y > 0; y--) {
			if (isEdge(img, BOTTOM, x, y + 1, y)) {
				System.out.println("Found a Edge at x: " + x + ", y: " + y);
				bottomEdge = y + EDGESPACER;
				break;
			}
		}

		bounds = new Boundaries(leftEdge, rightEdge, topEdge, bottomEdge);
	}

	/**
	 * 
	 * @param img
	 * @param direction
	 *            Die Richtung, von der die Pixel getestet werden
	 * @param pixelA
	 *            Der Pixel, der für beide gleich bleibt -> wenn es TOP ist, dan
	 *            ist PixelA der x-Wert
	 * @param pixelB
	 *            Der zweite Teil previous Pixel
	 * @param pixelC
	 *            De zweite Teil des aktuellen Pixels
	 * @return
	 */
	private boolean isEdge(BufferedImage img, int direction, int pixelA, int pixelB, int pixelC) {
		switch (direction) {
		case TOP:
			Color pPixel = new Color(img.getRGB(pixelA, pixelB));
			Color Pixel = new Color(img.getRGB(pixelA, pixelC));
			return compareColor(pPixel, Pixel);
		case BOTTOM:
			pPixel = new Color(img.getRGB(pixelA, pixelB));
			Pixel = new Color(img.getRGB(pixelA, pixelC));
			return compareColor(pPixel, Pixel);
		case LEFT:
			pPixel = new Color(img.getRGB(pixelB, pixelA));
			Pixel = new Color(img.getRGB(pixelC, pixelA));
			return compareColor(pPixel, Pixel);
		case RIGHT:
			pPixel = new Color(img.getRGB(pixelB, pixelA));
			Pixel = new Color(img.getRGB(pixelC, pixelA));
			return compareColor(pPixel, Pixel);
		default:
			return false;
		}
	}

	private boolean compareColor(Color a, Color b) {
		int aR = a.getRed();
		int aG = a.getGreen();
		int aB = a.getBlue();

		int bR = b.getRed();
		int bG = b.getGreen();
		int bB = b.getBlue();

		if (Math.abs(aR - bR) > treshhold || Math.abs(aG - bG) > treshhold || Math.abs(aB - bB) > treshhold) {
			return true;
		}

		return false;
	}

	private BufferedImage cutImage() {
		return img.getSubimage(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
	}

	private BufferedImage scaleThumbnailator(BufferedImage original, int width, int height) {
		BufferedImage scaledImage = null;
		if (original != null) {
			Resizer resizer = DefaultResizerFactory.getInstance()
					.getResizer(new Dimension(original.getWidth(), original.getHeight()), new Dimension(width, height));
			scaledImage = new FixedSizeThumbnailMaker(width, height, false, true).resizer(resizer).make(original);
		}
		return scaledImage;
	}

}
