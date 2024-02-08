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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserLibrarySearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserLibrarySearch extends Fragment implements LibraryAdapterUser.OnSelectButtonClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<String> libraryArrayList = new ArrayList<>();
    private ArrayList<String> searchList;

    public UserLibrarySearch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserBooksSearch.
     */
    // TODO: Rename and change types and number of parameters
    public static UserLibrarySearch newInstance(String param1, String param2) {
        UserLibrarySearch fragment = new UserLibrarySearch();
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

    public void onSelectButtonClick(String library) {
        // Handle the button click for the selected library
        // Example: Open a new activity or perform any other action
        Intent intent = new Intent(getActivity(), SearchBookUser.class);
        intent.putExtra("selectedLibrary", library);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_library_search, container, false);

        recyclerView = view.findViewById(R.id.recycleView);
        searchView = view.findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.clearFocus();
// Inflate the item layout to access its components
        View itemLayout = inflater.inflate(R.layout.library_file_user, container, false);

        setBooksFirebase();


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        LibraryAdapterUser bookAdapterUser = new LibraryAdapterUser(getActivity(), libraryArrayList,this);
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


        return view;
    }

    private void performSearch(String query) {
        searchList = new ArrayList<>();
        if (query.length() > 0) {
            for (int i = 0; i < libraryArrayList.size(); i++) {
                if (libraryArrayList.get(i).toUpperCase().contains(query.toUpperCase()))
                       {

                    searchList.add(libraryArrayList.get(i));
                }
            }
        } else {
            searchList.addAll(libraryArrayList);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        LibraryAdapterUser bookAdapterUser = new LibraryAdapterUser(getActivity(), searchList,this);
        recyclerView.setAdapter(bookAdapterUser);
    }

    public void setBooksFirebase() {

        CollectionReference adminsCollection = FirebaseFirestore.getInstance().collection("Admins");

        adminsCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Get the book title
                    String library = document.getString("library");


                    // Add the book to the list
                    libraryArrayList.add(library);
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
}