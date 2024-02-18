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
public class UserBooks extends Fragment implements BookAdapterUser.OnSelectButtonClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<Book> bookArrayList = new ArrayList<>();
    private ArrayList<Book> searchList;


    public UserBooks() {
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
    public static AdminBooks newInstance(String param1, String param2) {
        AdminBooks fragment = new AdminBooks();
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

    public void onSelectButtonClick(Book book) {
//        // Handle the button click for the selected library
//        // Example: Open a new activity or perform any other action
//        Intent intent = new Intent(getActivity(), EditBookAdmin.class);
//        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_books, container, false);

        recyclerView = view.findViewById(R.id.recycleView);
        searchView = view.findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.clearFocus();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        BookAdapterUser bookAdapterUser = new BookAdapterUser(getActivity(), new ArrayList<>(),this);
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

        setBooksFirebase();

        return view;
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

        recyclerView.setAdapter(new BookAdapterUser(getActivity(), searchList, this));
    }

    public void setBooksFirebase() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CollectionReference userBooksCollection = FirebaseFirestore.getInstance().collection("Users").document(userId).collection("borrowedBooksUser");

        userBooksCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Get the book title
                    String title = document.getString("title");
                    String genre = document.getString("genre");
                    String language = document.getString("language");
                    String numberOfPages = document.getString("num_pages");;
                    String author = document.getString("authors");
                    String belongs = document.getString("belongs");

                    // Create a Book object using the retrieved data

                    Book book = new Book();
                    book.setTitle(title);
                    book.setGenre(genre);
                    book.setLanguage(language);
                    book.setNum_pages(numberOfPages);
                    book.setAuthors(author);
                    book.setBelongs(belongs);

                    // Add the book to the list
                    bookArrayList.add(book);
                }

                // Now you can use the bookArrayList with the created Book objects
                // Display the books or perform other actions as needed
                recyclerView.setAdapter(new BookAdapterUser(getActivity(), bookArrayList, this));

            } else {
                // Handle the failure to retrieve data from Firestore
                Exception e = task.getException();
                Log.e("FirestoreError", "Error retrieving data from Firestore: " + e.getMessage(), e);
            }
        });
    }


}
