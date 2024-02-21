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

public class LoginGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private Container container = getContentPane();
    private JLabel userLabel = new JLabel("USERNAME:");
    private JLabel passwordLabel = new JLabel("PASSWORD:");
    private JTextField userTextField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JButton loginButton = new JButton("LOGIN");
    private JCheckBox showPassword = new JCheckBox("Show Password");

    LoginGUI() {
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
        userLabel.setBounds(50, 150, 100, 30);
        container.add(userLabel);
        passwordLabel.setBounds(50, 220, 100, 30);
        container.add(passwordLabel);
        userTextField.setBounds(150, 150, 150, 30);
        container.add(userTextField);
        passwordField.setBounds(150, 220, 150, 30);
        container.add(passwordField);
        showPassword.setBounds(150, 250, 150, 30);
        container.add(showPassword);
        loginButton.setBounds(50, 299, 250, 30);
        container.add(loginButton);
        
        JButton registerbutton = new JButton("No account yet? Create one.");
        registerbutton.setBounds(90, 340, 179, 23);
        registerbutton.setOpaque(false); // Must add
        registerbutton.setContentAreaFilled(false); // No fill
        registerbutton.setFocusable(false); // I'd like to set focusable false to the button.
        registerbutton.setBorderPainted(true); // I'd like to enable it.
        registerbutton.setBorder(null); // Border (No border for now)
        
        registerbutton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
            	Color blue = null;
            	registerbutton.setBorder(BorderFactory.createLineBorder(blue, 2,true));
                //When enter we can not know our mouse successfully entered to the button. So I'd like to add Border
            }
            @Override
            public void mouseExited(MouseEvent e){
            	registerbutton.setBorder(null);
                //When mouse exits no border.
            }
        }); 
        		
        registerbutton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                // Open the RegistrationGUI
                RegistrationGUI registration = new RegistrationGUI();
                registration.setTitle("Registration");
                registration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                registration.setResizable(false);
                registration.setVisible(true);
                registration.setBounds(10, 10, 370, 600);
                registration.setLocationRelativeTo(null); // Center the Registration GUI
                dispose(); // Close the Login GUI
        	}
        });
        getContentPane().add(registerbutton);
    }

    private void addActionEvent() {
        loginButton.addActionListener(this);
        showPassword.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText = userTextField.getText();
            String pwdText = new String(passwordField.getPassword());

            // Establish database connection
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://185.207.164.129:3306/s6260_CCEProjectData", "u6260_j1v7KCJUuY", "Yj.!4yj8@HNCfdyplr@XFZG3");

                // Create SQL query
                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, userText);
                statement.setString(2, pwdText);

                // Execute the query
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Login Successful");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                }

                // Close the database connection
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
    }

    public static void main(String[] args) {
        LoginGUI login = new LoginGUI();
        login.setTitle("Login");
        login.setBounds(10, 10, 370, 600);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setResizable(false);
        login.setVisible(true);
        login.setLocationRelativeTo(null); // Center the login GUI
    }
}