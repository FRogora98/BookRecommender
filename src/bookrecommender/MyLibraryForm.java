package bookrecommender;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.io.*;
import java.util.List;

public class MyLibraryForm extends JFrame {
    private JList<String> libraryList;
    private JList<String> bookDetailsList;
    private DefaultListModel<String> libraryModel;
    private DefaultListModel<String> bookDetailsModel;
    private JButton createLibraryButton;

    public MyLibraryForm() {
        setTitle("My Library");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        setLocationRelativeTo(null);

        libraryModel = new DefaultListModel<>();
        libraryList = new JList<>(libraryModel);
        libraryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        libraryList.addListSelectionListener(this::librarySelected);

        bookDetailsModel = new DefaultListModel<>();
        bookDetailsList = new JList<>(bookDetailsModel);

        createLibraryButton = new JButton("Crea Libreria");
        createLibraryButton.addActionListener(e -> openLibraryCreationDialog());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(libraryList),
                new JScrollPane(bookDetailsList));
        splitPane.setDividerLocation(200);

        add(splitPane, BorderLayout.CENTER);
        add(createLibraryButton, BorderLayout.SOUTH);

        loadLibraries();
        setVisible(true);
    }

    private void librarySelected(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String selectedLibrary = libraryList.getSelectedValue();
            loadLibraryDetails(selectedLibrary);
        }
    }

    private void loadLibraries() {
        // Load library names from Librerie.csv
        libraryModel.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/Librerie.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String libraryName = parts[0];
                if (!libraryModel.contains(libraryName)) {
                    libraryModel.addElement(libraryName);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore durante il caricamento delle librerie.", "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadLibraryDetails(String libraryName) {
        // Load books for the selected library from Librerie.csv
        bookDetailsModel.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/Librerie.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(libraryName + ",")) {
                    String[] parts = line.split(",");
                    bookDetailsModel.addElement(parts[1]);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore durante il caricamento dei dettagli della libreria.", "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openLibraryCreationDialog() {
        JDialog creationDialog = new JDialog(this, "Crea nuova libreria", true);
        DefaultListModel<String> allBooksModel = new DefaultListModel<>();
        JList<String> allBooksList = new JList<>(allBooksModel);
        JTextField filterTitle = new JTextField(10);
        JTextField filterAuthor = new JTextField(10);
        JTextField filterYear = new JTextField(10);
        JButton filterButton = new JButton("Filtra");
        JButton saveButton = new JButton("Salva Libreria");

        loadAllBooks(allBooksModel);

        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Titolo:"));
        filterPanel.add(filterTitle);
        filterPanel.add(new JLabel("Autore:"));
        filterPanel.add(filterAuthor);
        filterPanel.add(new JLabel("Anno:"));
        filterPanel.add(filterYear);
        filterPanel.add(filterButton);

        creationDialog.setLayout(new BorderLayout());
        creationDialog.add(filterPanel, BorderLayout.NORTH);
        creationDialog.add(new JScrollPane(allBooksList), BorderLayout.CENTER);
        creationDialog.add(saveButton, BorderLayout.SOUTH);

        filterButton.addActionListener(
                e -> applyFilters(allBooksModel, filterTitle.getText(), filterAuthor.getText(), filterYear.getText()));

        saveButton.addActionListener(e -> {
            List<String> selectedBooks = allBooksList.getSelectedValuesList();
            String libraryName = JOptionPane.showInputDialog(creationDialog, "Inserisci il nome della nuova libreria:");
            if (libraryName != null && !libraryName.isEmpty()) {
                saveLibrary(libraryName, selectedBooks);
                libraryModel.addElement(libraryName);
                creationDialog.dispose();
            }
        });

        creationDialog.pack();
        creationDialog.setLocationRelativeTo(this);
        creationDialog.setVisible(true);
    }

    private void applyFilters(DefaultListModel<String> model, String title, String author, String year) {
        model.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/Libri.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    String bookTitle = data[0];
                    String bookAuthor = data[1];
                    String bookYear = data[2];
                    if (bookTitle.toLowerCase().contains(title.toLowerCase()) &&
                            bookAuthor.toLowerCase().contains(author.toLowerCase()) &&
                            bookYear.toLowerCase().contains(year.toLowerCase())) {
                        model.addElement(bookTitle + " - " + bookAuthor + " - " + bookYear);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadAllBooks(DefaultListModel<String> model) {
        // Load all books from Libri.csv
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/Libri.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                model.addElement(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore durante il caricamento dei libri.", "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveLibrary(String libraryName, List<String> selectedBooks) {
        // Save the new library to Librerie.csv
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