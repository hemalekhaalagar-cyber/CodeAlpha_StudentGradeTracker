import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentGradeTrackerGUI extends JFrame {

    private JTextField nameField, marksField;
    private JTextArea outputArea;
    private JButton addButton, reportButton;

    private ArrayList<String> studentNames = new ArrayList<>();
    private ArrayList<Integer> studentMarks = new ArrayList<>();

    public StudentGradeTrackerGUI() {

        setTitle("Student Grade Tracker");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Top Panel
        JPanel inputPanel = new JPanel(new FlowLayout());

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField(15);
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Marks:"));
        marksField = new JTextField(10);
        inputPanel.add(marksField);

        addButton = new JButton("Add Student");
        reportButton = new JButton("Show Report");

        inputPanel.add(addButton);
        inputPanel.add(reportButton);

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Enter key support
        getRootPane().setDefaultButton(addButton);

        nameField.addActionListener(e -> marksField.requestFocus());

        marksField.addActionListener(e -> addButton.doClick());

        // Add Student Button
        addButton.addActionListener(e -> addStudent());

        // Show Report Button
        reportButton.addActionListener(e -> showReport());

        setVisible(true);
    }

    private void addStudent() {

        String name = nameField.getText().trim();
        String markText = marksField.getText().trim();

        if (name.isEmpty() || markText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter student name and marks.");
            return;
        }

        int marks;

        try {
            marks = Integer.parseInt(markText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Marks must be a number.");
            return;
        }

        if (marks < 0 || marks > 100) {
            JOptionPane.showMessageDialog(this,
                    "Marks must be between 0 and 100.");
            return;
        }

        studentNames.add(name);
        studentMarks.add(marks);

        JOptionPane.showMessageDialog(this,
                "Student Added Successfully!");

        nameField.setText("");
        marksField.setText("");

        nameField.requestFocus();
    }

    private void showReport() {

        if (studentNames.isEmpty()) {
            outputArea.setText("No student records found.");
            return;
        }

        int total = 0;
        int highest = studentMarks.get(0);
        int lowest = studentMarks.get(0);

        StringBuilder sb = new StringBuilder();

        sb.append("=========== STUDENT SUMMARY REPORT ===========\n\n");

        sb.append(String.format("%-20s %-10s\n",
                "Student Name", "Marks"));

        sb.append("---------------------------------------------\n");

        for (int i = 0; i < studentNames.size(); i++) {

            sb.append(String.format("%-20s %-10d\n",
                    studentNames.get(i),
                    studentMarks.get(i)));

            int mark = studentMarks.get(i);

            total += mark;

            if (mark > highest)
                highest = mark;

            if (mark < lowest)
                lowest = mark;
        }

        double average = (double) total / studentMarks.size();

        sb.append("\n---------------------------------------------\n");
        sb.append("Total Students : " + studentNames.size() + "\n");
        sb.append("Total Marks    : " + total + "\n");
        sb.append(String.format("Average Marks  : %.2f\n", average));
        sb.append("Highest Marks  : " + highest + "\n");
        sb.append("Lowest Marks   : " + lowest + "\n");
        sb.append("=============================================\n");

        outputArea.setText(sb.toString());
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new StudentGradeTrackerGUI());

    }
}