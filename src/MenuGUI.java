import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.jgoodies.forms.factories.DefaultComponentFactory;

public class MenuGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String loggedInUsername;

    public MenuGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 330, 300);
        setUndecorated(true); // Remove window decorations (title bar, close button, etc.)

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton GarageButton = new JButton("Your Garage");
        GarageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: Add action for the "Your Garage" button
            }
        });
        GarageButton.setBounds(25, 71, 171, 23);
        contentPane.add(GarageButton);

        JButton AccountInfoButton = new JButton("Account Info");
        AccountInfoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: Add action for the "Account Info" button
            }
        });
        AccountInfoButton.setBounds(25, 124, 171, 23);
        contentPane.add(AccountInfoButton);

        JButton ExitButton = new JButton("Exit");
        ExitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        ExitButton.setBounds(25, 227, 89, 23);
        contentPane.add(ExitButton);
        
        setLoggedInUsername("your_username");
        
        // Set the location of the frame to the top right corner of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        setLocation(screenWidth - getWidth(), 0);
    }

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
                JLabel HiLabel = DefaultComponentFactory.getInstance().createLabel("Hi: "+ userName);
                HiLabel.setBounds(22, 31, 92, 14);
                contentPane.add(HiLabel);
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