package bookrecommender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            if (checkCredentials(usernameField.getText(), new String(passwordField.getPassword()))) {
                JOptionPane.showMessageDialog(this, "Benvenuto nel bookrecommender!");
                if (loginListener != null) {
                    loginListener.onLogin(usernameField.getText());
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login fallito. Riprova.");
            }
        });
        panel.add(loginButton);

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
                if (parts.length >= 4 && parts[2].equals(username) && parts[3].equals(password)) {
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
