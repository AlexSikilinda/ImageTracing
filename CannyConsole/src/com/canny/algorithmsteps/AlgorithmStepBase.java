package com.canny.algorithmsteps;

import java.awt.image.BufferedImage;

public abstract class AlgorithmStepBase implements AlgorithmStep {

	protected int width;

	protected int height;

	protected BufferedImage sourceImage;

	protected int[] convertToArray(BufferedImage source) {
		return (int[]) source.getData().getDataElements(0, 0, width, height,
				null);
	}

	protected BufferedImage convertToBufferedImage(int[] source) {
		/*for (int i = 0; i < source.length; i++) {
			source[i] = source[i] & 0xff;
			// source[i] = source[i] & 0xff000000;
		}*/

		BufferedImage edgesImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		edgesImage.getWritableTile(0, 0).setDataElements(0, 0, width, height,
				source);

		return edgesImage;
	}

	public BufferedImage process(BufferedImage source) {
		fillFields(source);
		return perfromStep();
	}

	private void fillFields(BufferedImage image) {
		sourceImage = image;
		width = sourceImage.getWidth();
		height = sourceImage.getHeight();
	}

	protected abstract BufferedImage perfromStep();

}
