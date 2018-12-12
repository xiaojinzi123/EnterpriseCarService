package com.ehi.enterprise.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ehi.enterprise.ui.dialog.UIConfirmDialog;

public class DialogTestAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_test_act);
    }

    public void testConfirmDialog(View view) {
        UIConfirmDialog.build(this)
                .content("测试文本")
                .build()
                .show();
    }

}
