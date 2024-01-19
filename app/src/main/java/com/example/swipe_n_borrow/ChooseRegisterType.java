package com.example.swipe_n_borrow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseRegisterType extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_register_type);

        Button userRegisterButton = findViewById(R.id.userRegisterButton);
        Button adminRegisterButton = findViewById(R.id.adminRegisterButton);

        userRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userRegisterIntent = new Intent(ChooseRegisterType.this, RegisterUser.class);
                startActivity(userRegisterIntent);
            }
        });

        adminRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminRegisterIntent = new Intent(ChooseRegisterType.this, RegisterAdmin.class);
                startActivity(adminRegisterIntent);
            }
        });
        textView = findViewById(R.id.LoginNow);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
