$(document).ready(function() {
	var imageHeight = 447;
	var imageWidth = 293;

	var canvas = document.getElementById("myCanvas");
	var ctx = canvas.getContext("2d");
	
	var image = new Image();
	image.src = "test.png";
	
	
	$(image).load(function() {
		ctx.drawImage(image, 0, 0,imageWidth,imageHeight);
		
		var imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
		var pixels = imageData.data;
		var numPixels = imageData.width * imageData.height;
		
		for (var i = 0; i < numPixels; i++) {
			pixels[i*4] = 255-pixels[i*4]; // Red
			pixels[i*4+1] = 255-pixels[i*4+1]; // Green
			pixels[i*4+2] = 255-pixels[i*4+2]; // Blue
		};
		
		ctx.clearRect(0, 0, canvas.width, canvas.height);
		ctx.putImageData(imageData, 0, 0);
	
	
	});
	
	
	
});
