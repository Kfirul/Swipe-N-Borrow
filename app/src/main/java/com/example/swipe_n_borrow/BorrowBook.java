package com.example.swipe_n_borrow;

public class BorrowBook {

    private String titleBook = "";
    private String userName = "";
    private String userEmail = "";

    public String getTitleBook() {
        return titleBook;
    }

    public void setTitleBook(String titleBook) {
        this.titleBook = titleBook;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public BorrowBook(String titleBook, String userName, String userEmail) {
        this.titleBook = titleBook;
        this.userName = userName;
        this.userEmail = userEmail;
    }
    public BorrowBook(){

    }
}
