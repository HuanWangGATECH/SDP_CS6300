package edu.gatech.seclass.sdpvocabquiz.activities;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import edu.gatech.seclass.sdpvocabquiz.R;
import edu.gatech.seclass.sdpvocabquiz.data.Score;
import edu.gatech.seclass.sdpvocabquiz.utils.QuizDBManager;
import edu.gatech.seclass.sdpvocabquiz.utils.ScoreDBManager;
import edu.gatech.seclass.sdpvocabquiz.utils.StudentDBManager;
import edu.gatech.seclass.sdpvocabquiz.utils.TestDataUtil;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;


public class PracticeActivityTest {

    public static final String LOG_TAG = "RegisterActivityText";
    Context context;
    TestDataUtil testDataUtil = new TestDataUtil();
    final String studentTestDBManager = StudentDBManager.DB_NAME;
    final String quizTestDBManager = QuizDBManager.DB_NAME;
    final String scoreTestDBManger = ScoreDBManager.DB_NAME;
    StudentDBManager studentDBManager;
    QuizDBManager quizDBManager;
    ScoreDBManager scoreDBManager;

    @Rule
    public ActivityTestRule<LoginActivity> LoginActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);
    private LoginActivity loginActivity;

    @Before
    public void setUp() throws Exception {
        loginActivity = LoginActivityTestRule.getActivity();
        context = InstrumentationRegistry.getTargetContext();
        studentDBManager = new StudentDBManager(context, studentTestDBManager);
        studentDBManager.addStudent(testDataUtil.studentNoFullName);
        studentDBManager.addStudent(testDataUtil.studentFullNameOne);
        quizDBManager = new QuizDBManager(context, quizTestDBManager);
        quizDBManager.createQuiz(testDataUtil.stateCapitalQuiz);    // Note: Author is "Student1"
        scoreDBManager = new ScoreDBManager(context, scoreTestDBManger);
    }

    @After
    public void tearDown() throws Exception {
        loginActivity = null;
        studentDBManager.deleteStudent(testDataUtil.studentNoFullName.getUsername());
        studentDBManager.deleteStudent(testDataUtil.studentFullNameOne.getUsername());
        studentDBManager.close();
        quizDBManager.deleteQuiz(testDataUtil.stateCapitalQuiz);
        quizDBManager.close();
        scoreDBManager.deleteQuizScore(testDataUtil.shortQuiz.getUniqueName());
        scoreDBManager.close();
    }

    // open app to make sure
    @Test
    public void appLaunch(){
        View view = loginActivity.findViewById(R.id.buttonLogin);
        assertNotNull(view);
    }

    // Try to practice a quiz you are not the author of. Should have toast message
    // saying "You are the author of this quiz and cannot take it"
    // TODO: Need to evaluate
    public void testAuthorPracticeOwnQuiz(){
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentNoFullName.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        // find out quiz to click
        onView(Matchers.allOf(ViewMatchers.withText(testDataUtil.stateCapitalQuiz.getUniqueName()))).perform(click());
        // click practice quiz button
        onView(ViewMatchers.withId(R.id.previewPracticeQuizButton)).perform(click());
        // asset toast message says we cannot practice our own quiz
        onView(withText(R.string.toast_delete_quiz_success)).inRoot(withDecorView(not(is(LoginActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    // Try to practice a quiz that you are the author of. Should go to practice screen
    @Test
    public void testPracticeSomeonesQuiz(){
        quizDBManager.createQuiz(testDataUtil.shortQuiz);

        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentFullNameOne.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        // find out quiz to click
        onView(Matchers.allOf(ViewMatchers.withText(testDataUtil.shortQuiz.getUniqueName()))).perform(click());
        // click practice quiz button
        onView(ViewMatchers.withId(R.id.previewPracticeQuizButton)).perform(click());
        // assert we are in the practice screen
        onView(withId(R.id.displayWord)).check(matches(isDisplayed()));

        quizDBManager.deleteQuiz(testDataUtil.shortQuiz);
        }

    // During quiz practice for the first time. Score and First Time score should be equal.
    @Test
    public void testPracticeQuizFirstATime(){
        // add our sample quiz to test with
        quizDBManager.createQuiz(testDataUtil.shortQuiz);
        // login with Student2
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentFullNameOne.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        // click on our newly added quiz
        onView(Matchers.allOf(ViewMatchers.withText(testDataUtil.shortQuiz.getUniqueName()))).perform(click());
        // Click on the practice button
        onView(ViewMatchers.withId(R.id.previewPracticeQuizButton)).perform(click());
        // select the correct answer (Banana - is a yellow fruit)
        onView(Matchers.allOf(ViewMatchers.withText(testDataUtil.shortQuiz.getWordPairList().get(0).getCorrectDefinition()))).perform(click());
        onView(withId(R.id.fab_next_button)).perform(click());
        // should be in the result screen scoring 100%
        Score score = scoreDBManager.getQuizScore(testDataUtil.studentFullNameOne.getUsername(), testDataUtil.shortQuiz.getUniqueName());
        //TODO: Assert both previewQuizScoreFirst and previewQuizScoreHighest are equal
        onView(withId(R.id.previewQuizScoreFirst)).check(matches(withText("Scored " + score.getHighestScore() + "% on " + score.getHighestScoreDate().toString())));
        //Remove our sample quiz
        quizDBManager.deleteQuiz(testDataUtil.shortQuiz);
    }

    // At the end of practicing a quiz. Clicking on Back arrow should return to dashboard.
    @Test
    public void testGoBackToDashboard() {
        quizDBManager.createQuiz(testDataUtil.shortQuiz);

        // login with Student2
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentFullNameOne.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        // click on our newly added quiz
        onView(Matchers.allOf(ViewMatchers.withText(testDataUtil.shortQuiz.getUniqueName()))).perform(click());
        // Click on the practice button
        onView(ViewMatchers.withId(R.id.previewPracticeQuizButton)).perform(click());
        // click on an answer
        onView(ViewMatchers.withText(testDataUtil.shortQuiz.getWordPairList().get(0).getCorrectDefinition())).perform(click());
        // go to next question
        onView(ViewMatchers.withId(R.id.fab_next_button)).perform(click());
        // click on the back button
        onView(ViewMatchers.withId(R.id.viewResultsBack)).perform(click());
        // assert we are at the dashboard
        onView(withId(R.id.loggedUserDashHeader)).check(matches(isDisplayed()));

        quizDBManager.deleteQuiz(testDataUtil.shortQuiz);
    }

    // During quiz practice select the wrong answer. Should display Incorrect.
    @Test
    public void testPracticeQuizGetIncorrectAnswer() {
        quizDBManager.createQuiz(testDataUtil.shortQuiz);

        // login with Student2
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentFullNameOne.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        // click on our newly added quiz
        onView(Matchers.allOf(ViewMatchers.withText(testDataUtil.shortQuiz.getUniqueName()))).perform(click());
        // Click on the practice button
        onView(ViewMatchers.withId(R.id.previewPracticeQuizButton)).perform(click());
        // click on an answer
        onView(ViewMatchers.withText(testDataUtil.shortQuiz.getIncorrectDefList().get(0).getIncorrectDefinition())).perform(click());
        // asser that incorrect message is displayed
        onView(ViewMatchers.withId(R.id.displayCheckAnswer)).check(matches(withText(R.string.selection_incorrect)));

        quizDBManager.deleteQuiz(testDataUtil.shortQuiz);
    }

    // During quiz practice select the right answer. Should display Correct.
    @Test
    public void testPracticeQuizGetCorrectAnswer() {
        quizDBManager.createQuiz(testDataUtil.shortQuiz);

        // login with Student2
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentFullNameOne.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        // click on our newly added quiz
        onView(Matchers.allOf(ViewMatchers.withText(testDataUtil.shortQuiz.getUniqueName()))).perform(click());
        // Click on the practice button
        onView(ViewMatchers.withId(R.id.previewPracticeQuizButton)).perform(click());
        // click on an answer
        onView(ViewMatchers.withText(testDataUtil.shortQuiz.getWordPairList().get(0).getCorrectDefinition())).perform(click());
        // asser that incorrect message is displayed
        onView(ViewMatchers.withId(R.id.displayCheckAnswer)).check(matches(withText(R.string.selection_correct)));

        quizDBManager.deleteQuiz(testDataUtil.shortQuiz);
    }

    // During quiz practice. Scoring 100% should have name displayed in one of the Perfect 3 scorers
    @Test
    public void testPracticeGetPerfectScore() {
        quizDBManager.createQuiz(testDataUtil.shortQuiz);

        // login with Student2
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentFullNameOne.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        // click on our newly added quiz
        onView(Matchers.allOf(ViewMatchers.withText(testDataUtil.shortQuiz.getUniqueName()))).perform(click());
        // Click on the practice button
        onView(ViewMatchers.withId(R.id.previewPracticeQuizButton)).perform(click());
        // click on an answer (scoring correct on this one quiz = 100%)
        onView(ViewMatchers.withText(testDataUtil.shortQuiz.getWordPairList().get(0).getCorrectDefinition())).perform(click());
        // check perfect high scorers
        onView(ViewMatchers.withId(R.id.displayCheckAnswer)).check(matches(withText(R.string.selection_correct)));
        // assert that incorrect message is displayed
        // TODO: need to fix assertion
        String fullname = testDataUtil.studentFullNameOne.getFirstName() + " " + testDataUtil.studentFullNameOne.getLastName();
        onView(ViewMatchers.withId(R.id.previewQuizScorePerfect1)).check(matches(withText(fullname)));
        quizDBManager.deleteQuiz(testDataUtil.shortQuiz);
    }

    // During quiz practice with User WITHOUT fullname. Scoring 100% on same qui Again should only display their USERNAME once.
    @Test
    public void testPracticeNotFullNameUserGetPerfectScore() {
        quizDBManager.createQuiz(testDataUtil.shortQuiz);

        // login with Student2
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentFullNameOne.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        // click on our newly added quiz
        onView(Matchers.allOf(ViewMatchers.withText(testDataUtil.shortQuiz.getUniqueName()))).perform(click());
        // Click on the practice button
        onView(ViewMatchers.withId(R.id.previewPracticeQuizButton)).perform(click());
        // click on an answer (scoring correct on this one quiz = 100%)
        onView(ViewMatchers.withText(testDataUtil.shortQuiz.getWordPairList().get(0).getCorrectDefinition())).perform(click());
        // check perfect high scorers
        onView(ViewMatchers.withId(R.id.displayCheckAnswer)).check(matches(withText(R.string.selection_correct)));
        // asser that incorrect message is displayed
        // TODO: Need to verify username is shown in view
        onView(Matchers.allOf(ViewMatchers.withText(testDataUtil.studentFullNameOne.getUsername()))).check(matches(isDisplayed()));

        quizDBManager.deleteQuiz(testDataUtil.shortQuiz);

    }

    // During quiz practice with User WITH fullname. Scoring 100% on same qui Again should only display their FULL NAME once.
    @Test
    public void testPracticeFullNameUserGetPerfectScore() {
        quizDBManager.createQuiz(testDataUtil.shortQuiz);

        // login with Student2
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentFullNameOne.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        // click on our newly added quiz
        onView(Matchers.allOf(ViewMatchers.withText(testDataUtil.shortQuiz.getUniqueName()))).perform(click());
        // Click on the practice button
        onView(ViewMatchers.withId(R.id.previewPracticeQuizButton)).perform(click());
        // click on an answer (scoring correct on this one quiz = 100%)
        onView(ViewMatchers.withText(testDataUtil.shortQuiz.getWordPairList().get(0).getCorrectDefinition())).perform(click());
        // check perfect high scorers
        onView(ViewMatchers.withId(R.id.displayCheckAnswer)).check(matches(withText(R.string.selection_correct)));
        // asser that incorrect message is displayed
        // TODO: Need to verify fullname is shown in view
        String fullname = testDataUtil.studentFullNameOne.getFirstName() + " " + testDataUtil.studentFullNameOne.getLastName();
        onView(Matchers.allOf(ViewMatchers.withText(fullname))).check(matches(isDisplayed()));

        quizDBManager.deleteQuiz(testDataUtil.shortQuiz);

    }
}