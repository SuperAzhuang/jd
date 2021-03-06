package com.feihua.jjcb.jd.phone.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.feihua.jjcb.jd.phone.R;
import com.feihua.jjcb.jd.phone.constants.Constants;
import com.feihua.jjcb.jd.phone.utils.SharedPreUtils;


public class WelComeActivity extends Activity
{

    private ImageView imgWelCome;
    private Context cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel_come);

        cnt = this;

        imgWelCome = (ImageView) findViewById(R.id.imgWelCome);
        imgWelCome.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                checkIsLoaded();

            }
        }, 3000);
        
    }

    private void checkIsLoaded()
    {
        boolean isLogin = SharedPreUtils.getBoolean(Constants.IS_LOGIN,false,cnt);
        Intent intent;
        if (isLogin){
            intent = new Intent(this,MainActivity.class);
        }else{
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

}
