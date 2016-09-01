package online.pandaapps.gre.projecteuler.Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import online.pandaapps.gre.projecteuler.Utils.Constants;

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
        sqLiteDatabase.execSQL(" create table "+Constants.tableNameUserStorage+" ("+Constants.col1ID+ " INTEGER PRIMARY KEY , "+Constants.uCol2comment+" VARCHAR, "+Constants.uCol3remarks+" VARCHAR, "+Constants.uCol4random+" VARCHAR) ");
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
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT "+Constants.col1ID+", "+Constants.col5Title+" FROM "+Constants.tableName+" WHERE "+Constants.col4Difficulty+" IS "+difficulty,null);
        return result;
    }

    public Cursor getIndividualProblem(int ProblemID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+Constants.tableName+" WHERE "+Constants.col1ID+" IS "+ProblemID,null);
        return result;
    }

    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT "+Constants.col1ID+" FROM "+Constants.tableName,null);
        return result.getCount();
    }

    public boolean setComment(int id, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.uCol1ID, id);
        cv.put(Constants.uCol2comment, comment);
        Cursor present = getComment(id);
        long res = 0;
        if (present.getCount() == 0) {
            res = db.insert(Constants.tableNameUserStorage, null, cv);
        } else {
            res = db.update(Constants.tableNameUserStorage, cv, Constants.uCol1ID+" = "+id,null);

        }
        if (res == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getComment(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+Constants.tableNameUserStorage+" WHERE "+Constants.col1ID+" IS "+id,null);
        return result;
    }

    public Cursor getInRange(int start,int end){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT "
                +Constants.col1ID+", "+Constants.col5Title
                +" FROM "+Constants.tableName+" WHERE "
                +Constants.col1ID+" BETWEEN "+start+" AND "+end,null);
        return result;
    }


}
