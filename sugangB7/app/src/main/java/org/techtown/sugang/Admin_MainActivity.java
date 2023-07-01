package org.techtown.sugang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Admin_MainActivity extends AppCompatActivity {

    private Button btn_noticeAdmin, btn_su;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        btn_noticeAdmin = findViewById(R.id.btn_noticeAdmin);
        btn_su = findViewById(R.id.btn_su);
        
    }
}