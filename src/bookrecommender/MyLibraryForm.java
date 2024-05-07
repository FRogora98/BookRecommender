package bookrecommender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MyLibraryForm extends JFrame {
    private JList<String> libraryList;
    private DefaultListModel<String> listModel;
    private JButton btnCreate;

    public MyLibraryForm() {
        setTitle("My Library");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        listModel = new DefaultListModel<>();
        libraryList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(libraryList);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Aggiungi il pulsante "Crea" in alto a sinistra
        btnCreate = new JButton("Crea");
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewLibrary();
            }
        });
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(btnCreate);
        panel.add(topPanel, BorderLayout.NORTH);

        add(panel);
    }

    public void setLibrary(List<String> library) {
        listModel.clear();
        for (String book : library) {
            listModel.addElement(book);
        }
    }

    private void createNewLibrary() {
        // Implementa la logica per la creazione di una nuova libreria qui
        // Puoi aprire una finestra di dialogo per consentire all'utente di selezionare i libri
        // E quindi salvare i dettagli della nuova libreria nel file CSV
        JOptionPane.showMessageDialog(this, "Funzionalità di creazione di libreria da implementare.");
    }
}
