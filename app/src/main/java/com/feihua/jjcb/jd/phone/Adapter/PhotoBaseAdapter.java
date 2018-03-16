package com.feihua.jjcb.jd.phone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.feihua.jjcb.jd.phone.R;
import com.feihua.jjcb.jd.phone.bean.PhotoInfo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

public class PhotoBaseAdapter extends BaseAdapter
{

    private Context mContext;
    private LayoutInflater infalter;
    private ArrayList<PhotoInfo> data = new ArrayList<PhotoInfo>();
    ImageLoader imageLoader;

    public PhotoBaseAdapter(Context c, ImageLoader imageLoader)
    {
        infalter = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = c;
        this.imageLoader = imageLoader;
        // clearCache();
    }

    @Override
    public int getCount()
    {
        return data == null ? 5 : data.size();
    }

    @Override
    public PhotoInfo getItem(int position)
    {
        return data.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public ArrayList<PhotoInfo> getSelected()
    {
        ArrayList<PhotoInfo> dataT = new ArrayList<PhotoInfo>();

        for (int i = 0; i < data.size(); i++)
        {
            if (data.get(i).isSeleted)
            {
                dataT.add(data.get(i));
            }
        }

        return dataT;
    }

    public void addAll(ArrayList<PhotoInfo> files)
    {

        try
        {
            this.data.clear();
            this.data.addAll(files);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    public void changeSelection(View v, int position)
    {

        if (data.get(position).isSeleted)
        {
            data.get(position).isSeleted = false;
        }
        else
        {
            data.get(position).isSeleted = true;
        }

        ((ViewHolder) v.getTag()).imgQueueMultiSelected.setSelected(data.get(position).isSeleted);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        final ViewHolder holder;
        if (convertView == null)
        {

            convertView = infalter.inflate(R.layout.gallery_item, null);
            holder = new ViewHolder();
            holder.imgQueue = (ImageView) convertView.findViewById(R.id.imgQueue);

            holder.imgQueueMultiSelected = (ImageView) convertView.findViewById(R.id.imgQueueMultiSelected);

            convertView.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imgQueue.setTag(position);

        try
        {
            imageLoader.displayImage("file://" + data.get(position).sdcardPath, holder.imgQueue, new SimpleImageLoadingListener()
            {
                @Override
                public void onLoadingStarted(String imageUri, View view)
                {
                    holder.imgQueue.setImageResource(R.mipmap.no_media);
                    super.onLoadingStarted(imageUri, view);
                }
            });

            holder.imgQueueMultiSelected.setSelected(data.get(position).isSeleted);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return convertView;
    }

    public class ViewHolder
    {
        ImageView imgQueue;
        ImageView imgQueueMultiSelected;
    }
}