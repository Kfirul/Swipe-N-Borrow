package com.example.swipe_n_borrow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class SearchBookUser extends AppCompatActivity implements BookAdapterUserBorrow.OnSelectButtonClickListener {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<Book> bookArrayList = new ArrayList<>();
    private ArrayList<Book> searchList;

    String selectedLibrary = "";
    String selectedLibraryAddress = "";
    String adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book_user);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedLibrary") ) {
            selectedLibrary = intent.getStringExtra("selectedLibrary");
//            selectedLibraryAddress = intent.getStringExtra("selectedLibraryAddress");

            TextView libraryNameTextView = findViewById(R.id.txt);
            libraryNameTextView.setText(selectedLibrary);

//            TextView libraryAddressTextView = findViewById(R.id.txt2);
//            libraryAddressTextView.setText(selectedLibraryAddress);
        }

        recyclerView = findViewById(R.id.recycleView);
        searchView = findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.clearFocus();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        BookAdapterUserBorrow bookAdapterUser = new BookAdapterUserBorrow(this, new ArrayList<>(), this);
        recyclerView.setAdapter(bookAdapterUser);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return false;
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserHome.class);
                startActivity(intent);
                finish();
            }
        });
        setBooksFirebase();
    }

    private void performSearch(String query) {
        searchList = new ArrayList<>();
        if (query.length() > 0) {
            for (Book book : bookArrayList) {
                if (book.getTitle().toUpperCase().contains(query.toUpperCase())
                        || book.getGenre().toUpperCase().contains(query.toUpperCase())) {
                    searchList.add(book);
                }
            }
        } else {
            searchList.addAll(bookArrayList);
        }

        recyclerView.setAdapter(new BookAdapterUserBorrow(this, searchList, this));
    }

    public void setBooksFirebase() {
        CollectionReference adminsCollection = FirebaseFirestore.getInstance().collection("Admins");

        adminsCollection.whereEqualTo("library", selectedLibrary)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            adminId = document.getId();
                            Log.d("Firestore", "Admin ID: " + adminId);
                            fetchBooksForAdmin(adminId);
                        }
                    } else {
                        Exception e = task.getException();
                        Log.e("FirestoreError", "Error retrieving data from Firestore: " + e.getMessage(), e);
                    }
                });
    }

    private void fetchBooksForAdmin(String adminId) {
        CollectionReference adminBooksCollection = FirebaseFirestore.getInstance().collection("Admins").document(adminId).collection("books");

        adminBooksCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String title = document.getString("title");
                    String genre = document.getString("genre");
                    String language = document.getString("language");
                    String author = document.getString("authors");
                    String numberOfPages = document.getString("num_pages");
                    String belongs = document.getString("belongs");

                    Book book = new Book();
                    book.setTitle(title);
                    book.setGenre(genre);
                    book.setLanguage(language);
                    book.setAuthors(author);
                    book.setNum_pages(numberOfPages);
                    book.setBelongs(belongs);

                    bookArrayList.add(book);
                }

                recyclerView.setAdapter(new BookAdapterUserBorrow(this, bookArrayList, this));
            } else {
                Exception e = task.getException();
                Log.e("FirestoreError", "Error retrieving data from Firestore: " + e.getMessage(), e);
            }
        });
    }

    @Override
    public void onSelectButtonClick(Book book) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CollectionReference borrowedBooksUserCollection = FirebaseFirestore.getInstance()
                .collection("Users")
                .document(userId)
                .collection("borrowedBooksUser");

        borrowedBooksUserCollection.add(book)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(SearchBookUser.this, "Book borrowed successfully.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SearchBookUser.this, "Failed to borrow book.", Toast.LENGTH_SHORT).show();
                    Log.e("FirestoreError", "Error borrowing book: " + e.getMessage(), e);
                });

        CollectionReference borrowedBooksAdminCollection = FirebaseFirestore.getInstance()
                .collection("Admins")
                .document(adminId)
                .collection("borrowedBooksAdmin");

        borrowedBooksAdminCollection.add(book)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firestore", "Book added to borrowedBooksAdmin collection for admin: " + adminId);
                    deleteBookFromAdminCollection(adminId, book);
                    setBooksFirebase();
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreError", "Error adding book to borrowedBooksAdmin collection: " + e.getMessage(), e);
                });
    }

    private void deleteBookFromAdminCollection(String adminId, Book book) {
        CollectionReference adminBooksCollection = FirebaseFirestore.getInstance()
                .collection("Admins")
                .document(adminId)
                .collection("books");

        adminBooksCollection.whereEqualTo("title", book.getTitle())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            document.getReference().delete()
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("Firestore", "Book deleted from admin's books collection.");
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("FirestoreError", "Error deleting book from admin's books collection: " + e.getMessage(), e);
                                    });
                        }
                    } else {
                        Exception e = task.getException();
                        Log.e("FirestoreError", "Error getting documents: " + e.getMessage(), e);
                    }
                });
    }
}