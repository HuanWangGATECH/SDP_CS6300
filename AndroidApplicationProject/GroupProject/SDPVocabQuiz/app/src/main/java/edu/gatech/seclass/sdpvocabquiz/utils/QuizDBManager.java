package edu.gatech.seclass.sdpvocabquiz.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.gatech.seclass.sdpvocabquiz.QuizSelection;
import edu.gatech.seclass.sdpvocabquiz.data.IncorrectDefinition;
import edu.gatech.seclass.sdpvocabquiz.data.Quiz;
import edu.gatech.seclass.sdpvocabquiz.data.WordPair;

public class QuizDBManager extends SQLiteOpenHelper {

    public static final String DB_NAME = "SDPVocabQuiz";
    public static final String DB_TABLE_NAME = "quizTable";
    public static final String DB_COL_QUIZ_NAME= "quizName";
    public static final String DB_COL_AUTHOR = "author";
    public static final String DB_COL_DESCRIPTION = "description";
    public static final String DB_COL_WORD_COUNT = "wordCourt";
    public static final String DB_COL_1ST_100 = "firstHundred";
    public static final String DB_COL_2ND_100 = "secondHundred";
    public static final String DB_COL_3RD_100 = "thirdHundred";
    public static final String DB_COL_WORD1 = "word1";
    public static final String DB_COL_GOOD_DEF1 = "goodDef1";
    public static final String DB_COL_1ST_BAD_DEF1 = "firstBadDef1";
    public static final String DB_COL_2ND_BAD_DEF1 = "secondBadDef1";
    public static final String DB_COL_3RD_BAD_DEF1 = "thirdBadDef1";
    public static final String DB_COL_WORD2 = "word2";
    public static final String DB_COL_GOOD_DEF2 = "goodDef2";
    public static final String DB_COL_1ST_BAD_DEF2 = "firstBadDef2";
    public static final String DB_COL_2ND_BAD_DEF2 = "secondBadDef2";
    public static final String DB_COL_3RD_BAD_DEF2 = "thirdBadDef2";
    public static final String DB_COL_WORD3 = "word3";
    public static final String DB_COL_GOOD_DEF3 = "goodDef3";
    public static final String DB_COL_1ST_BAD_DEF3 = "firstBadDef3";
    public static final String DB_COL_2ND_BAD_DEF3 = "secondBadDef3";
    public static final String DB_COL_3RD_BAD_DEF3 = "thirdBadDef3";
    public static final String DB_COL_WORD4 = "word4";
    public static final String DB_COL_GOOD_DEF4 = "goodDef4";
    public static final String DB_COL_1ST_BAD_DEF4 = "firstBadDef4";
    public static final String DB_COL_2ND_BAD_DEF4 = "secondBadDef4";
    public static final String DB_COL_3RD_BAD_DEF4 = "thirdBadDef4";
    public static final String DB_COL_WORD5 = "word5";
    public static final String DB_COL_GOOD_DEF5 = "goodDef5";
    public static final String DB_COL_1ST_BAD_DEF5 = "firstBadDef5";
    public static final String DB_COL_2ND_BAD_DEF5 = "secondBadDef5";
    public static final String DB_COL_3RD_BAD_DEF5 = "thirdBadDef5";
    public static final String DB_COL_WORD6 = "word6";
    public static final String DB_COL_GOOD_DEF6 = "goodDef6";
    public static final String DB_COL_1ST_BAD_DEF6 = "firstBadDef6";
    public static final String DB_COL_2ND_BAD_DEF6 = "secondBadDef6";
    public static final String DB_COL_3RD_BAD_DEF6 = "thirdBadDef6";
    public static final String DB_COL_WORD7 = "word7";
    public static final String DB_COL_GOOD_DEF7 = "goodDef7";
    public static final String DB_COL_1ST_BAD_DEF7 = "firstBadDef7";
    public static final String DB_COL_2ND_BAD_DEF7 = "secondBadDef7";
    public static final String DB_COL_3RD_BAD_DEF7 = "thirdBadDef7";
    public static final String DB_COL_WORD8 = "word8";
    public static final String DB_COL_GOOD_DEF8 = "goodDef8";
    public static final String DB_COL_1ST_BAD_DEF8 = "firstBadDef8";
    public static final String DB_COL_2ND_BAD_DEF8 = "secondBadDef8";
    public static final String DB_COL_3RD_BAD_DEF8 = "thirdBadDef8";
    public static final String DB_COL_WORD9 = "word9";
    public static final String DB_COL_GOOD_DEF9 = "goodDef9";
    public static final String DB_COL_1ST_BAD_DEF9 = "firstBadDef9";
    public static final String DB_COL_2ND_BAD_DEF9 = "secondBadDef9";
    public static final String DB_COL_3RD_BAD_DEF9 = "thirdBadDef9";
    public static final String DB_COL_WORD10 = "word10";
    public static final String DB_COL_GOOD_DEF10 = "goodDef10";
    public static final String DB_COL_1ST_BAD_DEF10 = "firstBadDef10";
    public static final String DB_COL_2ND_BAD_DEF10 = "secondBadDef10";
    public static final String DB_COL_3RD_BAD_DEF10 = "thirdBadDef10";

    public QuizDBManager(Context context, String dbName)  {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_NAME + " (\n" +
                "    " + DB_COL_QUIZ_NAME + " varchar(200) NOT NULL PRIMARY KEY UNIQUE,\n" +
                "    " + DB_COL_AUTHOR + " varchar(50),\n" +
                "    " + DB_COL_DESCRIPTION + " varchar(200),\n" +
                "    " + DB_COL_WORD_COUNT + " integer,\n" +
                "    " + DB_COL_1ST_100 + " varchar(50),\n" +
                "    " + DB_COL_2ND_100 + " varchar(50),\n" +
                "    " + DB_COL_3RD_100 + " varchar(50),\n" +
                "    " + DB_COL_WORD1 + " varchar(50),\n" +
                "    " + DB_COL_GOOD_DEF1 + " varchar(100),\n" +
                "    " + DB_COL_1ST_BAD_DEF1 + " varchar(100),\n" +
                "    " + DB_COL_2ND_BAD_DEF1 + " varchar(100),\n" +
                "    " + DB_COL_3RD_BAD_DEF1 + " varchar(100),\n" +
                "    " + DB_COL_WORD2 + " varchar(50),\n" +
                "    " + DB_COL_GOOD_DEF2 + " varchar(100),\n" +
                "    " + DB_COL_1ST_BAD_DEF2 + " varchar(100),\n" +
                "    " + DB_COL_2ND_BAD_DEF2 + " varchar(100),\n" +
                "    " + DB_COL_3RD_BAD_DEF2 + " varchar(100),\n" +
                "    " + DB_COL_WORD3 + " varchar(50),\n" +
                "    " + DB_COL_GOOD_DEF3 + " varchar(100),\n" +
                "    " + DB_COL_1ST_BAD_DEF3 + " varchar(100),\n" +
                "    " + DB_COL_2ND_BAD_DEF3 + " varchar(100),\n" +
                "    " + DB_COL_3RD_BAD_DEF3 + " varchar(100),\n" +
                "    " + DB_COL_WORD4 + " varchar(50),\n" +
                "    " + DB_COL_GOOD_DEF4 + " varchar(100),\n" +
                "    " + DB_COL_1ST_BAD_DEF4 + " varchar(100),\n" +
                "    " + DB_COL_2ND_BAD_DEF4 + " varchar(100),\n" +
                "    " + DB_COL_3RD_BAD_DEF4 + " varchar(100),\n" +
                "    " + DB_COL_WORD5 + " varchar(50),\n" +
                "    " + DB_COL_GOOD_DEF5 + " varchar(100),\n" +
                "    " + DB_COL_1ST_BAD_DEF5 + " varchar(100),\n" +
                "    " + DB_COL_2ND_BAD_DEF5 + " varchar(100),\n" +
                "    " + DB_COL_3RD_BAD_DEF5 + " varchar(100),\n" +
                "    " + DB_COL_WORD6 + " varchar(50),\n" +
                "    " + DB_COL_GOOD_DEF6 + " varchar(100),\n" +
                "    " + DB_COL_1ST_BAD_DEF6 + " varchar(100),\n" +
                "    " + DB_COL_2ND_BAD_DEF6 + " varchar(100),\n" +
                "    " + DB_COL_3RD_BAD_DEF6 + " varchar(100),\n" +
                "    " + DB_COL_WORD7 + " varchar(50),\n" +
                "    " + DB_COL_GOOD_DEF7 + " varchar(100),\n" +
                "    " + DB_COL_1ST_BAD_DEF7 + " varchar(100),\n" +
                "    " + DB_COL_2ND_BAD_DEF7 + " varchar(100),\n" +
                "    " + DB_COL_3RD_BAD_DEF7 + " varchar(100),\n" +
                "    " + DB_COL_WORD8 + " varchar(50),\n" +
                "    " + DB_COL_GOOD_DEF8 + " varchar(100),\n" +
                "    " + DB_COL_1ST_BAD_DEF8 + " varchar(100),\n" +
                "    " + DB_COL_2ND_BAD_DEF8 + " varchar(100),\n" +
                "    " + DB_COL_3RD_BAD_DEF8 + " varchar(100),\n" +
                "    " + DB_COL_WORD9 + " varchar(50),\n" +
                "    " + DB_COL_GOOD_DEF9 + " varchar(100),\n" +
                "    " + DB_COL_1ST_BAD_DEF9 + " varchar(100),\n" +
                "    " + DB_COL_2ND_BAD_DEF9 + " varchar(100),\n" +
                "    " + DB_COL_3RD_BAD_DEF9 + " varchar(100),\n" +
                "    " + DB_COL_WORD10 + " varchar(50),\n" +
                "    " + DB_COL_GOOD_DEF10 + " varchar(100),\n" +
                "    " + DB_COL_1ST_BAD_DEF10 + " varchar(100),\n" +
                "    " + DB_COL_2ND_BAD_DEF10 + " varchar(100),\n" +
                "    " + DB_COL_3RD_BAD_DEF10 + " varchar(100)\n" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + DB_TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    /*
        @return true if title is added or updated
     */
    public boolean addQuizTitle(String userName, String quizName, String description, int wordCount) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        String nullString = null;
        contentValues.put(DB_COL_QUIZ_NAME, quizName);
        contentValues.put(DB_COL_AUTHOR, userName);
        contentValues.put(DB_COL_DESCRIPTION, description);
        contentValues.put(DB_COL_WORD_COUNT, wordCount);
        contentValues.put(DB_COL_1ST_100, nullString);
        contentValues.put(DB_COL_2ND_100, nullString);
        contentValues.put(DB_COL_3RD_100, nullString);

        if(DatabaseUtils.queryNumEntries(db, DB_TABLE_NAME, "quizName = ?",
                new String[] {quizName} ) > 0){ //Quiz name already,
            if(this.isAuthor(userName, quizName)){
                //Current user is author, update content
                return (db.update(DB_TABLE_NAME, contentValues,DB_COL_QUIZ_NAME + "=?",
                        new String[]{quizName}) > 0);
            }
            else {
                return false;    //reject add title
            }
        }
        else {  //Quiz doesn't exit, insert a new one in database
            db = getWritableDatabase();
            return (db.insert(DB_TABLE_NAME, null, contentValues) > 0);
        }
    }

    public boolean addQuizContent(String quizName, int wordNumber, String word, String goodDef,
                                  String badDef1, String badDef2, String badDef3){
        if(wordNumber <1 || wordNumber > 10){
            return false; //invalid number
        }
        String db_col_word = "word" + String.valueOf(wordNumber);
        String db_col_good_def = "goodDef" + String.valueOf(wordNumber);
        String db_col_bad_def1 = "firstBadDef" + String.valueOf(wordNumber);
        String db_col_bad_def2 = "secondBadDef" + String.valueOf(wordNumber);
        String db_col_bad_def3 = "thirdBadDef" + String.valueOf(wordNumber);

//        String query = "UPDATE "+ DB_TABLE_NAME + " SET "
//                        + db_col_word + "=" + word + ","
//                        + db_col_good_def + "=" + goodDef + " "
//                        + db_col_bad_def1 + "=" + badDef1 + " "
//                        + db_col_bad_def2 + "=" + badDef2 + " "
//                        + " WHERE " + DB_COL_QUIZ_NAME + "=" + quizName;

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(db_col_word.trim(), word);
        contentValues.put(db_col_good_def, goodDef);
        contentValues.put(db_col_bad_def1, badDef1);
        contentValues.put(db_col_bad_def2, badDef2);
        contentValues.put(db_col_bad_def3, badDef3);
        try {
            db.update(DB_TABLE_NAME, contentValues,DB_COL_QUIZ_NAME + "=?",
                    new String[]{quizName});
            return true; //content added.
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Cursor getQuizContent(String quizName) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + DB_TABLE_NAME + " WHERE " + DB_COL_QUIZ_NAME + "=?";
        return db.rawQuery(query, new String[] {quizName});
    }

    public boolean createQuiz(Quiz q){
        return createQuiz(q.getUniqueName(), q.getAuthor(), q.getDescription(), q.getWordPairList(), q.getIncorrectDefList());
    }

    public boolean createQuiz (String quizName, String userName, String description,
               List<WordPair> wordPairsList, List<IncorrectDefinition> badDefList){
        try {
            if(this.addQuizTitle(userName,quizName,description,wordPairsList.size())){
                //add Quiz Tile success, add quiz content.
                for (int i=0; i<wordPairsList.size();i++){
                    String word = wordPairsList.get(i).getWord();
                    String goodDef = wordPairsList.get(i).getCorrectDefinition();
                    int badDefIndex = i * 3;
                    String badDef1 = badDefList.get(badDefIndex).getIncorrectDefinition();
                    String badDef2 = badDefList.get(badDefIndex+1).getIncorrectDefinition();
                    String badDef3 = badDefList.get(badDefIndex+2).getIncorrectDefinition();

                    if(!this.addQuizContent(quizName,i+1,word,goodDef,badDef1,badDef2,
                            badDef3)){
                        return false;   //error
                    }
                }
                return true;    //Create quiz complete
            }
            else {
                return false; //error
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getQuizAuthor(String quizName) {
        try {
            Cursor c = this.getQuizContent(quizName);
            c.moveToFirst();
            String author = c.getString(c.getColumnIndex(DB_COL_AUTHOR));
            c.close();
            return author;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getQuizDescription(String quizName) {
        try {
            Cursor c = this.getQuizContent(quizName);
            c.moveToFirst();
            String description = c.getString(c.getColumnIndex(DB_COL_DESCRIPTION));
            c.close();
            return description;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getUserCreatedQuizList(String userName){
        String query = "SELECT " +
                DB_COL_QUIZ_NAME +
                " FROM " + DB_TABLE_NAME +
                " WHERE "+ DB_COL_AUTHOR + "=?";
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> createdQuizList = new ArrayList<>();
        try {
            Cursor c = db.rawQuery(query, new String[] {userName});
            c.moveToFirst();
            while (c!= null) {
                createdQuizList.add(c.getString(c.getColumnIndex(DB_COL_QUIZ_NAME)));
                c.moveToNext();
            }
            c.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return createdQuizList;
    }

    public ArrayList<String> getNonUserCreatedQuizList(String userName){
        String query = "SELECT " +
                DB_COL_QUIZ_NAME +
                " FROM " + DB_TABLE_NAME +
                " WHERE "+ DB_COL_AUTHOR + "!=?";
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> nonUserCreatedQuizList = new ArrayList<>();
        try {
            Cursor c = db.rawQuery(query, new String[] {userName});
            c.moveToFirst();
            while (c!= null) {
                nonUserCreatedQuizList.add(c.getString(c.getColumnIndex(DB_COL_QUIZ_NAME)));
                c.moveToNext();
            }
            c.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return nonUserCreatedQuizList;
    }

    public ArrayList<String> getFullScoreStudent(String quizName) {
        String query = "SELECT " +
                DB_COL_1ST_100 + ","+ DB_COL_2ND_100 + "," +  DB_COL_3RD_100 +
                " FROM " + DB_TABLE_NAME +
                " WHERE "+ DB_COL_QUIZ_NAME + "=?";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, new String[] {quizName});
        c.moveToFirst();
        //int columnCount = c.getColumnCount();
        ArrayList<String> fullScoreList = new ArrayList<>();
        for(int i=0;i<3; i++) {
            try {
                if(c.getString(i) != null) {
                    fullScoreList.add(c.getString(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        c.close();

        //sort alphabetically
        Collections.sort(fullScoreList);

        return fullScoreList;
    }

    // check if a username exist in a list
    public boolean isUsernameAlreadyInList(ArrayList<String> list, String userName){
        // if username exist in the list, return true otherwise return false
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).equals(userName)){
                return true;
            }else{
                continue;
            }
        }
        // repeat until we reach end
        return false;
    }

    // If user scores perfectly, add them to the list (called in practice quiz score method)
    public boolean setFullScoreName(String quizName, String userName){
        ArrayList<String> fullScoreNameList = this.getFullScoreStudent(quizName);
        // used to make sure username can only exit in list once
        boolean userNameInList = isUsernameAlreadyInList(fullScoreNameList, userName);

        if(fullScoreNameList.size()<3 && !userNameInList) {
            fullScoreNameList.add(userName);
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            if(fullScoreNameList.size() == 1) {
                contentValues.put(DB_COL_1ST_100, fullScoreNameList.get(0));
            }
            if(fullScoreNameList.size() == 2) {
                contentValues.put(DB_COL_2ND_100, fullScoreNameList.get(1));
            }
            if(fullScoreNameList.size() == 3) {
                contentValues.put(DB_COL_3RD_100, fullScoreNameList.get(2));
            }
            try {
                db.update(DB_TABLE_NAME, contentValues,DB_COL_QUIZ_NAME + "=?",
                        new String[]{quizName});
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<String> getQuizWord(String quizName){
        String query = "SELECT " +
                DB_COL_WORD1 + ","+ DB_COL_WORD2 + "," +
                DB_COL_WORD3 + ","+ DB_COL_WORD4 + "," +
                DB_COL_WORD5 + ","+ DB_COL_WORD6 + "," +
                DB_COL_WORD7 + ","+ DB_COL_WORD8 + "," +
                DB_COL_WORD9 + ","+ DB_COL_WORD10 +
                " FROM " + DB_TABLE_NAME +
                " WHERE "+ DB_COL_QUIZ_NAME + "=?";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, new String[] {quizName});
        c.moveToFirst();
        int columnCount = c.getColumnCount();
        ArrayList<String> wordList = new ArrayList<>();
        for(int i=0;i<columnCount; i++) {
            try {
                if(c.getString(i) != null) {
                    wordList.add(c.getString(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        c.close();
        return wordList;
    }

    public ArrayList<String> getQuizGoodDef(String quizName){
        String query = "SELECT " +
                        DB_COL_GOOD_DEF1 + ","+ DB_COL_GOOD_DEF2 + "," +
                        DB_COL_GOOD_DEF3 + ","+ DB_COL_GOOD_DEF4 + "," +
                        DB_COL_GOOD_DEF5 + ","+ DB_COL_GOOD_DEF6 + "," +
                        DB_COL_GOOD_DEF7 + ","+ DB_COL_GOOD_DEF8 + "," +
                        DB_COL_GOOD_DEF9 + ","+ DB_COL_GOOD_DEF10 +
                        " FROM " + DB_TABLE_NAME +
                        " WHERE "+ DB_COL_QUIZ_NAME + "=?";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, new String[] {quizName});
        c.moveToFirst();
        int columnCount = c.getColumnCount();
        ArrayList<String> goodDefList = new ArrayList<>();
        for(int i=0;i<columnCount; i++) {
            try {
                if(c.getString(i) != null) {
                    goodDefList.add(c.getString(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        c.close();
        return goodDefList;
    }

    public List<IncorrectDefinition> getQuizBadDef(String quizName){
        String query = "SELECT " +
                DB_COL_1ST_BAD_DEF1 + ","+ DB_COL_1ST_BAD_DEF2  + "," +
                DB_COL_1ST_BAD_DEF3 + ","+ DB_COL_1ST_BAD_DEF4  + "," +
                DB_COL_1ST_BAD_DEF5 + ","+ DB_COL_1ST_BAD_DEF6  + "," +
                DB_COL_1ST_BAD_DEF7 + ","+ DB_COL_1ST_BAD_DEF8  + "," +
                DB_COL_1ST_BAD_DEF9 + ","+ DB_COL_1ST_BAD_DEF10 + "," +
                DB_COL_2ND_BAD_DEF1 + ","+ DB_COL_2ND_BAD_DEF2  + "," +
                DB_COL_2ND_BAD_DEF3 + ","+ DB_COL_2ND_BAD_DEF4  + "," +
                DB_COL_2ND_BAD_DEF5 + ","+ DB_COL_2ND_BAD_DEF6  + "," +
                DB_COL_2ND_BAD_DEF7 + ","+ DB_COL_2ND_BAD_DEF8  + "," +
                DB_COL_2ND_BAD_DEF9 + ","+ DB_COL_2ND_BAD_DEF10 + "," +
                DB_COL_3RD_BAD_DEF1 + ","+ DB_COL_3RD_BAD_DEF2  + "," +
                DB_COL_3RD_BAD_DEF3 + ","+ DB_COL_3RD_BAD_DEF4  + "," +
                DB_COL_3RD_BAD_DEF5 + ","+ DB_COL_3RD_BAD_DEF6  + "," +
                DB_COL_3RD_BAD_DEF7 + ","+ DB_COL_3RD_BAD_DEF8  + "," +
                DB_COL_3RD_BAD_DEF9 + ","+ DB_COL_3RD_BAD_DEF10 +
                " FROM " + DB_TABLE_NAME +
                " WHERE "+ DB_COL_QUIZ_NAME + "=?";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, new String[] {quizName});
        c.moveToFirst();
        int columnCount = c.getColumnCount();
        List<IncorrectDefinition> badDefList = new ArrayList<>();
        for(int i=0;i<columnCount; i++) {
            try {
                if(c.getString(i) != null) {
                    IncorrectDefinition incorrectDefinition = new IncorrectDefinition(c.getString(i));
                    badDefList.add(incorrectDefinition);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        c.close();
        return badDefList;
    }

    /*
        @return String[] = "Word", "Word's Good definition
     */
    public ArrayList<WordPair> getQuizWordPair(String quizName){
        ArrayList<String> wordList = this.getQuizWord(quizName);
        ArrayList<String> goodDefList = this.getQuizGoodDef(quizName);
        ArrayList<WordPair> wordPairsList = new ArrayList<WordPair>();

        if(wordList.size() != goodDefList.size()) {
            return null; //invalid pair
        }
        for(int i=0;i<wordList.size();i++){
            if(wordList.get(i) != null && goodDefList.get(i) != null) {
                WordPair wordPair = new WordPair();
                wordPair.setWord( wordList.get(i));
                wordPair.setCorrectDefinition(goodDefList.get(i));
                wordPairsList.add(wordPair);
            }
        }
        return wordPairsList;
    }

    // Get the Quiz object by name reference
    public Quiz getQuiz(String quizName){
            SQLiteDatabase db = getWritableDatabase();
            Quiz q = new Quiz();
            q.setUniqueName(quizName);
            q.setAuthor(this.getQuizAuthor(quizName));
            q.setDescription(this.getQuizDescription(quizName));
            q.setWordPairList(this.getQuizWordPair(quizName));
            q.setIncorrectDefList(this.getQuizBadDef(quizName));
            q.setFullScoreStudent(this.getFullScoreStudent(quizName));
            return q;
    }

    public ArrayList<Quiz> getAllQuiz() {
        ArrayList<Quiz> quizList = new ArrayList<>();
        ArrayList<String> quizNameList = this.getAllQuizName();
        for (int i=0; i<quizNameList.size();i++) {
            Quiz quiz = new Quiz();
            String quizName = quizNameList.get(i);
            quiz.setUniqueName(quizName);
            quiz.setAuthor(this.getQuizAuthor(quizName));
            quiz.setDescription(this.getQuizDescription(quizName));
            quiz.setWordPairList(this.getQuizWordPair(quizName));
            quiz.setIncorrectDefList(this.getQuizBadDef(quizName));
            quiz.setFullScoreStudent(this.getFullScoreStudent(quizName));
            quizList.add(quiz);
        }
        return quizList;
    }

    public List<Quiz> getSatisticQuizList(String userName, ArrayList<String> userTestedQuizList_Sorted){
        ArrayList<String> userCreatedQuizList = this.getUserCreatedQuizList(userName);
        ArrayList<String> nonUserCreatedQuizList = this.getNonUserCreatedQuizList(userName);

        //Set user created list at the top
        List<String> orderedQuizList = new ArrayList<>();

        //user have never done any test
        if(userTestedQuizList_Sorted.size() == 0) {
            //Add the rest to the list
            orderedQuizList.addAll(nonUserCreatedQuizList);
        }
        else {  //user have done some test
            orderedQuizList.addAll(userTestedQuizList_Sorted);

            //filter out user test quiz from non user created quiz list
            for (int i=0;i< userTestedQuizList_Sorted.size();i++) {
                if(nonUserCreatedQuizList.contains(userTestedQuizList_Sorted.get(i))){
                    nonUserCreatedQuizList.remove(userTestedQuizList_Sorted.get(i));
                }
            }

            //add the rest to the ordered list
            orderedQuizList.addAll(nonUserCreatedQuizList);
        }

        if(userCreatedQuizList.size()>0) {
            orderedQuizList.addAll(userCreatedQuizList);
        }

        //Generate sorted quiz object
        ArrayList<Quiz> quizList = new ArrayList<>();
        for (int i=0; i<orderedQuizList.size();i++) {
            Quiz quiz = new Quiz();
            String quizName = orderedQuizList.get(i);
            quiz.setUniqueName(quizName);
            quiz.setAuthor(this.getQuizAuthor(quizName));
            quiz.setDescription(this.getQuizDescription(quizName));
            quiz.setWordPairList(this.getQuizWordPair(quizName));
            quiz.setIncorrectDefList(this.getQuizBadDef(quizName));
            quiz.setFullScoreStudent(this.getFullScoreStudent(quizName));
            quizList.add(quiz);
        }
        return quizList;
    }



    public Cursor getAllQuizCursor() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
    }

    public ArrayList<String> getAllQuizName(){
        ArrayList<String> allQuizName = new ArrayList<>();
        try {
            Cursor c = getAllQuizCursor();
            c.moveToFirst();
            while (c!= null) {
                allQuizName.add(c.getString( c.getColumnIndex(DB_COL_QUIZ_NAME)));
                c.moveToNext();
            }
            c.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return allQuizName;
    }

    public boolean isQuizExist(String uniqueName) {
        SQLiteDatabase db = getReadableDatabase();
        return (DatabaseUtils.queryNumEntries(db, DB_TABLE_NAME, DB_COL_QUIZ_NAME +"=?",
                new String[] {uniqueName}) > 0);
    }


    public boolean isAuthor(String username, String quizName) {
        try {
            String author = this.getQuizAuthor(quizName);
            return author.equals(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteQuiz(String username, String quizName) {
        if(isAuthor(username,quizName)){
            SQLiteDatabase db = getWritableDatabase();
            return db.delete(DB_TABLE_NAME, DB_COL_QUIZ_NAME + "=?",
                    new String[]{quizName}) == 1;
        }
        return false;
    }

    // Delete quiz used for testing
    public void deleteQuiz(Quiz q) {
        deleteQuiz(q.getAuthor(), q.getUniqueName());
    }

    public void deleteTable() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DROP TABLE IF EXISTS " + DB_TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    //For debug
    public void showSingleQuizLog(String quizName) {
        Cursor c = this.getQuizContent(quizName);
        try {
            c.moveToFirst();
            while (c != null){
                int columnCount = c.getColumnCount();
                for (int i=0;i<columnCount;i++){
                    showLogColumn(c, i);
                }
                c.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.close();
    }

    //For debug
    public void showAllQuizLog(){
        Cursor c = this.getAllQuizCursor();
        try {
            c.moveToFirst();
            while (c != null){
                int columnCount = c.getColumnCount();
                for (int i=0;i<columnCount;i++){
                    showLogColumn(c, i);
                }
                c.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.close();
    }

    //For debug
    public void showLogColumn(Cursor c, int index) {
        if(c.getString(index) != null) {
            String columnName = c.getColumnName(index);
            Log.i(columnName, c.getString(index));
        }
    }

    //For debug
    public void test(){
        String quizName = "Quiz";
        String author = "Quiz Author";
        String description = "Quiz Description";
        int wordCount = 10;

        //Construct 5 quiz
        for (int i=1; i<=5 ;i++) {
            String quizIndex = String.valueOf(i);

            this.addQuizTitle(author + quizIndex,
                    quizName + quizIndex,
                    description + quizIndex, wordCount);

            List<WordPair> wordPairList = new ArrayList<WordPair>();
            List<IncorrectDefinition> badDefList = new ArrayList<>();
            for(int j=1;j<=wordCount;j++){
                String wordIndex = String.valueOf(j);
                WordPair wordPair = new WordPair();
                wordPair.setWord ("quiz"+ quizIndex +" word " + wordIndex);
                wordPair.setCorrectDefinition("quiz"+ quizIndex +" good Def of word" + wordIndex);
                wordPairList.add(wordPair);

                IncorrectDefinition badDefOne = new IncorrectDefinition("quiz"+ quizIndex +" 1st bad Def of word " + wordIndex);
                IncorrectDefinition badDefTwo = new IncorrectDefinition("quiz"+ quizIndex +" 2nd bad Def of word " + wordIndex);
                IncorrectDefinition badDefThree = new IncorrectDefinition("quiz"+ quizIndex +" 3rd bad Def of word " + wordIndex);
                badDefList.add(badDefOne);
                badDefList.add(badDefTwo);
                badDefList.add(badDefThree);

            }
            this.createQuiz(quizName + quizIndex,author + quizIndex,
                    description + quizIndex, wordPairList, badDefList);
        }
        this.showAllQuizLog();

        this.setFullScoreName("Quiz2","1st Student Get 100");
        this.setFullScoreName("Quiz2","2nd Student Get 100");
        this.setFullScoreName("Quiz2","3rd Student Get 100");
        this.setFullScoreName("Quiz2","4th Student Get 100");

        //Get All Quiz List
        ArrayList<Quiz> quizList = this.getAllQuiz();
        ArrayList<QuizSelection> quizSelections0 = quizList.get(0).generateQuestions();
        ArrayList<QuizSelection> quizSelections1 = quizList.get(1).generateQuestions();

        //Get All good def in quiz
        ArrayList<String> goodDefList = this.getQuizGoodDef(quizName + String.valueOf(1));
        for (int i=0;i<goodDefList.size();i++) {
            if(goodDefList.get(i) != null) {
                Log.i("goodDef"+String.valueOf(i+1), goodDefList.get(i));
            }
        }

        //Get All bad def in quiz
        List<IncorrectDefinition> badDefList = this.getQuizBadDef(quizName + String.valueOf(1));
        for (int i=0;i<badDefList.size();i++) {
            if(badDefList.get(i) != null) {
                Log.i("badDef"+String.valueOf(i+1), badDefList.get(i).toString());
            }
        }

        //Get words in quiz
        ArrayList<String> wordList = this.getQuizWord(quizName + String.valueOf(1));
        for (int i=0;i<wordList.size();i++) {
            if(wordList.get(i) != null) {
                Log.i("word"+String.valueOf(i+1), wordList.get(i));
            }
        }

        //Get words in quiz
        ArrayList<WordPair> wordPairList = this.getQuizWordPair(quizName + String.valueOf(1));
        for (int i=0;i<wordPairList.size();i++) {
            String word = wordPairList.get(i).getWord();
            String goodDef = wordPairList.get(i).getCorrectDefinition();
            if(word != null &&  goodDef!= null) {
                Log.i("word pair"+String.valueOf(i+1), word + " - " +goodDef);
            }
        }

        //Check author and delete quiz
        if(this.isAuthor("Quiz Author1", "Quiz1")){
            if(this.deleteQuiz("Quiz Author1","Quiz1")){
                Log.i("Delete quiz","Quiz 1 is deleted");
            }
        }

        if(this.deleteQuiz("Quiz Author2","Quiz3")){
            Log.i("Delete quiz","Quiz 3 is deleted");
        }
        else {
            Log.i("Delete quiz","Quiz 3 is not deleted");
        }

        //Get Quiz Name
        ArrayList<String> quizNameList = this.getAllQuizName();
        for (int i=0;i<quizNameList.size();i++) {
            if(quizNameList.get(i) != null) {
                Log.i("quiz list ", quizNameList.get(i));
            }
        }

        //Get/Set full score name
        ArrayList<String> fullScoreNameList = this.getFullScoreStudent("Quiz4");
        for (int i=0;i<fullScoreNameList.size();i++) {
            if(fullScoreNameList.get(i) != null) {
                Log.i("Full Score Student ", fullScoreNameList.get(i));
            }
        }

        this.setFullScoreName("Quiz4","1st Student Get 100");
        fullScoreNameList = this.getFullScoreStudent("Quiz4");
        for (int i=0;i<fullScoreNameList.size();i++) {
            if(fullScoreNameList.get(i) != null) {
                Log.i("Full Score Student ", fullScoreNameList.get(i));
            }
        }

        this.setFullScoreName("Quiz4","2nd Student Get 100");
        this.setFullScoreName("Quiz4","3rd Student Get 100");
        fullScoreNameList = this.getFullScoreStudent("Quiz4");
        for (int i=0;i<fullScoreNameList.size();i++) {
            if(fullScoreNameList.get(i) != null) {
                Log.i("Full Score Student ", fullScoreNameList.get(i));
            }
        }

        this.setFullScoreName("Quiz4","4th Student Get 100");
        fullScoreNameList = this.getFullScoreStudent("Quiz4");
        for (int i=0;i<fullScoreNameList.size();i++) {
            if(fullScoreNameList.get(i) != null) {
                Log.i("Full Score Student ", fullScoreNameList.get(i));
            }
        }
    }
}
