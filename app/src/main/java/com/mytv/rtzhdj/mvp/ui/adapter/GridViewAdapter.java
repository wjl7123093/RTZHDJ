package com.mytv.rtzhdj.mvp.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.Settings.Global;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;

public class GridViewAdapter extends BaseAdapter {
	
	private boolean isVideo = false;
	private RTZHDJApplication mApplication;
	private int typeFrom=0;//0为普通创建，1为编辑退稿
    public static interface IPhotoType {  
        int IPHOTO_UPLOAD = 0;		// 上传  
        int IPHOTO_DISPLAY = 1;		// 展示  
    }  
    private static final int ITEMCOUNT = 2;// Type 的总数  

	private Context mContext;
	private List<Bitmap> list = new ArrayList<Bitmap>();
	private List<String> mList = new ArrayList<String>();
	public GridViewAdapter() {
		super();
	}
	
	@Override
	public int getItemViewType(int position) {		
		int type = 0;
		if (position == list.size())
			type = IPhotoType.IPHOTO_UPLOAD;
		else
			type = IPhotoType.IPHOTO_DISPLAY;
		return type;
	}
	
	@Override
	public int getViewTypeCount() {
		return ITEMCOUNT;
	}
	
/**
 * 获取列表数据
 * @param list
 */
	public void setListString(List<String> list, boolean isVideo){
		this.mList = list;
		this.isVideo = isVideo;
		this.notifyDataSetChanged();
		Log.e(" 3333 ", this.list.size()+"");
	}
	
/**
 * 获取列表数据
 * @param list
 */
	public void setList(List<Bitmap> list, boolean isVideo){
		this.list = list;
		this.isVideo = isVideo;
		this.notifyDataSetChanged();
		Log.e(" 3333 ", this.list.size()+"");
	}
	public GridViewAdapter(Context mContext,List<Bitmap> list,List<String> list_pic,int type) {
		super();
		this.mContext = mContext;
		this.list = list;
		this.mList = list_pic;
		this.mApplication = (RTZHDJApplication) ((Activity) mContext).getApplication();
		this.typeFrom = type;
		Log.e(" 2222 ", list.size()+"");
	}

	@Override
	public int getCount() {
		Log.e("  ", list.size()+"");
		if(typeFrom==0){
			if(list==null){
				return 1;
			}else if(list.size()==3){
				return 3;
			}else{
				return list.size()+1;
			}
		}else{
			return mList.size();
		}
		
	}

	@Override
	public Object getItem(int position) {
		if(typeFrom==0){
			if (list != null
					&& list.size() == 3)
			{
				return list.get(position);
			}

			else if (list == null || position - 1 < 0
					|| position > list.size())
			{
				return null;
			}
			else
			{
				return list.get(position - 1);
			}
		}else{
			return mList.get(position);
		}
		
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			if(typeFrom==0){
				if (getItemViewType(position) == IPhotoType.IPHOTO_UPLOAD) {
					convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_dialog_uploadphoto1,
							parent, false);
					holder.tvPhoto = (TextView) convertView.findViewById(R.id.tvPhoto);
				} else {
					convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_dialog_uploadphoto2,
							parent, false);
					holder.image = (ImageView) convertView
							.findViewById(R.id.ivItemPhoto);
//					holder.iconPlay = (IconView) convertView.findViewById(R.id.iconPlay);

				}
			}
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(typeFrom==0){
			if (isShowAddItem(position)) {
				if (3 == position)
					convertView.setVisibility(View.GONE);
			} else {
//				holder.iconPlay.setVisibility(View.GONE);
				holder.image.setImageBitmap(list.get(position));
			}
		}
		

		return convertView;
	}
	/**
	 * 判断当前下标是否是最大值
	 * @param position  当前下标
	 * @return
	 */
	private boolean isShowAddItem(int position)
	{
		int size = list == null ? 0 : list.size();
		return position == size;
	}
	
	class ViewHolder{
		public TextView tvPhoto;
		ImageView image;
//		IconView iconPlay;
		ImageView pic;
	}

}
