import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GradingForm {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Grading System");
        frame.setSize(450, 450);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setBounds(50, 30, 120, 30);
        JTextField nameField = new JTextField();
        nameField.setBounds(180, 30, 180, 30);

        JLabel mark1Label = new JLabel("Mark 1:");
        mark1Label.setBounds(50, 80, 120, 30);
        JTextField mark1Field = new JTextField();
        mark1Field.setBounds(180, 80, 180, 30);

        JLabel mark2Label = new JLabel("Mark 2:");
        mark2Label.setBounds(50, 130, 120, 30);
        JTextField mark2Field = new JTextField();
        mark2Field.setBounds(180, 130, 180, 30);

        JLabel mark3Label = new JLabel("Mark 3:");
        mark3Label.setBounds(50, 180, 120, 30);
        JTextField mark3Field = new JTextField();
        mark3Field.setBounds(180, 180, 180, 30);

        JButton calcButton = new JButton("Calculate Grade");
        calcButton.setBounds(130, 230, 160, 30);

        JTextArea resultArea = new JTextArea();
        resultArea.setBounds(50, 280, 330, 100);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));

        calcButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    int m1 = Integer.parseInt(mark1Field.getText());
                    int m2 = Integer.parseInt(mark2Field.getText());
                    int m3 = Integer.parseInt(mark3Field.getText());

                    String result = "Student Name: " + name + "\n" +
                                    GradeCalculator.calculateGrade(m1, m2, m3);
                    resultArea.setText(result);
                } catch (NumberFormatException ex) {
                    resultArea.setText("⚠️ Please enter valid numbers for marks!");
                }
            }
        });

        frame.add(nameLabel); frame.add(nameField);
        frame.add(mark1Label); frame.add(mark1Field);
        frame.add(mark2Label); frame.add(mark2Field);
        frame.add(mark3Label); frame.add(mark3Field);
        frame.add(calcButton); frame.add(resultArea);

        frame.setVisible(true);
    }
}