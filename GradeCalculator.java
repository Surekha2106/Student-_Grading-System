public class GradeCalculator {

    public static String calculateGrade(int m1, int m2, int m3) {
        int total = m1 + m2 + m3;
        float average = total / 3f;
        String grade;

        if (average >= 90)
            grade = "A+";
        else if (average >= 75)
            grade = "A";
        else if (average >= 60)
            grade = "B";
        else if (average >= 50)
            grade = "C";
        else
            grade = "Fail";

        return "Total Marks: " + total +
               "\nAverage: " + average +
               "\nGrade: " + grade;
    }
}