package socketexp;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm {

    private JFrame frame;
    private JTextField textFieldUsername;  // Username field
    private JTextField textFieldPassword;    // Password field

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/form";
    private static final String USERNAME = "root"; // Change if necessary
    private static final String PASSWORD = "root"; // Change if necessary

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginForm window = new LoginForm();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     * @wbp.parser.entryPoint
     */
    public LoginForm() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        textFieldUsername = new JTextField();
        textFieldUsername.setBounds(148, 27, 86, 20);
        frame.getContentPane().add(textFieldUsername);
        textFieldUsername.setColumns(10);
        
        textFieldPassword = new JTextField();
        textFieldPassword.setBounds(148, 65, 86, 20);
        frame.getContentPane().add(textFieldPassword);
        textFieldPassword.setColumns(10);
        
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(40, 30, 57, 14);
        frame.getContentPane().add(lblUsername);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(40, 68, 46, 14);
        frame.getContentPane().add(lblPassword);
        
        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(94, 110, 89, 23);
        frame.getContentPane().add(btnLogin);
        
        JButton btnReset = new JButton("Reset");
        btnReset.setBounds(94, 144, 89, 23);
        frame.getContentPane().add(btnReset);
        
        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(94, 178, 89, 23);
        frame.getContentPane().add(btnExit);

        // Action Listener for Login button
        btnLogin.addActionListener(e -> {
            String username = textFieldUsername.getText();
            String password = textFieldPassword.getText();
            validateLogin(username, password);
        });

        // Action Listener for Reset button
        btnReset.addActionListener(e -> {
            textFieldUsername.setText("");
            textFieldPassword.setText("");
        });

        // Action Listener for Exit button
        btnExit.addActionListener(e -> {
            System.exit(0);
        });
    }

    /**
     * Validate login credentials against the database.
     */
    private void validateLogin(String username, String password) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Establish connection
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Prepare SQL SELECT statement
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            // Execute query
            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid username or password.");
            }

        } catch (SQLException ex) {
            System.out.println("Database connection error: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
