package com.canny;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	
	public static void generateImageEdge() throws IOException{
		Image image = ImageIO.read(new File(MainActivity.IMAGE_ORIGINAL));

		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bufferedImage.getGraphics();
		bg.drawImage(image, 0, 0, null);
		bg.dispose();

		CannyEdgeDetector detector = new CannyEdgeDetector();
		detector.setSourceImage(bufferedImage);
		detector.process();

		BufferedImage edges = detector.getEdgesImage();
		File f = new File(MainActivity.IMAGE_EDGE);
		ImageIO.write(edges, "PNG", f);

	}

}
