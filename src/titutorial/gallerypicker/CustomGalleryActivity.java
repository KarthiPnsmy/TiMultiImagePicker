package titutorial.gallerypicker;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.Collections;

public class CustomGalleryActivity extends Activity {

	GridView gridGallery;
	Handler handler;
	GalleryAdapter adapter;

	ImageView imgNoMedia;
	Button btnGalleryOk;
	Button btnGalleryCancel;
	TextView galleryTitle;

	String action;
	String okButtonText = null;
	String cancelButtonText = null;
	String titleText = null;
	String errorMessageText = null;
	Integer limit = 5;
    private ImageLoader imageLoader;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//setContentView(R.layout.gallery);
		setContentView(Utility.resId_galleryLayout);

		action = getIntent().getAction();
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			cancelButtonText = extras.getString("cancelButtonText");
			okButtonText = extras.getString("okButtonText");
			titleText = extras.getString("titleText");
			errorMessageText = extras.getString("errorMessageText");
			limit = extras.getInt("limit", 5);
			action = extras.getString("action");
		}

		if (action == null) {
			finish();
		}
        initImageLoader();
		init();
	}

    private void initImageLoader() {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(this)
		.threadPoolSize(3)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.memoryCacheSize(1500000) // 1.5 Mb
		.denyCacheImageMultipleSizesInMemory()
		.memoryCache(new WeakMemoryCache())
		.discCacheFileNameGenerator(new Md5FileNameGenerator());

        ImageLoaderConfiguration config = builder.build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

	private void init() {

		handler = new Handler();
		//gridGallery = (GridView) findViewById(R.id.gridGallery);
		gridGallery = (GridView) findViewById(Utility.resId_gridGallery);
		gridGallery.setFastScrollEnabled(true);
		adapter = new GalleryAdapter(getApplicationContext(), imageLoader, errorMessageText);

		if (action.equalsIgnoreCase(Action.ACTION_MULTIPLE_PICK)) {

			//findViewById(R.id.llBottomContainer).setVisibility(View.VISIBLE);
			findViewById(Utility.resId_bottomContainer).setVisibility(View.VISIBLE);
			gridGallery.setOnItemClickListener(mItemMulClickListener);
			adapter.setMultiplePick(true);

		} else if (action.equalsIgnoreCase(Action.ACTION_PICK)) {

			//findViewById(R.id.llBottomContainer).setVisibility(View.GONE);
			findViewById(Utility.resId_bottomContainer).setVisibility(View.GONE);
			gridGallery.setOnItemClickListener(mItemSingleClickListener);
			adapter.setMultiplePick(false);

		}

		gridGallery.setAdapter(adapter);
		//imgNoMedia = (ImageView) findViewById(R.id.imgNoMedia);
		imgNoMedia = (ImageView) findViewById(Utility.resId_imgNoMedia);
		
		//btnGalleryOk = (Button) findViewById(R.id.btnGalleryOk);
		btnGalleryOk = (Button) findViewById(Utility.resId_btnGalleryOk);
		//btnGalleryCancel = (Button) findViewById(R.id.btnGalleryCancel);
		btnGalleryCancel = (Button) findViewById(Utility.resId_btnGalleryCancel);
		//galleryTitle = (TextView) findViewById(R.id.tvTitleText);
		galleryTitle = (TextView) findViewById(Utility.resId_tvTitleText);
		
		if (titleText != null) {
			galleryTitle.setText(titleText);
		}
		
		if (okButtonText != null) {
			btnGalleryOk.setText(okButtonText);
		}
		
		if (cancelButtonText != null) {
			btnGalleryCancel.setText(cancelButtonText);
		}	
		
		btnGalleryCancel.setOnClickListener(mCancelClickListener);
		btnGalleryOk.setOnClickListener(mOkClickListener);

		new Thread() {

			@Override
			public void run() {
				Looper.prepare();
				handler.post(new Runnable() {

					@Override
					public void run() {
						adapter.addAll(getGalleryPhotos());
						checkImageStatus();
					}
				});
				Looper.loop();
			};

		}.start();

	}

	private void checkImageStatus() {
		if (adapter.isEmpty()) {
			imgNoMedia.setVisibility(View.VISIBLE);
		} else {
			imgNoMedia.setVisibility(View.GONE);
		}
	}

	View.OnClickListener mOkClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			ArrayList<CustomGallery> selected = adapter.getSelected();

			String[] allPath = new String[selected.size()];
			for (int i = 0; i < allPath.length; i++) {
				allPath[i] = selected.get(i).sdcardPath;
			}
			
			//clear adapter
			adapter.clear();
			adapter.clearCache();
			Intent data = new Intent().putExtra("all_path", allPath);
			try{
				setResult(RESULT_OK, data);
			} catch (Exception e){
				e.printStackTrace();
			}
			finish();

		}
	};
	
	View.OnClickListener mCancelClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			//clear adapter
			adapter.clear();
			adapter.clearCache();
			setResult(RESULT_CANCELED);
			finish();
		}
	};
	
	AdapterView.OnItemClickListener mItemMulClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> l, View v, int position, long id) {
			adapter.changeSelection(v, position, limit);

		}
	};

	AdapterView.OnItemClickListener mItemSingleClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> l, View v, int position, long id) {
			CustomGallery item = adapter.getItem(position);
			Intent data = new Intent().putExtra("single_path", item.sdcardPath);
			setResult(RESULT_OK, data);
			finish();
		}
	};

	private ArrayList<CustomGallery> getGalleryPhotos() {
		ArrayList<CustomGallery> galleryList = new ArrayList<CustomGallery>();

		try {
			final String[] columns = { MediaStore.Images.Media.DATA,
					MediaStore.Images.Media._ID, MediaStore.Images.Thumbnails.DATA };
			final String orderBy = MediaStore.Images.Media._ID;

			@SuppressWarnings("deprecation")
			Cursor imagecursor = managedQuery(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
					null, null, orderBy);
			if (imagecursor != null && imagecursor.getCount() > 0) {

				while (imagecursor.moveToNext()) {
					CustomGallery item = new CustomGallery();

					int dataColumnIndex = imagecursor
							.getColumnIndex(MediaStore.Images.Media.DATA);
					int thumbColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
					item.sdcardPath = imagecursor.getString(dataColumnIndex);
					String thumbPath = imagecursor.getString(thumbColumnIndex);
					galleryList.add(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

        // show newest photo at beginning of the list
		Collections.reverse(galleryList);
        return galleryList;
	}
	
	@Override
	public void onBackPressed() {
		adapter.clear();
		adapter.clearCache();
		setResult(RESULT_CANCELED);
		super.onBackPressed();
	}

}
