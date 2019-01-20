# Design Information for Requirements Provided

1. When starting the application, a user can choose whether to (1) log in as a specific student or (2) register as a new student.
    1. To register as a new student, the user must provide the following student information:
        1. A unique username
        2. A major
        3. A seniority level (i.e., freshman, sophomore, junior, senior, or grad)
        4. An email address

        > To satisfy the first requirement of including the users, who are either existing students or new students, I added a class Student, which has the attributes: username(public variable), major, seniority, email

    2. The newly added student is immediately created in the system.

    > This functionality can be shown only via GUI implementation and not through my UML class diagram
    
    3. For simplicity, there is no password creation/authentication; that is, selecting or entering a student username is sufficient to log in as that student.

    > This functionality can not be shown in my UML class diagram

    4. Also for simplicity, student and quiz information is local to a device.

    > This functionality can not be shown in my UML class diagram

2. The application allows students to (1) add a quiz, (2) remove a quiz they created, (3) practice quizzes, and (4) view the list of quiz score statistics.

> I implemented this requirement by adding a relationship(a 1 to many relationship => a student can have access to multiple quizzes that he may create) between my class Student and another created class Quiz. The attributes for this relationship class(Quiz_Operation) contains the capability for the student to add a quiz or remove his/her own quiz, and also includes a createdQuizes(public) attribute(for the Student class as well) to keep a count of the quizzes created by the student. This attribute will be used later to identify the quizzes NOT created by the same student.

3. To add a quiz, a student must enter the following quiz information:
    1. Unique name
    2. Short description

    > I inclued the method for adding a quiz from the relationship class(Quiz_Operation) between the Student class and the Quiz class. I created a class for this requirement called Quiz which had the attributes username(from the student), the quiz name(public variable), a short description, a local variable named user_created_quizzes which uses the public createdQuizzes to keep a count of quizzes created by a user in the class. It  also has a timestamp attribute(public) which is useful for when someone accesses and finished the quiz. 

    3. List of N words, where N is between 1 and 10,  together with their definitions 
    4. List of N * 3 incorrect definitions, not tied to any particular word, where N is the number of words in the quiz.

    > For including the actual content of the quiz, i created a new class called words which was a dependent class for the quiz class because the Quiz class HAS Words class. This class includes 3 public variables, N(number of words that are in the quiz), N-correct-definitions for each of the N words(N in count), N_incorrect_definitons(N*3 in count)

4. To remove a quiz, students must select it from the list of the quizzes they created. Removing a quiz must also remove the score statistics associated with that quiz.

> Like mentioned above, I included the RemoveQuiz method in the class Quiz_Operation. This class also has a relationship with the class Scores which is used to calculate the scores for each of the quiz. This particular relationship removes the score from the Scores Statistics as well.

5. To practice a quiz, students must select it from the list of quizzes created by other students.

> I created a new class for the practiceQuiz functionality. This class takes attribtues as quiz_name, the user_name, and the timestamp (all 3 are public variables). This class is then in a relationship with the Student class as a 1 to many relationship because 1 student can practice as many quizzes as he/she needs. This class, practice Quiz is also in a relationship with the Quiz class as a 1 to 1 relationship because a student can practice only 1 quiz at a time. 

6. When a student is practicing a quiz, the application must do the following:
    1. Until all words in the quiz have been used in the current practice session: 
        1. Display a random word in the quiz word list.
        2. Display four definitions, including the correct definition for that word (the other three definitions must be randomly selected from the union of (1) the set of definitions for the other words in the quiz and (2) the set of incorrect definitions for the quiz. 

        > The practice Quiz class has 3 different methods. One is a wordrandomizer which randomly selects a possible word for a particular quiz. Another method is a optionselector which displays the correct definiton for the word and a set of 3 other definitions from the remaining definitions

        3. Let the student select a definition and display “correct” (resp., “incorrect”) if the definition is correct (resp., incorrect).

        > This is taken care of by the third method in the PracticeQuiz class called Validator which checks the student answer with the correct choice or not.

    2. After every word in the quiz has been used, the student will be shown the percentage of words they correctly defined, and this information will be saved in the quiz score statistics for that quiz and student.

    > This functionality is made available due to the 1 to 1 relationship between the quiz class and the scores class as well. 

7. The list of quiz score statistics for a student must list all quizzes, ordered based on when they were last played by the student (most recent first). Clicking on a quiz must display (1) the student’s first score and when it was achieved (date and time), (2) the student’s highest score and when it was achieved (date and time), and (3) the names of the first three students to score 100% on the quiz, ordered alphabetically.

>Listing out the quizzes is demonstrated by the Quiz_Ordered_listing method based on timestamp. Clicking on a quiz and displaying the students first score along with the timestamp is given by the CalcualteScore method. The student's highest score and the timestamp is taken care of by the Quiz_selected_listing which takes in the quiz name and the timestamp as its parameters to calculate. The top three students functionality is added by the Quiz_Top_studs method which gets the top 3 usernames from the scores for that particular quiz

8. The user interface must be intuitive and responsive.

> This can't be shown in my UML Class diagram but is for futher development upon the GUI.

9. The performance of the game should be such that students do not experience any considerable lag between their actions and the response of the application.

> This is taken into account only while implementation of the project but I am merely designing the system and providing NO additonal tasks as such.
