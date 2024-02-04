package com.example.swipe_n_borrow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminAddNewBook extends Fragment {

    private EditText titleEditText;
    private EditText authorEditText;
    private EditText languageEditText;
    private EditText numPagesEditText;
    private EditText genreEditText;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Button buttonAddBook;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_add_new_book, container, false);

        mAuth = FirebaseAuth.getInstance();

        titleEditText = view.findViewById(R.id.bookTitle);
        authorEditText = view.findViewById(R.id.author);
        languageEditText = view.findViewById(R.id.language);
        numPagesEditText = view.findViewById(R.id.numPages);
        genreEditText = view.findViewById(R.id.genre);

        buttonAddBook = view.findViewById(R.id.BTN_Add_Book);

        String adminId = mAuth.getCurrentUser().getUid();
        CollectionReference adminCollection = db.collection("Admins");
        CollectionReference adminBooksCollection = adminCollection.document(adminId).collection("books");

        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString().trim();
                String author = authorEditText.getText().toString().trim();
                String language = languageEditText.getText().toString().trim();
                String genre = genreEditText.getText().toString().trim();

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(language) || TextUtils.isEmpty(genre)) {
                    Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int numPages;
                try {
                    numPages = Integer.parseInt(numPagesEditText.getText().toString().trim());
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Invalid number of pages", Toast.LENGTH_SHORT).show();
                    return;
                }

                Book book = new Book(title, author, language, numPages, genre);

                // Add the book to the admin's collection of books
                adminBooksCollection.add(book)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(getActivity(), "Book added successfully!", Toast.LENGTH_SHORT).show();
                            // You can add additional actions here, e.g., navigate to another fragment
                            // FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                            // fragmentTransaction.replace(R.id.fragmentContainer, new YourFragment());
                            // fragmentTransaction.addToBackStack(null);
                            // fragmentTransaction.commit();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(getActivity(), "Error adding book: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        // Set up the back button
//        Button backButton = view.findViewById(R.id.backButton);
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // You can add code here to navigate to another fragment or perform other actions
//            }
//        });

        return view;
    }
}
