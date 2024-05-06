package bookrecommender;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private LoginListener loginListener;

    public LoginForm() {
        super("Login Form");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Aggiunge un margine al pannello

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Aggiunge spaziatura tra le caselle

        JLabel usernameLabel = new JLabel("Username:");
        inputPanel.add(usernameLabel);

        usernameField = new JTextField();
        inputPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        inputPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        inputPanel.add(passwordField);

        panel.add(inputPanel, BorderLayout.NORTH);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            if (checkCredentials(usernameField.getText(), new String(passwordField.getPassword()))) {
                JOptionPane.showMessageDialog(this, "Benvenuto nel Book Recommender!");
                if (loginListener != null) {
                    loginListener.onLogin(usernameField.getText());
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login fallito. Riprova.");
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }

    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    private boolean checkCredentials(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/UtentiRegistrati.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[4].equals(username) && parts[5].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Errore di accesso al file degli utenti.");
            e.printStackTrace();
        }
        return false;
    }
}

interface LoginListener {
    void onLogin(String username);
}
