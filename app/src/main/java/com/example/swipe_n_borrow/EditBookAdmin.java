package com.example.swipe_n_borrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Objects;

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

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String title, author, language, numPages, genre;
                title = String.valueOf(titleEditText.getText());
                author = String.valueOf(authorEditText.getText());
                language = String.valueOf(languageEditText.getText());
                numPages = String.valueOf(numPagesEditText.getText());
                genre = String.valueOf(genreEditText.getText());
                String adminId= mAuth.getCurrentUser().getUid();

                // Update the book information in Firestore
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
                                    String documentId = document.getId(); // Retrieve the document ID here
                                    Map<String, Object> updates = new HashMap<>();

                                    if (!title.isEmpty()) {
                                        updates.put("title", title);
                                    }

                                    if (!author.isEmpty()) {
                                        updates.put("authors", author);
                                    }

                                    if (!language.isEmpty()) {
                                        updates.put("language", language);
                                    }

                                    if (!numPages.isEmpty()) {
                                        updates.put("num_pages", numPages);
                                    }

                                    if (!genre.isEmpty()) {
                                        updates.put("genre", genre);
                                    }

                                    if (!updates.isEmpty()) {
                                        // Perform the update operation
                                        adminBooksCollection.document(documentId)
                                                .update(updates)
                                                .addOnSuccessListener(aVoid -> {
                                                    Log.d("EditBookAdmin", "Book updated successfully");
                                                    Toast.makeText(EditBookAdmin.this, "Book updated successfully.", Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
                                                })
                                                .addOnFailureListener(e -> {
                                                    Log.e("EditBookAdmin", "Failed to update book", e);
                                                    Toast.makeText(EditBookAdmin.this, "Failed to update book.", Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
                                                });
                                    } else {
                                        // No updates were provided
                                        Toast.makeText(EditBookAdmin.this, "No changes were made.", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            } else {
                                // Handle the case where no document is found
                                Log.d("EditBookAdmin", "No document found for the selected book");
                                Toast.makeText(EditBookAdmin.this, "No document found for the selected book.", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });


    }
}
