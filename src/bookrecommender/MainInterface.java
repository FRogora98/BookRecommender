package bookrecommender;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainInterface {
    private static JFrame frame = new JFrame("Book Recommender System");
    private static JLabel lblUsername = new JLabel("Benvenuto, Utente", JLabel.CENTER);
    private static boolean isLoggedIn = false;
    private static JButton btnLogin = new JButton("Login");
    private static JButton btnLogout = new JButton("Logout");
    private static JButton btnAddBook = new JButton("Aggiungi libro");
    private static JButton btnReview = new JButton("Scrivi recensione");
    private static JButton btnRegister = new JButton("Registrati");
    private static JTextField searchField = new JTextField(20);

    public static void main(String[] args) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(btnLogin);
        topPanel.add(btnLogout);
        topPanel.add(btnRegister);
        btnLogout.setVisible(false); // Initially the logout button is not visible

        JPanel centerPanel = new JPanel(new FlowLayout());
        JButton searchButton = new JButton("Cerca");
        centerPanel.add(searchField);
        centerPanel.add(searchButton);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 5));
        bottomPanel.add(btnAddBook);
        bottomPanel.add(btnReview);
        JButton btnShowBooks = new JButton("Mostra tutti i libri");
        JButton btnExit = new JButton("Esci");
        bottomPanel.add(btnShowBooks);
        bottomPanel.add(btnExit);

        btnAddBook.setVisible(false);
        btnReview.setVisible(false);

        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(lblUsername, BorderLayout.WEST);

        btnLogin.addActionListener(e -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setLoginListener(username -> {
                lblUsername.setText("Benvenuto, " + username);
                lblUsername.setVisible(true);
                isLoggedIn = true;
                updateInterfaceForLoggedInUser();
            });
            loginForm.setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            isLoggedIn = false;
            lblUsername.setVisible(false);
            updateInterfaceForLoggedInUser();
        });

        btnRegister.addActionListener(e -> {
            RegisterForm registerForm = new RegisterForm();
            registerForm.setVisible(true);
        });

        btnExit.addActionListener(e -> {
            exitApplication();
        });

        // Aggiungi un listener per l'evento di pressione del tasto "Cerca" nel campo di ricerca
        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText();
            List<Book> searchResults = searchBooks(searchTerm);
            displaySearchResults(searchResults);
        });

        frame.setVisible(true);
    }

    private static void updateInterfaceForLoggedInUser() {
        btnLogin.setVisible(!isLoggedIn);
        btnLogout.setVisible(isLoggedIn);
        btnRegister.setVisible(!isLoggedIn); // Show register button only when not logged in
        btnAddBook.setVisible(isLoggedIn);
        btnReview.setVisible(isLoggedIn);
    }

    private static void exitApplication() {
        frame.dispose(); // Chiude il frame
        System.exit(0); // Termina l'applicazione
    }

    private static List<Book> searchBooks(String searchTerm) {
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
            JOptionPane.showMessageDialog(null, "Errore durante la ricerca dei libri.");
            e.printStackTrace();
        }
        return results;
    }

    private static boolean containsIgnoreCase(String[] array, String searchTerm) {
        for (String str : array) {
            if (str.trim().equalsIgnoreCase(searchTerm)) {
                return true;
            }
        }
        return false;
    }

    private static void displaySearchResults(List<Book> searchResults) {
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
