package com.canny;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	public static int count;

	public static void generateImageEdge(float gaussianKernelRadius,
			float lowThreshold, float highThreshold, int gaussianKernelWidth,
			boolean contrastNormalized) throws IOException {
		Image image = ImageIO.read(new File(MainActivity.IMAGE_ORIGINAL));

		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bufferedImage.getGraphics();
		bg.drawImage(image, 0, 0, null);
		bg.dispose();

		CannyEdgeDetector detector = new CannyEdgeDetector(
				gaussianKernelRadius, lowThreshold, highThreshold,
				gaussianKernelWidth, contrastNormalized);
		detector.setSourceImage(bufferedImage);
		detector.process();

		BufferedImage edges = detector.getEdgesImage();
		File f = new File(count+MainActivity.IMAGE_EDGE);
		if (f.delete()) {
			f = new File(count+MainActivity.IMAGE_EDGE);
		}
		ImageIO.write(edges, "PNG", f);
		count++;

	}

}
