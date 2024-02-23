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
    private JPanel LoginPanel;

    LoginGUI() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        setUndecorated(true); // Remove window decorations (title bar, close button, etc.)

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Set the size and position of the frame to fullscreen
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Set the frame to fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create a JLabel with the background image
        ImageIcon backgroundImageIcon = new ImageIcon(("icons\\background.jpg"));
        Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImageIcon = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImageIcon);

        // Set the layout manager of the content pane to null
        getContentPane().setLayout(null);

        // Set the bounds of the background label to cover the entire content pane
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());

        // Add the background label to the content pane
        getContentPane().add(backgroundLabel);
       
    }

    private void setLayoutManager() {
    }

    private void setLocationAndSize() {
    }

    private void addComponentsToContainer() {
        getContentPane().setLayout(null);
        
        LoginPanel = new JPanel();
        LoginPanel.setBounds(10, 11, 345, 746);
        getContentPane().add(LoginPanel);
        LoginPanel.setLayout(null);
        userLabel.setIcon(new ImageIcon("icons\\user.png"));
        userLabel.setBounds(25, 123, 117, 14);
        LoginPanel.add(userLabel);
        userTextField.setBounds(25, 145, 260, 30);
        LoginPanel.add(userTextField);
        passwordLabel.setIcon(new ImageIcon("icons\\passwordicon.png"));
		passwordLabel.setBounds(25, 194, 117, 23);
        LoginPanel.add(passwordLabel);
        passwordField.setBounds(25, 226, 260, 30);
        LoginPanel.add(passwordField);
    }

    private void addActionEvent() {
        showPassword.setBounds(25, 263, 150, 30);
        LoginPanel.add(showPassword);
        loginButton.setBounds(25, 300, 260, 30);
        LoginPanel.add(loginButton);
        
        JButton registerbutton = new JButton("No account yet? Create one.");
        registerbutton.setBounds(59, 341, 179, 23);
        LoginPanel.add(registerbutton);
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
                registration.setBounds(23, 53, 317, 489);
                registration.setExtendedState(JFrame.MAXIMIZED_BOTH);
                registration.setLocationRelativeTo(null); // Center the Registration GUI
                dispose(); // Close the Login GUI
        	}
        });
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
                    
                    // Open the MainGUI
                    MainGUI maingui = new MainGUI();
                    maingui.setTitle("Registration");
                    maingui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    maingui.setResizable(false);
                    maingui.setVisible(true);
                    setBounds(100, 100, 1379, 577);
                    setExtendedState(JFrame.MAXIMIZED_BOTH); // Makes MainGUI Full SCreen                 
                    maingui.setLocationRelativeTo(null); // Center the MainGUI
                    dispose(); // Close the MainGUI
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
        login.setResizable(true);
        login.setVisible(true);
        login.setExtendedState(JFrame.MAXIMIZED_BOTH);
        login.setLocationRelativeTo(null); // Center the login GUI
    }
}