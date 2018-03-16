package com.feihua.jjcb.jd.phone.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;


import com.feihua.jjcb.jd.phone.callback.BiaoCeTables;
import com.feihua.jjcb.jd.phone.utils.L;

import java.util.ArrayList;

public class BiaoCeDatabase
{

    Context context;
    DatabaseHelper dbhelper;
    public SQLiteDatabase sqlitedatabase;

    public BiaoCeDatabase(Context context)
    {
        super();
        this.context = context;
    }

    // 打开数据库连接
    public void opendb(Context context)
    {
        dbhelper = new DatabaseHelper(context);
        sqlitedatabase = dbhelper.getWritableDatabase();
    }

    // 关闭数据库连接
    public void closedb(Context context)
    {
        if (sqlitedatabase.isOpen())
        {
            sqlitedatabase.close();
        }
    }

    // 插入表数据
    public void insert(String table_name, ContentValues values)
    {
        opendb(context);
        sqlitedatabase.insert(table_name, null, values);
        closedb(context);
    }

    // 更新数据
    public int updatatable(ContentValues values, String volumeNo)
    {
        opendb(context);
        return sqlitedatabase.update(DatabaseHelper.BIAOCE_TABLE_NAME, values, DatabaseHelper.BIAOCE_VOLUME_NO + "=?", new String[]{volumeNo});
    }

    // 删除表数据
    public void delete(String table_name, String whereClause, String[] whereArgs)
    {
        opendb(context);
        try
        {
            for (int i = 0; i < whereArgs.length; i++)
            {
                sqlitedatabase.delete(table_name, whereClause, new String[]{whereArgs[i]});
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closedb(context);
        }
    }

    // 删除表
    public void delete(String table_name)
    {
        opendb(context);
        try
        {

            sqlitedatabase.delete(table_name, null, null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closedb(context);
        }
    }

    // 查询数据表有多少条数据
    public int queryCount(String table_name)
    {
        opendb(context);
        Cursor cursor = sqlitedatabase.query(table_name, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // 查找数据
    //    public JSONArray DeptArray(String table_name)
    //    {
    //        JSONArray Items = new JSONArray();
    //        try
    //        {
    //            opendb(context);
    //            String sql = "SELECT * FROM " + DatabaseHelper.BIAOCE_TABLE_NAME;
    //            Cursor c = sqlitedatabase.rawQuery(sql, null);
    //
    //            if (c != null)
    //            {
    //                while (c.moveToNext())
    //                {
    //                    JSONObject item = new JSONObject();
    //                    item.put("_id", c.getInt(c.getColumnIndex("_id")));
    //                    item.put("lineX", c.getString(c.getColumnIndex("lineX")));
    //                    item.put("lineY", c.getString(c.getColumnIndex("lineY")));
    //                    item.put("user_id",
    //                            c.getString(c.getColumnIndex("user_id")));
    //                    item.put("up_time",
    //                            c.getString(c.getColumnIndex("up_time")));
    //                    item.put("speed", c.getString(c.getColumnIndex("speed")));
    //                    item.put("jq_way", c.getString(c.getColumnIndex("jq_way")));
    //                    item.put("fq_status",
    //                            c.getString(c.getColumnIndex("fq_status")));
    //                    Items.put(item);
    //                }
    //                Log.e("DeptArray", "Items.length() = " + Items.length());
    //                c.close();
    //            }
    //        }
    //        catch (Exception e)
    //        {
    //            e.printStackTrace();
    //        }
    //        finally
    //        {
    //            closedb(context);
    //        }
    //        return Items;
    //    }

    public ArrayList<String> queryVolumeNo(){
        return queryVolumeNo(null);
    }

    public ArrayList<String> queryVolumeNo(String userid)
    {
        String selection = null;
        String[] selectionArgs = null;
        if (!TextUtils.isEmpty(userid)){
            selection = DatabaseHelper.BIAOCE_USER_ID + "=?";
            selectionArgs = new String[]{userid};
        }

        ArrayList<String> datas = new ArrayList<String>();
        try
        {
            opendb(context);
            Cursor c = sqlitedatabase.query(DatabaseHelper.BIAOCE_TABLE_NAME, new String[]{DatabaseHelper.BIAOCE_VOLUME_NO}, selection, selectionArgs, null, null, DatabaseHelper.BIAOCE_VOLUME_NO + " ASC");
            if (c != null)
            {
                boolean toFirst = c.moveToFirst();
                while (toFirst)
                {
                    String volumeNo = c.getString(c.getColumnIndex(DatabaseHelper.BIAOCE_VOLUME_NO));
                    datas.add(volumeNo.trim());
                    toFirst = c.moveToNext();
                }
                c.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closedb(context);
        }

        return datas;
    }

    public ArrayList<BiaoCeTables> DeptArray(String userId)
    {
        ArrayList<BiaoCeTables> datas = new ArrayList<BiaoCeTables>();
        try
        {
            opendb(context);
            Cursor c = sqlitedatabase.query(DatabaseHelper.BIAOCE_TABLE_NAME, null, DatabaseHelper.BIAOCE_USER_ID + "=?", new String[]{userId}, null, null, DatabaseHelper.BIAOCE_VOLUME_NO + " ASC");

            if (c != null)
            {
                boolean toFirst = c.moveToFirst();
                while (toFirst)
                {
                    String volumeNo = c.getString(c.getColumnIndex(DatabaseHelper.BIAOCE_VOLUME_NO));
                    String mcount = c.getString(c.getColumnIndex(DatabaseHelper.BIAOCE_VOLUME_MCOUNT));
                    String USER_ID = c.getString(c.getColumnIndex(DatabaseHelper.BIAOCE_USER_ID));
                    String updata = c.getString(c.getColumnIndex(DatabaseHelper.BIAOCE_VOLUME_UPDATA));
                    String save = c.getString(c.getColumnIndex(DatabaseHelper.BIAOCE_VOLUME_SAVE));
                    datas.add(new BiaoCeTables(volumeNo, mcount, USER_ID, updata, save));
                    toFirst = c.moveToNext();
                }
                c.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closedb(context);
        }
        L.w("BiaoCeDatabase", "datas.size() = " + datas.size());
        return datas;
    }

    public BiaoCeTables queryBiaoCeStatu(String volumeNo)
    {
        BiaoCeTables tables = new BiaoCeTables(null, null, null, null, null);
        try
        {
            opendb(context);
            Cursor c = sqlitedatabase.query(DatabaseHelper.BIAOCE_TABLE_NAME, null, DatabaseHelper.BIAOCE_VOLUME_NO + "=?", new String[]{volumeNo}, null, null, null);
            if (c != null)
            {

                boolean toFirst = c.moveToFirst();
                tables.VOLUME_NO = c.getString(c.getColumnIndex(DatabaseHelper.BIAOCE_VOLUME_NO));
                tables.VOLUME_MCOUNT = c.getString(c.getColumnIndex(DatabaseHelper.BIAOCE_VOLUME_MCOUNT));
                tables.USER_ID = c.getString(c.getColumnIndex(DatabaseHelper.BIAOCE_USER_ID));
                tables.VOLUME_UPDATA = c.getString(c.getColumnIndex(DatabaseHelper.BIAOCE_VOLUME_UPDATA));
                tables.VOLUME_SAVE = c.getString(c.getColumnIndex(DatabaseHelper.BIAOCE_VOLUME_SAVE));

                c.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closedb(context);
        }
        return tables;
    }
}