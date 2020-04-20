package records;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

abstract class StudentDetailsParam {
    abstract void displaySDetails();
    abstract void addMarks(HashMap<String, Float> marksObtained);
    abstract void result(HashMap<String, Integer> map);
}
class StudentDetails extends StudentDetailsParam {
    String name;
    int regNo;
    String section;
    HashMap<String, Float> marks = null;
    float percentage = 0.0f;
    StudentDetails(String name, int regNo, String section) {
        this.name = name;
        this.regNo = regNo;
        this.section = section;
    }
    void displaySDetails() {
        System.out.println("Name : " + name);
        System.out.println("Registration No. : " + regNo);
        System.out.println("Section : " + section );
        System.out.println("---------------------------------------");
    }
    void addMarks(HashMap<String, Float> marksObtained) {
        marks = new HashMap<>(marksObtained);
        if(marks.isEmpty())
            System.out.println("\n[NO MARKS AS THERE ARE NO COURSES REGISTERED]");
        else {
            System.out.println("# MARKS ADDED  : " + marks);
            marks.forEach((k, v) -> {
                percentage += v;
            });
            percentage = percentage / marks.size();
        }
    }
    void result(HashMap<String, Integer> map) {
        System.out.println();
        System.out.println("Name : " + name + "\tReg No. : " + regNo + "\nSection : " + section);
        System.out.println("Detailed Marks Below :");
        map.forEach((k, v)-> {
            System.out.println("CourseCode(credits) : " + k + "(" + v + ")\t " + marks.get(k) + "(Out of 100)\t" + calculateGrade(marks.get(k)));
        });
        System.out.println("Percentage Obtained % : " + percentage + "%\t Grade : " + calculateGrade(percentage));
        System.out.println("```````````````````````````````````````````````````````````````");
    }
    static String calculateGrade(float ObtainedMarks) {
        String grade = " ";
        if (ObtainedMarks >= 90) {
            grade = "A+";
        } 
        else if (ObtainedMarks >= 80) {
            grade = "A";
        } 
        else if (ObtainedMarks >= 70) {
            grade = "B+";
        } 
        else if (ObtainedMarks >= 60) {
            grade = "B";
        } 
        else if (ObtainedMarks >= 50) {
            grade = "C+";
        } 
        else if(ObtainedMarks >= 40)
            grade = "C";
        else if(ObtainedMarks >= 33) 
            grade = "D";
        else 
            grade = "F";
        return grade;
    }

}
public class Students {
    static Scanner in = new Scanner(System.in);
    ArrayList<StudentDetails> students = null;
    Courses courses = null;
    boolean marksAdded = false;
    Students(Courses obj) {
        courses = obj;
        students = new ArrayList<>();
        marksAdded = false;
    }
    void addStudents() {
        char ch = ' ';
        do {
            System.out.print("Enter Registration Number : ");
            int reg = in.nextInt();
            in.nextLine();
            boolean alreadyExist = false;
            for(int i = 0; i < students.size(); i++) {
                if(students.get(i).regNo == reg) {
                    System.out.println("Details already exists ");
                    alreadyExist = true;
                }
            }
            if(alreadyExist)    continue;
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
    static void display(Students obj) {
        System.out.println("Registered students for this course are :");
        obj.students.forEach((StudentDetails o)-> o.displaySDetails());
    }
}   