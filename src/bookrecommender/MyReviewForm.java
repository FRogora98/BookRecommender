package bookrecommender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyReviewForm extends JFrame {
    private JLabel lblStyle = new JLabel("Stile:");
    private JLabel lblContent = new JLabel("Contenuto:");
    private JLabel lblEnjoyability = new JLabel("Gradevolezza:");
    private JLabel lblOriginality = new JLabel("Originalità:");
    private JLabel lblEdition = new JLabel("Edizione:");
    private JLabel lblFinalVote = new JLabel("Voto Finale:");
    private JTextField txtStyle = new JTextField(5);
    private JTextField txtContent = new JTextField(5);
    private JTextField txtEnjoyability = new JTextField(5);
    private JTextField txtOriginality = new JTextField(5);
    private JTextField txtEdition = new JTextField(5);
    private JTextField txtFinalVote = new JTextField(256);
    private JButton btnSubmit = new JButton("Inoltra Recensione");
    private Book book;
    private String username;

    public MyReviewForm(Book book, String username) {
        this.book = book;
        this.username = username;

        setTitle("Recensione Libro");
        setSize(800, 400); // Imposto le dimensioni del frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(lblStyle);
        panel.add(txtStyle);
        panel.add(lblContent);
        panel.add(txtContent);
        panel.add(lblEnjoyability);
        panel.add(txtEnjoyability);
        panel.add(lblOriginality);
        panel.add(txtOriginality);
        panel.add(lblEdition);
        panel.add(txtEdition);
        panel.add(lblFinalVote);
        panel.add(txtFinalVote);
        panel.add(new JLabel()); // Spazio vuoto per allineare il bottone
        panel.add(btnSubmit);

        // Imposto la dimensione delle label per adattarsi meglio
        Dimension labelSize = new Dimension(120, 20);
        lblStyle.setPreferredSize(labelSize);
        lblContent.setPreferredSize(labelSize);
        lblEnjoyability.setPreferredSize(labelSize);
        lblOriginality.setPreferredSize(labelSize);
        lblEdition.setPreferredSize(labelSize);
        lblFinalVote.setPreferredSize(labelSize);

        btnSubmit.addActionListener(this::submitReview);

        add(panel);
        setVisible(true);
    }

    private void submitReview(ActionEvent e) {
        try {
            int style = Integer.parseInt(txtStyle.getText());
            int content = Integer.parseInt(txtContent.getText());
            int enjoyability = Integer.parseInt(txtEnjoyability.getText());
            int originality = Integer.parseInt(txtOriginality.getText());
            int edition = Integer.parseInt(txtEdition.getText());
            String finalVote = txtFinalVote.getText();

            // Verifica che i voti siano compresi tra 1 e 5
            if (isValidVote(style) && isValidVote(content) && isValidVote(enjoyability) &&
                    isValidVote(originality) && isValidVote(edition)) {
                // Carica tutte le recensioni esistenti
                List<String> existingReviews = loadExistingReviews();
                // Sovrascrivi la recensione se esiste già una recensione per questo libro da parte dello stesso utente
                boolean overwritten = overwriteExistingReview(existingReviews, style, content, enjoyability, originality, edition, finalVote);
                // Scrivi tutte le recensioni, inclusa la nuova se non sovrascritta
                writeReviews(existingReviews);
                if (!overwritten) {
                    // Aggiungi una nuova recensione
                    addNewReview(style, content, enjoyability, originality, edition, finalVote);
                }
                JOptionPane.showMessageDialog(null, "Recensione aggiunta con successo.");
                dispose(); // Chiudi il form dopo l'inoltro della recensione
            } else {
                JOptionPane.showMessageDialog(null, "I voti devono essere compresi tra 1 e 5.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Inserire voti validi (numeri interi da 1 a 5).");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Errore durante l'aggiunta della recensione.");
            ex.printStackTrace();
        }
    }

    private List<String> loadExistingReviews() throws IOException {
        List<String> existingReviews = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/ValutazioneLibri.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                existingReviews.add(line);
            }
        }
        return existingReviews;
    }

    private boolean overwriteExistingReview(List<String> existingReviews, int style, int content, int enjoyability, int originality, int edition, String finalVote) {
        String newReview = book.getTitle() + "," + book.getAuthors() + "," + book.getPublishYear() + ",";
        newReview += style + "," + content + "," + enjoyability + "," + originality + "," + edition + "," + finalVote + ",";
        newReview += username;
        for (int i = 0; i < existingReviews.size(); i++) {
            String[] parts = existingReviews.get(i).split(",");
            if (parts.length >= 9 && parts[0].equals(book.getTitle()) && parts[7].equals(username)) {
                existingReviews.set(i, newReview); // Sovrascrivi la recensione esistente
                return true; // Recensione sovrascritta
            }
        }
        return false; // Nessuna recensione sovrascritta
    }

    private void writeReviews(List<String> reviews) throws IOException {
        try (FileWriter writer = new FileWriter("./data/ValutazioneLibri.csv")) {
            for (String review : reviews) {
                writer.write(review + "\n");
            }
        }
    }

    private void addNewReview(int style, int content, int enjoyability, int originality, int edition, String finalVote) throws IOException {
        try (FileWriter writer = new FileWriter("./data/ValutazioneLibri.csv", true)) {
            writer.write(book.getTitle() + "," + book.getAuthors() + "," + book.getPublishYear() + ",");
            writer.write(style + "," + content + "," + enjoyability + "," + originality + "," + edition + "," + finalVote + ",");
            writer.write(username + "\n");
        }
    }

    private boolean isValidVote(int vote) {
        return vote >= 1 && vote <= 5;
    }
}
