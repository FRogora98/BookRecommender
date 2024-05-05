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

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel firstNameLabel = new JLabel("Nome:");
        panel.add(firstNameLabel);

        firstNameField = new JTextField();
        panel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Cognome:");
        panel.add(lastNameLabel);

        lastNameField = new JTextField();
        panel.add(lastNameField);

        JLabel birthYearLabel = new JLabel("Anno di nascita:");
        panel.add(birthYearLabel);

        birthYearField = new JTextField();
        panel.add(birthYearField);

        JLabel fiscalCodeLabel = new JLabel("Codice fiscale:");
        panel.add(fiscalCodeLabel);

        fiscalCodeField = new JTextField();
        panel.add(fiscalCodeField);

        JButton registerButton = new JButton("Registra");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String birthYear = birthYearField.getText();
                String fiscalCode = fiscalCodeField.getText();

                // Verifica che tutti i campi siano stati compilati
                if (firstName.isEmpty() || lastName.isEmpty() || birthYear.isEmpty() || fiscalCode.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Completa tutti i campi.");
                } else {
                    // Effettua la registrazione
                    registerUser(firstName, lastName, birthYear, fiscalCode);
                }
            }
        });
        panel.add(registerButton);

        add(panel);
        setVisible(true);
    }

    private void registerUser(String firstName, String lastName, String birthYear, String fiscalCode) {
        // Codice per registrare l'utente (es. scrivere su file, salvare su database, ecc.)
        try {
            FileWriter writer = new FileWriter("./data/UtentiRegistrati.csv", true);
            writer.append(firstName + "," + lastName + "," + birthYear + "," + fiscalCode + "\n");
            writer.close();
            JOptionPane.showMessageDialog(null, "Utente registrato con successo.");
            dispose(); // Chiude la finestra di registrazione
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Errore durante la registrazione.");
            ex.printStackTrace();
        }
    }
}
