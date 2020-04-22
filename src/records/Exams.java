package records;


import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


abstract class ExamDetailsParams {
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
    }

    // ADD MARKS FOR EXAMS, then pass to the Students addMarks
    void addMarks() {
        if (students.marksAdded) {
            System.out.println("\nMARKS ALREADY ADDED !");
            System.out.print("  1. UPDATE MARKS    0. ABORT AND GO BACK $(Your Choice) => ");
            char ch = in.next().charAt(0);
            if (ch != '1') return;
        }
        students.students.forEach((StudentDetails s) -> {
            System.out.println("\n# STUDENT'S NAME => " + s.name);
            HashMap<String, Float> temp_map = new HashMap<>();
            courses.courses.forEach((k, v) -> {
                System.out.print("$ MARKS IN " + k + " (out of 100) => ");
                float mark = in.nextFloat();
                temp_map.put(k, mark);
            });
            s.addMarks(temp_map);
        });
        students.marksAdded = true;
    }

    /* Display result, First check marks added or not, if added then display else ask to
       add marks for the exam*/
    void result() {
        if (students.marksAdded == false) {
            System.out.println("YOU DIDN'T ADD MARKS FOR THIS EXAM ");
            System.out.print("DO YOU WANT TO ADD MARKS NOW ? $(Your Choice y/n) => ");
            char ch = in.next().charAt(0);
            if (ch == 'y') addMarks();
            else return;
        }
        System.out.println("\n# EXAM REPORT : ");
        students.students.forEach((StudentDetails s) -> s.result(courses.courses));
    }
}


interface ExamParams {
    boolean isPresent(LocalDate date);

    int size();

    void addDate(LocalDate date);

    void listAll(boolean upcoming, LocalDate date);

    LocalDate getDate(int i);

    String getVenue(int i);

    void getCourses(int i);

    void addMarks(int i);

    void result(int i);
}


// Exams class -> singleton class
// Stores the list of all the exams and their details
public class Exams implements ExamParams {
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

    public ArrayList<ExamDetails> exams;  // list of all available exams

    // check for any available exam on the passed date
    public boolean isPresent(LocalDate date) {
        for (ExamDetails o : exams)
            if (o.date.isEqual(date)) return true;

        return false;
    }

    // return size of number of exams
    public int size() {
        return exams.size();
    }

    // add NEW EXAM
    public void addDate(LocalDate date) {
        if (exams.size() == 0 || exams.get(exams.size() - 1).date.isBefore(date))
            exams.add(new ExamDetails(date));
        else {
            int i = 0;
            for (i = 0; exams.get(i).date.isBefore(date) && i < exams.size(); i++) ;
            exams.add(i, new ExamDetails(date));
        }
    }

    // list all exams to display all available and past exams
    public void listAll(boolean upcoming, LocalDate date) {
        boolean present = false;
        if (upcoming) {
            for (int i = 0; i < exams.size(); i++) {
                if (exams.get(i).date.isAfter(date) || exams.get(i).date.isEqual(date)) {
                    System.out.println("    " + (i + 1) + ". " + exams.get(i).date);
                    present = true;
                }
            }
            if (!present) System.out.println("  + NO UPCOMMING EXAMS");
        } else {
            for (int i = 0; i < exams.size(); i++) {
                if (exams.get(i).date.isBefore(date)) {
                    System.out.println("    " + (i + 1) + ". " + exams.get(i).date);
                    present = true;
                }
            }
            if (!present) System.out.println("  + NO PAST EXAMS");
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

    public void addMarks(int i) {
        exams.get(i).addMarks();
    }

    public void result(int i) {
        exams.get(i).result();
    }
}
