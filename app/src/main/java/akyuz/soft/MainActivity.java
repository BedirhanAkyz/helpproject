package akyuz.soft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.akyzsoft.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button startBtn = findViewById(R.id.start_btn);
        Button rankBtn = findViewById(R.id.rank_Btn);
        Button quitBtn = findViewById(R.id.quitBtn);


        startBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext() , playActivity.class);
                startActivity(intent);
            }
        });


        rankBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext() , rankActivity.class);
                startActivity(intent);
            }
        });


        quitBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                moveTaskToBack(true);
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });


    }



}

