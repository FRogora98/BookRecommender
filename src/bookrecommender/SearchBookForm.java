package bookrecommender;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class SearchBookForm extends JFrame {
    private JTextField searchFieldTitle = new JTextField(15);
    private JTextField searchFieldAuthor = new JTextField(15);
    private JTextField searchFieldYear = new JTextField(8);
    private DefaultTableModel model;
    private JTable table;
    private List<Book> allBooks;

    public SearchBookForm(List<Book> books) {
        this.allBooks = books;
        setTitle("Cerca Libri");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        searchPanel.add(new JLabel("Titolo:"));
        searchPanel.add(searchFieldTitle);
        searchPanel.add(new JLabel("Autore:"));
        searchPanel.add(searchFieldAuthor);
        searchPanel.add(new JLabel("Anno:"));
        searchPanel.add(searchFieldYear);

        JButton searchButton = new JButton("Cerca");
        searchButton.addActionListener(this::performSearch);
        searchPanel.add(searchButton);

        model = new DefaultTableModel(new String[]{"Titolo", "Autori", "Anno di pubblicazione"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
        setVisible(true);

        // Aggiungi un listener per fare clic su un libro nella tabella dei risultati di ricerca
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());
                if (row >= 0 && column >= 0) {
                    openBookForm(row);
                }
            }
        });
    }

    private void performSearch(ActionEvent e) {
        String titleFilter = searchFieldTitle.getText().toLowerCase();
        String authorFilter = searchFieldAuthor.getText().toLowerCase();
        String yearFilter = searchFieldYear.getText().toLowerCase();

        List<Book> filteredBooks = allBooks.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(titleFilter))
                .filter(book -> book.getAuthors().toLowerCase().contains(authorFilter))
                .filter(book -> book.getPublishYear().toLowerCase().contains(yearFilter))
                .collect(Collectors.toList());

        updateTable(filteredBooks);
    }

    private void updateTable(List<Book> books) {
        model.setRowCount(0);
        for (Book book : books) {
            model.addRow(new Object[]{book.getTitle(), book.getAuthors(), book.getPublishYear()});
        }
    }

    private void openBookForm(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < table.getRowCount()) {
            String title = (String) model.getValueAt(rowIndex, 0);
            String authors = (String) model.getValueAt(rowIndex, 1);
            String publishYear = (String) model.getValueAt(rowIndex, 2);

            Book book = new Book(title, authors, publishYear);
            new BookForm(book);
        }
    }
}
