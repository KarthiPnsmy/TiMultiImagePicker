/**
 * Copyright 2012 Facebook
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * MODIFICATIONS
 * 
 * Facebook Module
 * Copyright (c) 2009-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

/**
 * NOTES
 * Modifications made for Titanium:
 * - Add setLogEnabled() to enable/disable log messages and getLogEnabled() to get the value of ENABLE_LOG.
 * - Add loadResourceIds() to fetch resources ids using Resources.getIdentifier, since
 	we merge resources into Titanium project and don't have access to R.
 * 
 * Original file this is based on:
 * https://github.com/facebook/facebook-android-sdk/blob/4e2e6b90fbc964ca51a81e83e802bb4a62711a78/facebook/src/com/facebook/internal/Utility.java
 */

package titutorial.gallerypicker;

import android.content.Context;
import android.content.res.Resources;

public final class Utility {
    // *************** APPCELERATOR TITANIUM CUSTOMIZATION ***************************
    // Resource IDs used in titutorial.gallerypicker package. Fetch the resource id using Resources.getIdentifier, since
    // we merge resources into Titanium project and don't have access to R here.
    public static int resId_galleryLayout = -1;
    public static int resId_gridGallery = -1;
    public static int resId_bottomContainer = -1;
    public static int resId_imgNoMedia = -1;
    public static int resId_btnGalleryOk = -1;
    public static int resId_btnGalleryCancel = -1;
    public static int resId_tvTitleText = -1;
    public static int resId_gallery_itemLayout = -1;
    public static int resId_imgQueue = -1;
    public static int resId_imgQueueMultiSelected = -1;
    public static int resId_no_media = -1;
    public static int resId_mainLayout = -1;
    //public static int resId_gridGallery = -1;
    public static int resId_viewSwitcher = -1;
    public static int resId_imgSinglePick = -1;
    public static int resId_btnGalleryPick = -1;
    public static int resId_btnGalleryPickMul = -1;

	public static void loadResourceIds(Context context)
	{
		String packageName = context.getPackageName();
		Resources resources = context.getResources();
		
	    resId_galleryLayout = resources.getIdentifier("gallery", "layout", packageName);
	    resId_gridGallery = resources.getIdentifier("gridGallery", "id", packageName);
	    resId_bottomContainer = resources.getIdentifier("llBottomContainer", "id", packageName);
	    resId_imgNoMedia = resources.getIdentifier("imgNoMedia", "id", packageName);
	    resId_btnGalleryOk = resources.getIdentifier("btnGalleryOk", "id", packageName);
	    resId_btnGalleryCancel = resources.getIdentifier("btnGalleryCancel", "id", packageName);
	    resId_tvTitleText = resources.getIdentifier("tvTitleText", "id", packageName);
	    resId_gallery_itemLayout = resources.getIdentifier("gallery_item", "layout", packageName);
	    resId_imgQueue = resources.getIdentifier("imgQueue", "id", packageName);
	    resId_imgQueueMultiSelected = resources.getIdentifier("imgQueueMultiSelected", "id", packageName);
	    resId_no_media = resources.getIdentifier("no_media", "drawable", packageName);
	    resId_mainLayout = resources.getIdentifier("main", "layout", packageName);
	    //resId_gridGallery = resources.getIdentifier("gridGallery", "id", packageName);
	    resId_viewSwitcher = resources.getIdentifier("viewSwitcher", "id", packageName);
	    resId_imgSinglePick = resources.getIdentifier("imgSinglePick", "id", packageName);
	    resId_btnGalleryPick = resources.getIdentifier("btnGalleryPick", "id", packageName);
	    resId_btnGalleryPickMul = resources.getIdentifier("btnGalleryPickMul", "id", packageName);

	}
}
