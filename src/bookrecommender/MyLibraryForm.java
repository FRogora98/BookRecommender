package bookrecommender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyLibraryForm extends JFrame {
    private DefaultListModel<String> libraryModel;
    private JList<String> libraryList;
    private JButton createButton;
    private JButton showLibrariesButton;

    public MyLibraryForm() {
        setTitle("My Library");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        libraryModel = new DefaultListModel<>();
        libraryList = new JList<>(libraryModel);
        add(new JScrollPane(libraryList), BorderLayout.CENTER);

        createButton = new JButton("Crea Libreria");
        createButton.addActionListener(this::createLibrary);
        showLibrariesButton = new JButton("Mostra Librerie");
        showLibrariesButton.addActionListener(this::showLibraries);

        JPanel southPanel = new JPanel();
        southPanel.add(createButton);
        southPanel.add(showLibrariesButton);
        add(southPanel, BorderLayout.SOUTH);

        loadBooksIntoList();
        setVisible(true);
    }

    private void createLibrary(ActionEvent e) {
        String libraryName = JOptionPane.showInputDialog(this, "Nome della Libreria:", "Creazione Libreria",
                JOptionPane.PLAIN_MESSAGE);
        if (libraryName != null && !libraryName.isEmpty()) {
            List<String> selectedBooks = libraryList.getSelectedValuesList();
            if (!selectedBooks.isEmpty()) {
                saveLibrary(libraryName, selectedBooks);
                JOptionPane.showMessageDialog(this, "Libreria '" + libraryName + "' creata con successo!", "Successo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona almeno un libro per la libreria.", "Errore",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showLibraries(ActionEvent e) {
        JFrame librariesFrame = new JFrame("Librerie Esistenti");
        librariesFrame.setSize(400, 300);
        librariesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        librariesFrame.setLocationRelativeTo(this);

        DefaultListModel<String> librariesModel = new DefaultListModel<>();
        JList<String> librariesList = new JList<>(librariesModel);
        librariesFrame.add(new JScrollPane(librariesList));

        try (BufferedReader reader = new BufferedReader(new FileReader("./data/Librerie.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                librariesModel.addElement(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore durante il caricamento delle librerie.", "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }

        librariesFrame.setVisible(true);
    }

    private void loadBooksIntoList() {
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/Libri.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    String bookEntry = data[0] + " - " + data[1] + " - " + data[2];
                    libraryModel.addElement(bookEntry);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore durante il caricamento dei libri.", "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveLibrary(String libraryName, List<String> selectedBooks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./data/Librerie.csv", true))) {
            for (String book : selectedBooks) {
                writer.write(libraryName + "," + book + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore durante il salvataggio della libreria.", "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
