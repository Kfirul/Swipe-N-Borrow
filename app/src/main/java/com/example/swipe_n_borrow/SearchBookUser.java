package com.example.swipe_n_borrow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    String adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book_user);

        // Retrieve the selected library name from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedLibrary")) {
            selectedLibrary = intent.getStringExtra("selectedLibrary");

            // Update the TextView in the XML with the selected library name
            TextView libraryNameTextView = findViewById(R.id.txt);
            libraryNameTextView.setText(selectedLibrary);
        }

        recyclerView = findViewById(R.id.recycleView);
        searchView = findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.clearFocus();

        setBooksFirebase();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        BookAdapterUserBorrow bookAdapterUser = new BookAdapterUserBorrow(this, bookArrayList, this);
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
    }

    private void performSearch(String query) {
        searchList = new ArrayList<>();
        if (query.length() > 0) {
            for (int i = 0; i < bookArrayList.size(); i++) {
                if (bookArrayList.get(i).getTitle().toUpperCase().contains(query.toUpperCase())
                        || bookArrayList.get(i).getGenre().toUpperCase().contains(query.toUpperCase())) {
                    Book book = new Book();
                    book.setTitle(bookArrayList.get(i).getTitle());
                    book.setGenre(bookArrayList.get(i).getGenre());
                    searchList.add(book);
                }
            }
        } else {
            searchList.addAll(bookArrayList);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        BookAdapterUserBorrow bookAdapterUser = new BookAdapterUserBorrow(this, searchList, this);
        recyclerView.setAdapter(bookAdapterUser);
    }

    public void setBooksFirebase() {
        CollectionReference adminsCollection = FirebaseFirestore.getInstance().collection("Admins");

        adminsCollection.whereEqualTo("library", selectedLibrary)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Get the adminID when the library matches
                            adminId = document.getId();
                            Log.d("Firestore", "Admin ID: " + adminId);

                            // Fetch books for the specific admin
                            fetchBooksForAdmin(adminId);
                        }
                    } else {
                        // Handle the failure to retrieve data from Firestore
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
                    // Get the book title
                    String title = document.getString("title");
                    String genre = document.getString("genre");
                    String language = document.getString("language");
                    String author = document.getString("authors");

                    // Create a Book object using the retrieved data
                    Book book = new Book();
                    book.setTitle(title);
                    book.setGenre(genre);
                    book.setLanguage(language);
                    book.setAuthors(author);


                    // Add the book to the list
                    bookArrayList.add(book);
                }

                // Now you can use the bookArrayList with the created Book objects
                // Display the books or perform other actions as needed
            } else {
                // Handle the failure to retrieve data from Firestore
                Exception e = task.getException();
                Log.e("FirestoreError", "Error retrieving data from Firestore: " + e.getMessage(), e);
            }
        });
    }

    @Override
    public void onSelectButtonClick(Book book) {
        // Get the current user ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Create a reference to the "borrowedBooksUser" collection for the current user
        CollectionReference borrowedBooksUserCollection = FirebaseFirestore.getInstance()
                .collection("Users")
                .document(userId)
                .collection("borrowedBooksUser");

        // Add the selected book to the "borrowedBooksUser" collection for the current user
        borrowedBooksUserCollection.add(book)
                .addOnSuccessListener(documentReference -> {
                    // Handle success, for example, show a success message
                    Toast.makeText(SearchBookUser.this, "Book borrowed successfully.", Toast.LENGTH_SHORT).show();

                    // Optionally, you can add logic here to update the UI or perform other actions
                })
                .addOnFailureListener(e -> {
                    // Handle failure, for example, show an error message
                    Toast.makeText(SearchBookUser.this, "Failed to borrow book.", Toast.LENGTH_SHORT).show();
                    Log.e("FirestoreError", "Error borrowing book: " + e.getMessage(), e);
                });


        // Create a reference to the "borrowedBooksAdmin" collection for the admin
        CollectionReference borrowedBooksAdminCollection = FirebaseFirestore.getInstance()
                .collection("Admins")
                .document(adminId)
                .collection("borrowedBooksAdmin");

        // Add the selected book to the "borrowedBooksAdmin" collection for the admin
        borrowedBooksAdminCollection.add(book)
                .addOnSuccessListener(documentReference -> {
                    // Handle success, for example, log a message
                    Log.d("Firestore", "Book added to borrowedBooksAdmin collection for admin: " + adminId);

                    // Delete the book from the "books" collection in the admin
                    deleteBookFromAdminCollection(adminId, book);
                })
                .addOnFailureListener(e -> {
                    // Handle failure, for example, log an error message
                    Log.e("FirestoreError", "Error adding book to borrowedBooksAdmin collection: " + e.getMessage(), e);
                });
    }

    private void deleteBookFromAdminCollection(String adminId, Book book) {
        // Create a reference to the "books" collection for the admin
        CollectionReference adminBooksCollection = FirebaseFirestore.getInstance()
                .collection("Admins")
                .document(adminId)
                .collection("books");

        // Query to find the specific book document in the "books" collection
        adminBooksCollection.whereEqualTo("title", book.getTitle())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Delete the book document from the "books" collection
                            document.getReference().delete()
                                    .addOnSuccessListener(aVoid -> {
                                        // Handle success, for example, log a message
                                        Log.d("Firestore", "Book deleted from admin's books collection.");

                                        // Optionally, you can add more logic here
                                    })
                                    .addOnFailureListener(e -> {
                                        // Handle failure, for example, log an error message
                                        Log.e("FirestoreError", "Error deleting book from admin's books collection: " + e.getMessage(), e);
                                    });
                        }
                    } else {
                        // Handle the failure to retrieve data from Firestore
                        Exception e = task.getException();
                        Log.e("FirestoreError", "Error getting documents: " + e.getMessage(), e);
                    }
                });
    }

}
