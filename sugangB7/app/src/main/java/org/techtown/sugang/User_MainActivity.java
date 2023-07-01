package org.techtown.sugang;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class User_MainActivity extends AppCompatActivity {

    private Button btn_syllabus, btn_basket, btn_registration;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        Intent getIntent = getIntent();
        String userID = getIntent.getStringExtra("student_id");

        Log.d("User_MainActivity",userID);

        btn_syllabus = findViewById(R.id.btn_syllabus);
        btn_basket = findViewById(R.id.btn_basket);
        btn_registration = findViewById(R.id.btn_registration);

        btn_basket.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_MainActivity.this,ShoppingBasket.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        btn_registration.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_MainActivity.this, ApplicationForClasses.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });


    }
}