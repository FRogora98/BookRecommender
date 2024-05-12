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
    private static JButton btnRegister = new JButton("Registrati");
    private static JButton btnSearchBooks = new JButton("Cerca Libri");
    private static List<Book> allBooks = new ArrayList<>();
    private static String user;

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
        topPanel.add(btnLibrary);
        topPanel.add(btnSearchBooks);
        btnLogout.setVisible(false);

        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel.setBackground(Color.WHITE);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 5));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(btnLibrary);
        JButton btnExit = new JButton("Esci");
        bottomPanel.add(btnExit);

        btnLibrary.setVisible(false);

        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(lblUsername, BorderLayout.WEST);

        btnLogin.setBackground(Color.WHITE);
        btnLogout.setBackground(Color.WHITE);
        btnRegister.setBackground(Color.WHITE);
        btnLibrary.setBackground(Color.WHITE);
        btnSearchBooks.setBackground(Color.WHITE);
        btnExit.setBackground(Color.WHITE);

        frame.setVisible(true);

        btnLogin.addActionListener(e -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setLoginListener(username -> {
                lblUsername.setText("Benvenuto, " + username);
                user = username;
                lblUsername.setVisible(true);
                isLoggedIn = true;
                updateInterfaceForLoggedInUser();
            });
            loginForm.setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            isLoggedIn = false;
            user = "";
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

        btnSearchBooks.addActionListener(e -> {
            SearchBookForm searchBookForm = new SearchBookForm(allBooks);
            searchBookForm.setVisible(true);
        });

        btnLibrary.addActionListener(e -> {
            MyLibraryForm myLibraryForm = new MyLibraryForm(allBooks, user);
            myLibraryForm.setVisible(true);
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
    }

    private static void exitApplication() {
        frame.dispose();
        System.exit(0);
    }
}

