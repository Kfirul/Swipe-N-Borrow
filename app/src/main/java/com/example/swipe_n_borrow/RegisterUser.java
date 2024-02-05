package com.example.swipe_n_borrow;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextID, editTextFullName, editTextPhoneNumber, editTextAddress;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;
    FirebaseFirestore fstore;
    String userID;
    boolean acceptedTerms=false;


    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent= new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void openActivityTermsUser(View view) {
        Intent intent = new Intent(this, ActivityTermsUser.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth= FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextID = findViewById(R.id.id);
        editTextFullName = findViewById(R.id.fullName);
        editTextPhoneNumber = findViewById(R.id.phoneNumber);
        editTextAddress = findViewById(R.id.address);
        buttonReg =findViewById(R.id.BTN_Register);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.LoginNow);
        fstore = FirebaseFirestore.getInstance();
        CheckBox checkBoxTerms = findViewById(R.id.checkBoxTerms);

        checkBoxTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update the acceptedTerms variable based on the CheckBox state
                acceptedTerms = isChecked;
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password, id, fullName, phoneNumber, address;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                id = String.valueOf(editTextID.getText());
                fullName = String.valueOf(editTextFullName.getText());
                phoneNumber = String.valueOf(editTextPhoneNumber.getText());
                address = String.valueOf(editTextAddress.getText());

                if(TextUtils.isEmpty(fullName)){
                    Toast.makeText(RegisterUser.this,"Enter Full Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(id)){
                    Toast.makeText(RegisterUser.this,"Enter ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isAllDigits(id)){
                    Toast.makeText(RegisterUser.this,"ID must be made of digits only", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (id.length()<4){
                    Toast.makeText(RegisterUser.this,"ID must be at least a 4 digit number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(address)){
                    Toast.makeText(RegisterUser.this,"Enter Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterUser.this,"Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!email.contains("@")){
                    Toast.makeText(RegisterUser.this,"Email address is badly formatted", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phoneNumber)){
                    Toast.makeText(RegisterUser.this,"Enter Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isAllDigits(phoneNumber)||!(phoneNumber.length()==10)){
                    Toast.makeText(RegisterUser.this,"Phone Number must be a 10 digit number.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(phoneNumber.startsWith("05"))){
                    Toast.makeText(RegisterUser.this,"Phone Number is not legal, must start with 05.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterUser.this,"Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<6){
                    Toast.makeText(RegisterUser.this,"Password must be at least 6 characters ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!acceptedTerms) {
                    Toast.makeText(RegisterUser.this, "Please accept the terms and conditions", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterUser.this, "Account created.", Toast.LENGTH_SHORT).show();
                                    userID = mAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fstore.collection("Users").document(userID);
                                    Map<String,Object> user =new HashMap<>();
                                    user.put("email",email);
                                    user.put("fullName",fullName);
                                    user.put("id",id);
                                    user.put("address",address);
                                    user.put("phoneNumber",phoneNumber);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG,"onSuccess: user Profile is created for "+userID);
                                        }
                                    });
                                    Intent intent2 = new Intent(getApplicationContext(), UserHome.class);
                                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent2);
                                    finish();
                                }
                                else {

                                    // If sign up fails
                                    Toast.makeText(RegisterUser.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    static boolean isAllDigits(String s) {
        return s.matches("\\d+");
    }
}