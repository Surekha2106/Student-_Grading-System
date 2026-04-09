public class GradeCalculator {

    /**
     * Calculates the total marks from three subjects.
     * @param m1 Marks of subject 1
     * @param m2 Marks of subject 2
     * @param m3 Marks of subject 3
     * @return Total marks sum
     */
    public static int calculateTotal(int m1, int m2, int m3) {
        return m1 + m2 + m3;
    }

    /**
     * Calculates the average marks.
     * @param total Total marks
     * @param count Number of subjects
     * @return Average percentage
     */
    public static double calculateAverage(int total, int count) {
        return (double) total / count;
    }

    /**
     * Assigns a letter grade based on the average marks.
     * A (90–100), B (75–89), C (60–74), D (50–59), F (<50)
     * @param average Average percentage
     * @return Grade as a String
     */
    public static String calculateGrade(double average) {
        if (average >= 90) return "A";
        if (average >= 75) return "B";
        if (average >= 60) return "C";
        if (average >= 50) return "D";
        return "F";
    }
}
