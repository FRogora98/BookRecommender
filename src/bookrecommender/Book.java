package bookrecommender;

public class Book {
    private String title;
    private String authors;
    private String publishYear;

    public Book(String title, String authors, String publishYear) {
    this.title = title;
    this.authors = authors;
    this.publishYear = publishYear;
}

    // Getter and Setter Methods
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublicationYear(String publishYear) {
        this.publishYear = publishYear;
    }
}
