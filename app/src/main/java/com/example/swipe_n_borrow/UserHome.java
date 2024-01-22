package com.example.swipe_n_borrow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

/**
 * The {@code UserHome} class represents the main activity for the regular user.
 * It provides functionality for searching, viewing borrowed books, reissuing books, and logging out.
 * The user can navigate to different sections of the application by clicking on the corresponding buttons.
 * Additionally, the user has the ability to log out of the application.
 */
public class UserHome extends AppCompatActivity implements View.OnClickListener {

    private Button searchBook, seeBook, logOut, buttonReissue;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        // Initialize Firebase Authentication and Firestore instances
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        initializeViews();

        // Set click listeners for UI element
        setClickListeners();
    }

    private void initializeViews() {
        searchBook = findViewById(R.id.searchBook1);
        seeBook = findViewById(R.id.seeBook);
        logOut = findViewById(R.id.logOut1);
        buttonReissue = findViewById(R.id.buttonReissue);
    }

    private void setClickListeners() {
        searchBook.setOnClickListener(this);
        seeBook.setOnClickListener(this);
        logOut.setOnClickListener(this);
        buttonReissue.setOnClickListener(this);
    }
    /**
     * Handles the click event for buttons in the UserHome activity.
     * @param view The view that was clicked.
     * @throws IllegalArgumentException If the clicked view is not one of the expected buttons.
     */
    @Override
    public void onClick(View view) {
        if (view == logOut){
             handleLogout();
        }
        else if (view == searchBook) {
            navigateTo(SearchBookSet.class);
        } else if (view == seeBook) {
            navigateTo(UserSeeMyBooks.class);
        } else if (view == buttonReissue) {
            navigateTo(UserReissueBook.class);
        } else {
            // Throw an exception if none of the expected cases is matched
            throw new IllegalArgumentException("Unhandled view ID: " + view);
        }
    }

    /**
     * Handles the logout process for the regular user.
     * This method updates the Firebase Cloud Messaging (FCM) token to null in the Firestorm
     * database for the current user, signs out the user, and navigates to the Login activity.
     * If the update fails, it displays a Toast message indicating the logout failure.
     */

    private void handleLogout() {
        db.document("User/" + Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()).update("fcmToken", null)
                .addOnCompleteListener(task ->  {
                        if (task.isSuccessful()) {
                            firebaseAuth.signOut();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        } else {
                            Toast.makeText(UserHome.this, "Logout failed. Please try again", Toast.LENGTH_SHORT).show();
                        }
                });
    }
    /**
     * Navigates to the specified activity class.
     * @param destinationClass The class of the activity to navigate to.
     */
    private void navigateTo(Class<?> destinationClass) {
        startActivity(new Intent(getApplicationContext(), destinationClass));
    }
}
