package bookrecommender;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private String libraryName;
    private String owner;
    private List<Book> books;

    public Library(String libraryName, String owner) {
        this.libraryName = libraryName;
        this.owner = owner;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
