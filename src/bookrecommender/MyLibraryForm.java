package bookrecommender;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.io.*;
import java.util.List;

public class MyLibraryForm extends JFrame {
    private JList<Library> libraryList;
    private JList<Book> bookDetailsList;
    private DefaultListModel<Library> libraryModel;
    private DefaultListModel<Book> bookDetailsModel;
    private JButton createLibraryButton;
    private String Username;

    public MyLibraryForm(List<Book> allBooks, String username) {
        this.Username = username;
        setTitle("My Library");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        setLocationRelativeTo(null);

        libraryModel = new DefaultListModel<>();
        libraryList = new JList<>(libraryModel);
        libraryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        libraryList.addListSelectionListener(this::librarySelected);
        libraryList.setCellRenderer(new LibraryListCellRenderer());

        bookDetailsModel = new DefaultListModel<>();
        bookDetailsList = new JList<>(bookDetailsModel);
        bookDetailsList.setCellRenderer(new BookListCellRenderer());

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
        if (!e.getValueIsAdjusting() && libraryList.getSelectedValue() != null) {
            Library selectedLibrary = libraryList.getSelectedValue();
            bookDetailsModel.clear();
            selectedLibrary.getBooks().forEach(bookDetailsModel::addElement);
        }
    }

    private void loadLibraries() {
        libraryModel.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/Librerie.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Library library = new Library(parts[0], Username);
                for (int i = 1; i < parts.length; i++) {
                    String[] bookInfo = parts[i].split(" - ");
                    if (bookInfo.length == 3) {
                        library.addBook(new Book(bookInfo[0], bookInfo[1], bookInfo[2]));
                    }
                }
                libraryModel.addElement(library);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore durante il caricamento delle librerie.", "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /*------------------------------------------------------------------------------------- */
    /*------------------------------------------------------------------------------------- */
    /*------------------------------------------------------------------------------------- */
    // OPENCREATIONDIALOG
    private void openLibraryCreationDialog() {
        JDialog creationDialog = new JDialog(this, "Crea nuova libreria", true);
        creationDialog.setLayout(new BorderLayout());

        // Model and list for displaying available books
        DefaultListModel<Book> allBooksModel = new DefaultListModel<>();
        JList<Book> allBooksList = new JList<>(allBooksModel);
        allBooksList.setCellRenderer(new BookListCellRenderer());

        loadAllBooks(allBooksModel);

        // Filters
        JPanel filterPanel = new JPanel();
        JTextField filterTitle = new JTextField(10);
        JTextField filterAuthor = new JTextField(10);
        JTextField filterYear = new JTextField(10);
        JButton filterButton = new JButton("Filtra");
        filterPanel.add(new JLabel("Titolo:"));
        filterPanel.add(filterTitle);
        filterPanel.add(new JLabel("Autore:"));
        filterPanel.add(filterAuthor);
        filterPanel.add(new JLabel("Anno:"));
        filterPanel.add(filterYear);
        filterPanel.add(filterButton);

        // Actions for filter button
        filterButton.addActionListener(
                e -> applyFilters(allBooksModel, filterTitle.getText(), filterAuthor.getText(), filterYear.getText()));

        // Button to save the new library
        JButton saveButton = new JButton("Salva Libreria");
        saveButton.addActionListener(e -> {
            List<Book> selectedBooks = allBooksList.getSelectedValuesList();
            String libraryName = JOptionPane.showInputDialog(creationDialog, "Inserisci il nome della nuova libreria:");
            if (libraryName != null && !libraryName.isEmpty()) {
                Library newLibrary = new Library(libraryName, Username);
                selectedBooks.forEach(newLibrary::addBook);
                libraryModel.addElement(newLibrary);
                saveLibrary(newLibrary);
                creationDialog.dispose();
            }
        });

        creationDialog.add(filterPanel, BorderLayout.NORTH);
        creationDialog.add(new JScrollPane(allBooksList), BorderLayout.CENTER);
        creationDialog.add(saveButton, BorderLayout.SOUTH);

        creationDialog.pack();
        creationDialog.setLocationRelativeTo(this);
        creationDialog.setVisible(true);
    }

    private void applyFilters(DefaultListModel<Book> model, String title, String author, String year) {
        model.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/Libri.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    Book book = new Book(data[0], data[1], data[2]);
                    if (book.getTitle().toLowerCase().contains(title.toLowerCase()) &&
                            book.getAuthors().toLowerCase().contains(author.toLowerCase()) &&
                            book.getPublishYear().toLowerCase().contains(year.toLowerCase())) {
                        model.addElement(book);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadAllBooks(DefaultListModel<Book> model) {
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/Libri.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    model.addElement(new Book(data[0], data[1], data[2]));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveLibrary(Library library) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./data/Librerie.csv", true))) {
            for (Book book : library.getBooks()) {
                writer.write(library.getLibraryName() + "," + book.getTitle() + " - " + book.getAuthors() + " - "
                        + book.getPublishYear() + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    // FINE OPERCREATIONDIAOLOG
    /*------------------------------------------------------------------------------------- */
    /*------------------------------------------------------------------------------------- */

    class LibraryListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Library) {
                setText(((Library) value).getLibraryName());
            }
            return this;
        }
    }

    class BookListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Book) {
                Book book = (Book) value;
                setText(book.getTitle() + " - " + book.getAuthors() + " - " + book.getPublishYear());
            }
            return this;
        }
    }
}