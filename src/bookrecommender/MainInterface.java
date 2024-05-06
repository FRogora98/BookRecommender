package bookrecommender;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private static List<Book> allBooks = new ArrayList<>(); // Lista di tutti i libri caricati una sola volta

    public static void main(String[] args) {
        // Carica tutti i libri all'avvio dell'applicazione
        loadBooks();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(btnLogin);
        topPanel.add(btnLogout);
        topPanel.add(btnRegister);
        btnLogout.setVisible(false); // Initially the logout button is not visible

        JPanel centerPanel = new JPanel(new FlowLayout());
        JButton searchByTitleButton = new JButton("Cerca per Titolo");
        JButton searchByAuthorButton = new JButton("Cerca per Autore");
        JButton searchByYearButton = new JButton("Cerca per Anno di Uscita");
        centerPanel.add(searchField);
        centerPanel.add(searchByTitleButton);
        centerPanel.add(searchByAuthorButton);
        centerPanel.add(searchByYearButton);

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

        // Aggiungi un listener per l'evento di pressione del tasto "Cerca per Titolo"
        searchByTitleButton.addActionListener(e -> {
            String searchTerm = searchField.getText();
            List<Book> searchResults = searchBooksByTitle(searchTerm);
            displaySearchResults(searchResults);
        });

        // Aggiungi un listener per l'evento di pressione del tasto "Cerca per Autore"
        searchByAuthorButton.addActionListener(e -> {
            String searchTerm = searchField.getText();
            List<Book> searchResults = searchBooksByAuthor(searchTerm);
            displaySearchResults(searchResults);
        });

        // Aggiungi un listener per l'evento di pressione del tasto "Cerca per Anno di Uscita"
        searchByYearButton.addActionListener(e -> {
            String searchTerm = searchField.getText();
            List<Book> searchResults = searchBooksByYear(searchTerm);
            displaySearchResults(searchResults);
        });

        frame.setVisible(true);
    }

    private static void loadBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader("./BooksDatasetClean.csv"))) {
            String line;
            br.readLine(); // Salta la riga dell'intestazione
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Divide la stringa ignorando le virgole tra virgolette
                if (parts.length >= 8) {
                    String title = parts[0].replaceAll("^\"|\"$", ""); // Rimuove eventuali virgolette dagli estremi della stringa
                    String authors = parts[1].replaceAll("^\"|\"$", ""); // Rimuove eventuali virgolette dagli estremi della stringa
                    String description = parts[2].replaceAll("^\"|\"$", ""); // Rimuove eventuali virgolette dagli estremi della stringa
                    String category = parts[3].replaceAll("^\"|\"$", ""); // Rimuove eventuali virgolette dagli estremi della stringa
                    String publisher = parts[4].replaceAll("^\"|\"$", ""); // Rimuove eventuali virgolette dagli estremi della stringa
                    String price = parts[5].replaceAll("^\"|\"$", ""); // Rimuove eventuali virgolette dagli estremi della stringa
                    String publishMonth = parts[6].replaceAll("^\"|\"$", ""); // Rimuove eventuali virgolette dagli estremi della stringa
                    String publishYear = parts[7].replaceAll("^\"|\"$", ""); // Rimuove eventuali virgolette dagli estremi della stringa

                    Book book = new Book(title, authors.split("\\|"), description, category, publisher, price, publishMonth, publishYear);
                    allBooks.add(book);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Errore durante il caricamento dei libri.");
            e.printStackTrace();
        }
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

    private static List<Book> searchBooksByTitle(String searchTerm) {
        List<Book> results = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    private static List<Book> searchBooksByAuthor(String searchTerm) {
        List<Book> results = new ArrayList<>();
        for (Book book : allBooks) {
            for (String author : book.getAuthors()) {
                if (author.toLowerCase().contains(searchTerm.toLowerCase())) {
                    results.add(book);
                    break; // Se trova almeno un'autore corrispondente, aggiunge il libro e passa al libro successivo
                }
            }
        }
        return results;
    }

    private static List<Book> searchBooksByYear(String searchTerm) {
        List<Book> results = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getPublishYear().toLowerCase().contains(searchTerm.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    private static void displaySearchResults(List<Book> searchResults) {
        JFrame searchResultsFrame = new JFrame("Risultati della ricerca");
        searchResultsFrame.setSize(800, 600); // Imposta le dimensioni desiderate
        searchResultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Titolo", "Autori", "Descrizione", "Categoria", "Editore", "Prezzo", "Data di pubblicazione"};
        Object[][] rowData = new Object[searchResults.size()][7];

        int row = 0;
        for (Book book : searchResults) {
            rowData[row][0] = book.getTitle();
            rowData[row][1] = formatAuthors(book.getAuthors());
            rowData[row][2] = book.getDescription();
            rowData[row][3] = book.getCategory();
            rowData[row][4] = book.getPublisher();
            rowData[row][5] = book.getPrice(); // Formatta il prezzo con due decimali
            rowData[row][6] = book.getPublishMonth() + " " + book.getPublishYear();
            row++;
        }

        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true); // Riempie l'altezza della finestra con la tabella
        JScrollPane scrollPane = new JScrollPane(table);

        // Aggiungi il pannello dei risultati alla finestra dei risultati
        searchResultsFrame.getContentPane().add(scrollPane);
        searchResultsFrame.setLocationRelativeTo(null);
        searchResultsFrame.setVisible(true);
    }

    private static String formatAuthors(String[] authors) {
        StringBuilder formattedAuthors = new StringBuilder();
        for (String author : authors) {
            String[] nameParts = author.split(",");
            if (nameParts.length >= 2) {
                String lastName = nameParts[0].trim();
                String firstName = nameParts[1].trim();
                formattedAuthors.append(firstName).append(" ").append(lastName).append(", ");
            } else if (nameParts.length == 1) {
                formattedAuthors.append(nameParts[0].trim()).append(", ");
            }
        }
        if (formattedAuthors.length() > 0) {
            formattedAuthors.deleteCharAt(formattedAuthors.length() - 2); // Rimuove l'ultima virgola
        }
        return formattedAuthors.toString();
    }
}
