package edu.gatech.seclass.sdpvocabquiz.utils;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.gatech.seclass.sdpvocabquiz.data.Student;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class StudentDBManagerUnitTest {

    public static String LOG_TAG = "StudentDBManagerUnitTest";
    final String testDatabaseName = StudentDBManager.DB_NAME + "_test";
    StudentDBManager studentDBManager;
    Context quizContext;

    Student student1 = new Student("Student1", "student_one@gmail.com", "", "", "Computer Science", "Freshman");
    Student student2 = new Student("Student2", "student_two@gmail.com", "John", "Smith", "Biology", "Senior");
    Student student3 = new Student("Student3", "student_three@gmail.com", "Jane", "Doe", "Mathematics", "Grad");


    @Before
    public void setUp() throws Exception {
        quizContext = InstrumentationRegistry.getTargetContext();
        studentDBManager = new StudentDBManager(quizContext, testDatabaseName);
    }

    @After
    public void tearDown() throws Exception {
        studentDBManager.deleteStudent(student1.getUsername());
        studentDBManager.deleteStudent(student2.getUsername());
        studentDBManager.deleteStudent(student3.getUsername());
        studentDBManager.close();
    }

    @Test
    public void testIfDatabaseExists(){

        assertEquals(studentDBManager.getDatabaseName(), testDatabaseName);
    }

    // this test ensures that when we add a user it is the last item in our student table
    @Test
    public void testAddStudent(){
        studentDBManager.addStudent(student1);
        Cursor c = studentDBManager.getAllStudent();
        c.moveToLast();
        Log.i(LOG_TAG, c.getString(c.getColumnIndex(StudentDBManager.DB_COL_USERNAME)));
        assertEquals(student1.getUsername(), c.getString(c.getColumnIndex(StudentDBManager.DB_COL_USERNAME)));
    }

    // This test checks to ensure when user is added it exists in the database
    @Test
    public void testStudentExist(){
        studentDBManager.addStudent(student1);
        boolean result = studentDBManager.isStudentExist(student1.getUsername());
        assertEquals(result, true);
    }

    // This tests whether we can check if a username is not in use
    @Test
    public void testStudentDoesNotExist(){
        studentDBManager.addStudent(student2);
        boolean result = studentDBManager.isStudentExist("SomeRandomName");
        assertEquals(result, false);
    }

    // This tests whether we can get all students by counting number of rows
    // Adding 3 students will return 3 rows
    @Test
    public void testGetAllStudents(){
        studentDBManager.addStudent(student1);
        studentDBManager.addStudent(student2);
        studentDBManager.addStudent(student3);
        Cursor c = studentDBManager.getAllStudent();
        assertEquals(c.getCount(), 3);
    }

    // This test adds and then immediately deletes a student and checks the row count
    // Initial count should be zero (with a clean db) and when adding then removing a student, count should still be 0
    @Test
    public void testDeleteStudent(){
        int initialCount = studentDBManager.getAllStudent().getCount();
        studentDBManager.addStudent(student3);
        studentDBManager.deleteStudent(student3.getUsername());
        int finalCount = studentDBManager.getAllStudent().getCount();
        assertEquals(initialCount, finalCount);
    }

    // Test whether we can get a student username from the database via username
    @Test
    public void testGetStudentCompareUsername() {
        studentDBManager.addStudent(student3);
        Student student = studentDBManager.getStudent(student3.getUsername());
        assertEquals(student.getUsername(), student3.getUsername());
    }

    // Test whether we can get a student email from the database via username
    @Test
    public void testGetStudentCompareEmail() {
        studentDBManager.addStudent(student3);
        Student student = studentDBManager.getStudent(student3.getUsername());
        assertEquals(student.getEmail(), student3.getEmail());
    }

    // if we create a user and add it to the databse, when we retrieve it we should get the same
    // firstname property
    @Test
    public void testGetStudentCompareFirstName() {
        studentDBManager.addStudent(student3);
        Student student = studentDBManager.getStudent(student3.getUsername());
        assertEquals(student.getFirstName(), student3.getFirstName());
    }

    // if we create a user and add it to the databse, when we retrieve it we should get the same
    // lastname property
    @Test
    public void testGetStudentCompareLastName() {
        studentDBManager.addStudent(student3);
        Student student = studentDBManager.getStudent(student3.getUsername());
        assertEquals(student.getLastName(), student3.getLastName());
    }


}