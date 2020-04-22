package records;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


abstract class StudentDetailsParam {
    // Update/ Add the marks of the student
    abstract void addMarks(HashMap<String, Float> marksObtained);

    /*   DISPLAY RESULT OF THIS STUDENT AS :
     *   Name: Some_Name        Reg No. : some_number
     *   Section: Some_section
     *   Details Marks Below :
     *   Course(credits) : some_course(come_credits)       some_marks(out of 100)       some_grade
     *   repeat above line for all the marks
     *   Percentage Obtained % : some_percent       Grade : some_overall_grade
     *   ``````````````````````````````````````````````````````````````````````````````````````````
     */
    abstract void result(HashMap<String, Integer> map);
}


// Store details of the student
class StudentDetails extends StudentDetailsParam {
    String name;    // store students name
    int regNo;      // store registration number
    String section; // store section of student
    HashMap<String, Float> marks = null;    // store marks in the registered courses for the particular exam
    float percentage = 0.0f;    // Percentage of marks obtained

    StudentDetails(String name, int regNo, String section) {
        this.name = name;
        this.regNo = regNo;
        this.section = section;
    }

    void addMarks(HashMap<String, Float> marksObtained) {
        marks = new HashMap<>(marksObtained);
        if (marks.isEmpty()) System.out.println("\n[NO MARKS AS THERE ARE NO COURSES REGISTERED]");
        else {
            System.out.println("# MARKS ADDED : " + marks);
            marks.forEach((k, v) -> percentage += v);
            percentage = percentage / marks.size();
        }
    }

    void result(HashMap<String, Integer> map) {
        System.out.println();
        System.out.println("Name : " + name + "\tReg No. : " + regNo + "\nSection : " + section);
        System.out.println("Detailed Marks Below :");
        map.forEach((k, v) -> {
            System.out.println("CourseCode(credits) : " + k + "(" + v + ")\t " + marks.get(k) + "(Out of 100)\t" + calculateGrade(marks.get(k)));
        });
        System.out.println("Percentage Obtained % : " + percentage + "%\t Grade : " + calculateGrade(percentage));
        System.out.println("```````````````````````````````````````````````````````````````");
    }

    // Calculate grade based on the number passed in the argument
    static String calculateGrade(float ObtainedMarks) {
        String grade = " ";
        if (ObtainedMarks >= 90) grade = "A+";
        else if (ObtainedMarks >= 80) grade = "A";
        else if (ObtainedMarks >= 70) grade = "B+";
        else if (ObtainedMarks >= 60) grade = "B";
        else if (ObtainedMarks >= 50) grade = "C+";
        else if (ObtainedMarks >= 40) grade = "C";
        else if (ObtainedMarks >= 33) grade = "D+";
        else grade = "F";

        return grade;
    }
}


// Class to store student record for a particular exam
public class Students {
    static Scanner in = new Scanner(System.in);
    ArrayList<StudentDetails> students = null;  // store all the StudentDetails object in it
    Courses courses = null;  // store registered courses for the exam
    boolean marksAdded = false;  // set the marks added for the exam or not

    Students(Courses obj) {
        courses = obj;
        students = new ArrayList<>();
        marksAdded = false;
    }

    // Add new Student details
    void addStudents() {
        char ch = ' ';
        do {
            System.out.print("Enter Registration Number : ");
            int reg = in.nextInt();
            in.nextLine();
            boolean alreadyExist = false;
            for (StudentDetails obj : students) {
                if (obj.regNo == reg) {
                    System.out.println("Details already exists ");
                    alreadyExist = true;
                    break;
                }
            }
            if (alreadyExist) continue;
            System.out.print("Enter Students Name : ");
            String name = in.nextLine();
            System.out.print("Enter Students Section : ");
            String section = in.next();
            students.add(new StudentDetails(name, reg, section));
            System.out.print("Want to add more ? (y/n) ");
            ch = in.next().charAt(0);
            System.out.println();
        } while (ch == 'y');
    }
}
