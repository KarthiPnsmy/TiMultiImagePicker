# TiMultiImagePicker Module

## Description

Titanium Android module for select multiple images from Gallery. This module developed from existing 
Android open source library **[MultipleImagePick](https://github.com/luminousman/MultipleImagePick)**. Also this module uses **[Universal image loader](https://github.com/nostra13/Android-Universal-Image-Loader)** library for asynchronous loading and caching. 

![TiMultiImagePicker Screenshot](https://raw.github.com/railskarthi/TiMultiImagePicker/master/screens/screenshot.png "TiMultiImagePicker Screen")


## Features
* Select multiple images from Gallery
* Option to set maximum image selection limit
* User defined button title and error message
* Method for scale down the bitmap
* Success and error callback methods

## Accessing the TiMultiImagePicker Module

To access this module from JavaScript, you would do the following:
<pre>
var gallerypicker = require('titutorial.gallerypicker');
</pre>
The gallerypicker variable is a reference to the Module object.	

## Methods

### openGallery()

Method to open custom gallery with multiple image select option.

**Parameters**

<table border="1">
<tr>
<th>Property</th>
<th>Description</th>
<th>Default</th>
<th>Is Required?</th>
</tr>
<tr>
<td>cancelButtonTitle</td>
<td>Cancel button title text</td>
<td>Cancel</td>
<td>No</td>
</tr>
<tr>
<td>doneButtonTitle</td>
<td>Done button title text</td>
<td>Done</td>
<td>No</td>
</tr>
<tr>
<td>title </td>
<td>Custom gallery window title</td>
<td>Gallery</td>
<td>No</td>
</tr>
<tr>
<td>errorMessage</td>
<td>Message used to show, if maximum image selection limit reached</td>
<td>Max limit reached</td>
<td>No</td>
</tr>
<tr>
<td>limit</td>
<td>maximum image selection limit</td>
<td>5</td>
<td>No</td>
</tr>
<tr>
<td>success</td>
<td>callback function to handle success response</td>
<td>-</td>
<td>Yes</td>
</tr>
<tr>
<td>error</td>
<td>callback function to handle error response</td>
<td>-</td>
<td>Yes</td>
</tr>
<tr>
<td>cancel</td>
<td>callback function to handle cancel response</td>
<td>-</td>
<td>No</td>
</tr>
</table> 

### decodeBitmapResource()

Method to scale down the bitmap 

The system Gallery application displays photos taken using your Android devices's camera which are typically much higher 
resolution than the screen density of your device

Given that you are working with limited memory, ideally you only want to load a lower resolution version in memory. 
The lower resolution version should match the size of the UI component that displays it. 
An image with a higher resolution does not provide any visible benefit, but still takes up precious 
memory and incurs additional performance overhead due to additional on the fly scaling.

**This method used to decode large bitmaps without exceeding the per application memory limit by loading a smaller subsampled version in memory**

Reference: [Android guild lines](http://developer.android.com/training/displaying-bitmaps/load-bitmap.html)

**Parameters**

<table border="1">
<tr>
<th>Property</th>
<th>Description</th>
<th>Is Required?</th>
</tr>
<tr>
<td>path</td>
<td>Input image file path</td>
<td>Yes</td>
</tr>
<tr>
<td>width</td>
<td>Required thumbnail image width</td>
<td>Yes</td>
</tr>
<tr>
<td>height</td>
<td>Required thumbnail image height</td>
<td>Yes</td>
</tr>
</table>

## Usage

<code>
gallerypicker.openGallery({
    cancelButtonTitle: "Cancel",
    doneButtonTitle: "Okay",
    title: "Custom Gallery",
    errorMessage: "Limit reached",
    limit: 10,
    success: function (e) {
        Ti.API.info("response is => " + JSON.stringify(e));
        var imgArray = e.filePath.split(",");
        for (var i = 0; i < imgArray.length; i++) {
            if (imgArray[i]) {
                var imgView = Ti.UI.createImageView({
                    left: '10dp',
                    top: '10dp',
                    image: gallerypicker.decodeBitmapResource(imgArray[i], 100, 100)
                });
                imageHolder.add(imgView);
            }
        }
    },
    error: function (e) {
        alert("error " + JSON.stringify(e));
    },
    cancel : function(e) {
        alert("cancel " + JSON.stringify(e));
    }
});
</code>

refer example/app.js for more info

## Using Custom Resources

If you want to use custom resource in your App you can override default resource. For that you have to place your custom resource inside **app_root/platform/android/res** or **app_root/modules/android/titutorial.gallerypicker/0.1/platform/android/res** in the same name.

For example, if you want to override default image in Gallery item you have to override your custom image with name of **no_media.png**

## Author

Karthi Ponnusamy - karthi.nkl@gmail.com

## License

Copyright (c) 2013 titaniumtutorial.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
