import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private Container container = getContentPane();
    private JLabel nameLabel = new JLabel("Name:");
    private JLabel emailLabel = new JLabel("Email:");
    private JLabel passwordLabel = new JLabel("Password:");
    private JTextField nameTextField = new JTextField();
    private JTextField emailTextField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JButton registerButton = new JButton("Register");

    RegistrationGUI() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    private void setLayoutManager() {
        container.setLayout(null);
    }

    private void setLocationAndSize() {
        nameLabel.setBounds(50, 50, 100, 30);
        emailLabel.setBounds(50, 100, 100, 30);
        passwordLabel.setBounds(50, 150, 100, 30);
        nameTextField.setBounds(150, 50, 150, 30);
        emailTextField.setBounds(150, 100, 150, 30);
        passwordField.setBounds(150, 150, 150, 30);
        registerButton.setBounds(50, 206, 100, 30);
    }

    private void addComponentsToContainer() {
        container.add(nameLabel);
        container.add(emailLabel);
        container.add(passwordLabel);
        container.add(nameTextField);
        container.add(emailTextField);
        container.add(passwordField);
        container.add(registerButton);
    }

    private void addActionEvent() {
        registerButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String password = new String(passwordField.getPassword());

            // Establish database connection
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://185.207.164.129:3306/s6260_CCEProjectData", "u6260_j1v7KCJUuY", "Yj.!4yj8@HNCfdyplr@XFZG3");

                // Check if the username already exists
                String checkQuery = "SELECT * FROM users WHERE username = ?";
                PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
                checkStatement.setString(1, name);

                ResultSet resultSet = checkStatement.executeQuery();

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Username already exists");
                } else {
                    // Insert the registration information into the database
                    String insertQuery = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                    insertStatement.setString(1, name);
                    insertStatement.setString(2, email);
                    insertStatement.setString(3, password);

                    int rowsAffected = insertStatement.executeUpdate();

                    if (rowsAffected == 1) {
                        JOptionPane.showMessageDialog(this, "Registration Successful");
                        
                        // Open the LoginGUI after successful registration
                        LoginGUI login = new LoginGUI();
                        login.setTitle("Login");
                        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        login.setResizable(false);
                        login.setVisible(true);
                        login.setBounds(10, 10, 370, 600);
                        login.setLocationRelativeTo(null); // Center the login GUI
                        dispose(); // Close the registration GUI
                    } else {
                        JOptionPane.showMessageDialog(this, "Registration Failed");
                    }

                    insertStatement.close();
                }

                resultSet.close();
                checkStatement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
    	RegistrationGUI registration = new RegistrationGUI();
        registration.setTitle("Registration");
        registration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registration.setResizable(false);
        registration.setVisible(true);
        registration.setBounds(10, 10, 350, 300);
        registration.setLocationRelativeTo(null); // Center the registration GUI        
    }
}