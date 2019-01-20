package edu.gatech.seclass.sdpvocabquiz.activities;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Checkable;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import edu.gatech.seclass.sdpvocabquiz.R;
import edu.gatech.seclass.sdpvocabquiz.data.Student;
import edu.gatech.seclass.sdpvocabquiz.utils.StudentDBManager;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.*;

public class LoginActivityTest {

    public static final String LOG_TAG = "LoginActivityTest";
    Context context;
    final String studentTestDBManager = StudentDBManager.DB_NAME;
    StudentDBManager studentDBManager;
    Student studentNoFullName = new Student("Student1", "student_one@gmail.com", "", "", "Computer Science", "Freshman");
    Student studentFullNameOne = new Student("Student2", "student_two@gmail.com", "John", "Smith", "Biology", "Senior");
    Student studentFullNameTwo = new Student("Student3", "student_three@gmail.com", "Jane", "Doe", "Mathematics", "Grad");


    // For check boxes and checking with espresso its state
    // Source: https://stackoverflow.com/questions/37819278/android-espresso-click-checkbox-if-not-checked/39650813
    public static ViewAction setChecked(final boolean checked) {
        return new ViewAction() {
            @Override
            public BaseMatcher<View> getConstraints() {
                return new BaseMatcher<View>() {
                    @Override
                    public boolean matches(Object item) {
                        return isA(Checkable.class).matches(item);
                    }

                    @Override
                    public void describeMismatch(Object item, Description mismatchDescription) {}

                    @Override
                    public void describeTo(Description description) {}
                };
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                Checkable checkableView = (Checkable) view;
                checkableView.setChecked(checked);
            }
        };
    }

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);
    private LoginActivity mLoginActivity;


    @Before
    public void setUp() throws Exception {
        mLoginActivity = mActivityTestRule.getActivity();
        context = InstrumentationRegistry.getTargetContext();
        studentDBManager = new StudentDBManager(context, studentTestDBManager);
        studentDBManager.addStudent(studentNoFullName);
        studentDBManager.addStudent(studentFullNameOne);
        studentDBManager.addStudent(studentFullNameTwo);
    }

    @After
    public void tearDown() throws Exception {
        mLoginActivity = null;
        studentDBManager.deleteStudent(studentNoFullName.getUsername());
        studentDBManager.deleteStudent(studentFullNameOne.getUsername());
        studentDBManager.deleteStudent(studentFullNameTwo.getUsername());
        studentDBManager.close();
    }

    @Test
    public void appLaunch(){
        View view = mLoginActivity.findViewById(R.id.imageView);
        assertNotNull(view);
    }

    // Test will try to login with an empty user
    @Test
    public void testLoginEmptyUsername(){
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(),closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        ViewInteraction userInputCheck = onView(withId(R.id.editTextUsername));
        userInputCheck.check(matches(hasErrorText("Username does not exist")));
    }

    // Test will try to login with username that uses invalid charcters other than Alphanumeric
    @Test
    public void testLoginInvalidCharacters(){
        String userName = "@*$(&@_@/n";
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(userName), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        ViewInteraction userInputCheck = onView(withId(R.id.editTextUsername));
        userInputCheck.check(matches(hasErrorText("Username does not exist")));
    }

    // Test will try to login with a username full of spaces
    @Test
    public void testLoginSpacesUsername(){
        String userName = "              ";
        ViewInteraction userInputCheck = onView(withId(R.id.editTextUsername));
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(userName), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        userInputCheck.check(matches(hasErrorText("Username does not exist")));
    }

    // Test should check when student logs in who did not fill out Full Name in registration should only display their username
    @Test
    public void testLoginValidUserNoFullName() {
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(studentNoFullName.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        onView(withId(R.id.loggedUserDashHeader)).check(matches(withText("Welcome " + studentNoFullName.getUsername())));
    }

    // Test should checked when logged in user registers with a Full Name then display that instead of their username
    @Test
    public void testLoginValidUserFullName() {
        String fullName = studentFullNameOne.getFirstName() + " " + studentFullNameOne.getLastName();
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(studentFullNameOne.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        onView(withId(R.id.loggedUserDashHeader)).check(matches(withText("Welcome " + fullName)));
    }

    // Tests the remember me. If checked and logged in successfully, user can close app, relogin and still have username there
    @Test
    public void testRememberMeCheckedWithValidUsers() {
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(studentNoFullName.getUsername()), closeSoftKeyboard());
        onView(withId(R.id.rememberCheckBox)).perform(setChecked(true));
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        onView(ViewMatchers.withId(R.id.logoutLink)).perform(click());  //logout
        // Exit the app from login screen
        //Espresso.pressBack();
        // relaunch app
        //mLoginActivity = null;
        //TODO: Get Espresso to relaunch app (below not working )
        //mLoginActivity = mActivityTestRule.getActivity();
        onView(withId(R.id.editTextUsername)).check(matches(withText(studentNoFullName.getUsername())));
    }

    // Tests the remember me. If NOT check and logged in successfully, user can close app, relogin and still have username there
    @Test
    public void testRememberMeNotCheckedWithValidUsers() {
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(studentNoFullName.getUsername()), closeSoftKeyboard());
        onView(withId(R.id.rememberCheckBox)).perform(setChecked(false));
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        onView(ViewMatchers.withId(R.id.logoutLink)).perform(click());  //logout
        // Exit the app from login screen
       // Espresso.pressBack();
        // relaunch app
        //mLoginActivity = null;
        //TODO: Get Espresso to relaunch app (below not working )
       // mLoginActivity = mActivityTestRule.getActivity();
        onView(withId(R.id.editTextUsername)).check(matches(withText("")));
    }


}