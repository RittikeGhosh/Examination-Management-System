/**
 * LaunchApp
 */

import java.util.Scanner;
import java.time.LocalDate;

import records.Exams;

import java.io.IOException;
import java.time.format.DateTimeParseException;


// class to handle input and output in main file
class IO {
    protected static Scanner in = new Scanner(System.in);

    // printing function with new line at the end
    protected static void println(String... args) {
        if (args.length == 0) System.out.println();
        for (String s : args) System.out.println(s);
    }

    // print without new line at the end until specified in the argument
    protected static void print(String... args) {
        for (String s : args) System.out.print(s + " ");
    }
}

final public class LaunchApp extends IO {
    // to exit main function
    static class Today {
        static LocalDate today = LocalDate.now();
    }

    // Exit console
    static void exit() {
        println("\n@ THANK YOU, FOR YOUR VISIT @ :-) Good Bye...\n");
        System.exit(0);
    }

    // list all exams - upcoming and past exams
    static void examList() {
        println("________________PAST EXAMS________________________________________________");
        Exams.getInstance().listAll(false, Today.today);
        println();
        println("________________UPCOMING EXAMS___________________________________________");
        Exams.getInstance().listAll(true, Today.today);
        println();
    }

    // To add new exam dates and its details
    static void addExam() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();   //cls

        println("\n[TODAY'S DATE : " + Today.today + "]\n");
        print("$ What is the START DATE(yyyy-mm-dd) of the exam ? => ");
        String doe = in.next();
        try {
            LocalDate date = LocalDate.parse(doe);
            Exams instance = Exams.getInstance();
            if (instance.isPresent(date)) {
                print("\nTHERE IS A EXAM ON THIS DATE ! \nPlease select option 2 from the main menu to edit.");
            } else {
                instance.addDate(date);
                print("\nEXAM and its DETAILS ADDED SUCCESSFULLY !");
            }
        } catch (DateTimeParseException e) {
            print("\nWRONG DATE FORMAT ! ");
        } catch (Throwable e) {
            println("ERROR " + e);
            System.exit(1);
        } finally {
            print("Press enter to continue...");
            System.in.read();
            main();
        }
    }

    // Function to invoke features of Exam details
    static void examDetails() throws IOException, InterruptedException {
        Exams exams = Exams.getInstance();
        if (exams.size() == 0) {
            System.out.println("[THERE ARE NO EXAMS AVAILABLE] ADD EXAMS.");
            return;
        }
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("\n[SELECT FROM THE LIST OF AVAILABLE EXAMS BELOW BY THE NUMBER]\n");
        examList();
        while (true) {
            System.out.print("$('0' GO BACK | Your Choice) => ");
            int ch = in.nextInt();
            if (ch == 0) main();
            if (exams.size() >= ch && ch > 0) examDetails(ch);
            else System.out.print("\nINVALID INPUT ! Try Again...");
        }
    }

    // See Exam details on specific date
    static void examDetails(int i) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        Exams object = Exams.getInstance();
        System.out.println("\nExamination DATE : " + object.getDate(i - 1));
        System.out.println("\nExamination VENUE : " + object.getVenue(i - 1));
        object.getCourses(i - 1);
        println("\n@OPTIONS\n  1. ADD MARKS    2. EXAM REPORT\n");
        while (true) {
            print("$('0' GO BACK | Your Choice) => ");
            char ch = in.next().charAt(0);
            if (ch == '0') examDetails();
            else if (ch == '1') object.addMarks(i - 1);
            else if (ch == '2') object.result(i - 1);
            else System.out.print("\nINVALID CHOICE! Try again... ");
        }
    }

    public static void main(String... Args) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();   // clear the screen

        // Welcome Message///////////////////////////////////////////////////////////
        println();
        println("################ ######################################## ################");
        println("##                    EXAMINATION MANAGEMENT SYSTEM                     ##");
        println("################ ######################################## ################");
        println();
        println("    1. Add new Examination Date and details");
        println("    2. Add Marks or View report");
        println("    3. Change today's Date => {You will travel time :-)}");
        println("    0. Exit");
        println();
        println("[TODAY'S DATE : " + Today.today + "]\n");
        examList();
        //////////////////////////////////////////////////////////////////////////////////////

        while (true) {
            print("$(Your choice) => ");
            char ch = in.next().charAt(0);
            if (ch == '0') exit();
            else if (ch == '1') addExam();
            else if (ch == '2') examDetails();
            else if (ch == '3') {
                try {
                    print("Enter the new Date for today(yyyy-mm-dd) :");
                    String date = in.next();
                    LocalDate d = LocalDate.parse(date);
                    Today.today = d;
                    print("\nTODAY'S DATE CHANGED SUCCESSFULLY : ");
                    print("Press enter to continue...");
                    System.in.read();
                    main();
                } catch (DateTimeParseException e) {
                    println("\nWRONG DATE FORMAT !");
                }
            } else print("INVALID CHOICE ! Try Again... ");
        }
    }
}