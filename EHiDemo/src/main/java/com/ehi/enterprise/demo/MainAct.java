package com.ehi.enterprise.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act);
    }

    public void toastTestClick(View view) {
        Intent intent = new Intent(this,ToolsTestAct.class);
        startActivity(intent);
    }

    public void dialogTestClick(View view) {
        Intent intent = new Intent(this,DialogTestAct.class);
        startActivity(intent);
    }
}
