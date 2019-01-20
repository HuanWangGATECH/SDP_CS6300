package edu.gatech.seclass.sdpvocabquiz.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import edu.gatech.seclass.sdpvocabquiz.R;
import edu.gatech.seclass.sdpvocabquiz.data.Global;
import edu.gatech.seclass.sdpvocabquiz.data.Quiz;
import edu.gatech.seclass.sdpvocabquiz.data.Student;
import edu.gatech.seclass.sdpvocabquiz.utils.QuizDBManager;
import edu.gatech.seclass.sdpvocabquiz.utils.StudentDBManager;
import edu.gatech.seclass.sdpvocabquiz.utils.TestDataUtil;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotNull;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class DashboardActivityTest {

    public static final String LOG_TAG = "DashboardActivityTest";
    Context context;
    final String studentDatabase = StudentDBManager.DB_NAME;
    final String quizDatabase = QuizDBManager.DB_NAME;
    TestDataUtil testDataUtil = new TestDataUtil();
    StudentDBManager studentDBManager;
    QuizDBManager quizDBManager;

    // Used to test custom adapter views
    // Source: https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    @Rule
    public ActivityTestRule<LoginActivity> LoginActivityRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);
    private LoginActivity loginActivity;

    @Before
    public void setUp() throws Exception {

        loginActivity = LoginActivityRule.getActivity();
        context = InstrumentationRegistry.getTargetContext();
        studentDBManager = new StudentDBManager(context, studentDatabase);
        quizDBManager = new QuizDBManager(context, quizDatabase);
        studentDBManager.addStudent(testDataUtil.studentNoFullName);
        studentDBManager.addStudent(testDataUtil.studentFullNameOne);
        studentDBManager.addStudent(testDataUtil.studentFullNameTwo);
        }

    @After
    public void tearDown() throws Exception {
        loginActivity = null;
        quizDBManager.deleteQuiz(testDataUtil.stateCapitalQuiz.getAuthor(), testDataUtil.stateCapitalQuiz.getUniqueName());
        studentDBManager.deleteStudent(testDataUtil.studentNoFullName.getUsername());
        studentDBManager.deleteStudent(testDataUtil.studentFullNameOne.getUsername());
        studentDBManager.deleteStudent(testDataUtil.studentFullNameTwo.getUsername());
        studentDBManager.close();
        quizDBManager.close();
    }

    @Test
    public void appLaunch(){
        View view = loginActivity.findViewById(R.id.imageView);
        assertNotNull(view);
    }

    // Logged in user will try and create new Quiz, and should be able to go to the create quiz screen
    @Test
    public void testNavigateToCreateQuizFromDashboard(){
        //login as user
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentNoFullName.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());

        // Go to create quiz screen
        onView(ViewMatchers.withId(R.id.fab_add_quiz)).perform(click());
        onView(withId(R.id.createQuizUniqueName)).check(matches(isDisplayed()));
    }

    // Logged in user will try and create new Quiz, and should go back to dashboard to see it added
    @Test
    public void testCreateQuizGoBackToDashboard(){
        quizDBManager.deleteQuiz(testDataUtil.studentNoFullName.getUsername(),testDataUtil.stateCapitalQuiz.getUniqueName());
        //login as user
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentNoFullName.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        // go to create quiz screen
        onView(ViewMatchers.withId(R.id.fab_add_quiz)).perform(click());
        // Fill in quiz info on quiz create screen
        onView(ViewMatchers.withId(R.id.createQuizUniqueName)).perform(clearText(), typeText(testDataUtil.stateCapitalQuiz.getUniqueName()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.createQuizDescription)).perform(clearText(), typeText(testDataUtil.stateCapitalQuiz.getDescription()), closeSoftKeyboard());
        // Click create and go to dashboard and verify it exist
        onView(ViewMatchers.withId(R.id.fab_confirm_quiz_details)).perform(click());
        // Click Create on the pop up dialog
        onView(withText("Create"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        // in dashboard check if the quiz exist
        onView(withId(R.id.quizCardList))
                .check(matches(atPosition(0, hasDescendant(withText(testDataUtil.stateCapitalQuiz.getUniqueName())))));
        quizDBManager.deleteQuiz(testDataUtil.studentNoFullName.getUsername(),testDataUtil.stateCapitalQuiz.getUniqueName());
    }

    // Test if logged in user, the author can remove a quiz. Quiz should be removed from list viw
    // Note: tricky to simulate swipe, come back to this last
    @Test
    public void testAuthorRemoveQuiz(){
        quizDBManager.deleteQuiz(testDataUtil.stateCapitalQuiz.getAuthor(), testDataUtil.stateCapitalQuiz.getUniqueName());
        // add quiz
        quizDBManager.createQuiz(testDataUtil.stateCapitalQuiz);
        //login as Student 1
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentNoFullName.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        // swipe on the quiz we previously added
        onView(Matchers.allOf(ViewMatchers.withText(testDataUtil.stateCapitalQuiz.getUniqueName()))).perform(ViewActions.swipeLeft());
        //onView(TestDataUtil.nthChildOf(withId(R.id.quizCardList), 0)).perform(ViewActions.swipeLeft());
        // When Dialog pop ups, click Remove
        onView(withText("Remove"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        // Check if the toast message pops up
        onView(withText(R.string.toast_delete_quiz_success)).inRoot(withDecorView(not(is(LoginActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        Espresso.pressBack();
        quizDBManager.deleteQuiz(testDataUtil.stateCapitalQuiz.getAuthor(), testDataUtil.stateCapitalQuiz.getUniqueName());
    }

    // Test if logged in user, NOT the author cannot remove a quiz. Quiz should still exist
    // Note: tricky to simulate swipe, come back to this last
    @Test
    public void testNotAuthorRemoveQuiz(){
        quizDBManager.deleteQuiz(testDataUtil.stateCapitalQuiz.getAuthor(), testDataUtil.stateCapitalQuiz.getUniqueName());
        // add quiz
        quizDBManager.createQuiz(testDataUtil.stateCapitalQuiz);
        //login with non author
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentFullNameOne.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        // swipe on the quiz we previously added
        onView(TestDataUtil.nthChildOf(withId(R.id.quizCardList), 0)).perform(ViewActions.swipeLeft());
        onView(withText("Could Not Remove Quiz"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        quizDBManager.deleteQuiz(testDataUtil.stateCapitalQuiz.getAuthor(), testDataUtil.stateCapitalQuiz.getUniqueName());
    }

    // Test if logged in user can tap a quiz. Should navigate to PreviewQuiz Activity
    @Test
    public void testTapToPreviewQuiz(){
        quizDBManager.deleteQuiz(testDataUtil.stateCapitalQuiz.getAuthor(), testDataUtil.stateCapitalQuiz.getUniqueName());
        // add quiz
        quizDBManager.createQuiz(testDataUtil.stateCapitalQuiz);
        //login as Student 1
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentNoFullName.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        onView(ViewMatchers.withId(R.id.quizCardList)).perform(click());
        onView(withId(R.id.previewQuizTitle)).check(matches(isDisplayed()));
    }

    // Test if logged in user (Who is Author), taps on quiz and tries to practice. Is greeted with toast that they cannot
    @Test
    public void testAuthorTryPracticeQuiz(){
        quizDBManager.deleteQuiz(testDataUtil.stateCapitalQuiz.getAuthor(), testDataUtil.stateCapitalQuiz.getUniqueName());
        // add quiz
        quizDBManager.createQuiz(testDataUtil.stateCapitalQuiz);
        //login as Student 1
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentNoFullName.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        // Find the item in RecylerVIerer that has our quiz name we want and click on it
        onView(Matchers.allOf(ViewMatchers.withText(testDataUtil.stateCapitalQuiz.getUniqueName()))).perform(click());
        // click on button
        onView(ViewMatchers.withId(R.id.previewPracticeQuizButton)).perform(click());
        // Check out toast message matches cannot take quiz
        onView(withText(R.string.msg_author_cannot_take_quiz)).inRoot(withDecorView(not(is(LoginActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    // Test if logged in user (Who is NOT Author), taps on quiz and tries to practice. Navigates to pracice quiz activity menu
    @Test
    public void testNotAuthorTryPracticeQuiz(){
        quizDBManager.deleteQuiz(testDataUtil.stateCapitalQuiz.getAuthor(), testDataUtil.stateCapitalQuiz.getUniqueName());
        // add quiz
        quizDBManager.createQuiz(testDataUtil.stateCapitalQuiz);
        //login as
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(testDataUtil.studentFullNameTwo.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());

        onView(TestDataUtil.nthChildOf(withId(R.id.quizCardList), 0)).perform(click());

        onView(ViewMatchers.withId(R.id.previewPracticeQuizButton)).perform(click());
        // look to see if quizWord is displayed
        onView(withId(R.id.displayWord)).check(matches(isDisplayed()));
    }

}