package com.ehi.enterprise.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ehi.enterprise.tools.ToastUtil;

public class ToolsTestAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tools_test_act);
        getSupportActionBar().setTitle("Test Toast");
    }

    public void centerToast(View view) {
        ToastUtil.showCenterText("hello world");
    }

    public void successToast(View view) {
        ToastUtil.successShow("成功操作");
    }

    public void leftImgRightTextToast(View view) {
        ToastUtil.leftImgRightTextShow("左边文本",R.drawable.ic_launcher_background);
    }

}
