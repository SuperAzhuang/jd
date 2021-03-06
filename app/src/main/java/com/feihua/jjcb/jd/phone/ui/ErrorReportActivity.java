package com.feihua.jjcb.jd.phone.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.feihua.jjcb.jd.phone.Adapter.AbnormalImageBaseAdapter;
import com.feihua.jjcb.jd.phone.R;
import com.feihua.jjcb.jd.phone.bean.ErrorTypeBean;
import com.feihua.jjcb.jd.phone.callback.ChaoBiaoTables;
import com.feihua.jjcb.jd.phone.callback.CommonCallback;
import com.feihua.jjcb.jd.phone.callback.ImageUploadCallback;
import com.feihua.jjcb.jd.phone.callback.NotObjCommonCallback;
import com.feihua.jjcb.jd.phone.constants.Constants;
import com.feihua.jjcb.jd.phone.utils.L;
import com.feihua.jjcb.jd.phone.utils.PhotoUtils;
import com.feihua.jjcb.jd.phone.utils.SharedPreUtils;
import com.feihua.jjcb.jd.phone.utils.ToastUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

/*
* 异常上报
* */
public class ErrorReportActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener
{

    private Spinner mSpinner_bk;
    private GridView mGridviewImage;
    private String location;
    //    private TextView tvLocation;
    private ArrayList<ErrorTypeBean.ErrorType> errorTypeDatas;
    private ArrayList<String> errorNameDatas;
    private Context ctx;
    private String errorType;
    public final int REQUESTCODE_MULTIPLE = 200;
    private ArrayList<Bitmap> mBitmapLists;
    private ArrayList<File> mFileLists;
    private String mFilePathSmall;
    private AbnormalImageBaseAdapter adapter;
    private static final int IMAGE_ADD_WAY = 1;
    private static final int IMAGE_CHANGE_WAY = 2;
    private static final int IMAGE_DEL_MAY = 3;
    private int index;
    private int way;
    private View layoutMenu;
    private String USERB_KH;
    private String YEAR;
    private String MONTH;
    private TranslateAnimation ta;
    private String picUrl = "";
    private EditText etAbnormal;
    private TextView tvTitle;
    private String mFilePath;
    private String mPhotoPath;
    private ChaoBiaoTables tables;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_report);

        Intent intent = getIntent();
        location = intent.getStringExtra("location");
        tables = (ChaoBiaoTables) intent.getSerializableExtra("tables");
        if (tables != null)
        {
            USERB_KH = tables.getUSERB_KH();
            YEAR = tables.getWATERU_YEAR();
            MONTH = tables.getWATERU_MONTH();
        }
        errorTypeDatas = new ArrayList<>();
        errorNameDatas = new ArrayList<>();
        mBitmapLists = new ArrayList<Bitmap>();
        mFileLists = new ArrayList<File>();
        ctx = this;
        mFilePath = Environment.getExternalStorageDirectory().getPath() + Constants.FILEPATH_DIR;
        mFilePathSmall = Environment.getExternalStorageDirectory().getPath() + Constants.FILEPATHSMALL_DIR;
        ta = new TranslateAnimation(0, 0, 600, 0);
        ta.setDuration(200);
        ta.setInterpolator(new AccelerateInterpolator());

        initView();

        initDatas();
    }

    private void initDatas()
    {
        OkHttpUtils.post()//
                .url(Constants.GET_ABNORMAL_TYPE)//
                //                .addParams("RECORDER", userId)//
                .build()//
                .execute(new CommonCallback()
                {
                    @Override
                    public void onNetworkError(boolean isNetwork, String error)
                    {
                        if (isNetwork)
                        {
                            ToastUtil.showShortToast(R.string.toast_network_anomalies);
                        }
                        else
                        {
                            ToastUtil.showShortToast(error);
                        }
                    }

                    @Override
                    public void onResponse(String datas)
                    {
                        ErrorTypeBean bean = new Gson().fromJson(datas, ErrorTypeBean.class);
                        if (bean.datadic != null && bean.datadic.size() > 0)
                        {
                            errorTypeDatas.clear();
                            errorTypeDatas.addAll(bean.datadic);
                            for (ErrorTypeBean.ErrorType errorType : errorTypeDatas)
                            {
                                errorNameDatas.add(errorType.DATADIC_NAME);
                                ArrayAdapter<String> mBKadapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, errorNameDatas);
                                mBKadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mSpinner_bk.setAdapter(mBKadapter);
                            }
                        }
                    }
                });

    }

    private void initView()
    {
        //        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvTitle = (TextView) findViewById(R.id.head_title);

        etAbnormal = (EditText) findViewById(R.id.etAbnormal);
        mSpinner_bk = (Spinner) findViewById(R.id.spinner_bk);
        mGridviewImage = (GridView) findViewById(R.id.gv_error_report);
        adapter = new AbnormalImageBaseAdapter(ctx, mBitmapLists, R.layout.griditem_image);
        mGridviewImage.setAdapter(adapter);
        mGridviewImage.setOnItemClickListener(this);
        layoutMenu = findViewById(R.id.layoutMenu);
        layoutMenu.setOnClickListener(this);
        findViewById(R.id.btn_left).setOnClickListener(this);
        //        findViewById(R.id.btnGetLocation).setOnClickListener(this);
        findViewById(R.id.btnChange).setOnClickListener(this);
        findViewById(R.id.btnDel).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        mSpinner_bk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                errorType = errorTypeDatas.get(position).DATADIC_VALUE;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        tvTitle.setText(USERB_KH);
        //        tvLocation.setText(location);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_left:
                finish();
                break;
            //            case R.id.btnGetLocation:
            //                getLocation();
            //                break;
            case R.id.btnChange:
                getImageChange();
                break;
            case R.id.btnDel:
                getImageDel();
                break;
            case R.id.btnCancel:
                getImageCancel();
                break;
            case R.id.layoutMenu:
                getImageCancel();
                break;
            case R.id.btn_commit:
                commit();
                break;
            default:
                break;
        }
    }

    private void commit()
    {
        mProgressBar.setProgressMsg(getResources().getString(R.string.progress_upload_all));
        mProgressBar.show();
        picUrl = "";
        if (mFileLists.size() > 0)
        {
            uploadImage();
        }
        else
        {
            uploadDetails(false);
        }

    }

    private void uploadDetails(boolean isImage)
    {
        String USER_ID = SharedPreUtils.getString(Constants.USER_ID, ctx);
        String abnormal = etAbnormal.getText().toString();
        if (tables == null){
            ToastUtil.showShortToast("数据异常");
            return;
        }
        L.w("CommonCallback", "abnormal = " + abnormal);
        PostFormBuilder post = OkHttpUtils.post();
        post.url(Constants.UPLOAD_ABNORMAL_DATA_DETAILS)//
                .addParams("CREATE_PERSON", USER_ID)//
                .addParams("USERB_KH", USERB_KH)//
                .addParams("EXC_TYPE", errorType)//
                .addParams("EXC_DESC", abnormal)//
                .addParams("YEAR", YEAR)//
                .addParams("MONTH", MONTH)//
                .addParams("VOLUME_NO", tables.getVOLUME_NO())//
                .addParams("WATERM_NO", tables.getWATERM_NO());//
        if (isImage)
        {
            post.addParams("PIC_BASEPATH", picUrl);
        }
        post.build()//
                .execute(new NotObjCommonCallback()
                {
                    @Override
                    public void onNetworkError(boolean isNetwork, String error)
                    {
                        if (isNetwork)
                        {
                            ToastUtil.showShortToast(R.string.toast_network_anomalies);
                        }
                        else
                        {
                            ToastUtil.showShortToast(error);
                        }
                        mProgressBar.dismiss();
                    }

                    @Override
                    public void onResponse(Boolean isSuccess)
                    {
                        mProgressBar.dismiss();
                        if (isSuccess)
                        {
                            ToastUtil.showShortToast(R.string.toast_updata_success);
                            finish();
                        }
                    }
                });
    }

    private void uploadImage()
    {
        PostFormBuilder post = OkHttpUtils.post();
        int i = 0;
        for (File file : mFileLists)
        {
            i++;
            String fileName = file.getName();
            if (fileName == null)
                fileName = i + ".jpg";
            L.w("ErrorReportActivity", "fileName = " + fileName);
            post.addFile("PIC_BASEPATH" + i, fileName, file);
        }
        String USER_ID = SharedPreUtils.getString(Constants.USER_ID, ctx);
        post.url(Constants.UPLOAD_ABNORMAL_DATA_IMAGE)//
                .addParams("CREATE_PERSON", USER_ID)//
                .build()//
                .execute(new ImageUploadCallback()
                {
                    @Override
                    public void onNetworkError(boolean isNetwork, String error)
                    {
                        if (isNetwork)
                        {
                            ToastUtil.showShortToast(R.string.toast_network_anomalies);
                        }
                        else
                        {
                            ToastUtil.showShortToast(error);
                        }
                        mProgressBar.dismiss();
                    }

                    @Override
                    public void onResponse(String datas)
                    {
                        picUrl = datas;
                        uploadDetails(true);
                    }
                });
    }

    private void getImageCancel()
    {
        layoutMenu.setVisibility(View.GONE);
    }

    private void getImageDel()
    {
        mBitmapLists.remove(index);
        mFileLists.remove(index);
        layoutMenu.setVisibility(View.GONE);
        adapter.onDatasChanged(mBitmapLists);
    }

    private void getImageChange()
    {
        way = IMAGE_CHANGE_WAY;
        layoutMenu.setVisibility(View.GONE);
        //        startMultiple(1);
        getPic();
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        index = position;
        if (position < mBitmapLists.size())
        {
            layoutMenu.setVisibility(View.VISIBLE);
            layoutMenu.startAnimation(ta);
        }
        else
        {
            way = IMAGE_ADD_WAY;
            //            startMultiple(6 - mBitmapLists.size());
            getPic();
        }
    }

    private void getPic()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir = new File(mFilePath);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        String name = "IMG_" + DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        mPhotoPath = mFilePath + name;
        File file = new File(mPhotoPath);
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {

            }
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, Constants.UPLOADPHOTO_CAMERA);
    }

    private void startMultiple(int size)
    {
        Intent i = new Intent(this, MultiplePickPhotoActivity.class);
        i.putExtra("size", size);
        i.putExtra("way", way);
        startActivityForResult(i, REQUESTCODE_MULTIPLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode)
        {
            Bitmap smallBitmap = null;
            switch (requestCode)
            {
                case Constants.UPLOADPHOTO_CAMERA:// 相机拍照
                    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                    {
                        Toast.makeText(this, "SD卡不可用", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    smallBitmap = PhotoUtils.getSmallBitmap(mPhotoPath);
                    File mPicFile = saveBitmapFile(smallBitmap);
                    switch (way)
                    {
                        case IMAGE_CHANGE_WAY:
                            mBitmapLists.remove(index);
                            mFileLists.remove(index);
                            mBitmapLists.add(index, smallBitmap);
                            mFileLists.add(index, mPicFile);
                            break;
                        case IMAGE_ADD_WAY:
                            mBitmapLists.add(smallBitmap);
                            mFileLists.add(mPicFile);
                            break;
                        default:
                            break;

                    }
                    adapter.onDatasChanged(mBitmapLists);
                    break;
                case REQUESTCODE_MULTIPLE:
                    String[] allPathList = data.getStringArrayExtra("all_path");
                    if (allPathList != null && allPathList.length > 0)
                    {
                        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                        {
                            ToastUtil.showShortToast("SD卡不可用");
                            return;
                        }

                        for (int i = 0; i < allPathList.length; i++)
                        {
                            smallBitmap = PhotoUtils.getSmallBitmap(allPathList[i]);
                            File bitmapFile = saveBitmapFile(smallBitmap);
                            switch (way)
                            {
                                case IMAGE_CHANGE_WAY:
                                    mBitmapLists.remove(index);
                                    mFileLists.remove(index);
                                    mBitmapLists.add(index, smallBitmap);
                                    mFileLists.add(index, bitmapFile);
                                    break;
                                case IMAGE_ADD_WAY:
                                    mBitmapLists.add(smallBitmap);
                                    mFileLists.add(bitmapFile);
                                    break;
                                default:
                                    break;

                            }
                        }
                        adapter.onDatasChanged(mBitmapLists);
                    }
                    break;
                default:
                    break;
            }
        }
        else
        {
            ToastUtil.showShortToast("照片获取失败");
        }
    }

    public File saveBitmapFile(Bitmap bitmap)
    {
        String fileName = UUID.randomUUID().toString() + ".jpg";
        String photoPathSmall = mFilePathSmall + fileName;
        File dir = new File(mFilePathSmall);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        File file = new File(photoPathSmall);
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            FileOutputStream fos = new FileOutputStream(file);
            int options = 100;
            // 如果大于100kb则再次压缩,最多压缩三次
            while (bos.toByteArray().length / 1024 > 100 && options != 10)
            {
                // 清空baos
                bos.reset();
                // 这里压缩options%，把压缩后的数据存放到baos中
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, bos);
                options -= 30;
            }
            fos.write(bos.toByteArray());
            fos.close();
            bos.flush();
            bos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return file;
    }
}
