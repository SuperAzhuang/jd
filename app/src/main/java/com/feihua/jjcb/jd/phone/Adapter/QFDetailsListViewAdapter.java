package com.feihua.jjcb.jd.phone.Adapter;

import android.content.Context;

import com.feihua.jjcb.jd.phone.R;
import com.feihua.jjcb.jd.phone.bean.FeeDetails;

import java.util.List;


/**
 * Created by wcj on 2016-04-18.
 */
public class QFDetailsListViewAdapter extends CommonAdapter<FeeDetails>
{
    private String userbKh;

    public QFDetailsListViewAdapter(Context context, List<FeeDetails> datas, int layoutId)
    {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, FeeDetails table, int position)
    {
        holder.setText(R.id.tvKH,table.getUSERB_KH());
        holder.setText(R.id.tvDate,table.getDEBTL_YEAR() + "." + table.getDEBTL_MON());
        holder.setText(R.id.tvSQDS,table.getWATERU_QAN());
        holder.setText(R.id.tvBQDS,table.getDEBTL_STOTAL());
    }

    public void setUserbKH(String userbKh){
        this.userbKh = userbKh;
    }

}