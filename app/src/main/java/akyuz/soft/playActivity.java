package akyuz.soft;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akyuzsoft.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class playActivity extends Activity {
    private AdView mAdView;
    private int checkNum = 1;
    private TextView mCountDown;
    private int count = 3;
    private int[] ranNum = new int[50];
    private Button  btn[] = new Button[25];
    private Integer[] numBtn = {R.id.btn1 , R.id.btn2 , R.id.btn3 , R.id.btn4 , R.id.btn5 , R.id.btn6 , R.id.btn7
            , R.id.btn8 , R.id.btn9 , R.id.btn10 , R.id.btn11 , R.id.btn12 , R.id.btn13 , R.id.btn14 , R.id.btn15 , R.id.btn16
            , R.id.btn17 , R.id.btn18 , R.id.btn19 , R.id.btn20 , R.id.btn21 , R.id.btn22 , R.id.btn23 , R.id.btn24 , R.id.btn25};
    private Chronometer chronometer;
    private final SoundPool sp = new SoundPool(1,
            AudioManager.STREAM_MUSIC,
            0);
    private int soundID3, soundID4;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private String record;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.setContentView(R.layout.activity_play);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);





        final int soundID = sp.load(this,
                R.raw.yes,
                1);
        final int soundID2 = sp.load(this,
                R.raw.no,
                1);

        soundID3 = sp.load(this,
                R.raw.count,
                1);
        soundID4 = sp.load(this,
                R.raw.start,
                1);


        for(int i=0; i<ranNum.length; i++){
            ranNum[i] = i+1; //1~50
        }

        for(int x=0; x<70; x++) {
            int i = (int)(Math.random() * 25);
            int j = (int)(Math.random() * 25);
            int tmp = 0;

            tmp = ranNum[i];
            ranNum[i] = ranNum[j];
            ranNum[j] = tmp;
        }

        chronometer = findViewById(R.id.chronometer_view);

        OnClickListener clickListener  = new OnClickListener(){
            @Override
            public void onClick(View v){
                int clickNum = Integer.parseInt( String.valueOf(((Button)v).getText()) );
                String result = checkValue(v , checkNum , clickNum );
                if(result.equals("success")){

                    sp.play(soundID,
                            1,
                            1,
                            0,
                            0,
                            1.2f);

                    if(checkNum <= 25) {
                        ((Button) v).setText(String.valueOf(ranNum[checkNum + 24]));
                    } else{
                        v.setEnabled(false);
                        v.setVisibility(View.INVISIBLE);
                    }
                    if(checkNum == 50){
                        chronometer.stop();
                        record = String.valueOf(chronometer.getText());
                        showMessage();

                        return;
                    }
                    checkNum++;
                } else{

                    sp.play(soundID2,
                            1,
                            1,
                            0,
                            0,
                            1.2f);

                }
            }
        };


        for(int i=0; i<btn.length; i++){
            btn[i] = findViewById(numBtn[i]);
            btn[i].setEnabled(false);
            btn[i].setOnClickListener(clickListener);
        }


        LayoutInflater inflater = (LayoutInflater)getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linear = (LinearLayout)inflater.inflate(R.layout.over, null);

        LinearLayout.LayoutParams paramlinear = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);
        linear.setGravity(Gravity.CENTER);
        win.addContentView(linear, paramlinear);


        mCountDown = findViewById(R.id.tv_countdown);
        mCountDown.setText(String.valueOf(count));

        autoCountHandler.postDelayed(autoCountRunnable,1000);
    }

    private Handler autoCountHandler = new Handler();
    private Runnable autoCountRunnable = new Runnable() {
        public void run() {
            count--;
            if(count !=0) {
                mCountDown.setText(String.valueOf(count));
                sp.play(soundID3,
                        1,
                        1,
                        0,
                        0,
                        0.5f);
            }
            if (count > 0) {
                autoCountHandler.postDelayed(autoCountRunnable, 1000);
            } else {
                if (autoCountHandler != null) {

                    mCountDown.setText("");
                    for(int i=0; i<btn.length; i++){
                        btn[i].setText(String.valueOf(ranNum[i]));
                        btn[i].setEnabled(true);
                    }
                    sp.play(soundID4, // 준비한 soundID
                            1,
                            1,
                            0,
                            0,
                            0.5f);
                    Toast.makeText(getApplicationContext() , "START!" , Toast.LENGTH_SHORT).show();
                    autoCountHandler.removeCallbacks(autoCountRunnable);


                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();

                }
            }
        }
    };

    @Override
    public void onPause(){
        super.onPause();
        if(autoCountHandler != null){
            autoCountHandler.removeCallbacks(autoCountRunnable);
        }
    }


    public String checkValue(View v , int checkNum , int clickNum){
        if(checkNum == clickNum){
            return "success";
        } else{
            return "fail";
        }
    }


    public void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Well Done! Record : "+record);

        final EditText et = new EditText(playActivity.this);
        et.setHint("Insert Your Name");
        builder.setView(et);

        builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNeutralButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                User user = new User(String.valueOf(et.getText()) , record);
                //databaseReference.child("users").push().setValue(user);
                Toast.makeText(getApplicationContext() , "Rank Completed.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext() , rankActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Try Again!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


}
