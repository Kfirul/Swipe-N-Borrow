package com.example.swipe_n_borrow;

public class Book {
    private int bookID;
    private String title;
    private String authors;
    private float average_rating;
    private String isbn;
    private String isbn13;
    private String language_code;
    private int num_pages;
    private int ratings_count;
    private int text_reviews_count;
    private String publication_date;
    private String publisher;
    private int number_of_books;

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", average_rating=" + average_rating +
                ", isbn='" + isbn + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", language_code='" + language_code + '\'' +
                ", num_pages=" + num_pages +
                ", ratings_count=" + ratings_count +
                ", text_reviews_count=" + text_reviews_count +
                ", publication_date='" + publication_date + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }



    // Constructors
    public Book() {
        // Default constructor required by Firebase
    }
    public Book(int bookID, String title, String authors, float average_rating, String isbn, String isbn13, String language_code, int num_pages, int ratings_count, int text_reviews_count, String publication_date, int numberOfBooks, String publisher) {
        this.bookID = bookID;
        this.title = title;
        this.authors = authors;
        this.average_rating = average_rating;
        this.isbn = isbn;
        this.isbn13 = isbn13;
        this.language_code = language_code;
        this.num_pages = num_pages;
        this.ratings_count = ratings_count;
        this.text_reviews_count = text_reviews_count;
        this.publication_date = publication_date;
        number_of_books = numberOfBooks;
        this.publisher = publisher;
    }


    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

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

    public float getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(float average_rating) {
        this.average_rating = average_rating;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public int getNum_pages() {
        return num_pages;
    }

    public void setNum_pages(int num_pages) {
        this.num_pages = num_pages;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public int getText_reviews_count() {
        return text_reviews_count;
    }

    public void setText_reviews_count(int text_reviews_count) {
        this.text_reviews_count = text_reviews_count;
    }

    public String getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(String publication_date) {
        this.publication_date = publication_date;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getNumber_of_books() {
        return number_of_books;
    }

    public void setNumber_of_books(int number_of_books) {
        this.number_of_books = number_of_books;
    }
}


