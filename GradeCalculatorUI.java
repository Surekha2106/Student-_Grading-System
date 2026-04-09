import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class GradeCalculatorUI extends JFrame {

    private JTextField subject1Field;
    private JTextField subject2Field;
    private JTextField subject3Field;
    private JLabel totalLabel;
    private JLabel averageLabel;
    private JLabel gradeLabel;
    private JPanel resultsPanel;

    public GradeCalculatorUI() {
        setTitle("Student Grade Calculator");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main Panel with Gradient Background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(26, 11, 46); // #1a0b2e
                Color color2 = new Color(78, 67, 118); // #4e4376
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        
        // Center Card (Glassmorphism concept via semi-transparent panel)
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 30));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.setColor(new Color(255, 255, 255, 50));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 40, 40);
                g2.dispose();
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setPreferredSize(new Dimension(500, 650));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Title
        JLabel titleLabel = new JLabel("Student Grade Calculator");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Enter your subject marks below");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(176, 190, 197));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Input Area
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setOpaque(false);
        inputPanel.setBorder(new EmptyBorder(30, 0, 30, 0));

        subject1Field = createInputField();
        subject2Field = createInputField();
        subject3Field = createInputField();

        inputPanel.add(createLabeledField("Mathematics (0-100):", subject1Field));
        inputPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        inputPanel.add(createLabeledField("Science (0-100):", subject2Field));
        inputPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        inputPanel.add(createLabeledField("English (0-100):", subject3Field));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        
        JButton calculateBtn = createButton("Calculate", new Color(0, 210, 255), new Color(58, 123, 213));
        JButton resetBtn = createButton("Reset", new Color(150, 150, 150, 100), new Color(200, 200, 200, 100));
        
        calculateBtn.addActionListener(e -> calculateGrades());
        resetBtn.addActionListener(e -> resetFields());

        buttonPanel.add(calculateBtn);
        buttonPanel.add(resetBtn);

        // Results Area
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setOpaque(false);
        resultsPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        resultsPanel.setVisible(false); // Hidden initially

        totalLabel = createResultLabel("Total Marks: --", 18);
        averageLabel = createResultLabel("Average: --", 18);
        
        JLabel gradeTitle = createResultLabel("Final Grade", 16);
        gradeTitle.setForeground(new Color(224, 224, 224));
        
        gradeLabel = createResultLabel("-", 64);
        gradeLabel.setFont(new Font("Segoe UI", Font.BOLD, 64));

        resultsPanel.add(totalLabel);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        resultsPanel.add(averageLabel);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        resultsPanel.add(gradeTitle);
        resultsPanel.add(gradeLabel);

        // Add to card
        cardPanel.add(titleLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        cardPanel.add(subtitleLabel);
        cardPanel.add(inputPanel);
        cardPanel.add(buttonPanel);
        cardPanel.add(resultsPanel);

        mainPanel.add(cardPanel);
        setContentPane(mainPanel);
    }

    private JTextField createInputField() {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 40));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
                g2.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (hasFocus()) {
                    g2.setColor(new Color(0, 210, 255));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                }
                g2.dispose();
            }
        };
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        field.setMaximumSize(new Dimension(400, 45));
        field.setPreferredSize(new Dimension(400, 45));
        
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.repaint();
            }
            @Override
            public void focusLost(FocusEvent e) {
                field.repaint();
            }
        });
        return field;
    }

    private JPanel createLabeledField(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(224, 224, 224));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(field);
        
        panel.setMaximumSize(new Dimension(400, 70));
        return panel;
    }

    private JButton createButton(String text, Color grad1, Color grad2) {
        JButton btn = new JButton(text) {
            private boolean isHovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
                    public void mouseExited(MouseEvent e) { isHovered = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isHovered) {
                    g2.setPaint(new GradientPaint(0, 0, grad2, getWidth(), getHeight(), grad1));
                } else {
                    g2.setPaint(new GradientPaint(0, 0, grad1, getWidth(), getHeight(), grad2));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 45));
        return btn;
    }

    private JLabel createResultLabel(String text, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, size));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private void calculateGrades() {
        try {
            int m1 = parseAndValidate(subject1Field.getText());
            int m2 = parseAndValidate(subject2Field.getText());
            int m3 = parseAndValidate(subject3Field.getText());

            int total = GradeCalculator.calculateTotal(m1, m2, m3);
            double average = GradeCalculator.calculateAverage(total, 3);
            String grade = GradeCalculator.calculateGrade(average);

            totalLabel.setText("Total Marks: " + total + " / 300");
            averageLabel.setText(String.format("Average: %.2f%%", average));
            gradeLabel.setText(grade);
            
            if (grade.equals("F")) {
                gradeLabel.setForeground(new Color(255, 71, 87)); // Red
            } else if (grade.equals("A")) {
                gradeLabel.setForeground(new Color(46, 213, 115)); // Green
            } else {
                gradeLabel.setForeground(new Color(255, 165, 2)); // Orange
            }

            resultsPanel.setVisible(true);
            resultsPanel.revalidate();
            resultsPanel.repaint();

        } catch (NumberFormatException ex) {
            showError("Please enter valid numeric marks between 0 and 100.");
        }
    }

    private int parseAndValidate(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new NumberFormatException("Empty field");
        }
        int value = Integer.parseInt(text.trim());
        if (value < 0 || value > 100) {
            throw new NumberFormatException("Out of range");
        }
        return value;
    }

    private void resetFields() {
        subject1Field.setText("");
        subject2Field.setText("");
        subject3Field.setText("");
        resultsPanel.setVisible(false);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        SwingUtilities.invokeLater(() -> {
            GradeCalculatorUI ui = new GradeCalculatorUI();
            ui.setVisible(true);
        });
    }
}
