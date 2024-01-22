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

public class UserHome extends AppCompatActivity implements View.OnClickListener {

    private Button searchBook, seeBook, logOut, buttonReissue;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        initializeViews();
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

    private void navigateTo(Class<?> destinationClass) {
        startActivity(new Intent(getApplicationContext(), destinationClass));
    }
}
