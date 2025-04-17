package edu.nku.classapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import edu.nku.classapp.ui.UserProfileFragment

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.profileFragmentContainer, UserProfileFragment())
                    .commit()
        }
    }
}
