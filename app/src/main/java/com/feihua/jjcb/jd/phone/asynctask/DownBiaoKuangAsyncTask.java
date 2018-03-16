package com.feihua.jjcb.jd.phone.asynctask;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.feihua.jjcb.jd.phone.R;
import com.feihua.jjcb.jd.phone.bean.BiaoKuangBean;
import com.feihua.jjcb.jd.phone.bean.Datadic;
import com.feihua.jjcb.jd.phone.db.BiaoKuangDatabase;
import com.feihua.jjcb.jd.phone.db.DatabaseHelper;
import com.feihua.jjcb.jd.phone.utils.L;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by wcj on 2016-04-18.
 */
public abstract class DownBiaoKuangAsyncTask extends AsyncTask<String, Void, String>
{
    private Context ctx;
    private BiaoKuangDatabase biaoKDatabase;

    public DownBiaoKuangAsyncTask(Context ctx)
    {
        this.ctx = ctx;
        biaoKDatabase = new BiaoKuangDatabase(ctx);
    }

    @Override
    protected String doInBackground(String... params)
    {
        String content = null;
        if (params[0] != null)
        {
            content = saveDatas(params[0]);
        }

        return content;
    }

    //保存已下载成功的数据到数据库
    private String saveDatas(String datas)
    {
        BiaoKuangBean bean = new Gson().fromJson(datas, BiaoKuangBean.class);
        L.w("DownBiaoKuangAsyncTask", "开始表况存储");
        List<Datadic> bkList = bean.BK;
        //        List<BiaoCeTables> volume = downTables.volume;
        L.w("DownBiaoKuangAsyncTask", "bkList.size() = " + bkList.size());
        //表册
        if (bkList == null || bkList.size() == 0)
        {
            return ctx.getResources().getString(R.string.down_biaokuang_not);
        }

        if (biaoKDatabase.queryCount(DatabaseHelper.BIAOK_TABLE_NAME) > 0)
        {
            biaoKDatabase.delete(DatabaseHelper.BIAOK_TABLE_NAME);
        }

        for (Datadic bk : bkList)
        {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.WATER_BK, bk.getDatadic_name());
            values.put(DatabaseHelper.IS_CBWAY, bk.getDatadic_value());
            biaoKDatabase.insert(DatabaseHelper.BIAOK_TABLE_NAME, values);
        }
        L.w("DownBiaoKuangAsyncTask", "表况存储完成");
        return ctx.getResources().getString(R.string.down_biaokuang_success);

    }
}
