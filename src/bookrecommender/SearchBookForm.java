package bookrecommender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchBookForm extends JFrame {
    private JTextField searchField;

    public SearchBookForm() {
        super("Cerca Libri");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel searchLabel = new JLabel("Inserisci il Titolo, Autore o Categoria:");
        panel.add(searchLabel);

        searchField = new JTextField(20);
        panel.add(searchField);

        JButton searchButton = new JButton("Cerca");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText();
                List<Book> searchResults = searchBooks(searchTerm);
                // Visualizza i risultati della ricerca in un'altra finestra o come preferisci
                displaySearchResults(searchResults);
            }
        });
        panel.add(searchButton);

        add(panel);
        setVisible(true);
    }

    private List<Book> searchBooks(String searchTerm) {
        List<Book> results = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./BooksDatasetClean.csv"))) {
            String line;
            br.readLine(); // Salta la riga dell'intestazione
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String title = parts[0];
                String[] authors = parts[1].split("\\|");
                String description = parts[2];
                String category = parts[3];
                String publisher = parts[4];
                String price = parts[5];
                String publishMonth = parts[6];
                String publishYear = parts[7];
                Book book = new Book(title, authors, description, category, publisher, price, publishMonth, publishYear);
                // Esegui la logica per la ricerca basata su searchTerm
                if (title.contains(searchTerm) || containsIgnoreCase(authors, searchTerm) || category.contains(searchTerm)) {
                    results.add(book);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Errore durante la ricerca dei libri.");
            e.printStackTrace();
        }
        return results;
    }

    private boolean containsIgnoreCase(String[] array, String searchTerm) {
        for (String str : array) {
            if (str.trim().equalsIgnoreCase(searchTerm)) {
                return true;
            }
        }
        return false;
    }

    private void displaySearchResults(List<Book> searchResults) {
        JFrame searchResultsFrame = new JFrame("Risultati della ricerca");
        JPanel resultsPanel = new JPanel(new GridLayout(searchResults.size(), 1));
        for (Book book : searchResults) {
            JLabel titleLabel = new JLabel("Titolo: " + book.getTitle());
            JLabel authorsLabel = new JLabel("Autori: " + String.join(", ", book.getAuthors()));
            JLabel categoryLabel = new JLabel("Categoria: " + book.getCategory());
            // Aggiungi altre informazioni del libro se necessario
            resultsPanel.add(titleLabel);
            resultsPanel.add(authorsLabel);
            resultsPanel.add(categoryLabel);
        }
        searchResultsFrame.getContentPane().add(resultsPanel);
        searchResultsFrame.pack();
        searchResultsFrame.setLocationRelativeTo(null);
        searchResultsFrame.setVisible(true);
    }
    
}

