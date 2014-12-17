package com.canny.algorithmsteps;

public class LuminanceConverter implements AlgorithmStep {

	int[] source;

	int[] result;

	@Override
	public void process(int[] source) {
		this.source = source;
		int picsize = source.length;
		result = new int[picsize];
		for (int i = 0; i < picsize; i++) {
			int p = source[i];
			int r = (p & 0xff0000) >> 16;
			int g = (p & 0xff00) >> 8;
			int b = p & 0xff;
			source[i] = luminance(r, g, b);
		}
	}

	@Override
	public int[] getSourceImage() {
		return source;
	}

	@Override
	public int[] getResultImage() {
		return result;
	}

	private int luminance(float r, float g, float b) {
		return Math.round(0.299f * r + 0.587f * g + 0.114f * b);
	}

}
