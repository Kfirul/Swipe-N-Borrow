package com.example.swipe_n_borrow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class SearchBookUser extends AppCompatActivity implements BookAdapterUser.OnSelectButtonClickListener {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<Book> bookArrayList = new ArrayList<>();
    private ArrayList<Book> searchList;

    String selectedLibrary = "";

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

        BookAdapterUser bookAdapterUser = new BookAdapterUser(this, bookArrayList, this);
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

        BookAdapterUser bookAdapterUser = new BookAdapterUser(this, searchList, this);
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
                            String adminId = document.getId();
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
        // Handle the selection of a book
    }
}
