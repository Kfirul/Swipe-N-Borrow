package com.example.swipe_n_borrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class EditBookAdmin extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button buttonEdit;
    FirebaseFirestore db;
    ProgressBar progressBar;
    String selectedBook = "";

    TextInputEditText titleEditText, authorEditText, languageEditText, numPagesEditText, genreEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book_admin);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("bookTitle")) {
            selectedBook = intent.getStringExtra("bookTitle");
        } else {
            // Handle the case where no book title is provided
            // For example, you could finish the activity and display an error message
            Toast.makeText(this, "No book title provided.", Toast.LENGTH_SHORT).show();
            finish();
        }

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        titleEditText = findViewById(R.id.titleEditText);
        authorEditText = findViewById(R.id.authorEditText);
        languageEditText = findViewById(R.id.languageEditText);
        numPagesEditText = findViewById(R.id.numPagesEditText);
        genreEditText = findViewById(R.id.genreEditText);
        buttonEdit = findViewById(R.id.BTN_Edit);
        progressBar = findViewById(R.id.progressBar);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminHome.class);
                startActivity(intent);
                finish();
            }
        });

        // Retrieve the book information and populate EditText fields
        retrieveBookInformation();

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your existing code for updating book information goes here
            }
        });
    }

    private void retrieveBookInformation() {
        String adminId = mAuth.getCurrentUser().getUid();
        CollectionReference adminBooksCollection = FirebaseFirestore.getInstance()
                .collection("Admins")
                .document(adminId)
                .collection("books");

        // Query to find the specific book document in the "books" collection
        adminBooksCollection.whereEqualTo("title", selectedBook)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Retrieve book information from Firestore
                            String title = document.getString("title");
                            String author = document.getString("authors");
                            String language = document.getString("language");
                            String numPages = document.getString("num_pages");
                            String genre = document.getString("genre");

                            // Set retrieved information to EditText fields
                            titleEditText.setText(title);
                            authorEditText.setText(author);
                            languageEditText.setText(language);
                            numPagesEditText.setText(numPages);
                            genreEditText.setText(genre);
                        }
                    } else {
                        // Handle the case where no document is found
                        Toast.makeText(EditBookAdmin.this, "No document found for the selected book.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
