package bookrecommender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
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

        JButton registerButton = new JButton("Registrati");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRegistrationForm();
            }
        });
        panel.add(registerButton);

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

    private void showRegistrationForm() {
        JFrame registrationFrame = new JFrame("Registrazione");
        registrationFrame.setSize(300, 200);
        registrationFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        JTextField newUsernameField = new JTextField();
        panel.add(newUsernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        JPasswordField newPasswordField = new JPasswordField();
        panel.add(newPasswordField);

        JButton registerButton = new JButton("Registra");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newUsername = newUsernameField.getText();
                String newPassword = new String(newPasswordField.getPassword());
                // Codice per registrare l'utente nel file CSV
                try {
                    FileWriter writer = new FileWriter("./data/UtentiRegistrati.csv", true);
                    writer.append(newUsername + "," + newPassword + "\n");
                    writer.close();
                    JOptionPane.showMessageDialog(null, "Utente registrato con successo.");
                    registrationFrame.dispose(); // Chiude la finestra di registrazione
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Errore durante la registrazione.");
                    ex.printStackTrace();
                }
            }
        });
        panel.add(registerButton);

        registrationFrame.add(panel);
        registrationFrame.setVisible(true);
    }
}
