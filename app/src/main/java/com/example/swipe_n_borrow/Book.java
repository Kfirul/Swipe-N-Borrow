package com.example.swipe_n_borrow;

public class Book {
    //    private int bookID;
    private String title;
    private String authors;
    private String language;
    private String num_pages;
    private String genre;
    private String belongs = "";

    @Override
    public String toString() {
        return "Book{" +
//                "bookID=" + bookID +
                ", title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", language=" + language +
                ",num_pages=" + num_pages +
                ",genre=" + genre +
                '}';
    }

    // Constructors
    public Book() {
        // Default constructor required by Firebase
    }

    public Book(String title, String authors, String language, String num_pages, String genre) {
//        this.bookID = bookID;
        this.title = title;
        this.authors = authors;
        this.language = language;
        this.num_pages = num_pages;
        this.genre = genre;
    }


//    public int getBookID() {
//        return bookID;
//    }
//
//    public void setBookID(int bookID) {
//        this.bookID = bookID;
//    }

    public String getBelongs() {
        return belongs;
    }

    public void setBelongs(String belongs) {
        this.belongs = belongs;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNum_pages() {
        return num_pages;
    }

    public void setNum_pages(String num_pages) {
        this.num_pages = num_pages;
    }
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}





