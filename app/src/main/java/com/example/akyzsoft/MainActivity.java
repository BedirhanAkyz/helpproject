package com.example.akyzsoft;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.akyuzsoft.R;

@SuppressLint("Registered")
public class MainActivity extends AppCompatActivity {

    MyDatabaseOpenHelper helper;

    ImageView logo;
    ImageView logo2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo=(ImageView)findViewById(R.id.logo);



        @SuppressLint("CutPasteId") Button startBtn = (Button)findViewById(R.id.start_btn);
        @SuppressLint("CutPasteId") Button rankBtn = (Button)findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);

            }
        });

        rankBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, RankActivity.class);
                startActivity(intent);
            }
        });

        helper = new MyDatabaseOpenHelper(MainActivity.this, MyDatabaseOpenHelper.tableName, null, 1);
    }
}
