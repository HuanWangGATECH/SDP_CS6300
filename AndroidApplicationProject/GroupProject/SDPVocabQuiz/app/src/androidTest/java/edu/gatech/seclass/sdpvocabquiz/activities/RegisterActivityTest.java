package edu.gatech.seclass.sdpvocabquiz.activities;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class RegisterActivityTest {

    public static final String LOG_TAG = "RegisterActivityText";
    Context context;
    final String studentTestDBManager = StudentDBManager.DB_NAME;
    StudentDBManager studentDBManager;
    Student studentNoFullName = new Student("Student1", "student_one@gmail.com", "", "", "Computer Science", "Freshman");
    Student studentFullNameOne = new Student("Student2", "student_two@gmail.com", "John", "Smith", "Biology", "Senior");
    Student studentFullNameTwo = new Student("Student3", "student_three@gmail.com", "Jane", "Doe", "Mathematics", "Grad");

    @Rule
    public ActivityTestRule<RegisterActivity> ActivityTestRule = new ActivityTestRule<RegisterActivity>(RegisterActivity.class);
    private RegisterActivity registerActivity;
    public ActivityTestRule<DashboardActivity> DashboardActivityTestRule = new ActivityTestRule<DashboardActivity>(DashboardActivity.class);
    private DashboardActivity dashboardActivity;

    @Before
    public void setUp() throws Exception {
        registerActivity = ActivityTestRule.getActivity();
        context = InstrumentationRegistry.getTargetContext();
        studentDBManager = new StudentDBManager(context, studentTestDBManager);
        studentDBManager.addStudent(studentNoFullName);
        studentDBManager.addStudent(studentFullNameOne);
    }

    @After
    public void tearDown() throws Exception {
        registerActivity = null;
        studentDBManager.deleteStudent(studentNoFullName.getUsername());
        studentDBManager.deleteStudent(studentFullNameOne.getUsername());
        studentDBManager.deleteStudent(studentFullNameTwo.getUsername());
        studentDBManager.close();
    }

    @Test
    public void appLaunch(){
        View view = registerActivity.findViewById(R.id.registerButton);
        assertNotNull(view);
    }

    // Test will try to register with an empty user output for error
    @Test
    public void testRegisterWithEmptyUsername(){
        onView(ViewMatchers.withId(R.id.usernameEditText)).perform(clearText(),closeSoftKeyboard());
//        onView(ViewMatchers.withId(R.id.emailEditText)).perform(clearText(), typeText(studentFullNameOne.getEmail()),closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.registerButton)).perform(click());
//        ViewInteraction userInputCheck = onView(withId(R.id.usernameEditText));
////        userInputCheck.check(matches(hasErrorText(context.getResources().getString(R.string.error_cannot_be_blanks))));
//        userInputCheck.check(matches(hasErrorText("Cannot be left blank")));
//        onView(withId(R.id.usernameEditText)).check(matches(hasErrorText("Cannot be left blank")));
        // Check if view is still registration (will not pass until both errors username and email are cleared)
        onView(withId(R.id.usernameEditText)).check(matches(isDisplayed()));
    }

    // Test when user tries to register with invalid characters (non alphanumeric) output for error
    // Note because of how the errors text will not be display if either email or user is left blank we will
    // check which layout (register or login) is in view to check if test passes
    @Test
    public void testRegisterWithInvalidUsername(){
        onView(ViewMatchers.withId(R.id.usernameEditText)).perform(clearText(), typeText("&^$@*@/n"), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.usernameEditText)).check(matches(isDisplayed()));
    }

    // Test when user tries to register with alpha numeric character and Invalid email format, stay at registration screen
    @Test
    public void testRegisterWithValidUsernameInvalidEmail(){
        onView(ViewMatchers.withId(R.id.usernameEditText)).perform(clearText(), typeText(studentFullNameTwo.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.emailEditText)).perform(clearText(), typeText("@mail.com"), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.registerButton)).check(matches(isDisplayed()));
    }

    // test when user tries to register with blank email address ("") output error and stay at registration screen
    @Test
    public void testRegisterWithBlankEmail(){
        onView(ViewMatchers.withId(R.id.usernameEditText)).perform(clearText(), typeText(studentFullNameTwo.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.emailEditText)).perform(clearText(), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.registerButton)).perform(click());
        // check for email error
        onView(withId(R.id.registerButton)).check(matches(isDisplayed()));
        //onView(withId(R.id.emailEditText)).check(matches(hasErrorText(context.getResources().getString(R.string.error_cannot_be_blanks))));
    }

    // Test when user tries to register with alpha numeric character and correct email format, register user and go to login screen
    @Test
    public void testRegisterWithValidUsernameEmail(){
        onView(ViewMatchers.withId(R.id.usernameEditText)).perform(clearText(), typeText(studentFullNameTwo.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.emailEditText)).perform(clearText(), typeText(studentFullNameTwo.getEmail()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.registerButton)).perform(click());
        // check if we arrived at the login screen for success registrations
        onView(withId(R.id.buttonLogin)).check(matches(isDisplayed()));
    }

    // Test when user tries to register with alpha numeric character and correct email format, register user. Go to login screen and
    // login to the dashboard. Should only display full name
    @Test
    public void testRegisterWithValidUsernameEmailThenLogin(){
        // register user
        onView(ViewMatchers.withId(R.id.usernameEditText)).perform(clearText(), typeText(studentFullNameTwo.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.emailEditText)).perform(clearText(), typeText(studentFullNameTwo.getEmail()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.lastNameTextEdit)).perform(clearText(), typeText(studentFullNameTwo.getLastName()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.firstNameTextEdit)).perform(clearText(), typeText(studentFullNameTwo.getFirstName()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.registerButton)).perform(click());
        // login with new user
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(studentFullNameTwo.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        // check welcome header
        String fullName = studentFullNameTwo.getFirstName() + " " + studentFullNameTwo.getLastName();
        // Change view to dashboard screen
        onView(withId(R.id.loggedUserDashHeader)).check(matches(withText("Welcome " + fullName)));
    }

    // Test when user tries to register with alpha numeric character and correct email format, register user. Go to login screen and
    // login to the dashboard. Should only display username
    @Test
    public void testRegisterWithValidUsernameNoFullNameEmailThenLogin(){
        studentDBManager.deleteStudent(studentFullNameOne.getUsername());
        // register user
        onView(ViewMatchers.withId(R.id.usernameEditText)).perform(clearText(), typeText(studentFullNameOne.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.emailEditText)).perform(clearText(), typeText(studentFullNameOne.getEmail()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.registerButton)).perform(click());
        // login with new user
        onView(ViewMatchers.withId(R.id.editTextUsername)).perform(clearText(), typeText(studentFullNameOne.getUsername()), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.buttonLogin)).perform(click());
        // check welcome header
        onView(withId(R.id.loggedUserDashHeader)).check(matches(withText("Welcome " + studentFullNameOne.getUsername())));
    }

}