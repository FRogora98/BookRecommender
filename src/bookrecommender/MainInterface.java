package bookrecommender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainInterface {
    private static JLabel lblUsername;

    public static void main(String[] args) {
        // Crea il frame principale
        JFrame frame = new JFrame("Book Recommender System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Dimensioni più grandi
        frame.setLocationRelativeTo(null); // Centra la finestra

        // Pannello con layout manager di tipo BorderLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Pannello per i pulsanti in alto
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Pulsanti allineati a destra

        // Aggiungi i pulsanti
        JButton btnRegister = new JButton("Registra nuovo utente");
        JButton btnLogin = new JButton("Login");

        // Aggiungi i pulsanti al pannello in alto
        topPanel.add(btnLogin);
        topPanel.add(btnRegister);

        // Aggiungi il pannello in alto al pannello principale
        panel.add(topPanel, BorderLayout.NORTH);

        // Pannello per la barra di ricerca al centro
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());

        // Aggiungi la barra di ricerca
        JTextField searchField = new JTextField(20); // Barra di ricerca con larghezza fissa
        centerPanel.add(searchField);

        // Aggiungi il pannello al centro al pannello principale
        panel.add(centerPanel, BorderLayout.CENTER);

        // Etichetta per il nome utente
        lblUsername = new JLabel("Benvenuto, Utente");
        lblUsername.setVisible(false); // Inizialmente non visibile
        panel.add(lblUsername, BorderLayout.WEST); // Aggiungi a sinistra

        // Definisci le azioni per i pulsanti
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Apri il LoginForm
                LoginForm loginForm = new LoginForm();
                loginForm.addLoginListener(new LoginListener() {
                    @Override
                    public void onLogin(String username) {
                        lblUsername.setText("Benvenuto, " + username);
                        lblUsername.setVisible(true); // Mostra il nome utente
                    }
                });
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Apri il LoginForm per la registrazione
                new LoginForm();
            }
        });

        // Aggiungi il pannello principale al frame
        frame.getContentPane().add(panel);

        // Rendi visibile il frame
        frame.setVisible(true);
    }
}
