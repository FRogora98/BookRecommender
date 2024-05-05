package bookrecommender;

import java.util.ArrayList;
import java.util.List;

public class Recommendation {
    private Book currentBook;
    private List<Book> recommendedBooks;

    public Recommendation(Book currentBook) {
        this.currentBook = currentBook;
        this.recommendedBooks = new ArrayList<>();
    }

    public void addRecommendedBook(Book book) {
        recommendedBooks.add(book);
    }

    public Book getCurrentBook() {
        return currentBook;
    }

    public List<Book> getRecommendedBooks() {
        return recommendedBooks;
    }
}
