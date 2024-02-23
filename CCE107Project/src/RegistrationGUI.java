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
    private JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
    private final JPanel RegistrationPanel = new JPanel();

    RegistrationGUI() {
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
        RegistrationPanel.setBounds(23, 11, 317, 531);

        getContentPane().add(RegistrationPanel);
        RegistrationPanel.setBounds(10, 11, 345, 746);
        RegistrationPanel.setLayout(null);
        nameLabel.setBounds(25, 113, 117, 14);
        nameLabel.setIcon(new ImageIcon("icons\\user.png"));
        RegistrationPanel.add(nameLabel);
        nameTextField.setBounds(25, 138, 263, 30);
        RegistrationPanel.add(nameTextField);
        emailTextField.setBounds(22, 200, 263, 30);
        RegistrationPanel.add(emailTextField);
        emailLabel.setBounds(22, 167, 100, 30);
        emailLabel.setIcon(new ImageIcon("icons\\mail.png"));
        RegistrationPanel.add(emailLabel);
        passwordLabel.setBounds(22, 231, 100, 30);
        RegistrationPanel.add(passwordLabel);
        passwordLabel.setIcon(new ImageIcon("icons\\passwordicon.png"));
        passwordField.setBounds(22, 262, 263, 30);
        RegistrationPanel.add(passwordField);
    }

    private void addActionEvent() {
        registerButton.setBounds(25, 335, 260, 30);
        RegistrationPanel.add(registerButton);

        JButton backtologin = new JButton("Already have an account?");
        backtologin.setBounds(67, 376, 179, 23);
        RegistrationPanel.add(backtologin);
        backtologin.setOpaque(false); // Must add
        backtologin.setContentAreaFilled(false); // No fill
        backtologin.setFocusable(false); // I'd like to set focusable false to the button.
        backtologin.setBorderPainted(true); // I'd like to enable it.
        backtologin.setBorder(null); // Border (No border for now)

        RegistrationPanel.add(showPasswordCheckBox);
        showPasswordCheckBox.setBounds(22, 299, 150, 23);
        RegistrationPanel.add(showPasswordCheckBox);

        backtologin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Color blue = null;
                backtologin.setBorder(BorderFactory.createLineBorder(blue, 2, true));
                //When enter we can not know our mouse successfully entered to the button. So I'd like to add Border
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backtologin.setBorder(null);
                //When mouse exits no border.
            }
        });
        showPasswordCheckBox.addActionListener(this);

        backtologin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Goes back to LoginGUI
                LoginGUI login = new LoginGUI();
                login.setTitle("Login");
                login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                login.setResizable(false);
                login.setVisible(true);
                login.setBounds(23, 53, 317, 489);
                login.setExtendedState(JFrame.MAXIMIZED_BOTH);
                login.setLocationRelativeTo(null); // Center the Registration GUI
                dispose(); // Close the Login GUI
            }
        });
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
        } else if (e.getSource() == showPasswordCheckBox) {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
    }

    public static void main(String[] args) {
        RegistrationGUI registration = new RegistrationGUI();
        registration.setTitle("Registration");
        registration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registration.setResizable(false);
        registration.setVisible(true);
        registration.setBounds(23, 53, 317, 489);
        registration.setExtendedState(JFrame.MAXIMIZED_BOTH);
        registration.setLocationRelativeTo(null); // Center the registration GUI   
    }
}