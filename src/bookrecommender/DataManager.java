package bookrecommender;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    public static List<Book> loadBooks(String filename) {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 8) {
                    String title = data[0].trim();
                    String[] authors = data[1].trim().split(";");
                    String description = data[2].trim();
                    String category = data[3].trim();
                    String publisher = data[4].trim();
                    String price = data[5].trim(); // Assuming the price starts with $
                    String publishMonth = data[6].trim();
                    String publishYear = data[7].trim();
                    books.add(new Book(title, authors, description, category, publisher, price, publishMonth, publishYear));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static void saveBooks(List<Book> books, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Book book : books) {
                writer.println(String.join(",",
                    book.getTitle(),
                    String.join(";", book.getAuthors()),
                    book.getDescription(),
                    book.getCategory(),
                    book.getPublisher(),
                    "$" + book.getPrice(),
                    String.valueOf(book.getPublishMonth()),
                    String.valueOf(book.getPublishYear())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
