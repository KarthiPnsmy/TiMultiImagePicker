package titutorial.gallerypicker;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class GalleryAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater infalter;
	private ArrayList<CustomGallery> data = new ArrayList<CustomGallery>();
	ImageLoader imageLoader;
	String errorMessageText;

	private boolean isActionMultiplePick;
	DisplayImageOptions options;

	public GalleryAdapter(Context c, ImageLoader imageLoader, String messageText) {
		infalter = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = c;
		this.imageLoader = imageLoader;
		this.errorMessageText = messageText;
        clearCache();
        
        options = new DisplayImageOptions.Builder()
        .showImageOnFail(Utility.resId_no_media)
        .bitmapConfig(Bitmap.Config.RGB_565)
        .imageScaleType(ImageScaleType.IN_SAMPLE_INT) 
        .build();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public CustomGallery getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setMultiplePick(boolean isMultiplePick) {
		this.isActionMultiplePick = isMultiplePick;
	}

	public void selectAll(boolean selection) {
		for (int i = 0; i < data.size(); i++) {
			data.get(i).isSeleted = selection;

		}
		notifyDataSetChanged();
	}

	public boolean isAllSelected() {
		boolean isAllSelected = true;

		for (int i = 0; i < data.size(); i++) {
			if (!data.get(i).isSeleted) {
				isAllSelected = false;
				break;
			}
		}

		return isAllSelected;
	}

	public boolean isAnySelected() {
		boolean isAnySelected = false;

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).isSeleted) {
				isAnySelected = true;
				break;
			}
		}

		return isAnySelected;
	}

	public ArrayList<CustomGallery> getSelected() {
		ArrayList<CustomGallery> dataT = new ArrayList<CustomGallery>();

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).isSeleted) {
				dataT.add(data.get(i));
			}
		}

		return dataT;
	}

	public void addAll(ArrayList<CustomGallery> files) {

		try {
			this.data.clear();
			this.data.addAll(files);

		} catch (Exception e) {
			e.printStackTrace();
		}

		notifyDataSetChanged();
	}

	public void changeSelection(View v, int position, int limit) {
		int selectedItemsLength = getSelected().size();
			if (data.get(position).isSeleted) {
				data.get(position).isSeleted = false;
				//((ViewHolder) v.getTag()).imgQueueMultiSelected.setVisibility(View.GONE);
			} else {
				if(limit > -1){
					if(selectedItemsLength < limit ){
						data.get(position).isSeleted = true;
						//((ViewHolder) v.getTag()).imgQueueMultiSelected.setVisibility(View.VISIBLE);
					} else {
						Toast.makeText(mContext, errorMessageText, Toast.LENGTH_SHORT).show();
					}	
				} else {
					data.get(position).isSeleted = true;
					//((ViewHolder) v.getTag()).imgQueueMultiSelected.setVisibility(View.VISIBLE);
				}
			}

	        ((ViewHolder) v.getTag()).imgQueueMultiSelected.setSelected(data.get(position).isSeleted);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {

			//convertView = infalter.inflate(R.layout.gallery_item, null);
			convertView = infalter.inflate(Utility.resId_gallery_itemLayout, null);
			holder = new ViewHolder();
			//holder.imgQueue = (ImageView) convertView.findViewById(R.id.imgQueue);
			holder.imgQueue = (ImageView) convertView.findViewById(Utility.resId_imgQueue);

			//holder.imgQueueMultiSelected = (ImageView) convertView.findViewById(R.id.imgQueueMultiSelected);
			holder.imgQueueMultiSelected = (ImageView) convertView.findViewById(Utility.resId_imgQueueMultiSelected);

			//Right now we are using this module only for multiple selection purpose.
			/*
			if (isActionMultiplePick) {
				holder.imgQueueMultiSelected.setVisibility(View.VISIBLE);
			} else {
				holder.imgQueueMultiSelected.setVisibility(View.GONE);
			}
			*/
			holder.imgQueueMultiSelected.setVisibility(View.VISIBLE);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
        holder.imgQueue.setTag(position);

		try {
            //holder.imgQueue.setImageResource(R.drawable.no_media);
            holder.imgQueue.setImageResource(Utility.resId_no_media);
			imageLoader.displayImage("file://" + data.get(position).sdcardPath, holder.imgQueue, options);

			if (isActionMultiplePick) {
				holder.imgQueueMultiSelected.setSelected(data.get(position).isSeleted);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return convertView;
	}

	public class ViewHolder {
		ImageView imgQueue;
		ImageView imgQueueMultiSelected;
	}

	public void clearCache() {
		imageLoader.clearDiscCache();
		imageLoader.clearMemoryCache();
	}

	public void clear() {
		data.clear();
		notifyDataSetChanged();
	}
}
