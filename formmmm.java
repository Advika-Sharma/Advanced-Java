package socketexp;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Form {

    private JFrame frame;
    private JTextField textField;  // Name
    private JTextField textField_1; // Phone no.
    private JTextField textField_2; // Email
    private JTextField textField_4; // OTP
    private JRadioButton rdbtnMale;  // Male Radio Button
    private JRadioButton rdbtnFemale; // Female Radio Button

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/form";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Form window = new Form();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Form() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 679, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(33, 41, 46, 14);
        frame.getContentPane().add(lblName);

        JLabel lblPhoneNo = new JLabel("Phone no.");
        lblPhoneNo.setBounds(33, 76, 46, 14);
        frame.getContentPane().add(lblPhoneNo);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(33, 112, 46, 14);
        frame.getContentPane().add(lblEmail);

        JLabel lblGender = new JLabel("Gender");
        lblGender.setBounds(33, 148, 46, 14);
        frame.getContentPane().add(lblGender);

        JLabel lblOtp = new JLabel("Otp");
        lblOtp.setBounds(33, 185, 46, 14);
        frame.getContentPane().add(lblOtp);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(109, 215, 89, 23);
        frame.getContentPane().add(btnSubmit);

        JButton btnReset = new JButton("Reset");
        btnReset.setBounds(247, 215, 89, 23);
        frame.getContentPane().add(btnReset);

        textField = new JTextField();
        textField.setBounds(109, 38, 86, 20);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(109, 73, 86, 20);
        frame.getContentPane().add(textField_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(109, 109, 86, 20);
        frame.getContentPane().add(textField_2);

        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(109, 182, 86, 20);
        frame.getContentPane().add(textField_4);

        rdbtnMale = new JRadioButton("Male");
        rdbtnMale.setBounds(109, 144, 109, 23);
        frame.getContentPane().add(rdbtnMale);

        rdbtnFemale = new JRadioButton("Female");
        rdbtnFemale.setBounds(227, 144, 109, 23);
        frame.getContentPane().add(rdbtnFemale);

        // Group radio buttons
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rdbtnMale);
        genderGroup.add(rdbtnFemale);

       // Action Listener for Submit button
       btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textField.getText();
                String phoneNo = textField_1.getText();
                String email = textField_2.getText();
                String otp = textField_4.getText();
                String gender = rdbtnMale.isSelected() ? "Male" : "Female";

                // Insert data into database
                insertData(name, phoneNo, email, gender, otp);
            }
       });

       // Action Listener for Reset button
       btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear all fields and reset radio buttons
                textField.setText("");
                textField_1.setText("");
                textField_2.setText("");
                textField_4.setText("");
                genderGroup.clearSelection(); // Deselect radio buttons
            }
       });
    }

    /**
     * Insert data into the database.
     */
    private void insertData(String name, String phoneNo, String email, String gender, String otp) {
       Connection con = null;
       PreparedStatement pstmt = null;

       try {
           // Establish connection
           con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

           // Prepare SQL INSERT statement
           String sql = "INSERT INTO users (name, phone_no, email, gender, otp) VALUES (?, ?, ?, ?, ?)";
           pstmt = con.prepareStatement(sql);

           // Set parameters
           pstmt.setString(1, name);
           pstmt.setString(2, phoneNo);
           pstmt.setString(3, email);
           pstmt.setString(4, gender);
           pstmt.setString(5, otp);

           // Execute update
           int rowsAffected = pstmt.executeUpdate();

           if (rowsAffected > 0) {
               System.out.println("Data inserted successfully!");
           } else {
               System.out.println("Failed to insert data.");
           }

       } catch (SQLException ex) {
           ex.printStackTrace();
       } finally {
           try {
               if (pstmt != null) pstmt.close();
               if (con != null) con.close();
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
       }
   }
}
