package com.canny.algorithmsteps;

import java.awt.image.BufferedImage;

public class LuminanceConverter extends AlgorithmStepBase implements AlgorithmStep {

	int[] source;

	int[] result;

	private void process() {
		int picsize = source.length;
		result = new int[picsize];
		for (int i = 0; i < picsize; i++) {
			int p = source[i];
			int r = (p & 0xff0000) >> 16;
			int g = (p & 0xff00) >> 8;
			int b = p & 0xff;
			result[i] = luminance(r, g, b);
		}
	}

	private int luminance(float r, float g, float b) {
		return Math.round(0.299f * r + 0.587f * g + 0.114f * b);
	}

	@Override
	protected BufferedImage perfromStep() {
		source = convertToArray(sourceImage);
		process();
		return convertToBufferedImage(result);
	}

}
