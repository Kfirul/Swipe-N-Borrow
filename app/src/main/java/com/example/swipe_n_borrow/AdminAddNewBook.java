package com.example.swipe_n_borrow;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminAddNewBook#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminAddNewBook extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AdminAddNewBook() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment page1.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminAddNewBook newInstance(String param1, String param2) {
        AdminAddNewBook fragment = new AdminAddNewBook();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_add_new_book, container, false);

        EditText titleEditText;
        EditText authorEditText;
        EditText languageEditText;
        EditText numPagesEditText;
        EditText genreEditText;
        EditText imageURLEditText;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Button buttonAddBook;
        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();

        titleEditText = view.findViewById(R.id.bookTitle);
        authorEditText = view.findViewById(R.id.author);
        languageEditText = view.findViewById(R.id.language);
        numPagesEditText = view.findViewById(R.id.numPages);
        genreEditText = view.findViewById(R.id.genre);
        imageURLEditText = view.findViewById(R.id.imageURL);

        buttonAddBook = view.findViewById(R.id.BTN_Add_Book);

        String adminId = mAuth.getCurrentUser().getUid();
        CollectionReference adminCollection = db.collection("Admins");
        CollectionReference adminBooksCollection = adminCollection.document(adminId).collection("books");

        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Confirm")
                        .setMessage("Are you sure you want to add this book?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                String title = titleEditText.getText().toString().trim();
                                String author = authorEditText.getText().toString().trim();
                                String language = languageEditText.getText().toString().trim();
                                String genre = genreEditText.getText().toString().trim();
                                String numPages = numPagesEditText.getText().toString().trim();
                                String imageURL = imageURLEditText.getText().toString().trim();

                                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(language) || TextUtils.isEmpty(genre)) {
                                    Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                try {
                                    Book book = new Book(title, author, language, numPages, genre );

                                    String adminId = mAuth.getCurrentUser().getUid();
                                    FirebaseFirestore.getInstance()
                                            .collection("Admins")
                                            .document(adminId)
                                            .get()
                                            .addOnSuccessListener(documentSnapshot -> {
                                                if (documentSnapshot.exists()) {
                                                    String libraryName = documentSnapshot.getString("library");

                                                    if (libraryName != null) {
                                                        book.setBelongs(libraryName);
                                                        Date currentDate = new Date();
                                                        book.setDate(currentDate);
                                                        book.setImageURL(imageURL);
                                                        // Add the book to the admin's collection of books
                                                        adminBooksCollection.add(book)
                                                                .addOnSuccessListener(documentReference -> {
                                                                    Toast.makeText(getActivity(), "Book added successfully!", Toast.LENGTH_SHORT).show();
                                                                    titleEditText.setText("");
                                                                    authorEditText.setText("");
                                                                    languageEditText.setText("");
                                                                    numPagesEditText.setText("");
                                                                    genreEditText.setText("");
                                                                    imageURLEditText.setText("");

                                                                })
                                                                .addOnFailureListener(e ->
                                                                        Toast.makeText(getActivity(), "Error adding book: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                                                    } else {
                                                        // Handle the case where libraryName is null
                                                        Toast.makeText(getActivity(), "Error: Library name is null", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    // Handle the case where the document does not exist
                                                    Toast.makeText(getActivity(), "Error: Admin document does not exist", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(e ->
                                                    Toast.makeText(getActivity(), "Error fetching admin document: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                                } catch (NumberFormatException e) {
                                    Toast.makeText(getActivity(), "Invalid number of pages", Toast.LENGTH_SHORT).show();
                                }

                                Toast.makeText(getActivity(), "Book added successfully!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        return view;
    }
}