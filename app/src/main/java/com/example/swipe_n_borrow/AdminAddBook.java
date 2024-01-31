package com.example.swipe_n_borrow;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminAddBook extends AppCompatActivity {
    FirebaseAuth mAuth;

    private EditText titleEditText;
    private EditText authorEditText;
    private EditText languageEditText;
    private EditText numPagesEditText;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Button buttonAddBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_book);
        mAuth = FirebaseAuth.getInstance();

        titleEditText = findViewById(R.id.bookTitle);
        authorEditText = findViewById(R.id.author);
        languageEditText = findViewById(R.id.language);
        numPagesEditText = findViewById(R.id.numPages);

        buttonAddBook = findViewById(R.id.BTN_Add_Book);

        String adminId = mAuth.getCurrentUser().getUid();
        CollectionReference adminCollection = db.collection("Admins");
        CollectionReference adminBooksCollection = adminCollection.document(adminId).collection("books");

        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString().trim();
                String author = authorEditText.getText().toString().trim();
                String language = languageEditText.getText().toString().trim();

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(language)) {
                    Toast.makeText(AdminAddBook.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int numPages;
                try {
                    numPages = Integer.parseInt(numPagesEditText.getText().toString().trim());
                } catch (NumberFormatException e) {
                    Toast.makeText(AdminAddBook.this, "Invalid number of pages", Toast.LENGTH_SHORT).show();
                    return;
                }

                Book book = new Book(title, author, language, numPages);

                // Add the book to the admin's collection of books
                adminBooksCollection.add(book)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(AdminAddBook.this, "Book added successfully!", Toast.LENGTH_SHORT).show();
                            // You can add additional actions here, e.g., navigate to another activity
                            // Intent intent = new Intent(AdminAddBook.this, NextActivity.class);
                            // startActivity(intent);
                            // finish();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(AdminAddBook.this, "Error adding book: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }
}
