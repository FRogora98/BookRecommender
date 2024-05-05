package bookrecommender;

public class Review {
    private Book book;
    private double style;
    private double content;
    private double pleasure;
    private double originality;
    private double edition;
    private double finalScore;

    public Review(Book book, double style, double content, double pleasure, double originality, double edition) {
        this.book = book;
        this.style = style;
        this.content = content;
        this.pleasure = pleasure;
        this.originality = originality;
        this.edition = edition;
        this.finalScore = calculateFinalScore();
    }

    private double calculateFinalScore() {
        return (style + content + pleasure + originality + edition) / 5;
    }

    // Getter methods for all attributes
    public Book getBook() {
        return book;
    }

    public double getStyle() {
        return style;
    }

    public double getContent() {
        return content;
    }

    public double getPleasure() {
        return pleasure;
    }

    public double getOriginality() {
        return originality;
    }

    public double getEdition() {
        return edition;
    }

    public double getFinalScore() {
        return finalScore;
    }
}
