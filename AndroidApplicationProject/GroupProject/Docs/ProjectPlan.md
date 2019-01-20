# Project Plan

**Author**: Team 28

## 1. Introduction

The aim of this project is to implement an Android app that will allow users to create or practice quizzes that have been created by other students.

## 2. Process Description

**A. Inception:**

*Description:*

The main goals of this phase is to establish a design for the project and get information about the end users and how interaction with the application can bring value to them as a customer. We also discussed basic front end specifications such as how the app should look like and what the expected functions or user interactions are. We also identified the scope for the initial release of the app and how it would line up with the business case.

*Entrance Criterias:*
- Complete set of requirements for the app defined by the end user
- Includes broad overview of what task users are doing and some insight on the system service features

*Exit Criterias:*
- Understand what tasks the end users had to accomplish (practice, create and remove quizzes)
- Identify targeted hardware (android tablet that ran app locally)

**B. Elaboration:**

*Description:*

This phase will identify and map each requirement to the classes, their attributes and methods and the corresponding relationships to one another in order to verify the app’s functionality. We will create the UML diagram using the previously identified information to visualize each relationship of a class and how they interact with one another.

*Entrance Criterias:*
- Extract precise set of requirements identifying each relationship between the - classes and further detail on how the relationship behaves
- More detail on class attributes and method parameters and expected output

*Exit Criterias:*
- Define and Verify all requirements have been cross checked
- A drafted Functional UML diagram which satisfies all the requirements visually representing the system design at a low level
- The UML, when brought into the next phase, where each defined classes and objects with each of its methods and attributes will be implemented in code


**C. Construction:**
*Description:*

During this phase we will use the UML design to begin the template of our code. Both front-end and back-end will be developed independently. Each iteration will be introduced by a new feature. This will be repeated until all requirements are satisfied.

*Entrance Criterias:*
- Fully functional UML diagram with detailed class attributes, method parameters and function types
- A design of the app layout given by the end user/customer
- Contains various events that occur during execution of the app (like on click or event listeners) and how each should behave within the app

*Exit Criterias:*
- Deliver final version of code implementation to QA for various testing and requirements validation

**D. Transition:**  
*Description:*

In this phase, all of the application's activities function will be tested by JUnit automated test. The purpose of the phase is to find out the existence of any bugs before project release. First phase of testing will introduce unit tests. Once Unit tests pass we will construct integration tests. All errors discovered throughout each iteration will be addressed and corrected before moving onto the next phase.

*Entrance Criterias:*
- All application activities functions should be implemented.
- Test cases for each application functions should be well defined and implemented.

*Exit Criterias:*
- Unit test of all application activities functions test cases should pass.
- Final version to be deployed to the customer/end user

## 3. Team
| Role Title          | Description                                                                                                           | Assigned To                             |
|---------------------|-----------------------------------------------------------------------------------------------------------------------|-----------------------------------------|
| Project Manager     | Ensures deadlines are met on a weekly basis; manages team members; manages weekly tasks                                                     | Adrian Alonzo      |
| Designer            | Focuses on user experience and user interface design                                                                  | Adrian Alonzo            |
| QA Developer        | Constructs testing Unit, Integration and System testing; tracks and reports bugs;                                                                         | Sriram Anne, Wong Kun Ng, Adrian Alonzo                                     |
| Back End Developer  | Designs the backend architecture, performs database management;  Participates with QA developer unit testing database calls | Sriram Anne, Wong Kun Ng, Adrian Alonzo                                     |
| Front End Developer | Constructs user interface; Participates in QA with integration tests using Espresso                                            | Sriram Anne, Wong Kun Ng, Adrian Alonzo |
