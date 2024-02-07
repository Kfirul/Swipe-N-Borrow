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
                // ... (your existing code for adding a book)

                // Example: Navigating to another fragment
                // You can uncomment and modify this part based on your requirements
                // FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                // fragmentTransaction.replace(R.id.fragmentContainer, new YourFragment());
                // fragmentTransaction.addToBackStack(null);
                // fragmentTransaction.commit();
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
