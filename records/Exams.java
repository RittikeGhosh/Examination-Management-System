package records;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.Scanner;

abstract class ExamDetailsParams {
    abstract void display();
    abstract void addMarks();
    abstract void result();
}
class ExamDetails extends ExamDetailsParams {
    static Scanner in = new Scanner(System.in);
    LocalDate date = null;
    String venue = null;
    Courses courses = null;
    Students students = null;

    ExamDetails(LocalDate date) {
        this.date = date;
        System.out.print("$ EXAM VENUE => ");
        venue = in.next();
        System.out.print("\n@[COURSE REGISTRATION] ");
        this.courses = new Courses();
        courses.addCourse();
        System.out.print("\n@[STUDENT REGISTRATION]\n");
        this.students = new Students(courses);
        students.addStudents();
        // display();
    }

    void display() {
        System.out.println();
        System.out.print("Date : " + date);
        System.out.println("\tVenue : " + venue);
        Courses.display(courses);
        Students.display(students);
    }

    void addMarks() {
        if(students.marksAdded) {
            System.out.println("MARKS ALREADY ADDED !");
            System.out.print("  1. UPDATE MARKS    0. ABORT AND GO BACK $(Your Choice) => ");
            char ch = in.next().charAt(0);
            if(ch != '1') {
                return;
            }
        }
        students.students.forEach((StudentDetails s)-> {
            System.out.println("\n# STUDENT'S NAME => " + s.name);
            HashMap<String, Float> temp_map = new HashMap<>();
            courses.courses.forEach((k, v)-> {
                System.out.print("$ MARKS IN " + k + " (out of 100) => ");
                float mark = in.nextFloat();
                temp_map.put(k, mark);
            });
            s.addMarks(temp_map);
        });
        students.marksAdded = true;
    }
    void result(){
        if(students.marksAdded == false) {
            System.out.println("MARKS IS NOT ADDED for this Exam ");
            System.out.print("DO YOU WANT TO ADD MARKS NOW ? $(Your CHoice y/n) => ");
            char ch = in.next().charAt(0);
            if(ch == 'y') {
                addMarks();
            }
            else    return;
        }
        System.out.println("\n# EXAM REPORT : ");
        students.students.forEach((StudentDetails s)-> {
            s.result(courses.courses);
        });
    }
}

interface ExamParams {
    boolean isPresent(LocalDate date);
    int size();
    void addDate(LocalDate date);
    void listAll(boolean upcomming, LocalDate date);
    LocalDate getDate(int i);
    String getVenue(int i);
    void getCourses(int i);
    void addMarks(int i);
    void result(int i);
}
// singleton class
public class Exams implements ExamParams{
    // class object -> singleton
    private static Exams singleton = null;
    private Exams() {
        exams = new ArrayList<>();
    }
    public static Exams getInstance() {
        if (singleton == null)
            singleton = new Exams();
        return singleton;
    }

    public ArrayList<ExamDetails> exams;
    // is the passed exam already added
    public boolean isPresent(LocalDate date) {
        for(ExamDetails o : exams) {
            if(o.date.isEqual(date))  return true;
        }
        return false;
    }
    // return size of number of exams
    public int size() {
        return exams.size();
    }
    // add new new exam
    public void addDate(LocalDate date) {
        if(exams.size() == 0 || exams.get(exams.size() - 1).date.isBefore(date))
            exams.add(new ExamDetails(date));
        else {
            int i = 0;
            for(i = 0; exams.get(i).date.isBefore(date) && i < exams.size(); i++);
            exams.add(i, new ExamDetails(date));
        }
    }
    // list all exams
    public void listAll(boolean upcomming, LocalDate date) {
        if(upcomming) {
            boolean present = false;
            for (int i = 0; i < exams.size(); i++) {
                if(exams.get(i).date.isAfter(date) || exams.get(i).date.isEqual(date)) {
                    System.out.println("    " + (i + 1) + ". " + exams.get(i).date);
                    present = true;
                }
            }
            if(!present)
                System.out.println("  + NO UPCOMMING EXAMS");
        }
        else {
            boolean present = false;
            for (int i = 0; i < exams.size(); i++) {
                if(exams.get(i).date.isBefore(date)) {
                    System.out.println("    " + (i + 1) + ". " + exams.get(i).date);
                    present = true;
                }
            }
            if (!present)
                System.out.println("  + NO PAST EXAMS");
        }
    }
    public LocalDate getDate(int i) {
        return exams.get(i).date;
    }
    public String getVenue(int i) {
        return exams.get(i).venue;
    }
    public void getCourses(int i) {
        Courses.display(exams.get(i).courses);
    }
    public void addMarks(int i){
        exams.get(i).addMarks();
    }
    public void result(int i) {
        exams.get(i).result();
    }
}