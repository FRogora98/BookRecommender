package bookrecommender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterForm extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField birthYearField;
    private JTextField fiscalCodeField;

    public RegisterForm() {
        super("Registrazione");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Nome:"));
        firstNameField = new JTextField();
        panel.add(firstNameField);

        panel.add(new JLabel("Cognome:"));
        lastNameField = new JTextField();
        panel.add(lastNameField);

        panel.add(new JLabel("Anno di nascita:"));
        birthYearField = new JTextField();
        panel.add(birthYearField);

        panel.add(new JLabel("Codice fiscale:"));
        fiscalCodeField = new JTextField();
        panel.add(fiscalCodeField);

        JButton registerButton = new JButton("Registra");
        registerButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String birthYear = birthYearField.getText();
            String fiscalCode = fiscalCodeField.getText();

            if (firstName.isEmpty() || lastName.isEmpty() || birthYear.isEmpty() || fiscalCode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Completa tutti i campi.");
            } else {
                registerUser(firstName, lastName, birthYear, fiscalCode);
            }
        });
        panel.add(registerButton);

        add(panel);
        setVisible(true);
    }

    private void registerUser(String firstName, String lastName, String username, String fiscalCode) {
        try {
            FileWriter writer = new FileWriter("./data/UtentiRegistrati.csv", true);
            writer.append(firstName + "," + lastName + "," + username + "," + fiscalCode + "\n");
            writer.close();
            JOptionPane.showMessageDialog(null, "Utente registrato con successo.");
            dispose();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Errore durante la registrazione.");
            ex.printStackTrace();
        }
    }
}
