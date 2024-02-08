package com.example.swipe_n_borrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Map;
import java.util.HashMap;


public class EditAdminProfile extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button buttonEdit;
    FirebaseFirestore fstore;
    ProgressBar progressBar;

    TextInputEditText editTextFullName, editTextPhoneNumber, editTextAddress,editTextLibrary;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin_profile);
        mAuth = FirebaseAuth.getInstance();
        editTextFullName = findViewById(R.id.fullName);
        editTextPhoneNumber = findViewById(R.id.phoneNumber);
        editTextAddress = findViewById(R.id.address);
        editTextLibrary = findViewById(R.id.library);
        buttonEdit = findViewById(R.id.BTN_Edit);
        progressBar = findViewById(R.id.progressBar);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AdminHome.class);
                startActivity(intent);
                finish();
            }
        });

        String adminId = mAuth.getCurrentUser().getUid();
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String fullName, phoneNumber, address, library;
                fullName = String.valueOf(editTextFullName.getText());
                phoneNumber = String.valueOf(editTextPhoneNumber.getText());
                address = String.valueOf(editTextAddress.getText());
                library = String.valueOf(editTextLibrary.getText());

                if (!isAllDigits(phoneNumber)&& !phoneNumber.isEmpty() || (!(phoneNumber.length() == 10) && !phoneNumber.isEmpty())) {
                    Toast.makeText(EditAdminProfile.this, "Phone Number must be a 10-digit number.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if (!(phoneNumber.startsWith("05")) && !phoneNumber.isEmpty()) {
                    Toast.makeText(EditAdminProfile.this, "Phone Number is not legal, must start with 05.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                // Update the profile information in Firestore
                String adminId = mAuth.getCurrentUser().getUid();
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

                if (!library.isEmpty()) {
                    updates.put("library", library);
                }

                if (!updates.isEmpty()) {
                    db.collection("Admins").document(adminId)
                            .update(updates)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(EditAdminProfile.this, "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // You can add more logic here, such as finishing the activity or navigating to another screen.
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(EditAdminProfile.this, "Failed to update profile.", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // Handle the failure, for example, display an error message.
                            });
                } else {
                    // No updates were provided
                    Toast.makeText(EditAdminProfile.this, "No changes were made.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }




            }

        });
    }

    static boolean isAllDigits(String s) {
        return s.matches("\\d+");
    }

}