package bookrecommender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CreateLibraryForm extends JFrame {
    private JTextField libraryNameField;
    private JButton createButton;

    public CreateLibraryForm() {
        setTitle("Crea Libreria");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Nome Libreria:");
        libraryNameField = new JTextField();
        panel.add(nameLabel);
        panel.add(libraryNameField);

        createButton = new JButton("Crea");
        createButton.addActionListener(this::createLibrary);
        panel.add(createButton);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void createLibrary(ActionEvent e) {
        String libraryName = libraryNameField.getText();
        if (!libraryName.isEmpty()) {
            Library newLibrary = new Library(libraryName);
            saveLibrary(newLibrary);
            JOptionPane.showMessageDialog(this, "Libreria creata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Il campo Nome Libreria non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveLibrary(Library library) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./data/Librerie.csv", true))) {
            writer.write(library.getLibraryName() + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore durante il salvataggio della libreria.", "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
