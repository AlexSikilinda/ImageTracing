package com.canny.algorithmsteps;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class EdgesDetector extends AlgorithmStepBase {

	float lowThreshold;
	
	float highThreshold;
	
	int[] data;
	
	int[] magnitude;
	
	private final static float GAUSSIAN_CUT_OFF = 0.005f;
	private final static float MAGNITUDE_SCALE = 100F;
	private final static float MAGNITUDE_LIMIT = 1000F;
	private final static int MAGNITUDE_MAX = (int) (MAGNITUDE_SCALE * MAGNITUDE_LIMIT);
	
	//data - это то шо после первого степа получилось
	public EdgesDetector(float low, float high) {
		this.lowThreshold = low;
		this.highThreshold = high;
		//data = convertToArray(firstStepResult);
		magnitude = convertToArray(sourceImage);
	}
	
	@Override
	protected BufferedImage perfromStep() {
		int low = Math.round(lowThreshold * MAGNITUDE_SCALE);
		int high = Math.round(highThreshold * MAGNITUDE_SCALE);
		performHysteresis(low, high);
		thresholdEdges();
		return convertToBufferedImage(data);
	}
	
	private void performHysteresis(int low, int high) {		
		Arrays.fill(data, 0);

		int offset = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (data[offset] == 0 && magnitude[offset] >= high) {
					follow(x, y, offset, low);
				}
				offset++;
			}
		}
	}

	private void follow(int x1, int y1, int i1, int threshold) {
		int x0 = x1 == 0 ? x1 : x1 - 1;
		int x2 = x1 == width - 1 ? x1 : x1 + 1;
		int y0 = y1 == 0 ? y1 : y1 - 1;
		int y2 = y1 == height - 1 ? y1 : y1 + 1;

		data[i1] = magnitude[i1];
		for (int x = x0; x <= x2; x++) {
			for (int y = y0; y <= y2; y++) {
				int i2 = x + y * width;
				if ((y != y1 || x != x1) && data[i2] == 0
						&& magnitude[i2] >= threshold) {
					follow(x, y, i2, threshold);
					return;
				}
			}
		}
	}
	
	private void thresholdEdges() {
		for (int i = 0; i < width * height; i++) {
			data[i] = data[i] > 0 ? -1 : 0xff000000;
		}
	}

	@Override
	public int[] getFinalData() {
		// TODO Auto-generated method stub
		return null;
	}

}
