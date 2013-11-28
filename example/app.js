var win = Ti.UI.createWindow({
	backgroundColor : 'white',
	navBarHidden:true,
	layout : "vertical"
});

var gallerypicker = require('titutorial.gallerypicker');
Ti.API.info("module is => " + gallerypicker);

var open = Ti.UI.createButton({
	title : 'open',
	height : '50dp',
	width : '150dp',
	top : '40dp'
});
win.add(open);

var scrollview = Ti.UI.createScrollView();
win.add(scrollview);

var imageHolder = Ti.UI.createView({
	top : '10dp',
	backgroundColor : '#ccc',
	layout : "horizontal"
});
scrollview.add(imageHolder);

open.addEventListener('click', function() {
	gallerypicker.openGallery({
		cancelButtonTitle : "Cancel",
		doneButtonTitle : "Okay",
		title : "Custom Gallery",
		errorMessage : "Limit reached",
		limit : 10,
		success : function(e) {
			Ti.API.info("@@## response is => " + JSON.stringify(e));

			var imgArray = e.filePath.split(",");
			Ti.API.info("@@## imgArray.length = " + imgArray.length);

			for (var i = 0; i < imgArray.length; i++) {
				if (imgArray[i]) {
					var imgView = Ti.UI.createImageView({
						left : '10dp',
						top : '10dp',
						image : gallerypicker.decodeBitmapResource(imgArray[i], 100, 100)
					});
					imageHolder.add(imgView);
				}
			}
		},
		error : function(e) {
			alert("error => " + JSON.stringify(e));
			Ti.API.info("@@## error is => " + JSON.stringify(e));
		},
		cancel : function(e) {
			alert("cancel => " + JSON.stringify(e));
			Ti.API.info("@@## cancel is => " + JSON.stringify(e));
		}
	});
});

win.open();
