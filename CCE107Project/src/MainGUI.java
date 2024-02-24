import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class MainGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

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
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel titleLabel = new JLabel("Alfie Car Dealership");
        titleLabel.setBounds(10, 11, 242, 30);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        contentPane.add(titleLabel);
        
        JButton shopbutton = new JButton("Shop");
        shopbutton.setBounds(292, 21, 89, 23);
        contentPane.add(shopbutton);
        
        JButton sellortradebutton = new JButton("Sell or Trade");
        sellortradebutton.setBounds(426, 21, 120, 23);
        contentPane.add(sellortradebutton);
        
        JButton LocationButton = new JButton("Location");
        LocationButton.setBounds(585, 21, 89, 23);
        contentPane.add(LocationButton);
        
        JButton SignInButton = new JButton("Sign In");
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
        SignInButton.setBounds(1161, 21, 89, 23);
        contentPane.add(SignInButton);
        
        JButton MenuButton = new JButton("Menu");
        MenuButton.setBounds(1260, 21, 89, 23);
        contentPane.add(MenuButton);
    }
}