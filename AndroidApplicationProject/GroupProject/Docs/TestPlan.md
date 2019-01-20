# Test Plan

**Author**: Team28

## 1 Testing Strategy

### 1.1 Overall strategy

We will establish test objectives for the entire app before implementing them. Testing will be divided into components and performed in phases: Unit, Integration and System Testing. Each test phase will ensure complete test coverage. Each test case implementation will be verified for correctness, robustness and functionality. Regression testing will be performed several times to check against feature changes and for design robustness. Developers who do not design the software component will design the black box test of the that component, otherwise, he/she will design the white box test. 

### 1.2 Test Selection 

##### Unit Testing
* uses Junit to test a class' own private method (unit tests for individual each class)
* any invalid inputs should be handled (bounds checking, null, other exceptions)
* operation logic is validated (multiple test cases for assertions)

##### Integration Testing:
* Will use Junit to test class relationships with one another
* Will use both private and public methods of classes (tests will group components together)

##### System Testing:
* Will use Espresso to simulate user behavior by constructing test that combine the system as a whole (both back-end and front end)
* Handling other unexpected behaviors such as providing UI feedback (errors, navigation etc) as well as:
    * Stress and performance tests: How will system handle the display of 1000 quizzes in a list and their associated scores?
    * Security testing: Ensure user cannot break inputs such as SQL injecting, inputting invalid characters that may crash app into textEdit fields
    * Recovery testing: How will the app recover from an error instead of crashing; Provide helpful user crash/error messages

##### Regression Testing:
* Will use a combination of manual and automated testing: Automated testing will perform a majority of the test workload (maybe 90% automated vs 10% manual)
* We will re-run an entire suite against each new build to make sure previous features did not break

### 1.3 Adequacy Criterion

* In order to have good quality of the test plan, the android studio code coverage will be use as reference for our test case coverage. We will cover test cases which consider reasonable corner cases. Additional test cases will be added throughout the test cycle.

### 1.4 Bug Tracking

* Bugs and enhancement requests will be tracked using a tracking suite such as JIRA or simply documenting it on GitHub Issues
* Inline TODO comments will be used to track bugs with a short description and a URL linking back to the JIRA discussion or Github Issues page

### 1.5 Technology

    * For unit tests and integration tests we intend to use JUnit
    * For System tests we intend to use Espresso

### 2 Test Cases
**Note: UX = Unit Tests and IX = Integration Tests**

---

| ID   | Test Case                                             | Purpose                                                      | Description                                                  | Expected Result                                              | Actual Result                                                | Pass /Fail |
| ---- | ----------------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ---------- |
| U1   | testAddStudent                                        | Add Student in database                                      | Insert student into database then compare the read back value | Read back student name should match to database              | Result match                                                 | Pass       |
| U2   | testStudentDoesNotExist                               | Return true  if student exist in database                    | Add new student into database then check the return result   | Return True                                                  | Return True                                                  | Pass       |
| U3   | testStudentDoesNotExist                               | Return false if student doesn’t exist in database            | Delete existing student from database then check the return result | Return False                                                 | Return False                                                 | Pass       |
| U4   | testGetAllStudents                                    | Get all Student name from database                           | Read all student name in database and store them in a string list | All predefined student are matched in the return String List | String list match                                            | Pass       |
| U5   | testDeleteStudent                                     | Remove student from database                                 | Get existing student number in database. Remove few students.Get latest student number in database | Deduced number should match as expected                      | Value match                                                  | Pass       |
| U6   | testGetStudentCompareUsername                         | Get student name from database by using student name         | Get student object by using using student name               | Student name match                                           | Name matched                                                 | Pass       |
| U7   | testGetStudentCompareEmail                            | Test student email insert into database correctly            | Get student object by using using student name               | Return student object’s email should match with input student’s email | Email matched                                                | Pass       |
| U8   | testGetStudentCompareFirstName                        | Student first name insert into database correctly            | Get student object by using using student name               | Return student object’s first name should match with input student’s first name | First name matched                                           | Pass       |
| U9   | testGetStudentCompareLastName                         | Student first name insert into database correctly            | Get student object by using using student name               | Return student object’s last name should match with input student’s last name | Last Name matched                                            | Pass       |
| U10  | testIfDatabaseExists                                  | Check database created successfully                          | Read database name to match predefined database name.        | Database name matched                                        | Database name matched                                        | Pass       |
| U11  | testCreateAQuiz                                       | Insert quiz object data into database                        | Add quiz object into empty table in database. The database row number should increase by 1. | Database cursor change to 1.                                 | Cursor = 1                                                   | Pass       |
| U12  | testAddQuizTitle                                      | Add quiz object into database                                | Add quiz object into database. Then read back author name from database | Quiz object’s author name should match read back author name | Author name matched                                          | Pass       |
| U13  | testGetQuizAuthor                                     | Add quiz object into database                                | Add quiz object into database. Then read back author name from database | Quiz object’s author name should match read back author name | Author name matched                                          | Pass       |
| U14  | testAddQuizDescription                                | Add quiz object into database                                | Add quiz object into database. Then read back description from database | Quiz object’s description should match read back description | description name matched                                     | Pass       |
| U16  | testSetFullScoreName                                  | Set 3 full score students into a new quiz                    | Create quiz in database then insert 3 full score student name. | The read back full score student names should match the predefined names | Three name match                                             | Pass       |
| U17  | testFirstPerfectScorersOnlyShowFirstThree             | Full score students cannot be added after 3 students have added | Insert 4 student into quiz full score. Then read back the full score student list | Only first 3 students name will return                       | Result matched                                               | Pass       |
| U18  | testUserCreatedQuizList                               | Get quiz name list that includes all quiz name created by Student | Create two quiz by test student name and two quiz by random student name. Then read back all quiz name from database | Only two quiz that are created by test student will return   | Return value correct                                         | Pass       |
| U19  | testNonUserCreatedQuizList                            | Get quiz name list that includes all quiz name not  created by Student | Create two quiz by test student name and two quiz by random student name. Then read back all quiz name from database | Only two quiz that are not created by test student will return | Return value correct                                         | Pass       |
| U20  | testDashboardQuizList                                 | Get correct quiz object base on requirement order            | Create 2 quiz (quiz 1, 2) by student1 then create 2 quiz (quiz 3, 4) by other student. Define quiz 3 as student’s played quiz. Read back quiz object list from database | The return quiz object name should be most recent played quiz first, non-played quiz in middle and student’s created quiz at bottom | Return quiz name = quiz 3, quiz 4, quiz 1, quiz 2            | Pass       |
| U21  | testDeleteQuizByAuthor                                | Quiz can  be deleted by author                               | Test delete quiz method with author name                     | Quiz deleted from database                                   | Quiz deleted                                                 | Pass       |
| U22  | testDeleteQuizByNonAuthor                             | Quiz cannot  be deleted by author                            | Test delete quiz method with non author name                 | Quiz still exist in database                                 | Quiz exist                                                   | Pass       |
| U23  | testSetNewQuizScore                                   | Score insert into database correctly                         | Set a new score by quiz name, student name and first score   | The read back score object from database should not be null  | Return not null                                              | Pass       |
| U24  | testSetNewFirstScore                                  | First score insert into database correctly                   | Set a new score by quiz name, student name and first score   | The read back first score value should match with input      | Score value matched                                          | Pass       |
| U25  | testSetLatestScore                                    | Latest score insert into database correctly                  | Set a new score by quiz name, student name and latest score  | The read back latest score value should match with input     | Score value matched                                          | Pass       |
| U26  | testLatestScoreUpdate                                 | Latest score update in  database correctly                   | Set latest quiz score into database then update it with a new score. | The read back value should equal to new score                | Score value matched                                          | Pass       |
| U27  | testScoreCreateDate                                   | First score and latest score date are the same when created  | Set first score into database                                | The first score date should be equal to latest score date    | Date equal                                                   | Pass       |
| U28  | testScoreDateUpdate                                   | Latest score date should update when score update            | Create a new score. Wait 1 second then update latest score value. | The latest score date should be after first score date       | Latest score is after first score date                       | Pass       |
| U29  | testGetUserScoreListByOrder                           | Get student’s score object  list by date order               | Set 4 different quiz score by one second delay for a single student | The return score object list should be ordered by date descending | Score list is ordered by date descending                     | Pass       |
| U30  | testGetUserQuizListByOrder                            | Get student’s quiz name  list by date order                  | Set 4 different quiz score by one second delay for a single student | The return quiz name t list should be ordered by date descending | Quiz name  list is ordered by date descending                | Pass       |
| U31  | testDeleteScore                                       | Delete quiz score by student name and quiz name              | Remove quiz score                                            | Score should be removed from database                        | Score removed                                                | Pass       |
| I1   | testRegisterWithEmptyUsername                         | Test register User with empty Username - should fail         | Try to perform an insert into DB with an empty username through android UI during registration page | Should Fail and not let register happen; remains on the same page | Stays on the same page                                       | Pass       |
| I2   | testRegisterWithInvalidUsername                       | Test register User with invalid username - should fail       | Try to perform an insert into the DB with an invalid username (non alphanumeric characters) | Should Fail and not let register happen; remains on the same page | Stays on the same page                                       | Pass       |
| I3   | testRegisterWithValidUsernameInvalidEmail             | Test register User with invalid email - should fail          | Try to perform an insert into the DB with an invalid email (non alphanumeric characters) | Should Fail and not let register happen; remains on the same page | Stays on the same page                                       | Pass       |
| I4   | testRegisterWithBlankEmail                            | Test register User with empty Email - should fail            | Try to perform an insert into DB with an empty email through android UI during registration page | Should Fail and not let register happen; remains on the same page | Stays on the same page                                       | Pass       |
| I5   | testRegisterWithValidUsernameEmail                    | Test register user with valid Email and Username             | Try to perform an insert into DB with a valid email and username through android UI during registration page | Should pass and arrive at the login page                     | Moves to login page                                          | Pass       |
| I6   | testRegisterWithValidUsernameEmailThenLogin           | Test register and login user with valid Email and Username and Full Name | Try to perform an insert into DB with a valid email and username through android UI during registration page, then login with that registration info, check to see if logged in view has full name | Should register, login, and check to see Full Name in dashboard | Moves to dashboards and finds full name                      | Pass       |
| I7   | testRegisterWithValidUsernameNoFullNameEmailThenLogin | Test register and login user with valid Email and Username but no Full Name | Try to perform an insert into DB with a valid email and username through android UI during registration page, then login with that registration info, check to see if logged in view displays username | Should register, login, and check to see username in dashboard | Moves to dashboards and finds username                       | Pass       |
| I8   | testAuthorPracticeOwnQuiz                             | Test practicing the quiz you are the author of to ensure people can’t upload score results for their own quiz | Go to quiz dashboard, click on your own quiz, click practice, get toast saying you are the author you cannot take this quiz | User should be denied to practicing their own quiz.          | Pressing Practice quiz button on self created quiz does not practice and instead toasts | Pass       |
| I9   | testPracticeSomeonesQuiz                              | Test practicing a quiz you are NOT the author of to see if quizzes can be practiced | Go to quiz dashboard, click on not your own quiz, click practice, go to practice page. | User should be able to practice any quiz that is not their own | Pressing Practice quiz button on non-self created quiz does practice | Pass       |
| I10  | testPracticeQuizFirstATime                            | Test practicing a quiz for the first time                    | Test practicing a quiz, make sure score and first time are equal | User should be able to practice any quiz in initial state when first time | Pressing practice quiz does do it in initial mode the first time | Pass       |
