package com.canny.algorithmsteps;

import java.awt.image.BufferedImage;

public interface AlgorithmStep {
	BufferedImage process(BufferedImage source);
	int[] getFinalData();
}
