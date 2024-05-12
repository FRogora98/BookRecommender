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
<<<<<<< HEAD
    private static List<Book> allBooks = new ArrayList<>(); // Lista di tutti i libri caricati una sola volta
=======
    private static JButton btnSearchBooks = new JButton("Cerca Libri");
    private static List<Book> allBooks = new ArrayList<>();
    private static String user;
>>>>>>> fix

    public static void main(String[] args) {
        loadBooks();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

<<<<<<< HEAD
        // Impostazione dello sfondo
        ImageIcon backgroundImage = new ImageIcon("path/to/your/image.jpg");
        JLabel background = new JLabel(backgroundImage);
        frame.setContentPane(background);
        background.setLayout(new BorderLayout());

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
=======
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
>>>>>>> fix

        JPanel bottomPanel = new JPanel(new GridLayout(1, 5));
        bottomPanel.setOpaque(false);
        bottomPanel.add(btnLibrary);

        JButton btnExit = new JButton("Esci");
        bottomPanel.add(btnExit);

        btnLibrary.setVisible(false);
        btnLogout.setVisible(false); // Initially the logout button is not visible

<<<<<<< HEAD
        background.add(topPanel, BorderLayout.NORTH);
        background.add(centerPanel, BorderLayout.CENTER);
        background.add(bottomPanel, BorderLayout.SOUTH);
=======
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
>>>>>>> fix

        frame.setVisible(true);

        btnLogin.addActionListener(e -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setLoginListener(username -> {
<<<<<<< HEAD
=======
                lblUsername.setText("Benvenuto, " + username);
                user = username;
                lblUsername.setVisible(true);
>>>>>>> fix
                isLoggedIn = true;
                updateInterfaceForLoggedInUser();
            });
            loginForm.setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            isLoggedIn = false;
<<<<<<< HEAD
=======
            user = "";
            lblUsername.setVisible(false);
>>>>>>> fix
            updateInterfaceForLoggedInUser();
        });

        btnRegister.addActionListener(e -> {
            RegisterForm registerForm = new RegisterForm();
            registerForm.setVisible(true);
        });

        btnExit.addActionListener(e -> {
            exitApplication();
        });

<<<<<<< HEAD
=======
        btnSearchBooks.addActionListener(e -> {
            SearchBookForm searchBookForm = new SearchBookForm(allBooks);
            searchBookForm.setVisible(true);
        });

>>>>>>> fix
        btnLibrary.addActionListener(e -> {
            MyLibraryForm myLibraryForm = new MyLibraryForm(allBooks, user);
            myLibraryForm.setVisible(true);
        });
<<<<<<< HEAD

        btnSearchBooks.addActionListener(e -> {
            SearchBookForm searchBookForm = new SearchBookForm(allBooks);
            searchBookForm.setVisible(true);
        });
=======
>>>>>>> fix
    }

    private static void loadBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/Libri.csv"))) {
            String line;
<<<<<<< HEAD
            br.readLine(); // Skip header line
=======
            br.readLine();
>>>>>>> fix
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
<<<<<<< HEAD
}
=======
}

>>>>>>> fix
