package edu.gatech.seclass.sdpvocabquiz.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import edu.gatech.seclass.sdpvocabquiz.data.Student;


/* Code Reference: https://www.simplifiedcoding.net/sqliteopenhelper-tutorial/#Creating-and-Managing-Database-with-SQLiteOpenHelper */
public class StudentDBManager extends SQLiteOpenHelper {

    public static final String DB_NAME = "Students";
    public static final String DB_TABLE_NAME = "students";
    public static final String DB_COL_USERNAME = "username";
    public static final String DB_COL_EMAIL = "email";
    public static final String DB_COL_FIRSTNAME = "firstname";
    public static final String DB_COL_LASTNAME = "lastname";
    public static final String DB_COL_MAJOR = "major";
    public static final String DB_COL_SENIORITY = "seniority";

    public StudentDBManager(Context context, String name)  {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_NAME + " (\n" +
                "    " + DB_COL_USERNAME + " varchar(50) NOT NULL PRIMARY KEY UNIQUE,\n" +
                "    " + DB_COL_EMAIL + " varchar(200),\n" +
                "    " + DB_COL_FIRSTNAME + " varchar(200),\n" +
                "    " + DB_COL_LASTNAME + " varchar(200),\n" +
                "    " + DB_COL_MAJOR + " varchar(200),\n" +
                "    " + DB_COL_SENIORITY + " varchar(200)\n" +
                ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + DB_TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public boolean addStudent(Student s){
        return addStudent(s.getUsername(), s.getEmail(), s.getFirstName(), s.getLastName(), s.getMajor(), s.getSeniority());
    }

    public boolean addStudent(String username, String email, String firstname, String lastname,
                       String major, String seniority) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_COL_USERNAME, username);
        contentValues.put(DB_COL_EMAIL, email);
        contentValues.put(DB_COL_FIRSTNAME, firstname);
        contentValues.put(DB_COL_LASTNAME, lastname);
        contentValues.put(DB_COL_MAJOR, major);
        contentValues.put(DB_COL_SENIORITY, seniority);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(DB_TABLE_NAME, null, contentValues) != -1;
    }

    // this method will return the full name of the student if provided at login
    public String getUserNameFullName(String userName){
        Student s = getStudent(userName);
        String first = s.getFirstName();
        String last = s.getLastName();
        String newName = userName;

        if(first != null && last != null){
            if(first.trim().length()!=0 && !first.isEmpty() && last.trim().length()!=0 && !last.isEmpty()){
                newName =  first + " " + last;
            }
        }
        return newName;
    }

    public Student getStudent(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + DB_TABLE_NAME + " WHERE " + DB_COL_USERNAME + "=?";
        Student student = new Student();
        try {
            Cursor c = db.rawQuery(query, new String[] {userName});
            c.moveToNext();
            if(c!=null) {
                student.setUsername(userName);
                if(c.getString(c.getColumnIndex(DB_COL_EMAIL)) != null){
                    student.setEmail(c.getString(c.getColumnIndex(DB_COL_EMAIL)));
                }
                if(c.getString(c.getColumnIndex(DB_COL_FIRSTNAME)) != null){
                    student.setFirstName(c.getString(c.getColumnIndex(DB_COL_FIRSTNAME)));
                }
                if(c.getString(c.getColumnIndex(DB_COL_LASTNAME)) != null){
                    student.setLastName(c.getString(c.getColumnIndex(DB_COL_LASTNAME)));
                }
                if(c.getString(c.getColumnIndex(DB_COL_MAJOR)) != null){
                    student.setMajor(c.getString(c.getColumnIndex(DB_COL_MAJOR)));
                }
                if(c.getString(c.getColumnIndex(DB_COL_SENIORITY)) != null){
                    student.setSeniority(c.getString(c.getColumnIndex(DB_COL_SENIORITY)));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return student;
    }

    public Cursor getAllStudent() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
    }

    public boolean isStudentExist(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        return (DatabaseUtils.queryNumEntries(db, DB_TABLE_NAME, DB_COL_USERNAME +"=?",
                new String[] {userName}) > 0);
    }

    public boolean deleteStudent(String username) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(DB_TABLE_NAME, DB_COL_USERNAME + "=?",
                new String[]{username}) == 1;
    }

    public ArrayList<Student> cloneTable() {
        ArrayList<Student> studentList = new ArrayList<>();
        Cursor c = this.getAllStudent();
        int userNameIndex = c.getColumnIndex("username");
        int emailIndex = c.getColumnIndex("email");
        int firstnameIndex = c.getColumnIndex("firstname");
        int lastnameIndex = c.getColumnIndex("lastname");
        int majorIndex = c.getColumnIndex("major");
        int seniorityIndex = c.getColumnIndex("seniority");

        try {
            c.moveToFirst();
            while (c != null){
                Student student = new Student(
                            c.getString(userNameIndex),
                            c.getString(emailIndex),
                            c.getString(firstnameIndex),
                            c.getString(lastnameIndex),
                            c.getString(majorIndex),
                            c.getString(seniorityIndex)
                        );
                studentList.add(student);
                c.moveToNext();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        c.close();
        return studentList;
    }

    public String getFirstUsername(){
        Cursor c = this.getAllStudent();
        c.moveToFirst();
        return c.getString(c.getColumnIndex(DB_COL_USERNAME));
    }

    //For debug
    public void showLog(){
        Cursor c = this.getAllStudent();
        try {
            int userNameIndex = c.getColumnIndex("username");
            int emailIndex = c.getColumnIndex("email");
            int firstnameIndex = c.getColumnIndex("firstname");
            int lastnameIndex = c.getColumnIndex("lastname");
            int majorIndex = c.getColumnIndex("major");
            int seniorityIndex = c.getColumnIndex("seniority");

            c.moveToFirst();
            while (c != null){
                if(c.getString(userNameIndex) != null) {
                    Log.i("username ", c.getString(userNameIndex));
                }
                if(c.getString(emailIndex) != null) {
                    Log.i("email ", c.getString(emailIndex));
                }
                if(c.getString(firstnameIndex) != null) {
                    Log.i("firstname ", c.getString(firstnameIndex));
                }
                if(c.getString(lastnameIndex) != null){
                    Log.i("lastname ", c.getString(lastnameIndex));
                }
                if(c.getString(majorIndex) != null) {
                    Log.i("major ", c.getString(majorIndex));
                }
                if(c.getString(seniorityIndex) != null) {
                    Log.i("seniority ", c.getString(seniorityIndex));
                }
                c.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.close();
    }
}
