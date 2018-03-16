package com.feihua.jjcb.jd.phone.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.feihua.jjcb.jd.phone.R;
import com.feihua.jjcb.jd.phone.bean.DetailsBean;

import java.util.List;


/**
 * Created by wcj on 2016-03-31.
 */
public class CBGLDetailsListAdapter extends CommonAdapter<DetailsBean>
{
    private List<DetailsBean> datas;
    public CBGLDetailsListAdapter(Context context, List<DetailsBean> datas, int layoutId)
    {
        super(context, datas, layoutId);
        this.datas = datas;
    }

    @Override
    public void convert(ViewHolder holder, DetailsBean datas, int position)
    {
        holder.setText(R.id.tvName, datas.getName());
        holder.setText(R.id.tvContent, datas.getContent());
        holder.setImageResource(R.id.imgIcon, datas.getIconId());
        holder.getView(R.id.layoutMore).setVisibility(View.GONE);
        if (datas.getName().equals("基本吨户:"))
        {
            holder.getView(R.id.cbCheck).setVisibility(View.VISIBLE);
            holder.getView(R.id.tvContent).setVisibility(View.GONE);
            CheckBox cbCheck = holder.getView(R.id.cbCheck);
            if (datas.getContent().equals("1"))
            {
                cbCheck.setChecked(true);
            }
            else
            {
                cbCheck.setChecked(false);
            }
        }
        else
        {
            holder.getView(R.id.cbCheck).setVisibility(View.GONE);
            holder.getView(R.id.tvContent).setVisibility(View.VISIBLE);
        }
        if (datas.getNameMore() != null)
        {
            holder.getView(R.id.layoutMore).setVisibility(View.VISIBLE);
            holder.setText(R.id.tvNameMore, datas.getNameMore());
            holder.setText(R.id.tvContentMore, datas.getContentMore());
            holder.setImageResource(R.id.imgIconMore, datas.getIconMoreId());
        }
    }

    public void notifyPhone(String phoneNum) {

        DetailsBean   detailsBean = datas.get(datas.size() - 1);
        String USERB_DH = detailsBean.getContentMore();
        datas.remove(detailsBean);
        datas.add(new DetailsBean(R.mipmap.icon_details_c4, "手机:", phoneNum, R.mipmap.icon_details_c3, "电话:", USERB_DH));
        notifyDataSetChanged();
    }
}
