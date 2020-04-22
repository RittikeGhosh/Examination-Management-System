echo off
echo "Compilation"
echo "Compiling Package 'record'"
REM echo "Compiling 'Records/Records.java'"
REM javac records/Records.java
echo "Compiling 'records/Courses.java'"
javac records/Courses.java -d ../out
echo "Compiling 'records/Exams.java'"
javac records/Exams.java -d ../out
echo "Compiling 'records/Students.java'"
javac records/Students.java -d ../out
echo "Compiling 'MainFile'"
echo "Compiling 'LaunchApp.java'"
javac LaunchApp.java -d ../out
echo "Running 'LaunchApp'"
java -cp ../out LaunchApp