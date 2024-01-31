package com.example.swipe_n_borrow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore fstore; // Add this line to declare fstore

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance(); // Initialize fstore

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            CollectionReference adminsCollection = fstore.collection("Admins");
            adminsCollection.document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> adminTask) {
                    if (adminTask.isSuccessful()) {
                        DocumentSnapshot adminDocument = adminTask.getResult();
                        if (adminDocument.exists()) {
                            // Admin user
                            Intent intent = new Intent(getApplicationContext(), AdminHome.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Regular user
                            Intent intent = new Intent(getApplicationContext(), UserHome.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            });
        }
    }
}
