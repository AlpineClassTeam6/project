package com.neusoft.wushuang.login;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.neusoft.wushuang.login.MainActivity;
import com.neusoft.wushuang.login.R;


public class Login extends Activity {
    Button bt_land;
    CheckBox remember;
    EditText usr;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //TODO 设置状态栏透明
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        Button bt_logon = (Button)findViewById(R.id.logon);
        bt_land = (Button)findViewById(R.id.land);

       // bt_land.setBackground(R.drawable.wea);
         remember = (CheckBox)findViewById(R.id.checkBox1);
         usr = (EditText)findViewById(R.id.usename);
         password = (EditText)findViewById(R.id.password);

        //准备写入对象
        final SharedPreferences mysp = getSharedPreferences("passport", MODE_PRIVATE);

        //根据自动填入设置填入账号和密码
        boolean flag = mysp.getBoolean("ischecked", true);
        if (flag) {
            System.out.println("22222");
            usr.setText(mysp.getString("default_username", null));
            password.setText(mysp.getString("default_password", null));
        }
        //注册部分
        bt_logon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String in_usename = usr.getText().toString();
                String in_password = password.getText().toString();
                if (in_password.equals("")||in_usename.equals("")) {
                    Toast.makeText(getApplicationContext(), "用户名或者密码未填写", Toast.LENGTH_SHORT).show();
                } else if (mysp.getString("username"+in_usename, "").equals("")) {
                    SharedPreferences.Editor editor = mysp.edit();
                    editor.putString("username"+in_usename, in_usename);
                    editor.putString("password"+in_usename, in_password);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "用户名已经存在", Toast.LENGTH_SHORT).show();
                }


            }
        });
        usr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //todo 密码
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // 登陆部分
        bt_land.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String in_usename = usr.getText().toString();
                String in_password = password.getText().toString();
                if (in_password.equals(mysp.getString("password"+in_usename, null))&&in_usename.equals(mysp.getString("username"+in_usename, ""))) {
                    SharedPreferences.Editor editor = mysp.edit();
                    //记住账号密码
                    if (remember.isChecked()) {
                        editor.putString("default_username",in_usename );
                        editor.putString("default_password", in_password);
                        editor.putBoolean("ischecked",true);

                    } else {
                        editor.putString("default_username",null );
                        editor.putString("default_password", null);
                        editor.putBoolean("ischecked", false);

                    }
                    editor.commit();
                    Intent intent = new Intent();
                    //intent.putExtra("username", in_usename);
                    intent.setClass(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "用户名或者密码错误", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



}
