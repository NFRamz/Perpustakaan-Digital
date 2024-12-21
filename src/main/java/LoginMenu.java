import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import io.github.cdimascio.dotenv.Dotenv;

public class LoginMenu {

    private JFrame frame;
    private JTextField usernameField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JLabel errorLabel = new JLabel("");
    private Dotenv dotenv = Dotenv.configure().load();
    String adminUsername = dotenv.get("ADMIN_USERNAME");
    String adminPassword = dotenv.get("ADMIN_PASSWORD");
    String studentUsername = dotenv.get("STUDENT_USERNAME");
    String studentPassword = dotenv.get("STUDENT_PASSWORD");

    public void display() {
        frame = createMainFrame();
        JPanel mainPanel = createMainPanel();
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JFrame createMainFrame() {
        JFrame frame = new JFrame("Login Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(250, 250, 250));

        mainPanel.add(createTitlePanel(), BorderLayout.NORTH);
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Digital Library");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(250, 250, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField.setPreferredSize(new Dimension(200, 25));
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField.setPreferredSize(new Dimension(200, 25));
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(errorLabel, gbc);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(250, 250, 250));
        JButton loginButton = new JButton("Login");
        JButton closeButton = new JButton("Close");

        styleButton(loginButton, new Color(70, 130, 180));
        styleButton(closeButton, new Color(220, 20, 60));

        loginButton.addActionListener(this::handleLogin);
        closeButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(loginButton);
        buttonPanel.add(closeButton);

        return buttonPanel;
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }

    void handleLogin(ActionEvent e) {
        try {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            errorLabel.setText("");

            if (username.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Username or Password cannot be empty!");
            }

            if (username.equals(adminUsername) && password.equals(adminPassword)) {
                JOptionPane.showMessageDialog(null, "Admin Login Successful!");
                frame.dispose();
                createMainFrame().dispose();
                new Admin().display();
            } else if (username.equals(studentUsername) && password.equals(studentPassword)) {
                JOptionPane.showMessageDialog(null, "Student Login Successful!");
                frame.dispose();
                new StudentMenu().menu();
            } else {
                throw new IllegalArgumentException("Invalid login credentials!");
            }
        } catch (IllegalArgumentException ex) {
            errorLabel.setText(ex.getMessage());
        } catch (Exception ex) {
            errorLabel.setText("An unexpected error occurred. Please try again.");
        }
    }
    // Getter Methods for Unit Testing
    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }
}
