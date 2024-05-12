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
    private static JButton btnLibrary = new JButton("MyLibrary");
    private static JButton btnReview = new JButton("MyReview");
    private static JButton btnRecommend = new JButton("MyRecommend");
    private static JButton btnRegister = new JButton("Registrati");
    private static JButton btnSearchBooks = new JButton("Cerca Libri");
    private static JButton btnExit = new JButton("Esci"); // Pulsante "Esci"
    private static List<Book> allBooks = new ArrayList<>();
    private static String Username;

    public static void main(String[] args) {
        loadBooks();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(btnLogin);
        topPanel.add(btnLogout);
        topPanel.add(btnRegister);
        topPanel.add(btnSearchBooks);
        btnLogout.setVisible(false);

        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel.setBackground(Color.WHITE);

        JPanel bottomPanel = new JPanel(new BorderLayout()); // Modifica qui
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Aggiunto margine

        JPanel leftPanel = new JPanel(new GridLayout(5, 1, 5, 5)); // Pannello per i bottoni sul lato sinistro
        leftPanel.setBackground(Color.WHITE);

        btnLibrary.setBackground(Color.WHITE);
        btnLibrary.setVisible(false);
        leftPanel.add(btnLibrary);

        btnReview.setBackground(Color.WHITE);
        btnReview.setVisible(false);
        leftPanel.add(btnReview);

        btnRecommend.setBackground(Color.WHITE);
        btnRecommend.setVisible(false);
        leftPanel.add(btnRecommend);

        btnSearchBooks.setBackground(Color.WHITE);
        leftPanel.add(btnSearchBooks);

        bottomPanel.add(leftPanel, BorderLayout.WEST);

        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Pannello per il pulsante "Esci"
        exitPanel.setBackground(Color.WHITE);
        exitPanel.add(btnExit);
        bottomPanel.add(exitPanel, BorderLayout.SOUTH); // Aggiunto il pannello al lato destro

        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(lblUsername, BorderLayout.WEST);

        btnLogin.setBackground(Color.WHITE);
        btnLogout.setBackground(Color.WHITE);
        btnRegister.setBackground(Color.WHITE);
        btnSearchBooks.setBackground(Color.WHITE);
        btnExit.setBackground(Color.WHITE); // Impostare lo sfondo del pulsante "Esci"

        frame.setVisible(true);

        btnLogin.addActionListener(e -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setLoginListener(username -> {
                lblUsername.setText("Benvenuto, " + username);
                Username = username;
                lblUsername.setVisible(true);
                isLoggedIn = true;
                updateInterfaceForLoggedInUser();
            });
            loginForm.setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            isLoggedIn = false;
            Username = "";
            lblUsername.setVisible(false);
            updateInterfaceForLoggedInUser();
        });

        btnRegister.addActionListener(e -> {
            RegisterForm registerForm = new RegisterForm();
            registerForm.setVisible(true);
        });

        btnSearchBooks.addActionListener(e -> {
            SearchBookForm searchBookForm = new SearchBookForm(allBooks, false, false, "");
            searchBookForm.setVisible(true);
        });

        btnLibrary.addActionListener(e -> {
            MyLibraryForm myLibraryForm = new MyLibraryForm(allBooks, Username);
            myLibraryForm.setVisible(true);
        });

        btnReview.addActionListener(e -> {
            SearchBookForm reviewBookForm = new SearchBookForm(allBooks, true, false, Username);
            reviewBookForm.setVisible(true);
        });

        btnRecommend.addActionListener(e -> {
            SearchBookForm recommendBookForm = new SearchBookForm(allBooks, false, true, Username);
            recommendBookForm.setVisible(true);
        });

        btnExit.addActionListener(e -> {
            exitApplication();
        });
    }

    private static void loadBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/Libri.csv"))) {
            String line;
            br.readLine();
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
        btnRegister.setVisible(!isLoggedIn);
        btnLibrary.setVisible(isLoggedIn);
        btnReview.setVisible(isLoggedIn); 
        btnRecommend.setVisible(isLoggedIn); 
    }

    private static void exitApplication() {
        frame.dispose();
        System.exit(0);
    }
}
