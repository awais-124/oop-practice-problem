import java.io.PrintWriter; // used for writing data to files, used in printing methods in code
import java.io.BufferedReader; // used for reading data from file.
import java.io.BufferedWriter;
import java.io.FileReader; // used to read file char-by-char.
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays; // used for array operations, like copying etc. 

/* 
    COPYRIGHT BCS221093-MUHAMMAD AWAIS 2025
*/

public class BCS221093 {

    // Global Variables started
    static int feePerCreditHour = 0;
    static int numberOfStudents = 0;
    static int MAX_COURSES = 6; // (6) given in question
    static int MAX_NO_OF_STUDENTS = 10; // given in question
    // Global Variables ended

    /* CLASS COURSE STARTED */
    static class CourseType {
        private String courseName;
        private String courseNumber;
        private int courseCredits;

        /* Default Constructor */
        public CourseType() {
            courseName = "_";
            courseNumber = "_";
            courseCredits = 0;
        }

        /* Getters */
        public int getCourseCredits() { return courseCredits; }
        public String getCourseName() { return courseName; }
        public String getCourseNumber() { return courseNumber; }

        /* Combined Setter */
        public void setCourseInfo(String c_name, String c_no, int credits) {
            courseName = c_name;
            courseNumber = c_no;
            courseCredits = credits;
        }

        // Print function
        public void print(PrintWriter outputHandler) {
            outputHandler.printf("%-10s %-15s %-10d ", courseNumber, courseName, courseCredits);
        }   
    }
    /* CLASS COURSE ENDED */

    /* CLASS PERSON STARTED */
    static class PersonType {
        protected String firstName;
        protected String lastName;

        protected String getFullName() {
            // generating fullName
            return (firstName + " " + lastName);
        }

        /* Accessor and Mutators */
        public void setFirstName(String fn, String ln) {
            firstName = fn;
            lastName = ln;
        }

        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
    }
    /* CLASS PERSON ENDED */

    /* CLASS STUDENT STARTED */
    static class StudentType extends PersonType {
        /* Required Data Members */
        private CourseType[] CoursesEnrolled = new CourseType[MAX_COURSES];
        private char[] CoursesGrade = new char[MAX_COURSES];
        private int studentID;
        private int numberOfCourses;
        private boolean isTutionFeePaid;

        private void sortCourses() {
            // function to sort courses array in alphabetical 
            // order based on the courseNumber
            for (int i = 0; i < numberOfCourses - 1; i++) {
                int minIndex = i;
                for (int j = i + 1; j < numberOfCourses; j++) {
                    String course1 = CoursesEnrolled[minIndex].getCourseNumber();
                    String course2 = CoursesEnrolled[j].getCourseNumber();
                    
                    // Compare the full strings using compareTo method
                    if (course1.compareTo(course2) > 0) {
                        minIndex = j;
                    }
                }
                // Swap courses and grades
                CourseType temp = CoursesEnrolled[minIndex];
                CoursesEnrolled[minIndex] = CoursesEnrolled[i];
                CoursesEnrolled[i] = temp;

                char tempGrade = CoursesGrade[minIndex];
                CoursesGrade[minIndex] = CoursesGrade[i];
                CoursesGrade[i] = tempGrade;
            }
        }

        /* DEFAULT CONSTRUCTOR */
        public StudentType() {}

        public void setInfo(String _firstName, String _lastName, int sID, int noOfCourses,
                            boolean feePaid, CourseType[] courseArray, char[] grades) {
            firstName = _firstName;
            lastName = _lastName;
            studentID = sID;
            numberOfCourses = noOfCourses;
            isTutionFeePaid = feePaid;
            System.arraycopy(courseArray, 0, CoursesEnrolled, 0, numberOfCourses);
            System.arraycopy(grades, 0, CoursesGrade, 0, numberOfCourses);
            sortCourses();
        }

        public int getHoursEnrolled() {
            // Function to calculate total credit 
            // hours of a student
            int totalCreditHours = 0;
            for (int i = 0; i < numberOfCourses; i++) {
                totalCreditHours += CoursesEnrolled[i].getCourseCredits();
            }
            return totalCreditHours;
        }

        public double getGPA() {
            // Function to calculate GPA of students
            double totalGradePoints = 0;
            for (int i = 0; i < numberOfCourses; i++) {
                int credits = CoursesEnrolled[i].getCourseCredits();
                totalGradePoints += (CoursesGrade[i] == 'A') ? credits * 4 :
                                    (CoursesGrade[i] == 'B') ? credits * 3 :
                                    (CoursesGrade[i] == 'C') ? credits * 2 :
                                    (CoursesGrade[i] == 'D') ? credits * 1 : 0;
            }
            return totalGradePoints / getHoursEnrolled();
        }

        public double billingAmount(int feePerCreditHour) {
            // Function to calculate remaining billing amount of students if fee is not paid
            return getHoursEnrolled() * feePerCreditHour;
        }

        public void print(PrintWriter outputHandler) {
            // Function to print data into file in required format
            outputHandler.println("Student Name      :  " + getFullName());
            outputHandler.println("Student ID        :  " + studentID);
            outputHandler.println("Number of courses :  " + numberOfCourses + "\n");
            outputHandler.println("Course No  Course Name     Credits    Grades");
            outputHandler.println("_________  ____________    _______    ______\n");

            for (int i = 0; i < numberOfCourses; i++) {
                CoursesEnrolled[i].print(outputHandler);
                if (isTutionFeePaid){
                    outputHandler.printf("%-5s\n", Character.toString(CoursesGrade[i]));
                } else {
                    String stars = "***";
                    outputHandler.printf("%-5s\n", stars);
                }
            }
            outputHandler.println("\nTotal Number of credit hours:  " + getHoursEnrolled());
            if (isTutionFeePaid) {
                outputHandler.printf("Mid-Semester GPA :  %.2f\n", getGPA());
            } else {
                outputHandler.println("*** Grades are being held for not paying the tuition ***");
                outputHandler.printf("Amount Due:  %.2f\n", billingAmount(feePerCreditHour));
            }
            outputHandler.println("_ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _\n");
        }

        public void displayInConsole() {
            // Function to display data as output to console window
            System.out.println("Student Name      :  " + getFullName());
            System.out.println("Student ID        :  " + studentID);
            System.out.println("Number of courses :  " + numberOfCourses + "\n");
            System.out.println("Course No  Course Name     Credits    Grades");
            System.out.println("_________  ____________    _______    ______\n");

            for (int i = 0; i < numberOfCourses; i++) {
                String gradeToPrint = isTutionFeePaid ? Character.toString(CoursesGrade[i]) : "***";

                System.out.printf("%-10s %-15s %-10d %-5s\n",
                        CoursesEnrolled[i].getCourseNumber(),
                        CoursesEnrolled[i].getCourseName(),
                        CoursesEnrolled[i].getCourseCredits(),
                        gradeToPrint);
            }
            System.out.println("\nTotal Number of credit hours:  " + getHoursEnrolled());
            if (isTutionFeePaid) {
                System.out.printf("Mid-Semester GPA :  %.2f\n", getGPA());
            } else {
                System.out.println("*** Grades are being held for not paying the tuition ***");
                System.out.printf("Amount Due:  %.2f\n", billingAmount(feePerCreditHour));
            }
            System.out.println("_ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _ * _\n");
        }
    }
    /* CLASS STUDENT ENDED */

    /* 
    * SupportOperations Class:
    * This class is created to perform all processing required to 
    * read data from file and write in required format. It has necessary members 
    * to read data correctly and methods to execute logic.
    */
    // CLASS SUPPORT-OPERATIONS STARTED 
    static class SupportOperations {
        StudentType[] stdArray = new StudentType[MAX_NO_OF_STUDENTS];
        CourseType[] tempCourses = new CourseType[MAX_COURSES];
        char[] grade = new char[MAX_COURSES];

        // Temporary support variables
        char tempGrade = 'A';
        boolean tempIsFeePaid = false;
        int tempCreditHours = 0, tempStudentID = 0, tempNumberOfStudents = 10, tempNumberOfCourses = 0;
        String line = "", tempFirstName = "", tempLastName = "", tempCourseName = "", tempCourseNo = "", temp = "";
        String l1="", l2="", l3=""; // used for extracting parts of data from a single line

        int CG_index=0; // course grade index
        int studentIndex=0;
        int iterationNumber=0;
        int numberOfCoursesChecker=0;

        /* DEFAULT CONSTRUCTOR */
        public SupportOperations() {
            for (int i = 0; i < MAX_NO_OF_STUDENTS; i++) {
                stdArray[i] = new StudentType();  
            }
            
            for (int i = 0; i < MAX_COURSES; i++) {
                tempCourses[i] = new CourseType();  
                grade[i] = 'C';  // default grade
            }
        }

        /* converts string to integer, necessary because data read from file is
        in string type, and we need marks in int type, so this converts data type */
        private int stoi(String input) {
            return Integer.parseInt(input);
        }

        /* this method extract data from first line of file that is
        number of students and feePerCredit */
        private void firstLineExtractor() {
            temp = line.substring(0, line.indexOf(' ')); // extract num till space
            numberOfStudents = stoi(temp);
            tempNumberOfStudents = numberOfStudents;
            temp = line.substring(line.indexOf(' ') + 1);  // extract num after first space till end
            feePerCreditHour = stoi(temp);
            iterationNumber++;
        }

        private void nameAndIdExtractor() {
            tempFirstName = line.substring(0, line.indexOf(' ')); // Lisa - firstName
            l1 = line.substring(line.indexOf(' ') + 1);           // Miller 890238 Y 4 - rest of the line
            tempLastName = l1.substring(0, l1.indexOf(' '));      // Miller - lastName
            l2 = l1.substring(l1.indexOf(' ') + 1);               // 890238 Y 4 - rest of the line
            temp = l2.substring(0, l2.indexOf(' '));              // 890238 - id
            tempStudentID = stoi(temp);
            temp = l2.substring(l2.indexOf(' ') + 1); // Y 4
            tempIsFeePaid = (temp.charAt(0) == 'Y' || temp.charAt(0) == 'y');
            temp = temp.substring(temp.indexOf(' ') + 1);
            tempNumberOfCourses = stoi(temp); // 4
            numberOfCoursesChecker = tempNumberOfCourses;
            CG_index = 0;
        }

        private void coursesDataExtractor() {
            tempCourseName = line.substring(0, line.indexOf(' ')); // Mathematics MTH345 4 A
            l1 = line.substring(line.indexOf(' ') + 1);            // MTH345 4 A
            tempCourseNo = l1.substring(0, l1.indexOf(' '));
            l2 = l1.substring(l1.indexOf(' ') + 1); //  4 A
            temp = l2.substring(0, l2.indexOf(' '));
            tempCreditHours = stoi(temp);
            temp = l2.substring(l2.indexOf(' ') + 1); //   A
            tempGrade = temp.charAt(0);
            tempCourses[CG_index] = new CourseType();
            tempCourses[CG_index].setCourseInfo(tempCourseName, tempCourseNo, tempCreditHours);
            grade[CG_index] = tempGrade;
            CG_index++;
            numberOfCoursesChecker--;
        }

       public void readingAndSavingDataInVariables(BufferedReader inputHandler) {
            iterationNumber = 1;
            studentIndex = 0;
        
            try {
                while (inputHandler.ready() && tempNumberOfStudents > 0) {
                    line = inputHandler.readLine();
                    if(line.isEmpty()) continue;
                    if (iterationNumber == 1) {
                        firstLineExtractor();
                    } else {
                        if (tempNumberOfStudents > 0) {
                            nameAndIdExtractor();
                            while (numberOfCoursesChecker > 0 && tempNumberOfStudents > 0) {
                                line = inputHandler.readLine();
                                coursesDataExtractor();
                                if (numberOfCoursesChecker == 0) {
                                    stdArray[studentIndex] = new StudentType();
                                    stdArray[studentIndex].setInfo(tempFirstName, tempLastName,
                                            tempStudentID, tempNumberOfCourses, tempIsFeePaid, tempCourses, grade);
                                    studentIndex++;
                                    tempNumberOfStudents--;
                                    break;
                                }
                            }
                        }
                    }
                    iterationNumber++;
                }
            } catch (IOException e) {
                System.err.println("Something went wrong! \nError message : " + e.getMessage());
            }
        }



        public void writingFormattedDataIntoFile(PrintWriter outputHandler) {
            // Function to print all students data to file and also on console screen
            for (int i = 0; i < numberOfStudents; i++) {
                stdArray[i].print(outputHandler); // writing to file
                stdArray[i].displayInConsole();   // printing on screen
            }
        }
    }
    /* CLASS SUPPORT-OPERATIONS ENDED */


    /* MAIN FUNCTION STARTED */
    public static void main(String[] args) {
         SupportOperations support = new SupportOperations();

            // Creating an input file handler to read file
            try (BufferedReader inputHandler = new BufferedReader(new FileReader("inputs.txt"))) {
                // Extracting data and assigning values to object data members
                support.readingAndSavingDataInVariables(inputHandler);
            } catch (IOException e) {
                System.out.println("Unable to Open File!");
                return;
            }

            System.out.println("A File named Outputs has been created with Result Reports of All students.");
            System.out.println("Reports of Students are as follows : \n");
            try (PrintWriter outputHandler = new PrintWriter(new BufferedWriter(new FileWriter("outputs.txt")))) {
                // Writing data into new file after formatting and also printing on screen
                support.writingFormattedDataIntoFile(outputHandler);

            } catch (IOException e) {
                System.out.println("Unable to Open File!");
                return;
            }
        }
    }
    /* MAIN FUNCTION ENDED */