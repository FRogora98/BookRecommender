package bookrecommender;

import javax.swing.*;
import java.awt.*;

public class BookForm extends JFrame {
    private JLabel lblTitle = new JLabel();
    private JLabel lblAuthors = new JLabel();
    private JLabel lblPublishDate = new JLabel();
    private JLabel lblAverageRating = new JLabel();
    private JLabel lblRecommendedBooks = new JLabel();

    public BookForm(Book book) {
        setTitle("Dettagli del libro");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 1));
        setResizable(false);

        // Font per le etichette
        Font labelFont = new Font("Arial", Font.BOLD, 14);

        lblTitle.setText("Titolo: " + book.getTitle());
        lblAuthors.setText("Autori: " + book.getAuthors());
        lblPublishDate.setText("Data di pubblicazione: " + book.getPublishYear());
        lblAverageRating.setText("Valutazione media: " + calculateAverageRating(book));
        lblRecommendedBooks.setText("Libri consigliati: " + getRecommendedBooks(book));

        // Imposta font per le etichette
        lblTitle.setFont(labelFont);
        lblAuthors.setFont(labelFont);
        lblPublishDate.setFont(labelFont);
        lblAverageRating.setFont(labelFont);
        lblRecommendedBooks.setFont(labelFont);

        add(lblTitle);
        add(lblAuthors);
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
