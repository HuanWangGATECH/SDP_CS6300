# Use Case Model

**Author**: Team28

## 1 Use Case Diagram

![Use Case Model](https://github.gatech.edu/gt-omscs-se-2018fall/6300Fall18Team28/blob/master/GroupProject/Docs/Diagrams/UseCaseModel.png)

## 2 Use Case Descriptions

## Register
* **Description:** Student creates an account using their information
* **Requirement:** User must not be logged in and be at the registration screen
* **Pre-Conditions:** The username selected must not already exist
* **Post-Conditions:** Student content is added to the database and they navigate back to the main login screen
* **Scenarios:** At the main login screen new users must create an account by clicking on the **Create an Account** link found below the login form. The screen will navigate to the registration screen. The registration form will have multiple fields followed by a **Register** button at the bottom. The user can input their desired username, email, full name, major and seniority level then click on the register button which will display errors if any are found. If no errors exist then a success toast message will appear and shortly return back to the login screen.

## Login
* **Description:** Students can login using their username that was created during the registration process
* **Requirement:** User must not be logged in and must be at the login screen
* **Pre-Conditions:** Username selected must exist in the database
* **Post-Conditions:** User will login and be sent to the App’s homepage where they may then interact with Quiz and Scores
* **Scenarios:** User will enter their username in the **Username TextEdit Field** and click the **Login** Button. If the username does not exist then an error will be displayed. However, if the username does exist in the database then they will be directed to the Apps Home Page. Here the user will have options to create/ remove a quiz and take quizzes made by other students and view the quizzes corresponding score statistics.

## Add Quiz
* **Description:** Student can create a quiz which then adds it to the database for other students to practice
* **Requirement:** User must be logged in and be at the quiz creation screen
* **Pre-Conditions:** Quiz must have a **unique name** in the textEdit field that does not exist in the database
* **Post-Conditions:** The quiz is added (along with all the other quiz info they filled out) and the user is then returned to the Home Screen where they can see their quiz appended to a list of quizzes
* **Scenarios:** When at the homepage the user can click on an **+** Button which transitions to a new screen. This screen will contain a form specifically for the quiz class which multiple fields: title (serving as the unique name), quiz description (displayed when users view the quiz), words and their definitions along with a list of incorrect definitions and lastly a **Create Quiz** button. Because a quiz can only have 1 to 10 words, it will initially have one word and definition section with a **+** Button to add additional words. Each word definition pair added will add 3 additional incorrect fields below it. This **+** button disappears when we reach the maximum of 10 words. Each word and corresponding definition can be removed by swiping them from right to left.

## Practice Quiz
* **Description:** Students can take a quiz from a list of quizzes which they are not an author of
* **Requirement:** User must be logged in and be at the home screen with the quiz they want to take visible
* **Pre-Conditions:** Quiz unique name exists in database and the logged in student is not the author
* **Post-Conditions:** Quiz statistic will be updated and store the graded results in the database
* **Scenarios:** At the home page, the user can select a quiz from the list presented to them. Tapping on a quiz in the list will bring up a new screen which displays information of the quiz such as the author, highest and first scores by that student and top 3 perfect scores by other students displaying their username/name alphabetically. There will also be a **Take Quiz** Button (if user is not the author). When users click the **Take Quiz** Button the screen will transition to the quiz screen which will then display all the generated questions to the user. Generated questions are composed of a random word with the correct definition and 3 randomly selected definitions from the list of definitions. Users can tap on each definition which instantly shows Incorrect Or Correct. When there are no more words left to be displayed, the quiz is immediately graded. This score is then displayed to the user in a **Scorescreen** and stores the score for that quiz and student. Below this screen will have a **return home** button. Clicking this button will navigate the user back to the home page where they can once again take another quiz.

## Remove Quiz
* **Description:** Student can remove a quiz on the screen that they are an author of. The selected quiz content and score statistic will be removed from database
* **Requirement:** User must be logged in and be at the home screen
* **Pre-Conditions:** Quiz uniqueName exists in the database and current user is the quiz author
* **Post-Conditions:** The selected quiz is removed from the displayed list and both the quiz and associated scores are removed from database
* **Scenarios:** User must be at the homepage where a list of quizzes are displayed to them. Users may remove quizzes which are done all in the home page. Quizzes created by the user will be displayed at the top of the list with a special icon/color (Ex. a **Quill** Icon to denote they authored that quiz). Swiping from right to left on the quiz list will expose a **Trashcan** icon that the user can tap to remove a quiz. Removing a quiz effectively removes the quiz and the scores from the database.

## View Quiz
* **Description:** Student cam click on a Quiz to display the quiz score statistic.  
* **Requirement:** User must be logged in and be at the home screen
* **Pre-Conditions:** Quiz statistic should have at least one quiz statistic
* **Post-Conditions:** Display quiz statistic
* **Scenarios:** At the home page, the user can select a quiz from the list presented to them. Tapping on a quiz in the list will bring up a **Quiz screen** which displays information about the quiz such as the author, highest and first scores by the logged in student and top 3 perfect scores by other students. Only the first three students' name or username who get 100% will be displayed alphabetically.
