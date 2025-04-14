package edu.nku.classapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.nku.classapp.R
import edu.nku.classapp.data.model.response.LoginSignupResponse
import edu.nku.classapp.di.AppModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize the signup button and set an onClick listener
        val signupButton = findViewById<Button>(R.id.btnSignup)
        signupButton.setOnClickListener {
            performRegister()
        }
    }

    // Function to perform the registration
    private fun performRegister() {
        // Get values from the input fields
        val firstName = findViewById<EditText>(R.id.first_name_edittext_signup).text.toString().trim()
        val lastName = findViewById<EditText>(R.id.last_name_edittext_signup).text.toString().trim()
        val username = findViewById<EditText>(R.id.username_edittext_signup).text.toString().trim()
        val email = findViewById<EditText>(R.id.email_edittext_signup).text.toString().trim()
        val password = findViewById<EditText>(R.id.password_edittext_signup).text.toString().trim()

        if (!validateInput(firstName, lastName, username, email, password)) return

        val userData = mapOf(
            "first_name" to firstName,
            "last_name" to lastName,
            "username" to username,
            "email" to email,
            "password" to password
        )

        AppModule.instance.signup(userData).enqueue(object : Callback<LoginSignupResponse>
            {
            override fun onResponse(call: Call<LoginSignupResponse>, response: Response<LoginSignupResponse>) {
                if (response.isSuccessful) {
                    showToast("Signup successful! Redirecting to login...")
                    navigateToLogin()
                } else {
                    showToast("Signup failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<LoginSignupResponse>, t: Throwable) {
                showToast("Network Error: ${t.message}")
            }
        })
    }

    private fun validateInput(firstName: String, lastName: String, username: String, email: String, password: String): Boolean {
        return when {
            firstName.isEmpty() -> {
                showToast("Please enter your first name")
                false
            }
            lastName.isEmpty() -> {
                showToast("Please enter your last name")
                false
            }
            username.isEmpty() -> {
                showToast("Please enter your username")
                false
            }
            email.isEmpty() -> {
                showToast("Please enter your email")
                false
            }
            password.isEmpty() || password.length < 6 -> {
                showToast("Password must be at least 6 characters")
                false
            }
            else -> true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}