# Design Document

**Author**: Team 28

## 1. Design Considerations

## 1.1 Assumptions

- During the design of this app, we had assumed that there would be no issue of concurrency meaning that there would be only one user accessing the system at a single time for the initial development process
- We have assumed that this app will be restricted to mobile Android devices only including tablets

## 1.2 Constraints

- This application will need to have read and write access to a database namely a SQLite database
- Users will have access to quiz class and its methods although will have a check constraint when remove quiz method is called for a certain user
- Users and quizzes will also have access to the scoreboard interface so that each of their methods can access it


## 1.3 System Environment

- App will be developed in Android Studio
- All the devices that can use the app must be an Android device
- APK of 23 or higher (Android 6.0-marshmallow- or higher)

## 2. Architectural Design

## 2.1 Component Diagram

- This diagram below lists out the components involved in this application
- Also shows how each component is linked to each other

![Component Diagram](https://github.gatech.edu/gt-omscs-se-2018fall/6300Fall18Team28/blob/master/GroupProject/Docs/Diagrams/Component%20Diagram%20for%20D2.png)

## 2.2 Deployment Diagram

- This is an example of a simple system where we run this application on local devices.
- This implementation does not require any deployment diagram

## 3. Low-Level Design

## 3.1 Class Diagram

- The diagram below shows the UML class diagram for the application
- Further operations can be seen in the [Use Case Diagram](https://github.gatech.edu/gt-omscs-se-2018fall/6300Fall18Team28/blob/master/GroupProject/Docs/Diagrams/UseCaseModel.png) and the sequence diagram below

![Class Diagram](https://github.gatech.edu/gt-omscs-se-2018fall/6300Fall18Team28/blob/master/GroupProject/Docs/Diagrams/Team28FinalUML.png)

## 3.2 Other Diagram

- This diagram below is the sequence diagram which shows the control flow of the entire application process
- Includes how the system interacts with the database and how the user interacts with the system

![Sequence Diagram](https://github.gatech.edu/gt-omscs-se-2018fall/6300Fall18Team28/blob/master/GroupProject/Docs/Diagrams/Sequence%20Diagram.PNG)

## 4. User Interface Design

1. **Login Screen**

![Login Diagram](https://github.gatech.edu/gt-omscs-se-2018fall/6300Fall18Team28/blob/master/GroupProject/Docs/Diagrams/LoginScreen.png)

2. **Register Screen**

![Register Diagram](https://github.gatech.edu/gt-omscs-se-2018fall/6300Fall18Team28/blob/master/GroupProject/Docs/Diagrams/RegisterScreen.png)

3. **Home Screen**

![Home Page](https://github.gatech.edu/gt-omscs-se-2018fall/6300Fall18Team28/blob/master/GroupProject/Docs/Diagrams/DashboardScreen.png)

4. **Add Quiz**

![Add Quiz](https://github.gatech.edu/gt-omscs-se-2018fall/6300Fall18Team28/blob/master/GroupProject/Docs/Diagrams/CreateQuizScreen.png)

5. **Practice Quiz**

![Practice Quiz](https://github.gatech.edu/gt-omscs-se-2018fall/6300Fall18Team28/blob/master/GroupProject/Docs/Diagrams/PracticeQuizScreen.png)

6. **View Quiz**

![View Quiz](https://github.gatech.edu/gt-omscs-se-2018fall/6300Fall18Team28/blob/master/GroupProject/Docs/Diagrams/ViewQuizScreen.png)

7. **Display Results**

![Display Results](https://github.gatech.edu/gt-omscs-se-2018fall/6300Fall18Team28/blob/master/GroupProject/Docs/Diagrams/DisplayResultsScreen.png)

8. **Remove Quiz**

![Remove Quiz](https://github.gatech.edu/gt-omscs-se-2018fall/6300Fall18Team28/blob/master/GroupProject/Docs/Diagrams/RemoveQuiz.PNG)
