package com.example.akyzsoft;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    int num[]= new int[50];
    long begin;
    //int num[]=new int[25];
    ImageView btn[] = new ImageView[25];
    int btnIdx;
    int count = 0;
    private String m_Text = "";
    int imgBtn[] = {R.drawable.num1,R.drawable.num2,R.drawable.num3,R.drawable.num4,R.drawable.num5,
            R.drawable.num6,R.drawable.num7,R.drawable.num8,R.drawable.num9,R.drawable.num10,
            R.drawable.num11,R.drawable.num12,R.drawable.num13,R.drawable.num14,R.drawable.num15,
            R.drawable.num16,R.drawable.num17,R.drawable.num18,R.drawable.num19,R.drawable.num20,
            R.drawable.num21,R.drawable.num22,R.drawable.num23,R.drawable.num24,R.drawable.num25,
            R.drawable.num26,R.drawable.num27,R.drawable.num28,R.drawable.num29,R.drawable.num30,
            R.drawable.num31,R.drawable.num32,R.drawable.num33,R.drawable.num34,R.drawable.num35,
            R.drawable.num36,R.drawable.num37,R.drawable.num38,R.drawable.num39,R.drawable.num40,
            R.drawable.num41,R.drawable.num42,R.drawable.num43,R.drawable.num44,R.drawable.num45,
            R.drawable.num46,R.drawable.num47,R.drawable.num48,R.drawable.num49,R.drawable.num50};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        begin = System.currentTimeMillis();
        setContentView(R.layout.activity_game);
        View timeLayout = (View)findViewById(R.id.timer_layout);
        timeLayout.setVisibility(View.INVISIBLE);
        int numBtn[] = {R.id.btn_num1,R.id.btn_num2,R.id.btn_num3,R.id.btn_num4,R.id.btn_num5,
                R.id.btn_num6,R.id.btn_num7,R.id.btn_num8,R.id.btn_num9,R.id.btn_num10,
                R.id.btn_num11,R.id.btn_num12,R.id.btn_num13,R.id.btn_num14,R.id.btn_num15,
                R.id.btn_num16,R.id.btn_num17,R.id.btn_num18,R.id.btn_num19,R.id.btn_num20,
                R.id.btn_num21,R.id.btn_num22,R.id.btn_num23,R.id.btn_num24,R.id.btn_num25};

        for(int i = 0 ; i< 50 ; ++i){
            num[i] = i;
        }

        for(btnIdx = 0; btnIdx< 25 ; btnIdx++) {
            btn[btnIdx] = (ImageView) findViewById(numBtn[btnIdx]);
            btn[btnIdx].setOnClickListener(this);
            btn[btnIdx].setTag(btnIdx);
        }

        shuffle(num,0,25);
        shuffle(num,25,50);
        for(int i = 0 ; i<25 ; ++i){
            btn[i].setImageResource(imgBtn[num[i]]);
        }
    }
    void shuffle(int num[], int start, int end){
        for(int i = start; i<end ; ++i) {
            Random ran = new Random();
            int rand = ran.nextInt(25)+start;
            int temp;
            temp = num[i];
            num[i] = num[rand];
            num[rand] = temp;
        }
    }

    public void inputNameDialog(final long clearTime ){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Well Done! "+clearTime+":)");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                saveRankingScore(m_Text,clearTime);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        builder.show();
    }

    void saveRankingScore(String name, long clearTime ){
        MyDatabaseOpenHelper helper = MyDatabaseOpenHelper.getInstance();
        SQLiteDatabase database = helper.getWritableDatabase();
        helper.insertName(database, name, clearTime);
        helper.deleteRank(database);
    }

    @Override
    public void onClick(View v) {
        for(View tmp:btn){
            int position = (Integer)v.getTag();
            if(count>=25) {position +=25;}

            if(tmp == v && count == num[position]){
                if(count>=25){
                    btn[position-25].setVisibility(View.INVISIBLE);
                    if(count == 49){
                        long duration = (System.currentTimeMillis() - begin)/1000;
                        inputNameDialog(duration);
                    }
                }else{
                    btn[position].setImageResource(imgBtn[num[position+25]]);
                }
                count++;
            }
        }
    }
}
