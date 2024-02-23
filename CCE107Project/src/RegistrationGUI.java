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
        JButton closebutton = new JButton("");
        closebutton.setOpaque(false); // Must add
        closebutton.setContentAreaFilled(false); // No fill
        closebutton.setFocusable(false); // I'd like to set focusable false to the button.
        closebutton.setBorderPainted(true); // I'd like to enable it.
        closebutton.setBorder(null); // Border (No border for now)
        closebutton.setIcon(new ImageIcon("icons\\close-window.png"));
        closebutton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        closebutton.setBounds(1267, 11, 89, 52);
        getContentPane().add(closebutton);
        
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
        nameLabel.setBounds(10, 113, 117, 14);
        nameLabel.setIcon(new ImageIcon("icons\\user.png"));
        RegistrationPanel.add(nameLabel);
        nameTextField.setBounds(10, 138, 325, 30);
        RegistrationPanel.add(nameTextField);
        emailTextField.setBounds(10, 208, 325, 30);
        RegistrationPanel.add(emailTextField);
        emailLabel.setBounds(10, 167, 100, 30);
        emailLabel.setIcon(new ImageIcon("icons\\mail.png"));
        RegistrationPanel.add(emailLabel);
        passwordLabel.setBounds(10, 240, 100, 30);
        RegistrationPanel.add(passwordLabel);
        passwordLabel.setIcon(new ImageIcon("icons\\passwordicon.png"));
        passwordField.setBounds(10, 281, 325, 30);
        RegistrationPanel.add(passwordField);
    }

    private void addActionEvent() {
        registerButton.setBounds(10, 349, 325, 30);
        RegistrationPanel.add(registerButton);

        JButton backtologin = new JButton("Already have an account?");
        backtologin.setBounds(10, 390, 325, 23);
        RegistrationPanel.add(backtologin);
        backtologin.setOpaque(false); // Must add
        backtologin.setContentAreaFilled(false); // No fill
        backtologin.setFocusable(false); // I'd like to set focusable false to the button.
        backtologin.setBorderPainted(true); // I'd like to enable it.
        backtologin.setBorder(null); // Border (No border for now)

        RegistrationPanel.add(showPasswordCheckBox);
        showPasswordCheckBox.setBounds(10, 318, 150, 23);
        RegistrationPanel.add(showPasswordCheckBox);
        
        JPanel logo = new JPanel();
        logo.setBounds(10, 25, 325, 86);
        logo.setLayout(null);

        // Get the size of the JPanel
        int panelWidth = logo.getWidth();
        int panelHeight = logo.getHeight();

        // Update the file path to use forward slashes instead of backslashes
        ImageIcon logopic1 = new ImageIcon("icons/logo.png");

        // Get the original size of the ImageIcon
        int originalWidth = logopic1.getIconWidth();
        int originalHeight = logopic1.getIconHeight();

        // Calculate the scale factor to fit the image in the JPanel
        double widthScale = (double) panelWidth / originalWidth;
        double heightScale = (double) panelHeight / originalHeight;
        double scale = Math.min(widthScale, heightScale);

        // Calculate the scaled size of the image
        int scaledWidth = (int) (originalWidth * scale);
        int scaledHeight = (int) (originalHeight * scale);

        // Create a new ImageIcon with the scaled size
        Image scaledImage = logopic1.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Set the size of the JLabel to match the scaled size of the ImageIcon
        JLabel logopic = new JLabel(scaledIcon);
        logopic.setBounds(0, 0, scaledWidth, scaledHeight);

        logo.add(logopic);
        RegistrationPanel.add(logo);

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