package edu.gatech.seclass.sdpvocabquiz.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

import edu.gatech.seclass.sdpvocabquiz.data.Score;


import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ScoreDBManagerTest {

    ScoreDBManager scoreDBManager;
    Context scoreContext;
    String db_Name = "TestScoreDB";

    String quizName = "TestQuiz";
    String quizName1 = "QuizName1";
    String quizName2 = "QuizName2";
    String quizName3 = "QuizName3";

    String userName = "UserName";
    double firstScoreValue = 55.5;
    double newScoreValue = 100.0;

    public void msec_delay(int msec) {
        try {
            TimeUnit.MILLISECONDS.sleep(msec);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {
        scoreContext = InstrumentationRegistry.getTargetContext();
        scoreDBManager = new ScoreDBManager(scoreContext, db_Name);
    }

    @After
    public void tearDown() throws Exception {
       scoreDBManager.deleteQuizScore(quizName);
       scoreDBManager.close();
    }

    // Test insert a new test score into database
    @Test
    public void testSetNewQuizScore(){
        scoreDBManager.deleteQuizScore(quizName);
        scoreDBManager.setQuizScore(quizName,userName,firstScoreValue);
        Score score = scoreDBManager.getQuizScore(userName, quizName);
        assertNotNull(score);
    }

    // Test the first test score is write into database correctly
    @Test
    public void testSetNewFirstScore(){
        scoreDBManager.deleteQuizScore(quizName);
        scoreDBManager.setQuizScore(quizName,userName,firstScoreValue);
        Score score = scoreDBManager.getQuizScore(userName, quizName);
        Float expect = (float)(firstScoreValue);
        Float actual = score.getFirstScore();
        assertEquals(actual,expect);
    }

    // Test the new highest score is equal to first score when create.
    @Test
    public void testSetNewHighScore(){
        scoreDBManager.deleteQuizScore(quizName);
        scoreDBManager.setQuizScore(quizName,userName,firstScoreValue);
        Score score = scoreDBManager.getQuizScore(userName, quizName);
        Float expect = (float)(firstScoreValue);
        Float actual = score.getHighestScore();
        assertEquals(actual,expect);
    }

    // Test the latest score is equal to first score when create.
    @Test
    public void testSetLatestScore(){
        scoreDBManager.deleteQuizScore(quizName);
        scoreDBManager.setQuizScore(quizName,userName,firstScoreValue);
        Score score = scoreDBManager.getQuizScore(userName, quizName);
        Float expect = (float)(firstScoreValue);
        Float actual = score.getPercentage();
        assertEquals(actual,expect);
    }

    // Test the latest is updated correctly
    @Test
    public void testLatestScoreUpdate() {
        scoreDBManager.deleteQuizScore(quizName);
        scoreDBManager.setQuizScore(quizName,userName,firstScoreValue);
        scoreDBManager.setQuizScore(quizName,userName,newScoreValue);
        Score score = scoreDBManager.getQuizScore(userName, quizName);
        Float expect = (float)(newScoreValue);
        Float actual = score.getPercentage();
        assertEquals(actual,expect);
    }

    // Check first score date equals to latest score when create.
    @Test
    public void testScoreCreateDate() {
        scoreDBManager.deleteQuizScore(quizName);
        scoreDBManager.setQuizScore(quizName,userName,firstScoreValue);
        Score score = scoreDBManager.getQuizScore(userName, quizName);
        Date firstScoreDate = score.getFirstScoreDate();
        Date newScoreDate = score.getNewScoreDate();
        assertTrue(firstScoreDate.equals(newScoreDate));
    }

    // Check latest score is more recent than first score date after update
    @Test
    public void testScoreDateUpdate(){
        scoreDBManager.deleteQuizScore(quizName);
        scoreDBManager.setQuizScore(quizName,userName,firstScoreValue);
        msec_delay(1000);
        scoreDBManager.setQuizScore(quizName,userName,newScoreValue);
        Score score = scoreDBManager.getQuizScore(userName, quizName);
        Date firstScoreDate = score.getFirstScoreDate();
        Date newScoreDate = score.getNewScoreDate();
        assertTrue(firstScoreDate.before(newScoreDate));
    }

    //Test get non exist score from database
    @Test
    public void testGetNonExistScore() {
        scoreDBManager.deleteQuizScore(quizName);
        Score score = scoreDBManager.getQuizScore(userName, quizName);
        assertEquals(0, score.getTaken());
    }

    //Test remove test score from database
    @Test
    public void testDeleteScore() {
        scoreDBManager.deleteQuizScore(quizName);
        Score score = scoreDBManager.getQuizScore(userName, quizName);
        assertEquals(0, score.getTaken());
    }

    //Test get user played score list is in date order
    @Test
    public void testGetUserScoreListByOrder() {
        scoreDBManager.setQuizScore(quizName,userName,firstScoreValue);
        msec_delay(1000);
        scoreDBManager.setQuizScore(quizName1,userName,firstScoreValue);
        msec_delay(1000);
        scoreDBManager.setQuizScore(quizName2,userName,firstScoreValue);
        msec_delay(1000);
        scoreDBManager.setQuizScore(quizName3,userName,firstScoreValue);
        msec_delay(1000);
        List<Score> scoreList = scoreDBManager.getUserScore(userName);
        List<String> scoreNameList = Arrays.asList(
            scoreList.get(0).getUniqueName(),
            scoreList.get(1).getUniqueName(),
            scoreList.get(2).getUniqueName(),
            scoreList.get(3).getUniqueName());
        List<String> expectedList = Arrays.asList(quizName3, quizName2, quizName1, quizName);
        assertEquals(scoreNameList,expectedList);
    }

    //Test get user played quiz name list is in date order
    @Test
    public void testGetUserQuizListByOrder() {
        scoreDBManager.setQuizScore(quizName,userName,firstScoreValue);
        msec_delay(1000);
        scoreDBManager.setQuizScore(quizName1,userName,firstScoreValue);
        msec_delay(1000);
        scoreDBManager.setQuizScore(quizName2,userName,firstScoreValue);
        msec_delay(1000);
        scoreDBManager.setQuizScore(quizName3,userName,firstScoreValue);
        msec_delay(1000);
        List<String> scoreList = scoreDBManager.getUserQuizListbyScoreTimeOrder(userName);
        List<String> expectedList = Arrays.asList(quizName3, quizName2, quizName1, quizName);
        assertEquals(expectedList,scoreList);
    }
}