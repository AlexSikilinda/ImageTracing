$(document).ready(function() {
	
	GAUSSIAN_CUT_OFF = 0.005;
	MAGNITUDE_SCALE = 100;
	MAGNITUDE_LIMIT = 1000;
	MAGNITUDE_MAX = (MAGNITUDE_SCALE * MAGNITUDE_LIMIT);

	// fields	
	height = 447;
	width=293;
	picsize =0;
	data = [];
	//magnitude;
	//sourceImage;
	//edgesImage;
	pixels=[];
	
	lowThreshold = 2.5;
	highThreshold = 7.5;
	gaussianKernelRadius = 2;
	gaussianKernelWidth = 16;
	contrastNormalized = false;

	//xConv;
	//yConv;
	//xGradient;
	//yGradient;


	var imageHeight = 447;
	var imageWidth = 293;

	var canvas = document.getElementById("myCanvas");
	var ctx = canvas.getContext("2d");	
	var image = new Image();
	image.crossOrigin = '*';
	image.src = "test.png";

	
	
	$(image).load(function() {
		ctx.drawImage(image, 0, 0,imageWidth,imageHeight);
		
		var imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
		pixels = imageData.data;		
		picsize = imageData.width * imageData.height;
		
		process();
			
		ctx.clearRect(0, 0, canvas.width, canvas.height);
		ctx.putImageData(imageData, 0, 0);
		
		
		/*for (var i = 0; i < numPixels; i++) {
			pixels[i*4] = 255-pixels[i*4]; // Red
			pixels[i*4+1] = 255-pixels[i*4+1]; // Green
			pixels[i*4+2] = 255-pixels[i*4+2]; // Blue
		};
		
		ctx.clearRect(0, 0, canvas.width, canvas.height);
		ctx.putImageData(imageData, 0, 0);*/
	
	
	});
	
	
	
	function process() {		
		initArrays();
		readLuminance();
		computeGradients(gaussianKernelRadius, gaussianKernelWidth);
		low = Math.round(lowThreshold * MAGNITUDE_SCALE);
		high = Math.round( highThreshold * MAGNITUDE_SCALE);
		performHysteresis(low, high);
		
		thresholdEdges();
		
		/*if (contrastNormalized) normalizeContrast();
		
		int low = Math.round(lowThreshold * MAGNITUDE_SCALE);
		int high = Math.round( highThreshold * MAGNITUDE_SCALE);
		performHysteresis(low, high);
		thresholdEdges();
		writeEdges(data);*/
	}
	
	function initArrays() {		
		data = new Array(picsize);
		magnitude = new Array(picsize);

		xConv = new Array(picsize);
		yConv = new Array(picsize);
		xGradient = new Array(picsize);
		yGradient = new Array(picsize);		
	}
	
	function readLuminance() {			
		for (var i = 0; i < picsize; i++) {			
			r = pixels[i*4];
			g = pixels[i*4+1];
			b = pixels[i*4+2];;
			data[i] = luminance(r, g, b);
		}		
	}
	
	function luminance(r,g,b) {
		return Math.round(0.299 * r + 0.587 * g + 0.114 * b);
	}
	
	
	function computeGradients(kernelRadius,kernelWidth) {
		
		//generate the gaussian convolution masks
		kernel = new Array(kernelWidth);
		diffKernel = new Array(kernelWidth);
		kwidth=0;
		for (kwidth = 0; kwidth < kernelWidth; kwidth++) {
			g1 = gaussian(kwidth, kernelRadius);
			if (g1 <= GAUSSIAN_CUT_OFF && kwidth >= 2) break;
			g2 = gaussian(kwidth - 0.5, kernelRadius);
			g3 = gaussian(kwidth + 0.5, kernelRadius);
			kernel[kwidth] = (g1 + g2 + g3) / 3 / (2 * Math.PI * kernelRadius * kernelRadius);
			diffKernel[kwidth] = g3 - g2;
		}

		initX = kwidth - 1;
		maxX = width - (kwidth - 1);
		initY = width * (kwidth - 1);
		maxY = width * (height - (kwidth - 1));
		
		//perform convolution in x and y directions
		for (var x = initX; x < maxX; x++) {
			for (var y = initY; y < maxY; y += width) {
				var index = x + y;
				var sumX = data[index] * kernel[0];
				var sumY = sumX;
				var xOffset = 1;
				var yOffset = width;
				for(; xOffset < kwidth ;) {
					sumY += kernel[xOffset] * (data[index - yOffset] + data[index + yOffset]);
					sumX += kernel[xOffset] * (data[index - xOffset] + data[index + xOffset]);
					yOffset += width;
					xOffset++;
				}
				
				yConv[index] = sumY;
				xConv[index] = sumX;
			}
 
		}
 
		for (var x = initX; x < maxX; x++) {
			for (var y = initY; y < maxY; y += width) {
				var sum = 0;
				var index = x + y;
				for (var i = 1; i < kwidth; i++)
					sum += diffKernel[i] * (yConv[index - i] - yConv[index + i]);
 
				xGradient[index] = sum;
			}
 
		}

		for (var x = kwidth; x < width - kwidth; x++) {
			for (var y = initY; y < maxY; y += width) {
				var sum = 0.0;
				var index = x + y;
				var yOffset = width;
				for (var i = 1; i < kwidth; i++) {
					sum += diffKernel[i] * (xConv[index - yOffset] - xConv[index + yOffset]);
					yOffset += width;
				}
 
				yGradient[index] = sum;
			}
 
		}
 
		initX = kwidth;
		maxX = width - kwidth;
		initY = width * kwidth;
		maxY = width * (height - kwidth);
		for (var x = initX; x < maxX; x++) {
			for (var y = initY; y < maxY; y += width) {
				var index = x + y;
				var indexN = index - width;
				var indexS = index + width;
				var indexW = index - 1;
				var indexE = index + 1;
				var indexNW = indexN - 1;
				var indexNE = indexN + 1;
				var indexSW = indexS - 1;
				var indexSE = indexS + 1;
				
				var xGrad = xGradient[index];
				var yGrad = yGradient[index];
				var gradMag = hypot(xGrad, yGrad);

				//perform non-maximal supression
				var nMag = hypot(xGradient[indexN], yGradient[indexN]);
				var sMag = hypot(xGradient[indexS], yGradient[indexS]);
				var wMag = hypot(xGradient[indexW], yGradient[indexW]);
				var eMag = hypot(xGradient[indexE], yGradient[indexE]);
				var neMag = hypot(xGradient[indexNE], yGradient[indexNE]);
				var seMag = hypot(xGradient[indexSE], yGradient[indexSE]);
				var swMag = hypot(xGradient[indexSW], yGradient[indexSW]);
				var nwMag = hypot(xGradient[indexNW], yGradient[indexNW]);
				var tmp=0.0;
				/*
				 * An explanation of what's happening here, for those who want
				 * to understand the source: This performs the "non-maximal
				 * supression" phase of the Canny edge detection in which we
				 * need to compare the gradient magnitude to that in the
				 * direction of the gradient; only if the value is a local
				 * maximum do we consider the point as an edge candidate.
				 * 
				 * We need to break the comparison into a number of different
				 * cases depending on the gradient direction so that the
				 * appropriate values can be used. To avoid computing the
				 * gradient direction, we use two simple comparisons: first we
				 * check that the partial derivatives have the same sign (1)
				 * and then we check which is larger (2). As a consequence, we
				 * have reduced the problem to one of four identical cases that
				 * each test the central gradient magnitude against the values at
				 * two points with 'identical support'; what this means is that
				 * the geometry required to accurately interpolate the magnitude
				 * of gradient function at those points has an identical
				 * geometry (upto right-angled-rotation/reflection).
				 * 
				 * When comparing the central gradient to the two interpolated
				 * values, we avoid performing any divisions by multiplying both
				 * sides of each inequality by the greater of the two partial
				 * derivatives. The common comparand is stored in a temporary
				 * variable (3) and reused in the mirror case (4).
				 * 
				 */
				if (xGrad * yGrad <= 0 /*(1)*/
					? Math.abs(xGrad) >= Math.abs(yGrad) /*(2)*/
						? (tmp = Math.abs(xGrad * gradMag)) >= Math.abs(yGrad * neMag - (xGrad + yGrad) * eMag) /*(3)*/
							&& tmp > Math.abs(yGrad * swMag - (xGrad + yGrad) * wMag) /*(4)*/
						: (tmp = Math.abs(yGrad * gradMag)) >= Math.abs(xGrad * neMag - (yGrad + xGrad) * nMag) /*(3)*/
							&& tmp > Math.abs(xGrad * swMag - (yGrad + xGrad) * sMag) /*(4)*/
					: Math.abs(xGrad) >= Math.abs(yGrad) /*(2)*/
						? (tmp = Math.abs(xGrad * gradMag)) >= Math.abs(yGrad * seMag + (xGrad - yGrad) * eMag) /*(3)*/
							&& tmp > Math.abs(yGrad * nwMag + (xGrad - yGrad) * wMag) /*(4)*/
						: (tmp = Math.abs(yGrad * gradMag)) >= Math.abs(xGrad * seMag + (yGrad - xGrad) * sMag) /*(3)*/
							&& tmp > Math.abs(xGrad * nwMag + (yGrad - xGrad) * nMag) /*(4)*/
					) {
					magnitude[index] = gradMag >= MAGNITUDE_LIMIT ? MAGNITUDE_MAX : (MAGNITUDE_SCALE * gradMag);
					//NOTE: The orientation of the edge is not employed by this
					//implementation. It is a simple matter to compute it at
					//this point as: Math.atan2(yGrad, xGrad);
				} else {
					magnitude[index] = 0;
				}
			}
		}
	}
	
	function gaussian(x,sigma) {
		return Math.exp(-(x * x) / (2 * sigma * sigma));
	}
	
	function hypot(x, y) {
		return Math.hypot(x, y);
	}
	
	function performHysteresis(low,  high) {
		//NOTE: this implementation reuses the data array to store both
		//luminance data from the image, and edge intensity from the processing.
		//This is done for memory efficiency, other implementations may wish
		//to separate these functions.
		
		var offset = 0;
		for (var y = 0; y < height; y++) {
			for (var x = 0; x < width; x++) {
				if (magnitude[offset] >= high) {
					follow(x, y, offset, low);
				}
				else{
					data[offset] = 0;
				}
				offset++;
			}
		}
 	}
	
	function follow(x1,  y1,  i1,  threshold) {
		 x0 = x1 == 0 ? x1 : x1 - 1;
		 x2 = x1 == width - 1 ? x1 : x1 + 1;
		 y0 = y1 == 0 ? y1 : y1 - 1;
		 y2 = y1 == height -1 ? y1 : y1 + 1;
		
		data[i1] = magnitude[i1];
		for (var x = x0; x <= x2; x++) {
			for (var y = y0; y <= y2; y++) {
				var i2 = x + y * width;
				if ((y != y1 || x != x1)
					&& data[i2] == 0 
					&& magnitude[i2] >= threshold) {
					follow(x, y, i2, threshold);
					return;
				}
			}
		}
	}
	
	function thresholdEdges() {
		for (var i = 0; i < picsize; i++) {
			data[i] = data[i] > 0 ? -1 : 0xff000000;
		}
	}
	
	
	
	
	
	
	
});
