# GalleryPicker Module

## Description

Titanium gallery picker module for Android to select multiple images from Gallery. This module developed from existing 
Android open source library MultipleImagePick. Also this module uses Universal image loader library for asynchronous loading and caching. 

## Features
* Select multiple images from Gallery
* Option to set maximum image selection limit
* User defined button title and error message
* Method for scale down the bitmap
* Success and error callback methods

## Accessing the GalleryPicker Module

To access this module from JavaScript, you would do the following:
```javascript
var gallerypicker = require('titutorial.gallerypicker');
```
The gallerypicker variable is a reference to the Module object.	

## Methods

### openGallery()

Method to open custom gallery with multiple image select option.

**Parameters**

| Property    	| Description | Default |
| -------------	| ----------- | ------- |
| cancelButtonTitle  | Cancel button title text| Cancel |
| doneButtonTitle  | Done button title text | Done |
| title | Custom gallery window title| Gallery |
| errorMessage 	| Message used to show, if maximum image selection reached | Max limit reached |
| limit	| maximum image selection limit | 5 |
| success 	| callback function to handle success response | - |
| error 	| callback function to handle error response | - |

### decodeBitmapResource()

Method to scale down the bitmap 

The system Gallery application displays photos taken using your Android devices's camera which are typically much higher 
resolution than the screen density of your device

Given that you are working with limited memory, ideally you only want to load a lower resolution version in memory. 
The lower resolution version should match the size of the UI component that displays it. 
An image with a higher resolution does not provide any visible benefit, but still takes up precious 
memory and incurs additional performance overhead due to additional on the fly scaling.

This method used to decode large bitmaps without exceeding the per application memory limit by loading a smaller subsampled version in memory

**Parameters**

| Property    	| Description |
| -------------	| ----------- |
| path  | Input image file path|
| width  | Required thumbnail image width|
| height | Required thumbnail image height|

## Usage
```javascript
gallerypicker.openGallery({
	cancelButtonTitle : "Cancel",
	doneButtonTitle : "Okay",
	title : "Custom Gallery",
	errorMessage: "Limit reached",
	limit : 10,
	success : function(e) {
		Ti.API.info("response is => " + JSON.stringify(e));

		var imgArray = e.filePath.split(",");

		for(var i=0; i<imgArray.length; i++){
			if(imgArray[i]){
				var imgView = Ti.UI.createImageView({
					left:'10dp',
					top:'10dp',
					image: gallerypicker.decodeBitmapResource(imgArray[i], 100, 100)
				});
				imageHolder.add(imgView);
			}
		}
	},
	error : function(e) {
		alert("error " + JSON.stringify(e));
	}
});

```
refer example/app.js for more info

## Author

Karthi Ponnusamy - karthi.nkl@gmail.com

## License

Copyright (c) 2013 titaniumtutorial.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
