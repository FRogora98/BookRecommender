package bookrecommender;

import javax.swing.*;
import java.awt.*;

public class MainInterface {
    private static JFrame frame = new JFrame("Book Recommender System");
    private static JLabel lblUsername = new JLabel("Benvenuto, Utente", JLabel.CENTER);
    private static boolean isLoggedIn = false;
    private static JButton btnLogin = new JButton("Login");
    private static JButton btnLogout = new JButton("Logout");
    private static JButton btnAddBook = new JButton("Aggiungi libro");
    private static JButton btnReview = new JButton("Scrivi recensione");
    private static JButton btnRegister = new JButton("Registrati");
    private static JButton btnSearchBooks = new JButton("Cerca Libri");

    public static void main(String[] args) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(btnLogin);
        topPanel.add(btnLogout);
        topPanel.add(btnRegister);
        topPanel.add(btnSearchBooks);
        btnLogout.setVisible(false); // Initially the logout button is not visible

        JPanel centerPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(20);
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

        btnSearchBooks.addActionListener(e -> {
            SearchBookForm searchBookForm = new SearchBookForm();
            searchBookForm.setVisible(true);
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
}
