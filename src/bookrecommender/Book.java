package bookrecommender;

public class Book {
    private String title;
    private String[] authors;
    private int publishYear;
    private int publishMonth;
    private String publisher; // Opzionale
    private String description; 
    private String category; // Opzionale
    private double price;

    public Book(String title, String[] authors, String description, String category, String publisher, double price, int publishMonth, int publishYear) {
    this.title = title;
    this.authors = authors;
    this.description = description;
    this.category = category;
    this.publisher = publisher;
    this.price = price;
    this.publishMonth = publishMonth;
    this.publishYear = publishYear;
}

    // Getter and Setter Methods
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublicationYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public int getPublishMonth() {
        return publishMonth;
    }

    public void setPublicationMonth(int publishMonth) {
        this.publishMonth = publishMonth;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
