package online.pandaapps.gre.projecteuler.Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import online.pandaapps.gre.projecteuler.Constants;

/**
 * Created by sangeet on 27/08/16.
 */
public class SQLITE3storage extends SQLiteOpenHelper {

    public SQLITE3storage(Context context) {
        super(context, Constants.dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" create table "+Constants.tableName+" ("+Constants.col1ID+ " INTEGER PRIMARY KEY , "+Constants.col2Date+" STRING, "+Constants.col3Time+" STRING, "+Constants.col4Difficulty+" INTEGER, "+Constants.col5Title+" VARCHAR, "+Constants.col6Problem+" VARCHAR, "+Constants.col7Image+" VARCHAR, "+Constants.col8SolvedBY+" INTEGER) ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+Constants.tableName);
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(int id, String date,String time,String problem, String title, int difficulty, int solved_by, String images){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.col1ID,id);
        contentValues.put(Constants.col2Date,date);
        contentValues.put(Constants.col3Time,time);
        contentValues.put(Constants.col4Difficulty,difficulty);
        contentValues.put(Constants.col5Title,title);
        contentValues.put(Constants.col6Problem,problem);
        contentValues.put(Constants.col7Image,images);
        contentValues.put(Constants.col8SolvedBY,solved_by);
        long result = db.insert(Constants.tableName,null,contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }

    }

    public Cursor getAllByDifficulty(int difficulty){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT "+Constants.col1ID+", "+Constants.col5Title+" FROM "+Constants.tableName+" WHERE "+Constants.col4Difficulty+" IS "+difficulty,null);
        return result;
    }

    public int getCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT "+Constants.col1ID+" FROM "+Constants.tableName,null);
        return result.getCount();
    }

}
