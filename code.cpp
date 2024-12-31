#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <string.h>
#include <iomanip>
using namespace std;

// Global Variables
int feePerCreditHour = 0;
int numberOfStudents = 0;

// Course Class Starts here
class CourseType
{
private:
  string courseName;
  string courseNumber;
  int courseCredits;

public:
  CourseType() {}

  /* Getters */
  int getCourseCredits() { return courseCredits; }
  string getCourseName() { return courseName; }
  string getCourseNumber() { return courseNumber; }

  /*Combined Setter*/
  void setCourseInfo(string c_name, string c_no, int credits)
  {
    courseName = c_name;
    courseNumber = c_no;
    courseCredits = credits;
  }

  // print function
  void print(ofstream &outputHandler)
  {
    outputHandler << left;
    outputHandler << setw(10) << courseNumber << " ";
    outputHandler << setw(15) << courseName;
    outputHandler << setw(6) << courseCredits << " ";
  }
};
// End of Course Class

// Person Class Starts here
class PersonType
{
protected:
  string firstName;
  string lastName;

  string getFullName() // generating fullName
  {
    return (firstName + " " + lastName);
  }

public:
  /* accessor and mutators*/
  void setFirstName(string fn, string ln)
  {
    firstName = fn;
    lastName = ln;
  }
  string getFirstName() { return firstName; }
  string getLastName() { return lastName; }
};
// End of person class

// Student class starts here
class StudentType : public PersonType
{
private:
  /* Required Data Members */
  CourseType CoursesEnrolled[6];
  char CoursesGrade[6];
  int studentID;
  int numberOfCourses;
  bool isTutionFeePaid;

  /* Private Member Function */
  // function to sort courses array in alphabetical order
  void sortCourses()
  {
    int i, j;
    int minIndex;
    CourseType temp;
    char tempGrade;
    string course1;
    string course2;

    for (i = 0; i < numberOfCourses - 1; i++)
    {
      minIndex = i;

      for (j = i + 1; j < numberOfCourses; j++)
      {
        course1 = CoursesEnrolled[minIndex].getCourseNumber();
        course2 = CoursesEnrolled[j].getCourseNumber();
        // comparing first index of courseNumber strings
        if (course1[0] > course2[0])
          minIndex = j;
      }

      temp = CoursesEnrolled[minIndex];
      CoursesEnrolled[minIndex] = CoursesEnrolled[i];
      CoursesEnrolled[i] = temp;
      tempGrade = CoursesGrade[minIndex];
      CoursesGrade[minIndex] = CoursesGrade[i];
      CoursesGrade[i] = tempGrade;
    }
  }

public:
  StudentType() {}
  // combined setter
  void setInfo(string _firstName, string _lastName, int sID, int noOfCourses,
               bool feePaid, CourseType courseArray[], char grades[])
  {
    firstName = _firstName;
    lastName = _lastName;
    studentID = sID;
    numberOfCourses = noOfCourses;
    isTutionFeePaid = feePaid;
    for (int i = 0; i < numberOfCourses; i++)
    {
      CoursesEnrolled[i] = courseArray[i];
      CoursesGrade[i] = grades[i];
    }
    sortCourses();
  }

  /*Function to calculate total credit hours of a student and then return*/
  int getHoursEnrolled()
  {
    int totalCreditHours = 0;
    for (int i = 0; i < numberOfCourses; i++)
    {
      int temp = CoursesEnrolled[i].getCourseCredits();
      totalCreditHours += temp;
    }
    return totalCreditHours;
  }

  /*function to calculate GPA of students*/
  double getGPA()
  {
    double GPA = 0;
    int totalCredits = getHoursEnrolled();
    double totalGradePoints = 0;
    double tempSum = 0;
    for (int i = 0; i < numberOfCourses; i++)
    {
      tempSum =
          tempSum +
          ((CoursesGrade[i] == 'A')   ? CoursesEnrolled[i].getCourseCredits() * 4
           : (CoursesGrade[i] == 'B') ? CoursesEnrolled[i].getCourseCredits() * 3
           : (CoursesGrade[i] == 'C') ? CoursesEnrolled[i].getCourseCredits() * 2
           : (CoursesGrade[i] == 'D') ? CoursesEnrolled[i].getCourseCredits() * 1
           : (CoursesGrade[i] == 'F') ? CoursesEnrolled[i].getCourseCredits() * 0
                                      : 0);
      totalGradePoints = tempSum;
    }
    GPA = totalGradePoints / totalCredits;
    return GPA;
  }

  // function to calculate remaining billing
  // amount of students if fee is not paid
  double billingAmount(int feePerCreditHour)
  {
    double amount = 0;
    int hours = getHoursEnrolled();
    amount = hours * feePerCreditHour;
    return amount;
  }

  /* function to print data into file in required format */
  void print(ofstream &outputHandler)
  {
    outputHandler << "Student Name      :  " << getFullName() << endl;
    outputHandler << "Student ID        :  " << studentID << endl;
    outputHandler << "Number of courses :  " << numberOfCourses << endl
                  << endl;
    outputHandler << "Course No  Course Name   Credits  Grades" << endl;

    for (int i = 0; i < numberOfCourses; i++)
    {
      CoursesEnrolled[i].print(outputHandler);
      outputHandler << right;
      outputHandler << setw(5) << CoursesGrade[i] << endl;
    }
    outputHandler << "\nTotal Number of credit hours:  "
                  << getHoursEnrolled() << endl;
    if (isTutionFeePaid)
    {
      // setprecision will print 3.5454 as 3.54
      outputHandler << "Mid-Semester GPA :  " << setprecision(3)
                    << getGPA() << endl;
    }
    else
    {
      outputHandler << "*** Grades are being held for not paying the tution ***" << endl;
      // fixed keyword is used to print double values in
      // fixed notation instead of exponential notation
      outputHandler << "Amount Due:  " << setprecision(2) << fixed
                    << billingAmount(feePerCreditHour) << endl;
    }
    outputHandler << "_ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _" << endl
                  << endl;
  }

  // function to display data as output to console window
  void displayInConsole()
  {
    cout << "Student Name      :  " << getFullName() << endl;
    cout << "Student ID        :  " << studentID << endl;
    cout << "Number of courses :  " << numberOfCourses << endl
         << endl;
    cout << "Course No    Course Name    Credits  Grades" << endl;

    for (int i = 0; i < numberOfCourses; i++)
    {
      cout << left;
      cout << setw(12) << CoursesEnrolled[i].getCourseNumber() << " ";
      cout << setw(18) << CoursesEnrolled[i].getCourseName();
      cout << setw(4) << CoursesEnrolled[i].getCourseCredits() << " ";
      cout << right;
      cout << setw(4) << CoursesGrade[i] << endl;
    }
    cout << "\nTotal Number of credit hours:  " << getHoursEnrolled() << endl;
    if (isTutionFeePaid)
    {
      cout << "Mid-Semester GPA :  " << setprecision(2) << getGPA() << endl;
    }
    else
    {
      cout << "***    Grades are being held for not paying the tution     ***" << endl;
      cout << "Amount Due:  " << setprecision(2) << fixed
           << billingAmount(feePerCreditHour) << endl;
    }
    cout << "_ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _" << endl
         << endl;
  }
};
// end of student class

// Read and Write class in association with Student and Course Class
// in this Student is created and initialized with values after reading from file
// and then data is written in file in required pattern
class HelperFucntions
{
private:
  // Arrays
  StudentType stdArray[10];
  CourseType tempCourses[6];
  char grade[6];

  // temporary helper variables
  char tempGrade;
  bool tempIsFeePaid;
  double tempBillingAmount;
  int tempCreditHours, tempStudentID, tempNumberOfStudents, tempNumberOfCourses;
  string line, tempFirstName, tempLastName, tempCourseName, tempCourseNo, temp;
  string l1, l2, l3;

  // indexes
  int CG_index;
  int studentIndex;
  int iterationNumber;
  int numberOfCoursesChecker;

  // private member functions (showing concept of abstraction)

  // function to convert string to integer
  // after reading from file using stringstream
  int stoi(string input)
  {
    int returnMe;
    stringstream(input) >> returnMe;
    return returnMe;
  }

  // given below functions are used in extractor
  void firstLineExtractor()
  {
    temp = line.substr(0, line.find(' ')); // 3
    numberOfStudents = stoi(temp);
    tempNumberOfStudents = numberOfStudents;
    temp = line.substr(line.find(' ') + 1); // 345
    feePerCreditHour = stoi(temp);
    iterationNumber++;
  }
  void nameAndIdExtractor()
  {
    tempFirstName = line.substr(0, line.find(' ')); // Lisa
    l1 = line.substr(line.find(' ') + 1);           // Miller 890238 Y 4
    tempLastName = l1.substr(0, l1.find(' '));      // Miller
    l2 = l1.substr(l1.find(' ') + 1);               // 890238 Y 4
    temp = l2.substr(0, l2.find(' '));              // 890238
    tempStudentID = stoi(temp);
    temp = l2.substr(l2.find(' ') + 1); // Y 4
    tempIsFeePaid = ((temp[0] == 'Y') ? true : false);
    temp = temp.substr(temp.find(' ') + 1);
    tempNumberOfCourses = stoi(temp); // 4
    numberOfCoursesChecker = tempNumberOfCourses;
    CG_index = 0;
  }
  void coursesDataExtractor()
  {
    tempCourseName = line.substr(0, line.find(' ')); // Mathematics MTH345 4 A
    l1 = line.substr(line.find(' ') + 1);            // MTH345 4 A
    tempCourseNo = l1.substr(0, l1.find(' '));
    l2 = l1.substr(l1.find(' ') + 1); //  4 A
    temp = l2.substr(0, l2.find(' '));
    tempCreditHours = stoi(temp);
    temp = l2.substr(l2.find(' ') + 1); //   A
    tempGrade = temp[0];
    tempCourses[CG_index].setCourseInfo(tempCourseName,
                                        tempCourseNo, tempCreditHours);
    grade[CG_index] = tempGrade;
    CG_index++;
    numberOfCoursesChecker--;
  }

public:
  HelperFucntions() {}
  ~HelperFucntions()
  {
    cout
        << "\t\tObjects Destroyed___________Program Ended" << endl;
  }

  /*
  function to read student data from file line by line and extracting data
  simultaneously and at the end assigning values to All students.
  */
  void readingAndExtractingStudentData(ifstream &inputHandler)
  {
    iterationNumber = 1;
    studentIndex = 0;
    while (inputHandler.peek() != EOF)
    {
      if (iterationNumber == 1)
      {
        getline(inputHandler, line, '\n'); // 3 345
        firstLineExtractor();
      }
      if (iterationNumber == 2)
      {
        getline(inputHandler, line, '\n'); // Lisa Miller 890238 Y 4
        nameAndIdExtractor();
        while (numberOfCoursesChecker > 0 && tempNumberOfStudents > 0)
        {
          getline(inputHandler, line, '\n'); // Mathematics MTH345 4 A
          coursesDataExtractor();
          if (numberOfCoursesChecker == 0)
          {
            stdArray[studentIndex].setInfo(tempFirstName,
                                           tempLastName,
                                           tempStudentID,
                                           tempNumberOfCourses,
                                           tempIsFeePaid,
                                           tempCourses,
                                           grade);
            getline(inputHandler, line, '\n');
            studentIndex++;
            tempNumberOfStudents--;
            break;
          }
        }
      }
    }
  }

  // function to print all students data to file and also on console screen
  void writingFormattedDataIntoFile(ofstream &outputHandler)
  {
    for (int i = 0; i < numberOfStudents; i++)
    {
      stdArray[i].print(outputHandler); // writing to file
      stdArray[i].displayInConsole();   // printing on screen
    }
  }
};

int main()
{
  HelperFucntions instance;
  // creating an input file handler to read file
  ifstream inputHandler;
  system("CLS");
  inputHandler.open("Student-Input.txt", ios::in);
  if (!inputHandler)
  {
    cout << "Unable to Open File!" << endl;
    return 0;
  }
  // extracting data and assigning values to object data members
  instance.readingAndExtractingStudentData(inputHandler);
  inputHandler.close();

  cout << "A File named Student-Output has been created with the following data........." << endl;
  ofstream outputHandler;
  outputHandler.open("Student-Output.txt", ios::out);
  // writing data into new file after formatting and also printing on screen
  instance.writingFormattedDataIntoFile(outputHandler);
  outputHandler.close();
  return 0;
}