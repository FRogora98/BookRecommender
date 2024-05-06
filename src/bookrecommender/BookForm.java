package bookrecommender;

import javax.swing.*;
import java.awt.*;

public class BookForm extends JFrame {
    private JLabel lblTitle = new JLabel();
    private JLabel lblAuthors = new JLabel();
    private JLabel lblDescription = new JLabel();
    private JLabel lblCategory = new JLabel();
    private JLabel lblPublisher = new JLabel();
    private JLabel lblPrice = new JLabel();
    private JLabel lblPublishDate = new JLabel();
    private JLabel lblAverageRating = new JLabel();
    private JLabel lblRecommendedBooks = new JLabel();

    public BookForm(Book book) {
        setTitle("Dettagli del libro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 1));

        lblTitle.setText("Titolo: " + book.getTitle());
        lblAuthors.setText("Autori: " + book.getAuthors());
        lblDescription.setText("Descrizione: " + book.getDescription());
        lblCategory.setText("Categoria: " + book.getCategory());
        lblPublisher.setText("Editore: " + book.getPublisher());
        lblPrice.setText("Prezzo: " + book.getPrice());
        lblPublishDate.setText("Data di pubblicazione: " + book.getPublishMonth() + " " + book.getPublishYear());
        lblAverageRating.setText("Valutazione media: " + calculateAverageRating(book));
        lblRecommendedBooks.setText("Libri consigliati: " + getRecommendedBooks(book));

        add(lblTitle);
        add(lblAuthors);
        add(lblDescription);
        add(lblCategory);
        add(lblPublisher);
        add(lblPrice);
        add(lblPublishDate);
        add(lblAverageRating);
        add(lblRecommendedBooks);

        setVisible(true);
    }

    private String calculateAverageRating(Book book) {
        // Logica per calcolare la media delle valutazioni degli utenti per il libro
        return "N/A"; // Da implementare
    }

    private String getRecommendedBooks(Book book) {
        // Logica per ottenere i libri consigliati per il libro corrente
        return "N/A"; // Da implementare
    }
}

