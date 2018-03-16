package com.feihua.jjcb.jd.phone.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
	
	private SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	
	public ViewHolder(Context context,ViewGroup parent,int position,int layoutId){
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,false);
		mConvertView.setTag(this);
	}
	
	public static ViewHolder get(Context context,View convertView,ViewGroup parent,int position,int layoutId){
		if (convertView == null) {
			return new ViewHolder(context, parent, position, layoutId);
		}else{
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}
	}
	
	public <T extends View> T getView(int viewId){
		View view = mViews.get(viewId);
		if(view == null){
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T)view;
	}

	/**
	 * 通过viewId获取控件
	 * @param viewId
	 * @return
	 */
	public View getConvertView() {
		return mConvertView;
	}
	
	public int getPosition() {
		return mPosition;
	}
	
	/**
	 * 设置TextView的值
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int viewId,String text){
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}
	
	public ViewHolder setImageResource(int viewId,int resId){
		ImageView view = getView(viewId);
		view.setImageResource(resId);
		return this;
	}
	
	public ViewHolder setImageBitmap(int viewId,Bitmap bitmap){
		ImageView view = getView(viewId);
		view.setImageBitmap(bitmap);
		return this;
	}
	
//	public ViewHolder setImageURL(int viewId,String Url){
//		ImageView view = getView(viewId);
//		
//		return this;
//	}

}
