package edu.gatech.seclass.sdpvocabquiz.utils;

import android.support.test.espresso.NoMatchingViewException;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.Arrays;

import edu.gatech.seclass.sdpvocabquiz.data.IncorrectDefinition;
import edu.gatech.seclass.sdpvocabquiz.data.Quiz;
import edu.gatech.seclass.sdpvocabquiz.data.Student;
import edu.gatech.seclass.sdpvocabquiz.data.WordPair;

public class TestDataUtil {

    public Student studentNoFullName = new Student("Student1", "student_one@gmail.com", "", "", "Computer Science", "Freshman");
    public Student studentFullNameOne = new Student("Student2", "student_two@gmail.com", "John", "Smith", "Biology", "Senior");
    public Student studentFullNameTwo = new Student("Student3", "student_three@gmail.com", "Jane", "Doe", "Mathematics", "Grad");

    public Quiz stateCapitalQuiz= new Quiz("Guess the Capital", studentNoFullName.getUsername(),
            "A quiz about States and their capitols. Can you guess them all? ",
            new ArrayList<WordPair>(Arrays.asList(
                    new WordPair("California", "Sacramento"),
                    new WordPair("Arizona", "Phoenix"),
                    new WordPair("Washington", "Seattle"),
                    new WordPair("Florida", "Tallahassee")
            )),

            new ArrayList<IncorrectDefinition>(Arrays.asList(
                    new IncorrectDefinition("Little Rock"),
                    new IncorrectDefinition("Boise"),
                    new IncorrectDefinition("Denver"),
                    new IncorrectDefinition("Salt Lake City"),
                    new IncorrectDefinition("Hartford"),
                    new IncorrectDefinition("Honolulu"),
                    new IncorrectDefinition("Lansing"),
                    new IncorrectDefinition("Austin"),
                    new IncorrectDefinition("Richmond"),
                    new IncorrectDefinition("Madison"),
                    new IncorrectDefinition("Springfield"),
                    new IncorrectDefinition("Dover")
            ))
    );

    public Quiz biologyQuiz= new Quiz("Cell Vocabs", "Student2",
            "A quiz from the 4th chapter of biology class BIO101 ",
            new ArrayList<WordPair>(Arrays.asList(
                    new WordPair("California", "Sacramento"),
                    new WordPair("Arizona", "Phoenix"),
                    new WordPair("Washington", "Seattle"),
                    new WordPair("Florida", "Tallahassee")
            )),

            new ArrayList<IncorrectDefinition>(Arrays.asList(
                    new IncorrectDefinition("Little Rock"),
                    new IncorrectDefinition("Boise"),
                    new IncorrectDefinition("Denver"),
                    new IncorrectDefinition("Salt Lake City"),
                    new IncorrectDefinition("Hartford"),
                    new IncorrectDefinition("Honolulu"),
                    new IncorrectDefinition("Lansing"),
                    new IncorrectDefinition("Austin"),
                    new IncorrectDefinition("Richmond"),
                    new IncorrectDefinition("Madison"),
                    new IncorrectDefinition("Springfield"),
                    new IncorrectDefinition("Dover")
            ))
    );

    public Quiz shortQuiz= new Quiz("Short Quiz", "Student1",
            "This is a quiz with only one question used for testing.",
            new ArrayList<WordPair>(Arrays.asList(
                    new WordPair("Banana", "a yellow fruit")
            )),

            new ArrayList<IncorrectDefinition>(Arrays.asList(
                    new IncorrectDefinition("an animal"),
                    new IncorrectDefinition("a genre in music"),
                    new IncorrectDefinition("the president")
            ))
    );

    public Quiz tenQuiz= new Quiz("Ten Quiz", "Student2",
            "This is a quiz contains 10 question used for easier testing.",
            new ArrayList<WordPair>(Arrays.asList(
                    new WordPair("One", "one"),
                    new WordPair("two", "two"),
                    new WordPair("Three", "three"),
                    new WordPair("Four", "four"),
                    new WordPair("Five", "five"),
                    new WordPair("Six", "six"),
                    new WordPair("Seven", "seven"),
                    new WordPair("Eight", "eight"),
                    new WordPair("Nine", "nine"),
                    new WordPair("Ten", "ten")
            )),

            new ArrayList<IncorrectDefinition>(Arrays.asList(
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong"),
                    new IncorrectDefinition("wrong")
            ))
    );


    // Used to reference specifically which item in the RecyclerView you want to get via Espresso
    // Source: https://stackoverflow.com/questions/24748303/selecting-child-view-at-index-using-espresso/30073528#30073528
    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with "+childPosition+" child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(childPosition).equals(view);
            }
        };
    }
}
