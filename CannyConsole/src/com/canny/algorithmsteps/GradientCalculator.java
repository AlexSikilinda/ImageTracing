package com.canny.algorithmsteps;

import java.awt.image.BufferedImage;

public class GradientCalculator extends AlgorithmStepBase implements
		AlgorithmStep {

	private final static float GAUSSIAN_CUT_OFF = 0.005f;
	private final static float MAGNITUDE_SCALE = 100F;
	private final static float MAGNITUDE_LIMIT = 1000F;
	private final static int MAGNITUDE_MAX = (int) (MAGNITUDE_SCALE * MAGNITUDE_LIMIT);

	float kernelRadius;

	int kernelWidth;

	int[] source;

	int[] result;

	public GradientCalculator(float kernelRadius, int kernelWidth) {
		this.kernelRadius = kernelRadius;
		this.kernelWidth = kernelWidth;

	}

	private void computeGradients(float kernelRadius, int kernelWidth) {
		float kernel[] = new float[kernelWidth];
		float diffKernel[] = new float[kernelWidth];
		int kwidth;
		for (kwidth = 0; kwidth < kernelWidth; kwidth++) {
			float g1 = gaussian(kwidth, kernelRadius);
			if (g1 <= GAUSSIAN_CUT_OFF && kwidth >= 2)
				break;
			float g2 = gaussian(kwidth - 0.5f, kernelRadius);
			float g3 = gaussian(kwidth + 0.5f, kernelRadius);
			kernel[kwidth] = (g1 + g2 + g3) / 3f
					/ (2f * (float) Math.PI * kernelRadius * kernelRadius);
			diffKernel[kwidth] = g3 - g2;
		}

		int initX = kwidth - 1;
		int maxX = width - (kwidth - 1);
		int initY = width * (kwidth - 1);
		int maxY = width * (height - (kwidth - 1));
	}

	private float gaussian(float x, float sigma) {
		return (float) Math.exp(-(x * x) / (2f * sigma * sigma));
	}

	@Override
	protected BufferedImage perfromStep() {
		// TODO Auto-generated method stub
		return null;
	}

}
