# University Grade Report System

## Overview
This document details the classes, their properties, methods, and relationships required to implement a Java program for generating grade reports for students at CUST university. The program reads data from a file, processes it, and generates appropriate grade reports based on whether students have paid their tuition fees.

## Classes and Their Relationships

### 1. `CourseType`
Represents a course with its details.

#### Properties:
- `private String courseName`
- `private String courseNumber`
- `private int courseCredits`

#### Methods:
- **Constructor**:
  - `public CourseType()`
  - `public CourseType(String courseName, String courseNumber, int courseCredits)`
- **Setters**:
  - `public void setCourseInfo(String courseName, String courseNumber, int courseCredits)`
- **Getters**:
  - `public String getCourseName()`
  - `public String getCourseNumber()`
  - `public int getCourseCredits()`
- **Print Method**:
  - `public void print(PrintWriter outputHandler)`

---

### 2. `PersonType`
Represents a person with a first name and last name.

#### Properties:
- `protected String firstName`
- `protected String lastName`

#### Methods:
- **Constructor**:
  - `public PersonType()`
  - `public PersonType(String firstName, String lastName)`
- **Setters**:
  - `public void setName(String firstName, String lastName)`
- **Getters**:
  - `public String getFirstName()`
  - `public String getLastName()`
  - `public String getFullName()`

---

### 3. `StudentType` (extends `PersonType`)
Represents a student with additional details like student ID, courses enrolled, grades, and tuition fee status.

#### Properties:
- `private final int MAX_COURSES = 6`
- `private CourseType[] coursesEnrolled = new CourseType[MAX_COURSES]`
- `private char[] grades = new char[MAX_COURSES]`
- `private int studentID`
- `private int numberOfCourses`
- `private boolean isTuitionFeePaid`

#### Methods:
- **Constructor**:
  - `public StudentType()`
- **Setters**:
  - `public void setInfo(String firstName, String lastName, int studentID, int numberOfCourses, boolean isTuitionFeePaid, CourseType[] coursesEnrolled, char[] grades)`
- **Getters**:
  - `public int getHoursEnrolled()`
  - `public double getGPA()`
  - `public double billingAmount(int feePerCreditHour)`
- **Print Methods**:
  - `public void print(PrintWriter outputHandler)`
  - `public void displayInConsole()`
- **Private Methods**:
  - `private void sortCourses()`

---

## Main Program

### Main Class: `Main`
Handles reading data from the input file, processing it, and generating the output.

#### Properties:
- `final int MAX_NO_OF_STUDENTS = 10`
- `StudentType[] studentList = new StudentType[MAX_NO_OF_STUDENTS]`
- `int noOfStudents = 0`
- `int tuitionRate = 0`

#### Methods:
- **Main Method**:
  - `public static void main(String[] args)`
    - Reads the number of students and tuition rate from the input file.
    - Reads and processes each student's data.
    - Generates and prints grade reports for each student.

### Function to Get Student Data
This function reads the student data from the input file and loads it into the `studentList` array.

#### Details:
- **Parameters**:
  - A parameter to access the array `studentList`.
  - A parameter to know the number of students registered.
- **Steps**:
  - For each student in the university, get the first name, last name, student ID, and tuition payment status.
  - Get the number of courses the student is taking.
  - For each course, get the course name, course number, credit hours, and grade.
  - Load the course information into a `CourseType` object.
  - Load the data into a `StudentType` object.

---

## Detailed Working of Important Functions

### 1. `setCourseInfo` in `CourseType`
Sets the values of the private member variables according to the values of the parameters.

### 2. `print` in `CourseType`
Prints the course information:
- Prints the course number.
- Prints the course name.
- Prints the credit hours.

### 3. `setInfo` in `StudentType`
Initializes the private member variables according to the incoming parameters and then calls the `sortCourses` method to sort the array `coursesEnrolled` by course number.

### 4. `sortCourses` in `StudentType`
Sorts the array `coursesEnrolled` by course number using the selection sort algorithm.

### 5. `getHoursEnrolled` in `StudentType`
Calculates and returns the total credit hours that a student is taking by adding the credit hours of each course in which the student is enrolled.

### 6. `getGPA` in `StudentType`
Calculates a student's GPA:
- Finds the equivalent points for each grade.
- Adds the points.
- Divides the sum by the total credit hours the student is taking.

### 7. `billingAmount` in `StudentType`
Calculates and returns the amount due based on the number of credit hours enrolled if a student has not paid the tuition.

### 8. `print` in `StudentType`
Prints the grade report:
- Outputs the student's name.
- Outputs the student's ID.
- Outputs the number of courses in which the student is enrolled.
- Outputs the heading:
  - ```Course No  Course Name    Credits    Grade```

- Prints each course's information.
- If `isTuitionPaid` is true, outputs the grade.
- Otherwise, outputs three stars.
- Prints the total credit hours.
- If `isTuitionPaid` is true, outputs the GPA.
- Otherwise, outputs the billing amount and a message about withholding the grades.

---

## Sample Input Data
```txt
3 345
Lisa Miller 890238 Y 4
Mathematics MTH345 4 A
Physics PHY357 3 B
ComputerSci CSC478 3 B
History HIS356 3 A

Bill Wilton 798324 N 5
English ENG378 3 B
Philosophy PHL534 3 A
Chemistry CHM256 4 C
Biology BIO234 4 A
Mathematics MTH346 3 C

Dandy Goat 746333 Y 6
History HIS101 3 A
English ENG328 3 B
Mathematics MTH137 3 A
Chemistry CHM348 4 B
ComputerSci CSC201 3 B
Business BUS128 3 C
```

This document outlines the structure and functionality of the Java program to generate grade reports for students based on their tuition status and grades. The classes and methods described should be implemented to achieve the desired functionality.
