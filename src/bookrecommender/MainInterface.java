package bookrecommender;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        topPanel.setBackground(Color.WHITE);
        topPanel.add(btnLogin);
        topPanel.add(btnLogout);
        topPanel.add(btnRegister);
        btnLogout.setVisible(false); // Initially the logout button is not visible

        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel.setBackground(Color.WHITE);
        JButton searchByTitleButton = new JButton("Cerca per Titolo");
        JButton searchByAuthorButton = new JButton("Cerca per Autore");
        JButton searchByYearButton = new JButton("Cerca per Anno di Uscita");
        centerPanel.add(searchField);
        centerPanel.add(searchByTitleButton);
        centerPanel.add(searchByAuthorButton);
        centerPanel.add(searchByYearButton);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 5));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(btnAddBook);
        bottomPanel.add(btnReview);
        JButton btnExit = new JButton("Esci");
        bottomPanel.add(btnExit);

        btnAddBook.setVisible(false);
        btnReview.setVisible(false);

        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(lblUsername, BorderLayout.WEST);

        btnLogin.setBackground(Color.WHITE);
        btnLogout.setBackground(Color.WHITE);
        btnRegister.setBackground(Color.WHITE);
        searchByTitleButton.setBackground(Color.LIGHT_GRAY);
        searchByAuthorButton.setBackground(Color.LIGHT_GRAY);
        searchByYearButton.setBackground(Color.LIGHT_GRAY);
        btnExit.setBackground(Color.WHITE);

        frame.setVisible(true);

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
        try (BufferedReader br = new BufferedReader(new FileReader("./data/Libri.csv"))) {
            String line;
            br.readLine(); // Salta la riga dell'intestazione
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); 
                if (parts.length >= 3) {
                    String title = parts[0].replaceAll("^\"|\"$", ""); 
                    String authors = parts[1].replaceAll("^\"|\"$", ""); 
                    String publishYear = parts[2].replaceAll("^\"|\"$", "");

                    Book book = new Book(title, authors, publishYear);
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
            if (book.getAuthors().toLowerCase().contains(searchTerm.toLowerCase())) {
                results.add(book);
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
    
        String[] columnNames = {"Titolo", "Autori", "Data di pubblicazione"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames); // Crea un nuovo modello vuoto
    
        for (Book book : searchResults) {
            Object[] rowData = new Object[7];
            rowData[0] = book.getTitle();
            rowData[1] = book.getAuthors();
            rowData[2] = book.getPublishYear();
            model.addRow(rowData); // Aggiungi riga al modello
        }
    
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true); // Riempie l'altezza della finestra con la tabella
        JScrollPane scrollPane = new JScrollPane(table);
    
        // Aggiungi un listener di mouse per la tabella dei risultati della ricerca
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    Book selectedBook = searchResults.get(row);
                    openBookForm(selectedBook);
                }
            }
        });

        // Aggiungi il pannello dei risultati alla finestra dei risultati
        searchResultsFrame.getContentPane().add(scrollPane);
        searchResultsFrame.setLocationRelativeTo(null);
        searchResultsFrame.setVisible(true);
    }

    private static void openBookForm(Book book) {
        BookForm bookForm = new BookForm(book);
    }
}
