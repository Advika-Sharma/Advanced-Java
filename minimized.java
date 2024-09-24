package socketexp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Form {

    private JFrame frame;
    private JTextField nameField, phoneField, emailField, otpField;
    private JRadioButton rdbtnMale, rdbtnFemale;

    private static final String URL = "jdbc:mysql://localhost:3306/form";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new Form().frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Form() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("User Form");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 2, 10, 10));

        // Labels and input fields
        frame.add(new JLabel("Name:"));
        nameField = new JTextField();
        frame.add(nameField);

        frame.add(new JLabel("Phone no.:"));
        phoneField = new JTextField();
        frame.add(phoneField);

        frame.add(new JLabel("Email:"));
        emailField = new JTextField();
        frame.add(emailField);

        frame.add(new JLabel("Gender:"));
        JPanel genderPanel = new JPanel();
        ButtonGroup genderGroup = new ButtonGroup();
        rdbtnMale = new JRadioButton("Male");
        rdbtnFemale = new JRadioButton("Female");
        genderGroup.add(rdbtnMale);
        genderGroup.add(rdbtnFemale);
        genderPanel.add(rdbtnMale);
        genderPanel.add(rdbtnFemale);
        frame.add(genderPanel);

        frame.add(new JLabel("OTP:"));
        otpField = new JTextField();
        frame.add(otpField);

        // Submit and Reset buttons
        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> submitForm());
        frame.add(submitBtn);

        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> resetForm());
        frame.add(resetBtn);
    }

    private void submitForm() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String otp = otpField.getText();
        String gender = rdbtnMale.isSelected() ? "Male" : rdbtnFemale.isSelected() ? "Female" : "";

        // Insert into database
        if (!gender.isEmpty()) {
            try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = con.prepareStatement(
                     "INSERT INTO users (name, phone_no, email, gender, otp) VALUES (?, ?, ?, ?, ?)")) {

                pstmt.setString(1, name);
                pstmt.setString(2, phone);
                pstmt.setString(3, email);
                pstmt.setString(4, gender);
                pstmt.setString(5, otp);

                int rowsAffected = pstmt.executeUpdate();
                System.out.println(rowsAffected > 0 ? "Data inserted successfully!" : "Failed to insert data.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Please select a gender.");
        }
    }

    private void resetForm() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        otpField.setText("");
        rdbtnMale.setSelected(false);
        rdbtnFemale.setSelected(false);
    }
}
