package bookrecommender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyLibraryForm extends JFrame {
    private JTable libraryTable;
    private JTable booksTable;
    private Map<String, Library> librariesMap;

    public MyLibraryForm(List<Book> allBooks, String utente) {
        setTitle("My Library");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        librariesMap = loadLibraries();

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Library Table
        String[] libraryColumnNames = {"Library Name", "Owner"};
        Object[][] libraryData = getLibraryData();
        DefaultTableModel libraryTableModel = new DefaultTableModel(libraryData, libraryColumnNames);
        libraryTable = new JTable(libraryTableModel);
        libraryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane libraryScrollPane = new JScrollPane(libraryTable);

        // Books Table
        String[] bookColumnNames = {"Title", "Authors", "Publish Year"};
        DefaultTableModel bookTableModel = new DefaultTableModel(new Object[][]{}, bookColumnNames);
        booksTable = new JTable(bookTableModel);
        JScrollPane booksScrollPane = new JScrollPane(booksTable);

        libraryTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = libraryTable.getSelectedRow();
                if (selectedRow != -1) {
                    String libraryName = (String) libraryTable.getValueAt(selectedRow, 0);
                    displayBooks(libraryName, bookTableModel);
                }
            }
        });

        leftPanel.add(new JLabel("Libraries"), BorderLayout.NORTH);
        leftPanel.add(libraryScrollPane, BorderLayout.CENTER);

        rightPanel.add(new JLabel("Books"), BorderLayout.NORTH);
        rightPanel.add(booksScrollPane, BorderLayout.CENTER);

        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(rightPanel, BorderLayout.CENTER);

        setContentPane(contentPane);
    }

    private Object[][] getLibraryData() {
        List<Object[]> data = new ArrayList<>();
        for (Library library : librariesMap.values()) { 
            String libraryName = library.getLibraryName();
            String owner = library.getOwner();
            data.add(new Object[]{libraryName, owner});
        }
        return data.toArray(new Object[0][]);
    }
    

    private Map<String, Library> loadLibraries() {
        Map<String, Library> librariesMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/Librerie.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (parts.length >= 6) {
                    String libraryName = parts[3].replaceAll("^\"|\"$", "");
                    String owner = parts[4].replaceAll("^\"|\"$", "");
    
                    Library library = librariesMap.getOrDefault(libraryName, new Library(libraryName, owner));
                    Book book = new Book(parts[0], parts[1], parts[2]);
                    library.addBook(book);
                    librariesMap.put(libraryName, library);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading libraries.");
            e.printStackTrace();
        }
        return librariesMap;
    }
    

    private void displayBooks(String libraryName, DefaultTableModel bookTableModel) {
        bookTableModel.setRowCount(0);
        Library library = librariesMap.get(libraryName);
        if (library != null) {
            for (Book book : library.getBooks()) {
                bookTableModel.addRow(new Object[]{book.getTitle(), book.getAuthors(), book.getPublishYear()});
            }
        }
    }
}
