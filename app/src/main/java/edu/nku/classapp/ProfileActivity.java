package edu.nku.classapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        String email = intent.getStringExtra("EMAIL");

        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);

        if (nameTextView != null && emailTextView != null) {
            // check if name and email are available or else just display default messages
            String displayName = (name != null) ? name : "User";
            String displayEmail = (email != null) ? email : "No email provided";

            nameTextView.setText(String.format("Name: %s", displayName));
            emailTextView.setText(String.format("Email: %s", displayEmail));
        }
    }
}