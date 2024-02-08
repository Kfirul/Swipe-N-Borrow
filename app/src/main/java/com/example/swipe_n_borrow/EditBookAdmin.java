package com.example.swipe_n_borrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class EditBookAdmin extends AppCompatActivity {

    private static final String TAG = "EditBookAdmin"; // Define TAG here
    FirebaseAuth mAuth;
    Button buttonEdit;
    FirebaseFirestore fstore;
    ProgressBar progressBar;

    TextInputEditText titleEditText, authorEditText, languageEditText, numPagesEditText, genreEditText;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book_admin);
        mAuth = FirebaseAuth.getInstance();
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

        // Inside onClick method
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

                // You can add validation checks here for the book fields

                // Update the book information in Firestore
                Map<String, Object> updates = new HashMap<>();
                updates.put("title", title);
                updates.put("author", author);
                updates.put("language", language);
                updates.put("numPages", numPages);
                updates.put("genre", genre);

                // Retrieve all books
                db.collection("books")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // Update each book document with the provided updates
                                        document.getReference().update(updates)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(EditBookAdmin.this, "Books updated successfully.", Toast.LENGTH_SHORT).show();
                                                        progressBar.setVisibility(View.GONE);
                                                        // You can add more logic here, such as finishing the activity or navigating to another screen.
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(EditBookAdmin.this, "Failed to update books.", Toast.LENGTH_SHORT).show();
                                                        progressBar.setVisibility(View.GONE);
                                                        // Handle the failure, for example, display an error message.
                                                    }
                                                });
                                    }
                                } else {
                                    Log.e(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });
    }
}