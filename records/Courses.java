package records;

import java.util.HashMap;
import java.util.Scanner;

interface CourseDetails {
    boolean isPresent(String code);
    void addCourse();
}

public class Courses implements CourseDetails {
    static Scanner in = new Scanner(System.in);
    Courses() { courses = new HashMap<>();}
    // to store course and there credit <course_code, crdeits>
    HashMap<String, Integer> courses = null;
    // check whether given course code is present or not
    public boolean isPresent(String code) {
        if(courses.get(code) == null)   return false;
        else    return true;
    }
    // add new course
    public void addCourse() {
        System.out.print("$ NUMBER OF COURSES ? => ");
        int course_count = in.nextInt();
        if(course_count > 0)
            System.out.println("(ENTER COURSE_CODE and CREDITS seprearted by SPACE)");
        // input course and course credit and store in courses
        for(int i = 0; i < course_count; i++) {
            System.out.print("$ Course " + (i + 1) + " : ");
            String code = in.next();
            int credits = in.nextInt();
            if(courses.get(code) == null)    courses.put(code, credits);
            else {
                i -= 1;
                System.out.println("COURSE ALREADY REGISTERED ! Try different one...");
            }
        }
    }
    // display registered courses for perticular Exam
    static void display(Courses obj) {
        System.out.println("\n@[REGISTERED COURSES](Course : Credits) :");
        if(obj.courses.size() == 0)
            System.out.println("  + NO COURSE ADDED FOR THIS EXAM");
        obj.courses.forEach((key, value)->{
            System.out.println( "   " + key + " : " + value);
        });
        System.out.println();
    }
}