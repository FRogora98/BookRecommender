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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Chiude solo la finestra di login
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Codice per la gestione del login
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (checkCredentials(username, password)) {
                    JOptionPane.showMessageDialog(null, "Benvenuto nel bookrecommender!");
                    if (loginListener != null) {
                        loginListener.onLogin(username);
                    }
                    dispose(); // Chiude la finestra di login dopo il login
                } else {
                    JOptionPane.showMessageDialog(null, "Login fallito. Riprova.");
                }
            }
        });
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    public void addLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    private boolean checkCredentials(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/UtentiRegistrati.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String storedUsername = parts[0];
                    String storedPassword = parts[1];
                    if (username.equals(storedUsername) && password.equals(storedPassword)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
