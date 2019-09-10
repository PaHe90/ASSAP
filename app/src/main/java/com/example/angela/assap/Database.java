package com.example.angela.assap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {


    private static final String DB_NAME = "Requests.db";
    private static final String DB_TABLE = "All_Reports";

    //columns
    private static final String ID = "ID";
    private static final String Title = "Title";

    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE+"("+
            ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            Title +" TEXT "+ ")";

    public Database(Context context) {

        super(context, DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);

        onCreate(db);
    }


//create method to insert data
    public boolean addData(String title){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Title, title);

        long result = db.insert(DB_TABLE, null, contentValues);

        if(result==-1){

            return false;
        }
        else{

            return true;
        }

    }



    //create method to view data
    public Cursor viewData(){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+DB_TABLE;


        Cursor cursor = db.rawQuery(query,null);

        return cursor;

    }

}
