package com.example.swipe_n_borrow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminHome extends AppCompatActivity {
    ProgressBar progressBar;
    FirebaseFirestore fstore;

    String adminID;
    Button searchBook;
    Button addBook;
    Button removeBook;
    Button updateBook;
    Button issueBook;
    Button BooksList;
    Button reissueBook;
    Button collectFine;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // Initialize buttons
       searchBook = findViewById(R.id.searchBook);
       addBook = findViewById(R.id.addBook);
       removeBook = findViewById(R.id.removeBook);
       updateBook = findViewById(R.id.updateBook);
       issueBook = findViewById(R.id.issueBook);
       BooksList = findViewById(R.id.booksList);
       reissueBook = findViewById(R.id.reissueBook);
       collectFine = findViewById(R.id.collect1);
       logOut = findViewById(R.id.logOut);

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminAddBook.class);
                startActivity(intent);
                finish();

            }
        });

       logOut.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FirebaseAuth.getInstance().signOut();
               Intent intent = new Intent(getApplicationContext(), Login.class);
               startActivity(intent);
               finish();

           }
       });

    }


}
