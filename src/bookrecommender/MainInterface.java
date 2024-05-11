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
    private static boolean isLoggedIn = false;
    private static JButton btnLogin = new JButton("Login");
    private static JButton btnLogout = new JButton("Logout");
    private static JButton btnLibrary = new JButton("MyLibrary");
    private static JButton btnSearchBooks = new JButton("Cerca Libri");
    private static JButton btnRegister = new JButton("Registrati");
    private static List<Book> allBooks = new ArrayList<>(); // Lista di tutti i libri caricati una sola volta

    public static void main(String[] args) {
        loadBooks();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        // Impostazione dello sfondo
        // ImageIcon backgroundImage = new ImageIcon("./library.jpg");
        // JLabel background = new JLabel(backgroundImage);
        // frame.setContentPane(background);
        // background.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        topPanel.add(btnLogin, gbc);
        topPanel.add(btnRegister, gbc);
        topPanel.add(btnSearchBooks, gbc);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 5));
        bottomPanel.setOpaque(false);
        bottomPanel.add(btnLibrary);

        JButton btnExit = new JButton("Esci");
        bottomPanel.add(btnExit);

        btnLibrary.setVisible(false);
        btnLogout.setVisible(false); // Initially the logout button is not visible

        background.add(topPanel, BorderLayout.NORTH);
        background.add(centerPanel, BorderLayout.CENTER);
        background.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        btnLogin.addActionListener(e -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setLoginListener(username -> {
                isLoggedIn = true;
                updateInterfaceForLoggedInUser();
            });
            loginForm.setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            isLoggedIn = false;
            updateInterfaceForLoggedInUser();
        });

        btnRegister.addActionListener(e -> {
            RegisterForm registerForm = new RegisterForm();
            registerForm.setVisible(true);
        });

        btnExit.addActionListener(e -> {
            exitApplication();
        });

        btnLibrary.addActionListener(e -> {
            MyLibraryForm myLibraryForm = new MyLibraryForm();
            myLibraryForm.setVisible(true);
        });

        btnSearchBooks.addActionListener(e -> {
            SearchBookForm searchBookForm = new SearchBookForm(allBooks);
            searchBookForm.setVisible(true);
        });
    }

    private static void loadBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/Libri.csv"))) {
            String line;
            br.readLine(); // Skip header line
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