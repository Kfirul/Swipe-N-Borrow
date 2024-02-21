package com.example.swipe_n_borrow;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminBooks#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminBorrowedBooks extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private SearchView searchView;

    private ArrayList<BorrowBook> bookArrayList = new ArrayList<>();
    private ArrayList<BorrowBook> searchList;



    public AdminBorrowedBooks() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment page3.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminBorrowedBooks newInstance(String param1, String param2) {
        AdminBorrowedBooks fragment = new AdminBorrowedBooks();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_books, container, false);
        recyclerView = view.findViewById(R.id.recycleView);
        searchView = view.findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.clearFocus();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // Initialize the adapter with an empty list
        BookAdapterAdminBorrow bookAdapterAdminBorrow = new BookAdapterAdminBorrow(getActivity(), new ArrayList<>());
        recyclerView.setAdapter(bookAdapterAdminBorrow);


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

        // Fetch and set books from Firebase
        setBooksFirebase();

        return view;
    }

    private void performSearch(String query) {
        searchList = new ArrayList<>();
        if (query.length() > 0) {
            for (BorrowBook book : bookArrayList) {
                if (book.getTitleBook().toUpperCase().contains(query.toUpperCase()) ||
                        book.getUserName().toUpperCase().contains(query.toUpperCase())) {
                    BorrowBook newBook = new BorrowBook();
                    newBook.setTitleBook(book.getTitleBook());
                    newBook.setUserName(book.getUserName());
                    newBook.setUserEmail(book.getUserEmail());
                    searchList.add(book);
                }
            }
        } else {
            searchList.addAll(bookArrayList);
        }

        // Update the adapter with the search results
        recyclerView.setAdapter(new BookAdapterAdminBorrow(getActivity(), searchList));
    }

    public void setBooksFirebase() {
        String adminId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CollectionReference adminBooksCollection = FirebaseFirestore.getInstance().collection("Admins").document(adminId).collection("borrowedBooksAdmin");

        adminBooksCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                bookArrayList.clear(); // Clear the list before adding new data

                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Get the book details
                    String title = document.getString("title");

                    // Create a Book object
                    BorrowBook borrowBook = new BorrowBook();
                    borrowBook.setTitleBook(title);
                    fetchUserDetails(borrowBook);

                    // Add the book to the list
                    bookArrayList.add(borrowBook);
                }

                // Update the adapter with the fetched data
                recyclerView.setAdapter(new BookAdapterAdminBorrow(getActivity(), bookArrayList));

            } else {
                // Handle the failure to retrieve data from Firestore
                Exception e = task.getException();
                Log.e("FirestoreError", "Error retrieving data from Firestore: " + e.getMessage(), e);
            }
        });
    }

    private void fetchUserDetails(BorrowBook borrowBook) {
        FirebaseFirestore.getInstance().collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot userDocument : task.getResult()) {
                            String userId = userDocument.getId();

                            // Check if the user's borrowedBooksUser collection contains the book
                            FirebaseFirestore.getInstance().collection("Users")
                                    .document(userId)
                                    .collection("borrowedBooksUser")
                                    .whereEqualTo("title", borrowBook.getTitleBook())
                                    .get()
                                    .addOnCompleteListener(bookTask -> {
                                        if (bookTask.isSuccessful() && !bookTask.getResult().isEmpty()) {
                                            // User has borrowed the book, get user details
                                            String name = userDocument.getString("fullName");
                                            String email = userDocument.getString("email");

                                            // Set the user details in the BorrowBook object
                                            borrowBook.setUserName(name);
                                            borrowBook.setUserEmail(email);

                                            Log.e("Hereeeeeeeeee", "Hereeeeeeeeeeeeeeeeeeeeeeeee" + borrowBook.getUserName());
                                        }
                                    });
                        }
                    } else {
                        Log.e("FirestoreError", "Error fetching users: " + task.getException());
                    }
                });
    }



}