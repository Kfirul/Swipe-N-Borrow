package com.example.swipe_n_borrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditUserProfile extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button buttonEdit;
    FirebaseFirestore fstore;
    ProgressBar progressBar;

    TextInputEditText editTextFullName, editTextPhoneNumber, editTextAddress, editTextLibrary;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        mAuth = FirebaseAuth.getInstance();
        editTextFullName = findViewById(R.id.fullName);
        editTextPhoneNumber = findViewById(R.id.phoneNumber);
        editTextAddress = findViewById(R.id.address);
        buttonEdit = findViewById(R.id.BTN_Edit);
        progressBar = findViewById(R.id.progressBar);

        // Retrieve current user profile information from Firestore and populate EditText fields
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("Users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String fullName = documentSnapshot.getString("fullName");
                        String phoneNumber = documentSnapshot.getString("phoneNumber");
                        String address = documentSnapshot.getString("address");

                        // Set the retrieved values to the EditText fields
                        editTextFullName.setText(fullName);
                        editTextPhoneNumber.setText(phoneNumber);
                        editTextAddress.setText(address);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure to retrieve profile information
                    Toast.makeText(EditUserProfile.this, "Failed to retrieve profile information.", Toast.LENGTH_SHORT).show();
                });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserHome.class);
                startActivity(intent);
                finish();
            }
        });

        // Add click listener for the edit button
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String fullName, phoneNumber, address;
                fullName = String.valueOf(editTextFullName.getText());
                phoneNumber = String.valueOf(editTextPhoneNumber.getText());
                address = String.valueOf(editTextAddress.getText());

                if (!isAllDigits(phoneNumber) && !phoneNumber.isEmpty() || (!(phoneNumber.length() == 10) && !phoneNumber.isEmpty())) {
                    Toast.makeText(EditUserProfile.this, "Phone Number must be a 10-digit number.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if (!(phoneNumber.startsWith("05")) && !phoneNumber.isEmpty()) {
                    Toast.makeText(EditUserProfile.this, "Phone Number is not legal, must start with 05.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                // Update the profile information in Firestore
                String userId = mAuth.getCurrentUser().getUid();
                Map<String, Object> updates = new HashMap<>();

                if (!fullName.isEmpty()) {
                    updates.put("fullName", fullName);
                }

                if (!phoneNumber.isEmpty()) {
                    updates.put("phoneNumber", phoneNumber);
                }

                if (!address.isEmpty()) {
                    updates.put("address", address);
                }

                if (!updates.isEmpty()) {
                    db.collection("Users").document(userId)
                            .update(updates)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(EditUserProfile.this, "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // You can add more logic here, such as finishing the activity or navigating to another screen.
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(EditUserProfile.this, "Failed to update profile.", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // Handle the failure, for example, display an error message.
                            });
                } else {
                    // No updates were provided
                    Toast.makeText(EditUserProfile.this, "No changes were made.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    static boolean isAllDigits(String s) {
        return s.matches("\\d+");
    }
}
