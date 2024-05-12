package bookrecommender;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.List;

public class MyLibraryForm extends JFrame {
    private JTable libraryTable;
    private JTable bookDetailsTable;
    private DefaultTableModel libraryTableModel;
    private DefaultTableModel bookDetailsTableModel;
    private JButton createLibraryButton;
    private String username;
    private List<Book> allBooks;

    public MyLibraryForm(List<Book> allBooks, String username) {
        this.username = username;
        this.allBooks = allBooks;
        setTitle("My Library");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        initUI();
        loadLibraries();
        setVisible(true);
    }

    private void initUI() {
        libraryTableModel = new DefaultTableModel(new Object[]{"Library Name", "Action"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }

            public Class<?> getColumnClass(int column) {
                return column == 1 ? JButton.class : String.class;
            }
        };

        libraryTable = new JTable(libraryTableModel);
        libraryTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        libraryTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        libraryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        bookDetailsTableModel = new DefaultTableModel(new Object[]{"Title", "Authors", "Year"}, 0);
        bookDetailsTable = new JTable(bookDetailsTableModel);

        createLibraryButton = new JButton("Crea Libreria");
        createLibraryButton.addActionListener(e -> openLibraryCreationDialog());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(libraryTable), new JScrollPane(bookDetailsTable));
        splitPane.setDividerLocation(250);

        add(splitPane, BorderLayout.CENTER);
        add(createLibraryButton, BorderLayout.SOUTH);
    }

    private void loadLibraries() {
        libraryTableModel.setRowCount(0);
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/Librerie.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                libraryTableModel.addRow(new Object[]{parts[0], "Visualizza"});
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;
        }

        public Object getCellEditorValue() {
            return label;
        }

        public boolean stopCellEditing() {
            int selectedRow = libraryTable.getSelectedRow();
            if (selectedRow >= 0) {
                String libraryName = (String) libraryTableModel.getValueAt(selectedRow, 0);
                loadLibraryDetails(libraryName);
            }
            return super.stopCellEditing();
        }
    }

    private void loadLibraryDetails(String libraryName) {
        bookDetailsTableModel.setRowCount(0);
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/Librerie.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(libraryName + ",")) {
                    String booksData = line.substring(libraryName.length() + 1);
                    String[] books = booksData.split(",");
                    for (String book : books) {
                        String[] bookDetails = book.trim().split(" - ");
                        if (bookDetails.length == 3) {
                            bookDetailsTableModel.addRow(new Object[]{bookDetails[0], bookDetails[1], bookDetails[2]});
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void openLibraryCreationDialog() {
        JDialog creationDialog = new JDialog(this, "Crea nuova libreria", true);
        creationDialog.setLayout(new BorderLayout());
        DefaultTableModel allBooksModel = new DefaultTableModel(new Object[]{"Title", "Authors", "Year"}, 0);
        JTable allBooksTable = new JTable(allBooksModel);
        loadAllBooksToModel(allBooksModel);

        JPanel filterPanel = new JPanel(new FlowLayout());
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

        filterButton.addActionListener(e -> applyFilters(allBooksModel, filterTitle.getText(), filterAuthor.getText(), filterYear.getText()));

        JButton saveButton = new JButton("Salva Libreria");
        saveButton.addActionListener(e -> {
            int[] selectedRows = allBooksTable.getSelectedRows();
            String libraryName = JOptionPane.showInputDialog(creationDialog, "Inserisci il nome della nuova libreria:");
            if (libraryName != null && !libraryName.isEmpty()) {
                Library newLibrary = new Library(libraryName, username);
                for (int row : selectedRows) {
                    Book book = new Book(
                        allBooksModel.getValueAt(row, 0).toString(),
                        allBooksModel.getValueAt(row, 1).toString(),
                        allBooksModel.getValueAt(row, 2).toString()
                    );
                    newLibrary.addBook(book);
                }
                libraryTableModel.addRow(new Object[]{newLibrary.getLibraryName(), "Visualizza"});
                saveLibrary(newLibrary);
            }
        });
        creationDialog.add(filterPanel, BorderLayout.NORTH);
        creationDialog.add(new JScrollPane(allBooksTable), BorderLayout.CENTER);
        creationDialog.add(saveButton, BorderLayout.SOUTH);

        creationDialog.pack();
        creationDialog.setLocationRelativeTo(this);
        creationDialog.setVisible(true);
    }

    private void loadAllBooksToModel(DefaultTableModel model) {
        for (Book book : allBooks) {
            model.addRow(new Object[]{book.getTitle(), book.getAuthors(), book.getPublishYear()});
        }
    }

    private void applyFilters(DefaultTableModel model, String title, String author, String year) {
        model.setRowCount(0);
        for (Book book : allBooks) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase()) &&
                book.getAuthors().toLowerCase().contains(author.toLowerCase()) &&
                book.getPublishYear().toLowerCase().contains(year.toLowerCase())) {
                model.addRow(new Object[]{book.getTitle(), book.getAuthors(), book.getPublishYear()});
            }
        }
    }

    private void saveLibrary(Library library) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./data/Librerie.csv", true))) {
            writer.write(library.getLibraryName());
            for (Book book : library.getBooks()) {
                writer.write("," + book.getTitle() + " - " + book.getAuthors() + " - " + book.getPublishYear());
            }
            writer.write("\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}