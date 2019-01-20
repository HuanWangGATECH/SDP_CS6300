package edu.gatech.seclass.sdpvocabquiz.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.activities.LoginActivity;
import edu.gatech.seclass.sdpvocabquiz.data.IncorrectDefinition;
import edu.gatech.seclass.sdpvocabquiz.data.Quiz;
import edu.gatech.seclass.sdpvocabquiz.data.Student;
import edu.gatech.seclass.sdpvocabquiz.data.WordPair;
import edu.gatech.seclass.sdpvocabquiz.utils.QuizDBManager;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class QuizDBManagerUnitTest {

    public static String LOG_TAG = "QuizDbManagerUnitTest";
    final String testDatabaseName = QuizDBManager.DB_NAME + "_test";

    QuizDBManager quizDBManager;
    Context context;
    
    String author1 = "student1";
    String author2 = "student2";
    String author3 = "student3";

    String quizName1 = "Quiz 1";
    String quizName1a = "Quiz 1a";
    String quizName2 = "Quiz 2";
    String quizName3 = "Quiz 3";

    String description1 = "Quiz description 1";
    String description2 = "Quiz description 2";
    String description3 = "Quiz description 3";

    ArrayList<WordPair> quiz1WordPair = new ArrayList<WordPair>(Arrays.asList(
            new WordPair("quiz1Word1", "quiz1 word1 correct def"),
            new WordPair("quiz1Word2", "quiz1 word2 correct def"),
            new WordPair("quiz1Word3", "quiz1 word3 correct def"),
            new WordPair("quiz1Word4", "quiz1 word4 correct def")
    ));
    ArrayList<WordPair> quiz2WordPair = new ArrayList<WordPair>(Arrays.asList(
            new WordPair("quiz2Word1", "quiz2 word1 correct def"),
            new WordPair("quiz2Word2", "quiz2 word2 correct def"),
            new WordPair("quiz2Word3", "quiz2 word3 correct def"),
            new WordPair("quiz2Word4", "quiz2 word4 correct def")
    ));
    ArrayList<WordPair> quiz3WordPair = new ArrayList<WordPair>(Arrays.asList(
            new WordPair("quiz3Word1", "quiz3 word1 correct def"),
            new WordPair("quiz3Word2", "quiz3 word2 correct def"),
            new WordPair("quiz3Word3", "quiz3 word3 correct def"),
            new WordPair("quiz3Word4", "quiz3 word4 correct def")
    ));

    List<IncorrectDefinition> quiz1IncorrectDef = Arrays.asList(
            new IncorrectDefinition("quiz1 bad def 1"),
            new IncorrectDefinition("quiz1 bad def 2"),
            new IncorrectDefinition("quiz1 bad def 3"),
            new IncorrectDefinition("quiz1 bad def 4"),
            new IncorrectDefinition("quiz1 bad def 5"),
            new IncorrectDefinition("quiz1 bad def 6"),
            new IncorrectDefinition("quiz1 bad def 7"),
            new IncorrectDefinition("quiz1 bad def 8"),
            new IncorrectDefinition("quiz1 bad def 9"),
            new IncorrectDefinition("quiz1 bad def 10"),
            new IncorrectDefinition("quiz1 bad def 11"),
            new IncorrectDefinition("quiz1 bad def 12")
    );

    List<IncorrectDefinition> quiz2IncorrectDef = Arrays.asList(
            new IncorrectDefinition("quiz2 bad def 1"),
            new IncorrectDefinition("quiz2 bad def 2"),
            new IncorrectDefinition("quiz2 bad def 3"),
            new IncorrectDefinition("quiz2 bad def 4"),
            new IncorrectDefinition("quiz2 bad def 5"),
            new IncorrectDefinition("quiz2 bad def 6"),
            new IncorrectDefinition("quiz2 bad def 7"),
            new IncorrectDefinition("quiz2 bad def 8"),
            new IncorrectDefinition("quiz2 bad def 9"),
            new IncorrectDefinition("quiz2 bad def 10"),
            new IncorrectDefinition("quiz2 bad def 11"),
            new IncorrectDefinition("quiz2 bad def 12")
    );

    List<IncorrectDefinition> quiz3IncorrectDef = Arrays.asList(
            new IncorrectDefinition("quiz3 bad def 1"),
            new IncorrectDefinition("quiz3 bad def 2"),
            new IncorrectDefinition("quiz3 bad def 3"),
            new IncorrectDefinition("quiz3 bad def 4"),
            new IncorrectDefinition("quiz3 bad def 5"),
            new IncorrectDefinition("quiz3 bad def 6"),
            new IncorrectDefinition("quiz3 bad def 7"),
            new IncorrectDefinition("quiz3 bad def 8"),
            new IncorrectDefinition("quiz3 bad def 9"),
            new IncorrectDefinition("quiz3 bad def 10"),
            new IncorrectDefinition("quiz3 bad def 11"),
            new IncorrectDefinition("quiz3 bad def 12")
    );

    private Quiz quiz1 = new Quiz(quizName1, author1, description1, quiz1WordPair, quiz1IncorrectDef);
    private Quiz quiz1a = new Quiz(quizName1a, author1, description1, quiz1WordPair, quiz1IncorrectDef);
    private Quiz quiz2 = new Quiz(quizName2, author2, description2, quiz2WordPair, quiz2IncorrectDef);
    private Quiz quiz3 = new Quiz(quizName3, author3, description3, quiz3WordPair, quiz3IncorrectDef);

    Student student1 = new Student(author1, "student_one@gmail.com", "", "", "Computer Science", "Freshman");
    Student student2 = new Student(author2, "student_two@gmail.com", "John", "Smith", "Biology", "Senior");
    Student student3 = new Student(author3, "student_three@gmail.com", "Jane", "Doe", "Mathematics", "Grad");

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        quizDBManager = new QuizDBManager(context, testDatabaseName);
    }

    @After
    public void tearDown() throws Exception {
        quizDBManager.deleteTable();
        quizDBManager.close();
    }

    @Test
    public void testIfDatabaseExists(){
        assertEquals(quizDBManager.getDatabaseName(), testDatabaseName);
    }

    // purpose of this test is to make sure we can create a quiz and count if our table expands by one row
    @Test
    public void testCreateAQuiz(){
        quizDBManager.createQuiz(quiz1);
        Cursor c = quizDBManager.getAllQuizCursor();
        assertEquals(c.getCount(), 1);
    }

    // Test whether we can properly access the author of a quiz via the quiz name
    @Test
    public void testAddQuizTitle(){
        quizDBManager.createQuiz(quiz1);
        assertEquals(quiz1.getAuthor(), author1);
    }

    // Test author is correctly write into database
    @Test
    public void testGetQuizAuthor(){
        quizDBManager.deleteQuiz(quiz1.getAuthor(), quiz1.getUniqueName());
        quizDBManager.createQuiz(quiz1);
        String author = quizDBManager.getQuizAuthor(quiz1.getUniqueName());
        assertEquals(quiz1.getAuthor(), author);
    }

    // Test whether we can properly set the quiz description via the quiz name
    @Test
    public void testAddQuizDescription(){
        quizDBManager.createQuiz(quiz1);
        assertEquals(quizDBManager.getQuizDescription(quiz1.getUniqueName()), description1);
    }

    // Test checks to see when user scores perfectly, set their name on the highscore
    @Test
    public void testSetFullScoreName(){
        quizDBManager.deleteQuiz(quiz1.getAuthor(), quiz1.getUniqueName());
        quizDBManager.createQuiz(quiz1);
        quizDBManager.setFullScoreName(quiz1.getUniqueName(), student1.getUsername());
        quizDBManager.setFullScoreName(quiz1.getUniqueName(), student2.getUsername());
        quizDBManager.setFullScoreName(quiz1.getUniqueName(), student3.getUsername());
        ArrayList<String> highScorers = quizDBManager.getFullScoreStudent(quiz1.getUniqueName());
        List<String> studentNames = Arrays.asList(author1, author2,author3);
        assertEquals(highScorers, studentNames );
    }

    // This test ensures that only the first 3 students who achieve a perfect score will
    // be shown. Any other users added afterwards will not be shown in the list
    @Test
    public void testFirstPerfectScorersOnlyShowFirstThree(){
        quizDBManager.deleteQuiz(quiz1.getAuthor(), quiz1.getUniqueName());
        quizDBManager.createQuiz(quiz1);
        quizDBManager.setFullScoreName(quiz1.getUniqueName(), "randomUser");
        quizDBManager.setFullScoreName(quiz1.getUniqueName(), student1.getUsername());
        quizDBManager.setFullScoreName(quiz1.getUniqueName(), student2.getUsername());
        quizDBManager.setFullScoreName(quiz1.getUniqueName(), student3.getUsername());
        ArrayList<String> highScorers = quizDBManager.getFullScoreStudent(quizName1);
        List<String> studentNames = Arrays.asList("randomUser", author1, author2);
        assertEquals(highScorers, studentNames);
    }

    // Test get all quiz name list that user created
    @Test
    public void testUserCreatedQuizList() {
        quizDBManager.deleteQuiz(quiz1.getAuthor(), quiz1.getUniqueName());
        quizDBManager.deleteQuiz(quiz1a.getAuthor(), quiz1a.getUniqueName());
        quizDBManager.deleteQuiz(quiz2.getAuthor(), quiz2.getUniqueName());
        quizDBManager.deleteQuiz(quiz3.getAuthor(), quiz3.getUniqueName());
        quizDBManager.createQuiz(quiz1);
        quizDBManager.createQuiz(quiz1a);
        quizDBManager.createQuiz(quiz2);
        quizDBManager.createQuiz(quiz3);
        ArrayList<String> userCreatedList = quizDBManager.getUserCreatedQuizList(author1);
        List<String> expectedList = Arrays.asList(quizName1, quizName1a);
        assertEquals(userCreatedList, expectedList);
    }

    // Test get all quiz name list that is not user created
    @Test
    public void testNonUserCreatedQuizList() {
        quizDBManager.deleteQuiz(quiz1.getAuthor(), quiz1.getUniqueName());
        quizDBManager.deleteQuiz(quiz1a.getAuthor(), quiz1a.getUniqueName());
        quizDBManager.deleteQuiz(quiz2.getAuthor(), quiz2.getUniqueName());
        quizDBManager.deleteQuiz(quiz3.getAuthor(), quiz3.getUniqueName());
        quizDBManager.createQuiz(quiz1);
        quizDBManager.createQuiz(quiz1a);
        quizDBManager.createQuiz(quiz2);
        quizDBManager.createQuiz(quiz3);
        ArrayList<String> userCreatedList = quizDBManager.getNonUserCreatedQuizList(author1);
        List<String> expectedList = Arrays.asList(quizName2, quizName3);
        assertEquals(userCreatedList, expectedList);
    }

    // Test Dashboard Quiz list is sorted. Most recent played at the top and user created at bottom
    @Test
    public void testDashboardQuizList() {
        quizDBManager.deleteQuiz(quiz1.getAuthor(), quiz1.getUniqueName());
        quizDBManager.deleteQuiz(quiz1a.getAuthor(), quiz1a.getUniqueName());
        quizDBManager.deleteQuiz(quiz2.getAuthor(), quiz2.getUniqueName());
        quizDBManager.deleteQuiz(quiz3.getAuthor(), quiz3.getUniqueName());
        quizDBManager.createQuiz(quiz1);
        quizDBManager.createQuiz(quiz1a);
        quizDBManager.createQuiz(quiz2);
        quizDBManager.createQuiz(quiz3);
        ArrayList<String> userTestedQuiz_Sorted = new ArrayList<>(Arrays.asList(quizName3));
        List<Quiz> quizList = quizDBManager.getSatisticQuizList(author1, userTestedQuiz_Sorted);
        List<String> expectedList = Arrays.asList(quizName3, quizName2,quizName1, quizName1a);
        List<String> actualList = new ArrayList<>();
        for(int i=0; i<quizList.size();i++){
            actualList.add(quizList.get(i).getUniqueName());
        }
        assertEquals(actualList, expectedList);
    }

    // Test quiz can be removed by author
    @Test
    public void testDeleteQuizByAuthor() {
        quizDBManager.createQuiz(quiz1);
        quizDBManager.createQuiz(quiz1a);
        quizDBManager.createQuiz(quiz2);
        quizDBManager.createQuiz(quiz3);
        quizDBManager.deleteQuiz(quiz1.getAuthor(), quiz1.getUniqueName());
        ArrayList<String> quizNameList = quizDBManager.getAllQuizName();
        assertFalse(quizNameList.contains(quiz1.getUniqueName()));
    }

    // Test quiz can not be removed by author
    @Test
    public void testDeleteQuizByNonAuthor() {
        quizDBManager.createQuiz(quiz1);
        quizDBManager.createQuiz(quiz1a);
        quizDBManager.createQuiz(quiz2);
        quizDBManager.createQuiz(quiz3);
        quizDBManager.deleteQuiz(quiz2.getAuthor(), quiz1.getUniqueName());
        ArrayList<String> quizNameList = quizDBManager.getAllQuizName();
        assertTrue(quizNameList.contains(quiz1.getUniqueName()));
    }
}