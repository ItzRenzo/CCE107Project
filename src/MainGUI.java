import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String loggedInUsername; // Variable to store the currently logged-in username

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainGUI frame = new MainGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1379, 577);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false); // Remove window decorations (title bar, close button, etc.)

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Set the size and position of the frame to fullscreen
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Set the frame to fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel Menu = new JPanel();
        Menu.setBounds(0, 11, 1365, 71);
        contentPane.add(Menu);
        Menu.setLayout(null);

        JButton shopbutton = new JButton("Shop");
        shopbutton.setBounds(316, 24, 89, 23);
        Menu.add(shopbutton);

        JButton sellortradebutton = new JButton("Sell or Trade");
        sellortradebutton.setBounds(415, 24, 120, 23);
        Menu.add(sellortradebutton);

        JButton LocationButton = new JButton("Location");
        LocationButton.setBounds(545, 24, 89, 23);
        Menu.add(LocationButton);

        JButton SignInButton = new JButton("Sign In");
        SignInButton.setBounds(1173, 24, 89, 23);
        Menu.add(SignInButton);

        JPanel logo = new JPanel();
        logo.setBounds(26, 0, 120, 67);

        // Get the size of the JPanel
        int panelWidth = logo.getWidth();
        int panelHeight = logo.getHeight();

        // Update the file path to use forward slashes instead of backslashes
        ImageIcon logopic1 = new ImageIcon("icons/projectlogo.png");

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
        Menu.add(logo);

        JButton MenuButton = new JButton("");
        MenuButton.setOpaque(false); // Must add
        MenuButton.setContentAreaFilled(false); // No fill
        MenuButton.setFocusable(false); // I'd like to set focusable false to the button.
        MenuButton.setBorderPainted(true); // I'd like to enable it.
        MenuButton.setBorder(null); // Border (No border for now)
        MenuButton.setIcon(new ImageIcon("icons\\menu.png"));

        MenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuGUI menuGUI = new MenuGUI();
                menuGUI.setVisible(true);
                menuGUI.setLoggedInUsername(loggedInUsername);
            }
        });
        MenuButton.setBounds(1289, 11, 54, 49);
        Menu.add(MenuButton);

        SignInButton.addActionListener(new ActionListener() {
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
    }

    // Method to set the currently logged-in username
    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;

        // Fetch data for the currently logged-in user and display it in the label
        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://185.207.164.129:3306/s6260_CCEProjectData", "u6260_j1v7KCJUuY", "Yj.!4yj8@HNCfdyplr@XFZG3");

            // Create a prepared statement
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, loggedInUsername);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userName = resultSet.getString("username");
                JLabel usernameLabel = new JLabel("Hello, " + userName + "!");
                usernameLabel.setBounds(25, 37, 92, 14);
                contentPane.add(usernameLabel);
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}