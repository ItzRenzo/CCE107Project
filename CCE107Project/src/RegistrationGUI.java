import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private Container container = getContentPane();
    private JLabel nameLabel = new JLabel("USERNAME:");
    private JLabel emailLabel = new JLabel("EMAIL:");
    private JLabel passwordLabel = new JLabel("PASSWORD:");
    private JTextField nameTextField = new JTextField();
    private JTextField emailTextField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JButton registerButton = new JButton("REGISTER");

    RegistrationGUI() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    private void setLayoutManager() {
    }

    private void setLocationAndSize() {
    }

    private void addComponentsToContainer() {
        getContentPane().setLayout(null);
        nameLabel.setBounds(50, 165, 100, 30);
        container.add(nameLabel);
        emailLabel.setBounds(50, 206, 100, 30);
        container.add(emailLabel);
        passwordLabel.setBounds(50, 247, 100, 30);
        container.add(passwordLabel);
        nameTextField.setBounds(150, 165, 150, 30);
        container.add(nameTextField);
        emailTextField.setBounds(150, 206, 150, 30);
        container.add(emailTextField);
        passwordField.setBounds(150, 247, 150, 30);
        container.add(passwordField);
        registerButton.setBounds(50, 301, 250, 30);
        container.add(registerButton);
        
        JButton backtologin = new JButton("Already have an account?");
        backtologin.setOpaque(false); // Must add
        backtologin.setContentAreaFilled(false); // No fill
        backtologin.setFocusable(false); // I'd like to set focusable false to the button.
        backtologin.setBorderPainted(true); // I'd like to enable it.
        backtologin.setBorder(null); // Border (No border for now)
        
        backtologin.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
            	Color blue = null;
				backtologin.setBorder(BorderFactory.createLineBorder(blue, 2,true));
                //When enter we can not know our mouse successfully entered to the button. So I'd like to add Border
            }
            @Override
            public void mouseExited(MouseEvent e){
            	backtologin.setBorder(null);
                //When mouse exits no border.
            }
        });        
        
        backtologin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                // Goes back to LoginGUI
                LoginGUI login = new LoginGUI();
                login.setTitle("Login");
                login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                login.setResizable(false);
                login.setVisible(true);
                login.setBounds(10, 10, 370, 600);
                login.setLocationRelativeTo(null); 
                dispose(); // Close the registration GUI
        	}
        });
        backtologin.setBounds(101, 341, 150, 23);
        getContentPane().add(backtologin);
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
        registration.setBounds(10, 10, 370, 600);
        registration.setLocationRelativeTo(null); // Center the registration GUI        
    }
}