package com.example.swipe_n_borrow;

import com.google.firebase.Timestamp;

public class BorrowBook {

    private String titleBook = "";
    private String userName = "";
    private String userEmail = "";
    private String userAddres = "";
    private String imageURL = "";  // Add this line for the imageURL property

    public String getDateBorrow() {
        return dateBorrow;
    }

    public void setDateBorrow(String dateBorrow) {
        this.dateBorrow = dateBorrow;
    }

    private String dateBorrow;

    public String getTitleBook() {
        return titleBook;
    }

    public void setTitleBook(String titleBook) {
        this.titleBook = titleBook;
    }

    public String getUserAddress() {
        return userAddres;
    }

    public void setUserAddress(String userAddres) {
        this.userAddres = userAddres;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public BorrowBook(String titleBook, String userName, String userEmail) {
        this.titleBook = titleBook;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public BorrowBook() {

    }
}
