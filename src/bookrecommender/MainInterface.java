package bookrecommender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainInterface {
    private static JLabel lblUsername;
    private static LoginForm loginForm;
    private static JPanel panel;
    private static JButton btnLibrary;
    private static JButton btnLogout;

    public static void main(String[] args) {
        // Crea il frame principale
        JFrame frame = new JFrame("Book Recommender System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Dimensioni più grandi
        frame.setLocationRelativeTo(null); // Centra la finestra

        // Pannello con layout manager di tipo BorderLayout
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Pannello per il menu utente
        JPanel userMenuPanel = new JPanel();
        userMenuPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Etichetta per il nome utente
        lblUsername = new JLabel("Benvenuto, Utente");
        lblUsername.setVisible(false); // Inizialmente non visibile
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14)); // Imposta il font più piccolo
        userMenuPanel.add(lblUsername);

        // Bottone per la libreria
        btnLibrary = new JButton("Libreria");
        btnLibrary.setVisible(false); // Inizialmente non visibile
        btnLibrary.setPreferredSize(new Dimension(100, 30)); // Imposta le dimensioni più piccole
        btnLibrary.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Apri il form della libreria
                JOptionPane.showMessageDialog(frame, "Implementa la visualizzazione della libreria");
            }
        });
        userMenuPanel.add(btnLibrary);

        // Bottone per il logout
        btnLogout = new JButton("Logout");
        btnLogout.setVisible(false); // Inizialmente non visibile
        btnLogout.setPreferredSize(new Dimension(100, 30)); // Imposta le dimensioni più piccole
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Esegui il logout
                logout();
            }
        });
        userMenuPanel.add(btnLogout);

        // Aggiungi il pannello del menu utente al pannello principale
        panel.add(userMenuPanel, BorderLayout.NORTH);

        // Pannello sinistro per i bottoni vuoti
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(4, 1)); // 4 righe, 1 colonna

        // Aggiungi bottoni vuoti con dimensioni ridotte
        for (int i = 0; i < 4; i++) {
            JButton emptyButton = new JButton();
            emptyButton.setPreferredSize(new Dimension(100, 30)); // Imposta le dimensioni più piccole
            leftPanel.add(emptyButton);
        }

        // Aggiungi il pannello sinistro al pannello principale
        panel.add(leftPanel, BorderLayout.WEST);

        // Barra di ricerca al centro
        JTextField searchField = new JTextField();
        panel.add(searchField, BorderLayout.CENTER);

        // Bottone per la registrazione
        JButton btnRegister = new JButton("Registrazione");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Apri il form di registrazione
                RegisterForm registerForm = new RegisterForm();
                registerForm.setVisible(true);
            }
        });
        btnRegister.setPreferredSize(new Dimension(120, 30)); // Imposta le dimensioni più piccole
        panel.add(btnRegister, BorderLayout.EAST);

        // Aggiungi il pannello principale al frame
        frame.getContentPane().add(panel);

        // Rendi visibile il frame
        frame.setVisible(true);

        // Crea il LoginForm, ma non mostrare subito
        loginForm = new LoginForm();
        loginForm.addLoginListener(new LoginListener() {
            @Override
            public void onLogin(String username) {
                // Mostra il nome utente e il menu utente dopo il login
                lblUsername.setText("Benvenuto, " + username);
                lblUsername.setVisible(true);
                btnLibrary.setVisible(true);
                btnLogout.setVisible(true);
            }
        });
    }

    // Metodo per eseguire il logout
    private static void logout() {
        lblUsername.setText("Benvenuto, Utente");
        lblUsername.setVisible(false);
        btnLibrary.setVisible(false);
        btnLogout.setVisible(false);
        loginForm.setVisible(true); // Riapri il LoginForm
    }
}
