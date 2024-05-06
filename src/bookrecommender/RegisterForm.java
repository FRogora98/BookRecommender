package bookrecommender;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterForm extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField mailField;
    private JTextField fiscalCodeField;
    private JTextField usernameField;
    private JTextField passField;

    public RegisterForm() {
        super("Registrazione");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2));

        panel.add(new JLabel("Nome:"));
        firstNameField = new JTextField();
        panel.add(firstNameField);

        panel.add(new JLabel("Cognome:"));
        lastNameField = new JTextField();
        panel.add(lastNameField);

        panel.add(new JLabel("Codice fiscale:"));
        fiscalCodeField = new JTextField();
        panel.add(fiscalCodeField);
        
        panel.add(new JLabel("Indirizzo mail:"));
        mailField = new JTextField();
        panel.add(mailField);

        panel.add(new JLabel("Nome utente:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passField = new JTextField();
        panel.add(passField);

        JButton registerButton = new JButton("Registra");
        registerButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String mail = mailField.getText();
            String fiscalCode = fiscalCodeField.getText();
            String username = usernameField.getText();
            String password = passField.getText();

            if (firstName.isEmpty() || lastName.isEmpty() || mail.isEmpty() || fiscalCode.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Completa tutti i campi.");
            } else {
                registerUser(firstName, lastName, mail, fiscalCode, username, password);
            }
        });
        panel.add(registerButton);

        add(panel);
        setVisible(true);
    }

    private void registerUser(String firstName, String lastName, String mail, String fiscalCode, String username, String password) {
        try {
            FileWriter writer = new FileWriter("./data/UtentiRegistrati.csv", true);
            writer.append(firstName + "," + lastName + "," + mail + "," + fiscalCode + "," + username + "," + password + "\n");
            writer.close();
            JOptionPane.showMessageDialog(null, "Utente registrato con successo.");
            dispose();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Errore durante la registrazione.");
            ex.printStackTrace();
        }
    }
}
