package com.example.akyzsoft;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseOpenHelper extends SQLiteOpenHelper {
    static MyDatabaseOpenHelper instance;
    public static final String tableName = "ranking";

    public MyDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        if(instance == null) {
            instance = this;
        }
    }

    public static MyDatabaseOpenHelper getInstance(){
        if(instance == null) {
            return null;
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void createTable(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE " + tableName + "(`_id` INTEGER PRIMARY KEY AUTOINCREMENT, `name`	TEXT, `time`	INTEGER)";
        String sql_index = "CREATE INDEX timeIndex 2 ON " + tableName + "(`time`)";
        try
        {
            db.execSQL(sql);
        }
        catch (SQLException e)
        {
        }
    }

    public void insertName(SQLiteDatabase db, String name, long time)
    {
        db.beginTransaction();
        try
        {
            String sql = "insert into " + tableName + "(name, time)" + " values('" + name +"', " + time + ")";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.endTransaction();
        }
    }

    public void deleteRank(SQLiteDatabase db){
        db.beginTransaction();
        try
        {
            String sql = "select * from "+tableName;
            String sql_delete;
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor != null) {
                int count = cursor.getCount();
                Log.d("count : ", count+"");
                if(count > 5){
                    sql_delete = "delete from "+tableName + " where _id = (select max(_id) from "+tableName+" where time = (select max(time) from "+tableName+"))";
                    db.execSQL(sql_delete);
                }
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.endTransaction();
        }
    }

}
