package bookrecommender;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SearchBookForm extends JFrame {
    private JTextField searchField = new JTextField(20);
    private DefaultTableModel model;
    private JTable table;
    private List<Book> allBooks;

    public SearchBookForm(List<Book> books) {
        this.allBooks = books;
        setTitle("Cerca Libri");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Inserisci il termine di ricerca:"));
        searchPanel.add(searchField);

        // Aggiunta dei bottoni per la ricerca per titolo, autore e anno
        JButton searchByTitleButton = new JButton("Cerca Titolo");
        JButton searchByAuthorButton = new JButton("Cerca Autore");
        JButton searchByYearButton = new JButton("Cerca Anno");
        searchByTitleButton.addActionListener(this::searchByTitle);
        searchByAuthorButton.addActionListener(this::searchByAuthor);
        searchByYearButton.addActionListener(this::searchByYear);
        searchPanel.add(searchByTitleButton);
        searchPanel.add(searchByAuthorButton);
        searchPanel.add(searchByYearButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[] { "Titolo", "Autori", "Anno di pubblicazione" }, 0);
        table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0 && row < allBooks.size()) {
                    Book selectedBook = allBooks.get(row);
                    displayBookDetails(selectedBook);
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    // Metodo per la ricerca per titolo
    private void searchByTitle(ActionEvent e) {
        String searchTerm = searchField.getText();
        model.setRowCount(0); // Clear table
        for (Book book : allBooks) {
            if (book.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                model.addRow(new Object[] { book.getTitle(), book.getAuthors(), book.getPublishYear() });
            }
        }
    }

    // Metodo per la ricerca per autore
    private void searchByAuthor(ActionEvent e) {
        String searchTerm = searchField.getText();
        model.setRowCount(0); // Clear table
        for (Book book : allBooks) {
            if (book.getAuthors().toLowerCase().contains(searchTerm.toLowerCase())) {
                model.addRow(new Object[] { book.getTitle(), book.getAuthors(), book.getPublishYear() });
            }
        }
    }

    // Metodo per la ricerca per anno di pubblicazione
    private void searchByYear(ActionEvent e) {
        String searchTerm = searchField.getText();
        model.setRowCount(0); // Clear table
        for (Book book : allBooks) {
            if (book.getPublishYear().toLowerCase().contains(searchTerm.toLowerCase())) {
                model.addRow(new Object[] { book.getTitle(), book.getAuthors(), book.getPublishYear() });
            }
        }
    }

    private void displayBookDetails(Book book) {
        // Implement your code to display book details
    }
}
