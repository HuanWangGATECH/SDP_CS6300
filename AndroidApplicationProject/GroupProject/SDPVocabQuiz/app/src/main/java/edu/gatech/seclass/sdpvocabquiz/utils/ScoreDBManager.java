package edu.gatech.seclass.sdpvocabquiz.utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Array;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import edu.gatech.seclass.sdpvocabquiz.data.Score;


public class ScoreDBManager extends SQLiteOpenHelper{

   public static final String DB_NAME = "SDPVocabScore";
   public static final String DB_TABLE_NAME = "Scores";
   public static final String DB_STUDENT_TABLE = "students";
   public static final String DB_QUIZ_TABLE = "quizTable";
   public static final String DB_UNIQUE_NAME = "quizName";
   public static final String DB_USER = "username";
   public static final String DB_PERCENTAGE = "percentage";
   public static final String DB_FIRST_SCORE = "firstScore";
   public static final String DB_HIGHEST_SCORE = "highestScore";
   public static final String DB_DATE = "timestamp";
   public static final String DB_DATE_FIRST_SCORE = "firstScoreDate";
   public static final String DB_DATE_HIGHEST_SCORE = "highestScoreDate";
   public static final String DB_TAKEN = "taken";

    public ScoreDBManager(Context context, String dbName)
    {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String create_sql = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_NAME + " (\n" +
                "       " + DB_UNIQUE_NAME + " varchar(200) NOT NULL, \n" +
                "       " + DB_USER + " varchar(50) NOT NULL, \n" +
                "       " + DB_PERCENTAGE + " double, \n" +
                "       " + DB_FIRST_SCORE + " double, \n" +
                "       " + DB_HIGHEST_SCORE + " double, \n" +
                "       " + DB_DATE + " varchar(100), \n" +
                "       " + DB_DATE_FIRST_SCORE + " varchar(100), \n" +
                "       " + DB_DATE_HIGHEST_SCORE + " varchar(100), \n" +
                "       " + DB_TAKEN + " INTEGER DEFAULT 0" +
              //  "       " + "PRIMARY KEY (" + DB_UNIQUE_NAME + ", " + DB_USER + ")" +
//                "       " + "FOREIGN KEY (" + DB_USER + ")" + " REFERENCES " + DB_STUDENT_TABLE + "(" + DB_USER + "), \n" +
//                "       " + "FOREIGN KEY (" + DB_UNIQUE_NAME + ")" + " REFERENCES " + DB_QUIZ_TABLE + "(" + DB_UNIQUE_NAME + ")" +
                ");";
        sqLiteDatabase.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop_sql = "DROP TABLE IF EXISTS " + DB_TABLE_NAME + ";";
        sqLiteDatabase.execSQL(drop_sql);
        onCreate(sqLiteDatabase);
    }


    public boolean isUserScoreExist(String userName, String quizName) {
        SQLiteDatabase db = getReadableDatabase();
        if (DatabaseUtils.queryNumEntries(db, DB_TABLE_NAME,
                DB_UNIQUE_NAME  +" = ?"
                        + " AND " + DB_USER + " = ? ",
                new String[] {quizName,userName}) > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean setQuizScore(String quizName, String userName, double score){
        ContentValues contentValues = new ContentValues();
        //Get current date string
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date date = new Date();
        String strDate = dateFormat.format(date);
        SQLiteDatabase db = getWritableDatabase();
        if(score > 100) {
            score = 100;
        }
        if(score < 0) {
            score = 0;
        }
        //Check user is in database
        if (!this.isUserScoreExist(userName,quizName)) {
            //Insert new score
            contentValues.put(DB_UNIQUE_NAME, quizName);
            contentValues.put(DB_USER, userName);
            contentValues.put(DB_PERCENTAGE, score);
            contentValues.put(DB_FIRST_SCORE, score);
            contentValues.put(DB_HIGHEST_SCORE, score);
            contentValues.put(DB_DATE,strDate);
            contentValues.put(DB_DATE_FIRST_SCORE,strDate);
            contentValues.put(DB_DATE_HIGHEST_SCORE,strDate);
            contentValues.put(DB_TAKEN, 1);
            try {
                return db.insert(DB_TABLE_NAME, null, contentValues) > 0;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        else {
            //Update existing highest score
            Score newScore = this.getQuizScore(userName,quizName);
            if(score > newScore.getHighestScore()) {
                contentValues.put(DB_HIGHEST_SCORE, score);
                contentValues.put(DB_DATE_HIGHEST_SCORE, strDate);
            }
            contentValues.put(DB_PERCENTAGE, score);
            contentValues.put(DB_DATE, strDate);
            try {
                return (db.update(DB_TABLE_NAME, contentValues,
                        DB_UNIQUE_NAME  +" = ?"
                                + " AND " + DB_USER + " = ? ",
                        new String[]{quizName, userName}) > 0);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public Score getQuizScore(String userName, String quizName) {

        Score score = new Score();

        // if no username and quiz match
        if(!isUserScoreExist(userName,quizName)){
            score.setUniqueName(quizName);
            score.setUsername(userName);
            score.setPercentage(0);
            score.setFirstScore(0);
            score.setHighestScore(0);
            score.setTaken(0);
//            try {
//                Date date = new Date();
//                score.setTimeStamp(date);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        else {
            // Score with percentage was found
            String query = "SELECT * FROM " + DB_TABLE_NAME + " " +
                    " WHERE " + DB_UNIQUE_NAME + "=?" +
                    " AND " + DB_USER + "=?";
            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.rawQuery(query, new String[] {quizName, userName});
            c.moveToFirst();
            score.setUniqueName(c.getString(c.getColumnIndex(DB_UNIQUE_NAME)));
            score.setUsername(c.getString(c.getColumnIndex(DB_USER)));
            String strScore = c.getString(c.getColumnIndex(DB_PERCENTAGE));
            if (strScore != null) {
                score.setPercentage(Float.valueOf(strScore));
            } else {
                score.setPercentage(0);
            }
            strScore = c.getString(c.getColumnIndex(DB_FIRST_SCORE));
            if (strScore != null) {
                score.setFirstScore(Float.valueOf(strScore));
            } else {
                score.setFirstScore(0);
            }
            strScore = c.getString(c.getColumnIndex(DB_HIGHEST_SCORE));
            if (strScore != null) {
                score.setHighestScore(Float.valueOf(strScore));
            } else {
                score.setHighestScore(0);
            }
            strScore = c.getString(c.getColumnIndex(DB_TAKEN));
            if(strScore != null){
                score.setTaken(0);
            }

            try {
                String strDate = c.getString(c.getColumnIndex(DB_DATE));
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                Date date = dateFormat.parse(strDate);
                score.setNewScoreDate(date);

                strDate = c.getString(c.getColumnIndex(DB_DATE_FIRST_SCORE));
                date = dateFormat.parse(strDate);
                score.setFirstScoreDate(date);

                strDate = c.getString(c.getColumnIndex(DB_DATE_HIGHEST_SCORE));
                date = dateFormat.parse(strDate);
                score.setHighestScoreDate(date);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return score;
    }

    public ArrayList<Score> getAllScoreInDb() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Score> userScoreList =  new ArrayList<>();
        try {
            Cursor c = db.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
            c.moveToFirst();
            while (c!= null) {
                Score score = new Score();
                score.setUniqueName(c.getString(c.getColumnIndex(DB_UNIQUE_NAME)));
                score.setUsername(c.getString(c.getColumnIndex(DB_USER)));
                score.setPercentage(Float.valueOf(c.getString(c.getColumnIndex(DB_PERCENTAGE))));
                score.setFirstScore(Float.valueOf(c.getString(c.getColumnIndex(DB_FIRST_SCORE))));
                score.setHighestScore(Float.valueOf(c.getString(c.getColumnIndex(DB_HIGHEST_SCORE))));
                try {
                    String strDate = c.getString(c.getColumnIndex(DB_DATE));
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                    Date date = dateFormat.parse(strDate);
                    score.setNewScoreDate(date);

                    strDate = c.getString(c.getColumnIndex(DB_DATE_FIRST_SCORE));
                    date = dateFormat.parse(strDate);
                    score.setFirstScoreDate(date);

                    strDate = c.getString(c.getColumnIndex(DB_DATE_HIGHEST_SCORE));
                    date = dateFormat.parse(strDate);
                    score.setHighestScoreDate(date);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                userScoreList.add(score);
                c.moveToNext();
            }
            c.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return userScoreList;

    }

    public boolean deleteQuizScore(String quizName) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(DB_TABLE_NAME, DB_UNIQUE_NAME + "=?",
                new String[]{quizName}) == 1;
    }

    public ArrayList<Score> getUserScore(String userName) {
        String query = "SELECT * FROM " + DB_TABLE_NAME + " WHERE " + DB_USER + " = ? "
                + " ORDER BY " + DB_DATE + " DESC ";
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Score> userScoreList =  new ArrayList<>();
        try {
            Cursor c = db.rawQuery(query, new String[] {userName});
            c.moveToFirst();
            while (c!= null) {
                Score score = new Score();
                score.setUniqueName(c.getString(c.getColumnIndex(DB_UNIQUE_NAME)));
                score.setUsername(c.getString(c.getColumnIndex(DB_USER)));
                score.setPercentage(Float.valueOf(c.getString(c.getColumnIndex(DB_PERCENTAGE))));
                score.setFirstScore(Float.valueOf(c.getString(c.getColumnIndex(DB_FIRST_SCORE))));
                score.setHighestScore(Float.valueOf(c.getString(c.getColumnIndex(DB_HIGHEST_SCORE))));

                try {
                    String strDate = c.getString(c.getColumnIndex(DB_DATE));
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                    Date date = dateFormat.parse(strDate);
                    score.setNewScoreDate(date);

                    strDate = c.getString(c.getColumnIndex(DB_DATE_FIRST_SCORE));
                    date = dateFormat.parse(strDate);
                    score.setFirstScoreDate(date);

                    strDate = c.getString(c.getColumnIndex(DB_DATE_HIGHEST_SCORE));
                    date = dateFormat.parse(strDate);
                    score.setHighestScoreDate(date);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                userScoreList.add(score);
                c.moveToNext();
            }
            c.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return userScoreList;
    }

    public ArrayList<String> getUserQuizListbyScoreTimeOrder(String userName) {
        String query = "SELECT * FROM " + DB_TABLE_NAME + " WHERE " + DB_USER + " = ? "
                + " ORDER BY " + DB_DATE + " DESC ";
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> userOrderedQuizList =  new ArrayList<>();
        try {
            Cursor c = db.rawQuery(query, new String[] {userName});
            c.moveToFirst();
            while (c!= null) {
                userOrderedQuizList.add(c.getString(c.getColumnIndex(DB_UNIQUE_NAME)));
                c.moveToNext();
            }
            c.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return userOrderedQuizList;
    }

    public void deleteTable() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DROP TABLE IF EXISTS " + DB_TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    public void test() {
        //Add 10 quiz score for 10 user
        for(int i=1; i<=10;i++) {
            String quizName = "Quiz"+String.valueOf(i);
            String quizName2 = "Quiz"+String.valueOf(i+10);
            String quizName3 = "Quiz"+String.valueOf(i+30);
            String userName = "User"+String.valueOf(i);
            String userName2 = "User"+String.valueOf(i+10);

            if(this.setQuizScore(quizName,userName, (float)(i* 10.1))){
                System.out.println("add success");
            }
            else {
                System.out.println("add fail");
            }

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            if(this.setQuizScore(quizName,userName2, (float)(i* 10.1))){
                System.out.println("add success");
            }
            else {
                System.out.println("add fail");
            }

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            if(this.setQuizScore(quizName2,userName, (float)(i* 10.1))){
                System.out.println("add success");
            }
            else {
                System.out.println("add fail");
            }

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            if(this.setQuizScore(quizName3,userName, (float)(i* 10.1))){
                System.out.println("add success");
            }
            else {
                System.out.println("add fail");
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArrayList<Score> scoreArrayList = this.getAllScoreInDb();

        scoreArrayList = new ArrayList<Score>();
        //Update Score
        for(int i=1; i<=10;i++) {
            String quizName = "Quiz"+String.valueOf(i);
            String userName = "User"+String.valueOf(i);
            if(this.setQuizScore(quizName,userName, (float)(100 - i* 10.1))){
                System.out.println("add success");
            }
            else {
                System.out.println("add fail");
            }
        }
        scoreArrayList = this.getAllScoreInDb();

        scoreArrayList = new ArrayList<Score>();
        //Get individual score
        for(int i=1; i<=10;i++) {
            String quizName = "Quiz"+String.valueOf(i);
            String userName = "User"+String.valueOf(i);
            scoreArrayList.add(getQuizScore(userName,quizName));
        }

        scoreArrayList = this.getUserScore("User1");
        ArrayList<String> userOrderedQuizList = this.getUserQuizListbyScoreTimeOrder("User1");
        this.deleteQuizScore("Quiz1");
        scoreArrayList = this.getAllScoreInDb();
        scoreArrayList = this.getUserScore("User1");
        System.out.println("end");
    }
}