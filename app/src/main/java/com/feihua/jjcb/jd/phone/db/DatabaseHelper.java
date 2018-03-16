package com.feihua.jjcb.jd.phone.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.feihua.jjcb.jd.phone.utils.L;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 7; // 数据库版本号

    private static final String DATABASE_NAME = "Table"; // 数据库名�?

    public static final String BIAOCE_TABLE_NAME = "BIAOCE";// 表册清单数据库表名
    public static final String CHAOBIAO_TABLE_NAME = "CHAOBIAO";// 抄表清单数据库表名
    public static final String LOCATION_TABLE_NAME = "LOCATION";// 轨迹定位数据库表名
    public static final String BIAOK_TABLE_NAME = "BIAOKUANG";// 表况数据库表名
    /* 表中的字段 */
    public final static String BIAOCE_TABLE_ID = "_id";
    public static final String BIAOCE_VOLUME_NO = "VOLUME_NO";// 表册册号
    public static final String BIAOCE_VOLUME_MCOUNT = "VOLUME_MCOUNT";// 表册用户总数
    public static final String BIAOCE_VOLUME_UPDATA = "VOLUME_UPDATA";// 表册用户上传数
    public static final String BIAOCE_VOLUME_SAVE = "VOLUME_SAVE";// 表册用户抄表数
    public static final String BIAOCE_USER_ID = "USER_ID";// 表册用户抄表数

    public static final String CHAOBIAO_TABLE_ID = "_id";
    public static final String CHAOBIAO_VOLUME_NO = "VOLUME_NO";// 表册用户抄表数
    public static final String CHAOBIAO_USERB_KH = "USERB_KH";// 表册用户抄表数
    public static final String CHAOBIAO_USERB_HM = "USERB_HM";// 表册用户抄表数
    public static final String CHAOBIAO_USERB_ADDR = "USERB_ADDR";// 表册用户抄表数
    public static final String CHAOBIAO_USERB_DH = "USERB_DH";// 表册用户抄表数
    public static final String CHAOBIAO_USERB_MT = "USERB_MT";// 表册用户抄表数
    public static final String CHAOBIAO_METERC_CALIBRE = "METERC_CALIBRE";// 表册用户抄表数
    public static final String CHAOBIAO_WATERM_TYPE = "WATERM_TYPE";// 表册用户抄表数
    public static final String CHAOBIAO_METERC_BASIC = "METERC_BASIC";// 表册用户抄表数
    public static final String CHAOBIAO_USERB_YSXZ = "USERB_YSXZ";// 表册用户抄表数
    public static final String CHAOBIAO_USERB_CUP = "USERB_CUP";// 表册用户抄表数
    public static final String CHAOBIAO_WATERM_AZRQ = "WATERM_AZRQ";// 表册用户抄表数
    public static final String CHAOBIAO_USERB_LHRQ = "USERB_LHRQ";// 表册用户抄表数
    public static final String CHAOBIAO_WATERM_NX = "WATERM_NX";// 表册用户抄表数
    public static final String CHAOBIAO_WATERM_NO = "WATERM_NO";// 表册用户抄表数
    public static final String CHAOBIAO_USERB_SQDS = "USERB_SQDS";// 表册用户抄表数
    public static final String CHAOBIAO_USERB_JBH = "USERB_JBH";// 表册用户抄表数
    public static final String CHAOBIAO_WATERM_LOALTION = "WATERM_LOALTION";// 表册用户抄表数
    public static final String CHAOBIAO_WATERM_CHECK_CODE = "WATERM_CHECK_CODE";// 表册用户抄表数
    public static final String CHAOBIAO_WATERM_YIELD = "WATERM_YIELD";// 表册用户抄表数
    public static final String CHAOBIAO_IS_UPDATA = "IS_UPDATA";// 表册用户抄表数
    public static final String CHAOBIAO_IS_SAVE = "IS_SAVE";// 表册用户抄表数
    public static final String CHAOBIAO_Q3Q = "Q3Q";// 表册用户抄表数
    public static final String CHAOBIAO_WATERM_REPDATE = "WATERM_REPDATE";// 表册用户抄表数
    public static final String CHAOBIAO_USERB_YHLX = "USERB_YHLX";// 表册用户抄表数
    public static final String CHAOBIAO_WATERT_NAME = "WATERT_NAME";// 表册用户抄表数
    public static final String CHAOBIAO_WATERU_YEAR = "WATERU_YEAR";// 表册用户抄表数
    public static final String CHAOBIAO_WATERU_MONTH = "WATERU_MONTH";// 表册用户抄表数
    public static final String CHAOBIAO_WATER_BK = "WATER_BK";// 表册用户抄表数
    public static final String CHAOBIAO_BASICTON_TAG = "BASICTON_TAG";// 表册用户抄表数
    public static final String CHAOBIAO_BASICTON_QAN = "BASICTON_QAN";// 表册用户抄表数
    public static final String CHAOBIAO_FIRST_TAG = "FIRST_TAG";// 表册用户抄表数
    public static final String CHAOBIAO_IS_CBWAY = "IS_CBWAY";// 表册用户抄表数
    public static final String CHAOBIAO_AREA_NO = "AREA_NO";// 表册用户抄表数
    public static final String CHAOBIAO_TIME = "TIME";// 表册用户抄表数
    public static final String CHAOBIAO_METERH_WATERQAN = "METERH_WATERQAN";// 表册用户抄表数
    public static final String CHAOBIAO_USERB_SFFS = "USERB_SFFS";// 表册用户抄表数
    public static final String CHAOBIAO_USERB_TOTAL = "USERB_TOTAL";// 表册用户抄表数
    public static final String CHAOBIAO_ISCHARGING = "ISCHARGING";// 表册用户抄表数
    public static final String CHAOBIAO_FREEPULL_TAG = "FREEPULL_TAG";// 表册用户抄表数
    public static final String CHAOBIAO_LADDER_TAG = "LADDER_TAG";// 表册用户抄表数
    public static final String CHAOBIAO_SEASON_TAG = "SEASON_TAG";// 表册用户抄表数
    public static final String CHAOBIAO_WZ_TAG = "WZ_TAG";// 表册用户抄表数
    public static final String CHAOBIAO_USERB_SYSL = "USERB_SYSL";// 表册用户抄表数
    public static final String CHAOBIAO_QFJE = "QFJE";// 欠费金额
    /* 表中的字段 */
    public static final String LOCATON_TABLE_ID = "_id";
    public static final String LONTITUDE = "LONTITUDE";// 数据库经度
    public static final String LATITUDE = "LATITUDE";// 数据库纬度
    public static final String USER_ID = "USER_ID";// 数据库user_id
    public static final String UP_TIME = "UP_TIME";// 数据库采集时间
    public static final String SPEED = "SPEED";// 数据库移动速度
    public static final String LOC_MODE = "LOC_MODE";// 数据库定位方式

    /* 表中的字段 */
    public static final String BIAOK_TABLE_ID = "_id";
    public static final String WATER_BK = "WATER_BK";// 表况
    public static final String IS_CBWAY = "IS_CBWAY";// 实抄1估抄0


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String biaok = "CREATE TABLE " + BIAOK_TABLE_NAME + " (" + BIAOK_TABLE_ID + " INTEGER PRIMARY KEY,"
                + WATER_BK + " VARCHAR(20)," + IS_CBWAY + " VARCHAR(5))";
        db.execSQL(biaok);
        String location = "CREATE TABLE " + LOCATION_TABLE_NAME + " (" + LOCATON_TABLE_ID + " INTEGER PRIMARY KEY,"
                + LONTITUDE + " VARCHAR(36)," + LATITUDE + " VARCHAR(36)," + USER_ID + " VARCHAR(36)," + UP_TIME + " VARCHAR(36)," + SPEED + " VARCHAR(36)," + LOC_MODE + " VARCHAR(36))";
        db.execSQL(location);
        L.e("DatabaseHelper", "LOCATION_TABLE_NAME创建表成功");
        String biaoce = "CREATE TABLE " + BIAOCE_TABLE_NAME + " (" + BIAOCE_TABLE_ID + " INTEGER PRIMARY KEY," + BIAOCE_VOLUME_NO + " VARCHAR(36)," + BIAOCE_VOLUME_MCOUNT + " VARCHAR(5)," + BIAOCE_USER_ID + " VARCHAR(36)," + BIAOCE_VOLUME_UPDATA + " VARCHAR(5)," + BIAOCE_VOLUME_SAVE + " VARCHAR(5))";
        db.execSQL(biaoce);
        L.e("DatabaseHelper", "BIAOCE_TABLE_NAME创建表成功");
        String chaobiao = "CREATE TABLE " + CHAOBIAO_TABLE_NAME + " (" + CHAOBIAO_TABLE_ID + " INTEGER PRIMARY KEY,"
                + CHAOBIAO_VOLUME_NO + " VARCHAR(36)," + CHAOBIAO_USERB_KH + " CHAR(6)," + CHAOBIAO_USERB_HM + " CHAR(40)," + CHAOBIAO_USERB_ADDR + " VARCHAR(36),"
                + CHAOBIAO_TIME + " VARCHAR(36)," + CHAOBIAO_QFJE + " VARCHAR(36)," + CHAOBIAO_Q3Q + " VARCHAR(36)," + CHAOBIAO_USERB_DH + " VARCHAR(36)," + CHAOBIAO_USERB_MT + " VARCHAR(36)," + CHAOBIAO_METERC_CALIBRE + " VARCHAR(36),"
                + CHAOBIAO_WATERM_REPDATE + " VARCHAR(36)," + CHAOBIAO_WATERM_TYPE + " VARCHAR(36)," + CHAOBIAO_METERC_BASIC + " VARCHAR(36)," + CHAOBIAO_USERB_YSXZ + " VARCHAR(36),"
                + CHAOBIAO_WATERU_YEAR + " VARCHAR(36)," + CHAOBIAO_USERB_CUP + " VARCHAR(36)," + CHAOBIAO_WATERM_AZRQ + " VARCHAR(36)," + CHAOBIAO_USERB_LHRQ + " VARCHAR(36),"
                + CHAOBIAO_METERH_WATERQAN + " VARCHAR(15)," + CHAOBIAO_WATERT_NAME + " VARCHAR(15)," + CHAOBIAO_WATERU_MONTH + " VARCHAR(36)," + CHAOBIAO_USERB_JBH + " VARCHAR(36)," + CHAOBIAO_WATERM_LOALTION + " VARCHAR(36)," + CHAOBIAO_WATERM_CHECK_CODE + " VARCHAR(36),"
                + CHAOBIAO_USERB_SFFS + " VARCHAR(10)," + CHAOBIAO_AREA_NO + " VARCHAR(36)," + CHAOBIAO_USERB_YHLX + " VARCHAR(36)," + CHAOBIAO_WATERM_YIELD + " VARCHAR(36)," + CHAOBIAO_IS_UPDATA + " VARCHAR(36)," + CHAOBIAO_IS_SAVE + " VARCHAR(36),"
                + CHAOBIAO_USERB_TOTAL + " VARCHAR(36)," + CHAOBIAO_ISCHARGING + " VARCHAR(36)," + CHAOBIAO_FREEPULL_TAG + " VARCHAR(36)," + CHAOBIAO_LADDER_TAG + " VARCHAR(36)," + CHAOBIAO_SEASON_TAG + " VARCHAR(36)," + CHAOBIAO_WZ_TAG + " VARCHAR(36)," + CHAOBIAO_USERB_SYSL + " VARCHAR(36),"
                + CHAOBIAO_FIRST_TAG + " VARCHAR(5)," + CHAOBIAO_BASICTON_TAG + " VARCHAR(5)," + CHAOBIAO_BASICTON_QAN + " VARCHAR(10)," + CHAOBIAO_WATER_BK + " VARCHAR(20)," + CHAOBIAO_IS_CBWAY + " VARCHAR(5)," + CHAOBIAO_WATERM_NX + " VARCHAR(36)," + CHAOBIAO_WATERM_NO + " VARCHAR(36)," + CHAOBIAO_USERB_SQDS + " VARCHAR(36))";
        db.execSQL(chaobiao);
        L.e("DatabaseHelper", "CHAOBIAO_TABLE_NAME创建表成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String biaok = "DROP TABLE IF EXISTS " + BIAOK_TABLE_NAME;
        db.execSQL(biaok);
        String biaoce = "DROP TABLE IF EXISTS " + BIAOCE_TABLE_NAME;
        db.execSQL(biaoce);
        String chaobiao = "DROP TABLE IF EXISTS " + CHAOBIAO_TABLE_NAME;
        db.execSQL(chaobiao);
        String location = "DROP TABLE IF EXISTS " + LOCATION_TABLE_NAME;
        db.execSQL(location);
        this.onCreate(db);
    }

}
