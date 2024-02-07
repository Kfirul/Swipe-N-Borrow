package com.example.swipe_n_borrow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminHomeFragment extends Fragment {
    private Button logOut;
    private LinearLayout bookListLayout;
    private TextView textViewAddress, textViewFullName, textViewLibrary, textViewPhoneNumber;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("AdminHomeFragment", "onCreateView executed");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        // Find the TextView elements
        textViewAddress = view.findViewById(R.id.profileAddress);
        textViewFullName = view.findViewById(R.id.profilelName);
        textViewLibrary = view.findViewById(R.id.profileLibrary);
//        textViewPhoneNumber = view.findViewById(R.id.phoneNumber);

//        // Fetch and display admin data
//        showAdminData();

        logOut = view.findViewById(R.id.logOut);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
}

//    private void showAdminData() {
//        // Replace "your_collection_path" with the actual path to your Firestore collection
//        String adminId = mAuth.getCurrentUser().getUid();
//        CollectionReference adminCollection = FirebaseFirestore.getInstance().collection("Admins");
//
//
//        // Replace "your_admin_document_id" with the actual ID of the admin document in Firestore
//        adminCollection.document("adminId").get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    if (documentSnapshot.exists()) {
//                        // Retrieve data from the document
//                        String address = documentSnapshot.getString("address");
//                        String fullName = documentSnapshot.getString("fullName");
//                        String libraryName = documentSnapshot.getString("library");
//                        String phoneNumber = documentSnapshot.getString("phoneNumber");
//
//                        // Display data in TextViews
//                        textViewAddress.setText(address);
//                        textViewFullName.setText(fullName);
//                        textViewLibrary.setText(libraryName);
//                        textViewPhoneNumber.setText(phoneNumber);
//                    } else {
//                        Log.d("AdminHomeFragment", "Admin document does not exist");
//                    }
//                })
//                .addOnFailureListener(e -> {
//                    Log.e("AdminHomeFragment", "Error fetching admin data: " + e.getMessage());
//                });
//    }
//}


//
//        // Find the rest of the UI elements (books layout and logout button)
//        bookListLayout = view.findViewById(R.id.bookListLayout);
//        logOut = view.findViewById(R.id.logOut);
//
//        logOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getActivity(), Login.class);
//                startActivity(intent);
//                getActivity().finish();
//            }
//        });
//
//        // Load and display admin details
//        loadAdminDetails();
//
//        // Load and display books
//        loadBooks();
//
//        return view;
//    }
//
//    private void loadAdminDetails() {
//        String adminId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        FirebaseFirestore.getInstance().collection("Admins").document(adminId).get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                if (task.getResult().exists()) {
//                    // Admin document exists, update TextViews with admin details
//                    textViewAddress.setText("Address: " + task.getResult().getString("address"));
//                    textViewEmail.setText("Email: " + task.getResult().getString("email"));
//                    textViewFullName.setText("Full Name: " + task.getResult().getString("fullName"));
//                    textViewId.setText("ID: " + task.getResult().getString("id"));
//                    textViewLibrary.setText("Library: " + task.getResult().getString("library"));
//                    textViewPhoneNumber.setText("Phone Number: " + task.getResult().getString("phoneNumber"));
//                } else {
//                    Log.e("AdminHomeFragment", "Admin document does not exist.");
//                }
//            } else {
//                Log.e("AdminHomeFragment", "Error fetching admin details: " + task.getException());
//            }
//        });
//    }
//
//    // This method is called when a tab is selected in the ViewPager
//    public void onTabSelected(int position) {
//        // Show the logout button only when the "Home" tab is selected
//        if (position == 0) {
//            logOut.setVisibility(View.VISIBLE);
//        } else {
//            logOut.setVisibility(View.GONE);
//        }
//    }
//
//    private void loadBooks() {
//        String adminId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        CollectionReference adminBooksCollection = FirebaseFirestore.getInstance().collection("Admins").document(adminId).collection("books");
//        adminBooksCollection.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    // Get the book title
//                    String bookTitle = document.getString("title");
//
//                    // Create a TextView to display the book title
//                    TextView textView = new TextView(getActivity());
//                    textView.setText(bookTitle);
//
//                    // Add the TextView to the LinearLayout
//                    bookListLayout.addView(textView);
//                    Log.e("AdminHomeFragment", "sssss");
//                }
//            } else {
//                Log.e("AdminHomeFragment", "Error fetching books: " + task.getException());
//
//                // Check if the adminId is correct
//                Log.d("AdminHomeFragment", "Admin ID: " + adminId);
//
//                // Check if the 'books' collection exists for the admin
//                FirebaseFirestore.getInstance().collection("Admins").document(adminId).get().addOnCompleteListener(adminTask -> {
//                    if (adminTask.isSuccessful()) {
//                        if (!adminTask.getResult().exists()) {
//                            Log.e("AdminHomeFragment", "Admin document does not exist.");
//                        } else {
//                            Log.d("AdminHomeFragment", "Admin document exists.");
//                        }
//                    } else {
//                        Log.e("AdminHomeFragment", "Error checking admin document existence: " + adminTask.getException());
//                    }
//                });
//            }
//        });
//    }

//}