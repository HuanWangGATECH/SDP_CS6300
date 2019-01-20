//package edu.gatech.seclass.sdpvocabquiz.utils;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class DatabaseHelper extends SQLiteOpenHelper {
//
////    public static final String LOG_TAG = "DatabaseHelper";
////    public static final String DATABASE_NAME = "sdpvocabquizdb";
////    public static final int DATABASE_VERSION = 1;
////
////    // Table Names
////    public static final String TABLE_STUDENT = "students";
////    public static final String TABLE_QUIZ = "quizzes";
////    public static final String TABLE_SCORE = "scores";
////
////    public static final String CREATE_TABLE_STUDENT;
////    public static final String CREATE_TABLE_QUIZ;
////    public static final String CREATE_TABLE_SCORE;
////
////    // Common Column Names
////
////
////    //Student column names
////    public static final String DB_COL_USERNAME = "username";
////    public static final String DB_COL_EMAIL = "email";
////    public static final String DB_COL_FIRSTNAME = "firstname";
////    public static final String DB_COL_LASTNAME = "lastname";
////    public static final String DB_COL_MAJOR = "major";
////    public static final String DB_COL_SENIORITY = "seniority";
////
////    //Quiz column names
////    public static final String DB_COL_QUIZ_NAME= "quizName";
////    public static final String DB_COL_AUTHOR = "author";
////    public static final String DB_COL_DESCRIPTION = "description";
////    public static final String DB_COL_WORD_COUNT = "wordCourt";
////    public static final String DB_COL_1ST_100 = "firstHundred";
////    public static final String DB_COL_2ND_100 = "secondHundred";
////    public static final String DB_COL_3RD_100 = "thirdHundred";
////    public static final String DB_COL_WORD1 = "word1";
////    public static final String DB_COL_GOOD_DEF1 = "goodDef1";
////    public static final String DB_COL_1ST_BAD_DEF1 = "firstBadDef1";
////    public static final String DB_COL_2ND_BAD_DEF1 = "secondBadDef1";
////    public static final String DB_COL_3RD_BAD_DEF1 = "thirdBadDef1";
////    public static final String DB_COL_WORD2 = "word2";
////    public static final String DB_COL_GOOD_DEF2 = "goodDef2";
////    public static final String DB_COL_1ST_BAD_DEF2 = "firstBadDef2";
////    public static final String DB_COL_2ND_BAD_DEF2 = "secondBadDef2";
////    public static final String DB_COL_3RD_BAD_DEF2 = "thirdBadDef2";
////    public static final String DB_COL_WORD3 = "word3";
////    public static final String DB_COL_GOOD_DEF3 = "goodDef3";
////    public static final String DB_COL_1ST_BAD_DEF3 = "firstBadDef3";
////    public static final String DB_COL_2ND_BAD_DEF3 = "secondBadDef3";
////    public static final String DB_COL_3RD_BAD_DEF3 = "thirdBadDef3";
////    public static final String DB_COL_WORD4 = "word4";
////    public static final String DB_COL_GOOD_DEF4 = "goodDef4";
////    public static final String DB_COL_1ST_BAD_DEF4 = "firstBadDef4";
////    public static final String DB_COL_2ND_BAD_DEF4 = "secondBadDef4";
////    public static final String DB_COL_3RD_BAD_DEF4 = "thirdBadDef4";
////    public static final String DB_COL_WORD5 = "word5";
////    public static final String DB_COL_GOOD_DEF5 = "goodDef5";
////    public static final String DB_COL_1ST_BAD_DEF5 = "firstBadDef5";
////    public static final String DB_COL_2ND_BAD_DEF5 = "secondBadDef5";
////    public static final String DB_COL_3RD_BAD_DEF5 = "thirdBadDef5";
////    public static final String DB_COL_WORD6 = "word6";
////    public static final String DB_COL_GOOD_DEF6 = "goodDef6";
////    public static final String DB_COL_1ST_BAD_DEF6 = "firstBadDef6";
////    public static final String DB_COL_2ND_BAD_DEF6 = "secondBadDef6";
////    public static final String DB_COL_3RD_BAD_DEF6 = "thirdBadDef6";
////    public static final String DB_COL_WORD7 = "word7";
////    public static final String DB_COL_GOOD_DEF7 = "goodDef7";
////    public static final String DB_COL_1ST_BAD_DEF7 = "firstBadDef7";
////    public static final String DB_COL_2ND_BAD_DEF7 = "secondBadDef7";
////    public static final String DB_COL_3RD_BAD_DEF7 = "thirdBadDef7";
////    public static final String DB_COL_WORD8 = "word8";
////    public static final String DB_COL_GOOD_DEF8 = "goodDef8";
////    public static final String DB_COL_1ST_BAD_DEF8 = "firstBadDef8";
////    public static final String DB_COL_2ND_BAD_DEF8 = "secondBadDef8";
////    public static final String DB_COL_3RD_BAD_DEF8 = "thirdBadDef8";
////    public static final String DB_COL_WORD9 = "word9";
////    public static final String DB_COL_GOOD_DEF9 = "goodDef9";
////    public static final String DB_COL_1ST_BAD_DEF9 = "firstBadDef9";
////    public static final String DB_COL_2ND_BAD_DEF9 = "secondBadDef9";
////    public static final String DB_COL_3RD_BAD_DEF9 = "thirdBadDef9";
////    public static final String DB_COL_WORD10 = "word10";
////    public static final String DB_COL_GOOD_DEF10 = "goodDef10";
////    public static final String DB_COL_1ST_BAD_DEF10 = "firstBadDef10";
////    public static final String DB_COL_2ND_BAD_DEF10 = "secondBadDef10";
////    public static final String DB_COL_3RD_BAD_DEF10 = "thirdBadDef10";
////
////    // Score column names
////    private static final String DB_NAME = "SDPVocabScore";
////    private static final String DB_TABLE_NAME = "Scores";
////    private static final String DB_STUDENT_TABLE = "students";
////    private static final String DB_QUIZ_TABLE = "quizTable";
////    private static final String DB_UNIQUE_NAME = "quizName";
////    private static final String DB_USER = "username";
////    private static final String DB_PERCENTAGE = "percentage";
////    private static final String DB_FIRST_SCORE = "firstScore";
////    private static final String DB_HIGHEST_SCORE = "highestScore";
////    private static final String DB_DATE = "timestamp";
////    private static final String DB_TAKEN = "taken";
////
////    public DatabaseHelper(Context context) {
////        super(context, DATABASE_NAME, null, DATABASE_VERSION);
////    }
////
////    @Override
////    public void onCreate(SQLiteDatabase db) {
////
////        // creating required tables
////        db.execSQL(CREATE_TABLE_STUDENT);
////        db.execSQL(CREATE_TABLE_QUIZ);
////        db.execSQL(CREATE_TABLE_SCORE);
////    }
//}
