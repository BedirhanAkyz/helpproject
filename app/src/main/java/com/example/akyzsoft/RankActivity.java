package com.example.akyzsoft;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.akyuzsoft.R;

public class RankActivity extends AppCompatActivity {
    SQLiteDatabase database;
    MyDatabaseOpenHelper helper;
    int ranking_name[] = {R.id.name1, R.id.name2,R.id.name3, R.id.name4, R.id.name5};
    int ranking_time[] = {R.id.time1, R.id.time2,R.id.time3, R.id.time4, R.id.time5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        helper = MyDatabaseOpenHelper.getInstance();
        database = helper.getWritableDatabase();
        nameList();
    }

    private void nameList()
    {
        String sql = "select * from " + helper.tableName +" order by time asc";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null)
        {
            int count = cursor.getCount();
            count = (count<5)? count:5;
            for (int i = 0; i < count; i++)
            {
                cursor.moveToNext();
                String participant = cursor.getString(1);
                int time = cursor.getInt(2);
                Log.d("name", participant+", " + time);
                TextView nameView = (TextView)findViewById(ranking_name[i]);
                nameView.setText(participant);
                TextView timeView = (TextView)findViewById(ranking_time[i]);
                timeView.setText(time+"");
            }
            cursor.close();
        }
    }
}

