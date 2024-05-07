package bookrecommender;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyLibraryForm extends JFrame {
    private JList<String> libraryList;
    private DefaultListModel<String> listModel;

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

        add(panel);
    }

    public void setLibrary(List<String> library) {
        listModel.clear();
        for (String book : library) {
            listModel.addElement(book);
        }
    }
}
