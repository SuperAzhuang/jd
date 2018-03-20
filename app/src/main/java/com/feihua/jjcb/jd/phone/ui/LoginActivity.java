package com.feihua.jjcb.jd.phone.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.feihua.jjcb.jd.phone.R;
import com.feihua.jjcb.jd.phone.callback.DataInfo;
import com.feihua.jjcb.jd.phone.callback.User;
import com.feihua.jjcb.jd.phone.callback.UserCallback;
import com.feihua.jjcb.jd.phone.constants.Constants;
import com.feihua.jjcb.jd.phone.utils.L;
import com.feihua.jjcb.jd.phone.utils.SharedPreUtils;
import com.feihua.jjcb.jd.phone.utils.StringUtils;
import com.feihua.jjcb.jd.phone.utils.ToastUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener
{
    private Context ctn;
    private EditText etUserName;
    private EditText etPassword;
    private CheckBox cbRemember;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ctn = this;

        initViews();

        initDatas();
    }

    private void initDatas()
    {
        boolean remember = SharedPreUtils.getBoolean(Constants.Remember_Pw, true, ctn);
        if (remember)
        {
            String username = SharedPreUtils.getString("uname", "", ctn);
            String password = SharedPreUtils.getString("upswd", "", ctn);
            etUserName.setText(username);
            etPassword.setText(password);
        }
        cbRemember.setChecked(remember);
    }

    private void initViews()
    {
        etUserName = (EditText) findViewById(R.id.login_et_uesrname);
        etPassword = (EditText) findViewById(R.id.login_et_pw);
        cbRemember = (CheckBox) findViewById(R.id.login_loginactivity_Remember_password_cb);
        btnLogin = (Button) findViewById(R.id.login_btn);
        btnLogin.setOnClickListener(this);
        //        cbRemember.setOnCheckedChangeListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.login_btn:
                checkLogin();
                break;
            default:

        }
    }

    private void checkLogin()
    {
        String username = etUserName.getText().toString();
        String userPassWord = etPassword.getText().toString();
        if (StringUtils.isEmpty2(username) || StringUtils.isEmpty2(userPassWord))
        {
            ToastUtil.showShortToast(R.string.loginactivity_toast_null_password);
            return;
        }

        boolean isChecked = cbRemember.isChecked();
        //按钮被选中，下次进入时会显示账号和密码
        if (isChecked)
        {
            SharedPreUtils.putString("uname", username, ctn);
            SharedPreUtils.putString("upswd", userPassWord, ctn);
        }
        //按钮被选中，清空账号和密码，下次进入时会显示账号和密码
        else
        {
            SharedPreUtils.putString("uname", "", ctn);
            SharedPreUtils.putString("upswd", "", ctn);
        }
        SharedPreUtils.putBoolean(Constants.Remember_Pw, isChecked, ctn);
        login(username, userPassWord);
    }

    private void login(String userName, String userPassWord)
    {
        mProgressBar.setProgressMsg(getResources().getString(R.string.progress_logining));
        mProgressBar.show();

        OkHttpUtils.post()//
                .url(Constants.LOGIN_URL)//
                .addParams("account", userName)//
                .addParams("password", userPassWord)//
                .build()//
                .execute(new UserCallback()
                {
                    @Override
                    public void onNetworkError(boolean isNetwork, final String error)
                    {
                        L.w("UserCallback", "isNetwork" + isNetwork);
                        if (isNetwork)
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showShortToast(R.string.toast_network_anomalies);
                                }
                            });
                        }
                        else
                        {
//                            ToastUtil.showShortToast(error);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showShortToast(error);
                                }
                            });
                        }
                        mProgressBar.dismiss();
                    }

                    @Override
                    public void onResponse(User response)
                    {
                        if (response != null)
                        {
                            L.w("LoginActivity", "response = " + response);
                            SharedPreUtils.putBoolean(Constants.IS_LOGIN, true, ctn);
                            SharedPreUtils.putString(Constants.USERNAME, response.USERNAME, ctn);
                            SharedPreUtils.putString(Constants.PASSWORD, etPassword.getText().toString(), ctn);
                            SharedPreUtils.putString(Constants.USET_TOKEN, response.TK, ctn);
                            SharedPreUtils.putString(Constants.USER_ID, response.USER_ID, ctn);
                            SharedPreUtils.putString(Constants.NAME, response.NAME, ctn);
                            SharedPreUtils.putString(Constants.STATUS, response.STATUS, ctn);
                            SharedPreUtils.putString(Constants.DEPT_ID, response.DEPT_ID, ctn);
                            SharedPreUtils.putString(Constants.ROLE_ID, response.ROLE_ID, ctn);
                            SharedPreUtils.putString(Constants.PHONE, response.PHONE, ctn);
                            SharedPreUtils.putString(Constants.ORG_NO, response.ORG_NO, ctn);
                            SharedPreUtils.putString(Constants.POSITION, response.POSITION, ctn);
                            SharedPreUtils.putString(Constants.LOGIN_TOKENS, response.LOGIN_TOKENS, ctn);
                            startMain();
                            ToastUtil.showShortToast(R.string.loginactivity_toast_success);
                        }
                        mProgressBar.dismiss();
                    }
                });
    }

    private void startMain()
    {
        Intent intent = new Intent(ctn, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
