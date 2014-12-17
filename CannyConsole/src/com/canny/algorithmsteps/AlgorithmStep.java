package com.canny.algorithmsteps;

public interface AlgorithmStep {
	void process(int[] source);
	int[] getSourceImage();
	int[] getResultImage();
}
