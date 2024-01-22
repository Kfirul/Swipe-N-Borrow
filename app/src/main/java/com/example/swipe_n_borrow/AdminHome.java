package com.example.swipe_n_borrow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Objects;

/**
 * The {@code AdminHome} class represents the main activity for the admin user.
 * It provides functionality for managing books, issuing and returning books, and logging out.
 */

public class AdminHome extends Activity implements View.OnClickListener {

    private Button searchBook, addBook, removeBook, updateBook, issueBook, returnBook, logOut, collect1, reissueButton;
    private FirebaseAuth firebaseAuth;
//    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        searchBook = findViewById(R.id.searchBook);
        addBook = findViewById(R.id.addBook);
        removeBook = findViewById(R.id.removeBook);
        collect1 = findViewById(R.id.collect1);
        updateBook =  findViewById(R.id.updateBook);
        issueBook = findViewById(R.id.issueBook);
        returnBook =  findViewById(R.id.returnBook);
        logOut =  findViewById(R.id.logOut);
        reissueButton =  findViewById(R.id.reissueBook);

        // Set click listeners for UI elements
        searchBook.setOnClickListener(this);
        addBook.setOnClickListener(this);
        removeBook.setOnClickListener(this);
        updateBook.setOnClickListener(this);
        issueBook.setOnClickListener(this);
        returnBook.setOnClickListener(this);
        logOut.setOnClickListener(this);
        collect1.setOnClickListener(this);
        reissueButton.setOnClickListener(this);
    }
    /**
     * Handles the click event for buttons in the AdminHome activity.
     * @param view The view that was clicked.
     * @throws IllegalArgumentException If the clicked view is not one of the expected buttons.
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view == logOut) {
            handleLogout();
        } else if (view == searchBook) {
            navigateTo(SearchBookSet.class);
        } else if (view == addBook) {
            navigateTo(AdminAddBook.class);
        } else if (view == removeBook) {
            navigateTo(AdminRemoveBook.class);
        } else if (view == issueBook) {
            navigateTo(AdminIssueBook.class);
        } else if (view == returnBook) {
            navigateTo(AdminReturnBook.class);
        } else if (view == updateBook) {
            navigateTo(AdminUpdateBook.class);
        } else if (view == collect1) {
            navigateTo(AdminCollectFine.class);
        } else if (view == reissueButton) {
            navigateTo(AdminReissueBook.class);
        } else {
            // Throw an exception if none of the expected cases is matched
            throw new IllegalArgumentException("Unhandled view ID: " + view);
        }
    }

    /**
     * Navigates to the specified activity class.
     * @param destinationClass The class of the activity to navigate to.
     */
    private void navigateTo(Class<?> destinationClass) {
        startActivity(new Intent(getApplicationContext(), destinationClass));
    }

    /**
     * Handles the logout process for the admin user.
     * This method updates the Firebase Cloud Messaging (FCM) token to null in the Firestorm
     * database for the current user, signs out the user, and navigates to the Login activity.
     * If the update fails, it displays a Toast message indicating the logout failure.
     */

    private void handleLogout() {
//        db.document("User/" + Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()).update("fcmToken", null).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                firebaseAuth.signOut();
//                startActivity(new Intent(getApplicationContext(), Login.class));
//                finish();
//            } else {
//                Toast.makeText(AdminHome.this, "Logout failed. Please try again", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


}
