echo off
echo "Compilation"
echo "Compiling Package 'record'"
REM echo "Compiling 'Records/Records.java'"
REM javac records/Records.java
echo "Compiling 'Records/Courses.java'"
javac records/Courses.java
echo "Compiling 'Records/Exams.java'"
javac records/Exams.java
echo "Compiling 'Records/Students.java'"
javac records/Students.java
echo "Compiling 'MainFile'"
echo "Compiling 'LaunchApp.java'"
javac LaunchApp.java
echo "Running 'LaunchApp'"
java LaunchApp