package bookrecommender;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyLibraryForm extends JFrame {
    private JTable libraryTable;
    private JTable bookDetailsTable;
    private DefaultTableModel libraryTableModel;
    private DefaultTableModel bookDetailsTableModel;
    private JButton createLibraryButton;
    private String Username;

    public MyLibraryForm(List<Book> allBooks, String username) {
        this.Username = username;
        setTitle("My Library");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        libraryTableModel = new DefaultTableModel(new String[]{"Library Name"}, 0);
        libraryTable = new JTable(libraryTableModel);
        libraryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        libraryTable.getSelectionModel().addListSelectionListener(this::librarySelected);

        bookDetailsTableModel = new DefaultTableModel(new String[]{"Title", "Authors", "Year"}, 0);
        bookDetailsTable = new JTable(bookDetailsTableModel);

        createLibraryButton = new JButton("Crea Libreria");
        createLibraryButton.addActionListener(e -> openLibraryCreationDialog());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(libraryTable),
                new JScrollPane(bookDetailsTable));
        splitPane.setDividerLocation(200);

        add(splitPane, BorderLayout.CENTER);
        add(createLibraryButton, BorderLayout.SOUTH);

        loadLibraries();
        setVisible(true);
    }

    private void librarySelected(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = libraryTable.getSelectedRow();
            if (selectedRow != -1) {
                String libraryName = (String) libraryTableModel.getValueAt(selectedRow, 0);
                loadBookDetails(libraryName);
            }
        }
    }

    private void loadLibraries() {
        libraryTableModel.setRowCount(0);
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/Librerie.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length < 2) {
                    // Log some error or handle the issue
                    System.out.println("Skipping malformed line: " + line);
                    continue; // Skip this iteration if there are not enough parts
                }
                Library library = new Library(parts[0], Username);
                String[] bookInfos = parts[1].split(",");
                for (String bookInfo : bookInfos) {
                    String[] details = bookInfo.split(" - ");
                    if (details.length == 3) {
                        library.addBook(new Book(details[0], details[1], details[2]));
                    }
                }
                libraryTableModel.addRow(new Object[]{library.getLibraryName()});
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void loadBookDetails(String libraryName) {
        bookDetailsTableModel.setRowCount(0);
        Library library = findLibraryByName(libraryName);
        if (library != null) {
            for (Book book : library.getBooks()) {
                bookDetailsTableModel.addRow(new Object[]{book.getTitle(), book.getAuthors(), book.getPublishYear()});
            }
        }
    }

    private Library findLibraryByName(String name) {
        for (int i = 0; i < libraryTableModel.getRowCount(); i++) {
            if (libraryTableModel.getValueAt(i, 0).equals(name)) {
                // Assuming the library object is stored in a map or list somewhere
                // Since this is a simplified version, we will re-read the file (not efficient)
                try (BufferedReader reader = new BufferedReader(new FileReader("./data/Librerie.csv"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",", 2);
                        if (parts[0].equals(name)) {
                            Library library = new Library(parts[0], Username);
                            String[] bookInfos = parts[1].split(",");
                            for (String bookInfo : bookInfos) {
                                String[] details = bookInfo.split(" - ");
                                if (details.length == 3) {
                                    library.addBook(new Book(details[0], details[1], details[2]));
                                }
                            }
                            return library;
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
        return null;
    }

    private void openLibraryCreationDialog() {
        JDialog creationDialog = new JDialog(this, "Crea nuova libreria", true);
        creationDialog.setLayout(new BorderLayout());

        DefaultTableModel allBooksModel = new DefaultTableModel(new String[]{"Title", "Authors", "Year"}, 0);
        JTable allBooksTable = new JTable(allBooksModel);
        loadAllBooks(allBooksModel);

        JPanel filterPanel = new JPanel(new GridLayout(0, 2));
        JTextField filterTitle = new JTextField();
        JTextField filterAuthor = new JTextField();
        JTextField filterYear = new JTextField();
        JButton filterButton = new JButton("Filtra");
        filterPanel.add(new JLabel("Titolo:"));
        filterPanel.add(filterTitle);
        filterPanel.add(new JLabel("Autore:"));
        filterPanel.add(filterAuthor);
        filterPanel.add(new JLabel("Anno:"));
        filterPanel.add(filterYear);
        filterPanel.add(filterButton);

        filterButton.addActionListener(
            e -> applyFilters(allBooksModel, filterTitle.getText(), filterAuthor.getText(), filterYear.getText()));

        JButton saveButton = new JButton("Salva Libreria");
        saveButton.addActionListener(e -> {
            int[] selectedRows = allBooksTable.getSelectedRows();
            List<Book> selectedBooks = new ArrayList<>();
            for (int row : selectedRows) {
                Book book = new Book(
                    (String) allBooksModel.getValueAt(row, 0),
                    (String) allBooksModel.getValueAt(row, 1),
                    (String) allBooksModel.getValueAt(row, 2)
                );
                selectedBooks.add(book);
            }
            String libraryName = JOptionPane.showInputDialog(creationDialog, "Inserisci il nome della nuova libreria:");
            if (libraryName != null && !libraryName.isEmpty()) {
                Library newLibrary = new Library(libraryName, Username);
                selectedBooks.forEach(newLibrary::addBook);
                libraryTableModel.addRow(new Object[]{newLibrary.getLibraryName()});
                saveLibrary(newLibrary);
                creationDialog.dispose();
            }
        });

        creationDialog.add(filterPanel, BorderLayout.NORTH);
        creationDialog.add(new JScrollPane(allBooksTable), BorderLayout.CENTER);
        creationDialog.add(saveButton, BorderLayout.SOUTH);

        creationDialog.pack();
        creationDialog.setLocationRelativeTo(this);
        creationDialog.setVisible(true);
    }

    private void loadAllBooks(DefaultTableModel model) {
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/Libri.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    model.addRow(new Object[]{data[0], data[1], data[2]});
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void applyFilters(DefaultTableModel model, String title, String author, String year) {
        model.setRowCount(0);
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/Libri.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    Book book = new Book(data[0], data[1], data[2]);
                    if (book.getTitle().toLowerCase().contains(title.toLowerCase()) &&
                        book.getAuthors().toLowerCase().contains(author.toLowerCase()) &&
                        book.getPublishYear().toLowerCase().contains(year.toLowerCase())) {
                        model.addRow(new Object[]{book.getTitle(), book.getAuthors(), book.getPublishYear()});
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveLibrary(Library library) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./data/Librerie.csv", true))) {
            writer.write(library.getLibraryName() + ",");
            for (Book book : library.getBooks()) {
                writer.write(book.getTitle() + " - " + book.getAuthors() + " - " + book.getPublishYear() + ",");
            }
            writer.write("\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
