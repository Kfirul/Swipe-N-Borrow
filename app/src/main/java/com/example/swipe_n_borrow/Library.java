package com.example.swipe_n_borrow;

public class Library {

    private String library;
    private String address;

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Library(String library, String address) {
        this.library = library;
        this.address = address;
    }
}
