package edu.gatech.seclass.sdpvocabquiz.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import edu.gatech.seclass.sdpvocabquiz.data.Student;

import static android.content.Context.MODE_PRIVATE;

public class SQLUtils {

    public static boolean userNameExistsInDatabase(Activity activity, String username){
        SQLiteDatabase db = activity.openOrCreateDatabase("Students", MODE_PRIVATE, null);
        return  (DatabaseUtils.queryNumEntries(db, "Students", "username = ?", new String[] {username} ) > 0) ? true: false;
    }

    public static void register(Activity activity, Student s){
        SQLiteDatabase db = activity.openOrCreateDatabase("Students", MODE_PRIVATE, null);

        ContentValues values = new ContentValues();
        values.put(Student.DB_COL_USERNAME, s.getUsername());
        values.put(Student.DB_COL_EMAIL, s.getEmail());
        values.put(Student.DB_COL_FIRSTNAME, s.getFirstName());
        values.put(Student.DB_COL_LASTNAME, s.getLastName());
        values.put(Student.DB_COL_MAJOR, s.getMajor());
        values.put(Student.DB_COL_SENIORITY, s.getSeniority());

        db.insertOrThrow(Student.DB_TABLE_NAME, null, values);
        db.close();
    }
}
