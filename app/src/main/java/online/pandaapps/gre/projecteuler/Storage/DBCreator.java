package online.pandaapps.gre.projecteuler.Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import online.pandaapps.gre.projecteuler.Utils.Constants;

/**
 * Created by sangeet on 09/09/16.
 */
public class DBCreator extends SQLiteOpenHelper {

    public DBCreator(Context context) {
        super(context, Constants.dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" create table if not exists " +Constants.tableDetails+
                " ("+Constants.col1ID+ " INTEGER PRIMARY KEY , "
                +Constants.col2Date+" STRING, "
                +Constants.col3Time+" STRING, "
                +Constants.col5Title+" VARCHAR, "
                +Constants.col6Problem+" VARCHAR) ");
        sqLiteDatabase.execSQL(" create table if not exists "+Constants.tableDifficulty+
                " ("+Constants.col1ID+ " INTEGER PRIMARY KEY , "
                +Constants.col4Difficulty+" TINYINT, "
                +Constants.col8SolvedBY+" INT ) ");
//        sqLiteDatabase.execSQL(" create table "+Constants.tableNameUserStorage+" ("+Constants.col1ID+ " INTEGER PRIMARY KEY , "+Constants.uCol2comment+" VARCHAR, "+Constants.uCol3remarks+" VARCHAR, "+Constants.uCol4random+" VARCHAR) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Constants.tableDetails);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+Constants.tableDifficulty);
        onCreate(sqLiteDatabase);
    }

    public boolean insertDetails(int id, String date,String time, String title, String Problem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.col1ID,id);
        contentValues.put(Constants.col2Date,date);
        contentValues.put(Constants.col3Time,time);
        contentValues.put(Constants.col5Title,title);
        contentValues.put(Constants.col6Problem,Problem);
        long result = db.insert(Constants.tableDetails,null,contentValues);

        if (result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean insertDifficulty(int id, int difficulty,int solvedBY){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.col1ID,id);
        contentValues.put(Constants.col4Difficulty,difficulty);
        contentValues.put(Constants.col8SolvedBY,solvedBY);
        long result = db.insert(Constants.tableDifficulty,null,contentValues);

        if (result == -1){
            return false;
        }else {
            return true;
        }

    }

    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT "+Constants.col1ID+" FROM "+Constants.tableDifficulty,null);
        return result.getCount();
    }

    public Cursor getByDifficulty(int difficulty){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT A.ID,B.title \n" +
                "FROM PROBLEM_SOLVED_BY A, PROBLEM_LIST B\n" +
                "WHERE A.ID=B.ID   \n" +
                "AND A.DIFFICULTY = "+difficulty;
        Cursor result = db.rawQuery(query,null);
        return result;
    }

    public Cursor getInRange(int start,int end){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT A.ID,B.title \n" +
                "FROM PROBLEM_SOLVED_BY A, PROBLEM_LIST B\n" +
                "WHERE A.ID=B.ID   \n" +
                "AND A.ID BETWEEN "+start+" AND "+end;
        Cursor result = db.rawQuery(query,null);
        return result;
    }

    public Cursor getIndividualProblem(int ProblemID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT *\n" +
                "FROM PROBLEM_SOLVED_BY A, PROBLEM_LIST B\n" +
                "WHERE A.ID=B.ID\n" +
                "and A.ID = "+ProblemID;
        Cursor result = db.rawQuery(query,null);
        return result;
    }




}
