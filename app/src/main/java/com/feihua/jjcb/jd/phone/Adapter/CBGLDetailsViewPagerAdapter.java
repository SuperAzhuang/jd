package com.feihua.jjcb.jd.phone.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.feihua.jjcb.jd.phone.fragment.CBGLDetailsFragment;
import com.feihua.jjcb.jd.phone.navi.Node;
import com.feihua.jjcb.jd.phone.navi.OnNaviListener;
import com.feihua.jjcb.jd.phone.ui.CBGLDetailsActivity;

import java.util.ArrayList;

/**
 * Created by wcj on 2016-03-31.
 */
public class CBGLDetailsViewPagerAdapter extends FragmentPagerAdapter
{
    private CBGLDetailsActivity act;
    private ArrayList<String> list;
    private String volumeNo;

    public CBGLDetailsViewPagerAdapter(FragmentManager fm, ArrayList<String> list, String volumeNo, CBGLDetailsActivity act)
    {
        super(fm);
        this.list = list;
        this.volumeNo = volumeNo;
        this.act = act;
    }

    @Override
    public Fragment getItem(int position)
    {
        CBGLDetailsFragment fragment = new CBGLDetailsFragment();
        if (list != null)
        {
            String userbKh = list.get(position);
            fragment.setUserbKh(volumeNo, userbKh, String.valueOf(list.size()),position);
            fragment.setOnNaviListener(new OnNaviListener()
            {
                @Override
                public void setNaviNode(Node sNode, Node eNode)
                {
                    act.toNaiv(sNode,eNode);
                }
            });
        }
        return fragment;
    }

    @Override
    public int getCount()
    {
        if (list == null && list.size() < 1)
        {
            return 0;
        }
        return list.size();
    }

}
