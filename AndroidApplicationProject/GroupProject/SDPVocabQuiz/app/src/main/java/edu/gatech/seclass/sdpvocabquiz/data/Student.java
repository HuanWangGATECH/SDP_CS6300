package edu.gatech.seclass.sdpvocabquiz.data;


import edu.gatech.seclass.sdpvocabquiz.utils.StudentDBManager;

public class Student
{
    public static String DB_TABLE_NAME = "students";
    public static String DB_COL_USERNAME = "username";
    public static String DB_COL_EMAIL = "email";
    public static String DB_COL_FIRSTNAME = "firstname";
    public static String DB_COL_LASTNAME = "lastname";
    public static String DB_COL_MAJOR = "major";
    public static String DB_COL_SENIORITY = "seniority";

    private String username;
    private String email;
    private String firstName;           //optional
    private String lastName;            //optional
    private String major;
    private String seniority;

    public Student() {
    }

    public Student(String username, String email, String firstName, String lastName, String major, String seniority){
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.seniority = seniority;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setSeniority(String seniority) {
        this.seniority = seniority;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMajor() {
        return major;
    }

    public String getSeniority() {
        return seniority;
    }
    
}
