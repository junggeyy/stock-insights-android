package edu.nku.classapp.model

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import edu.nku.classapp.data.model.LoginResponse
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import edu.nku.classapp.LandingActivity
import edu.nku.classapp.ProfileActivity
import edu.nku.classapp.R
import edu.nku.classapp.di.AppModule


class Login : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.usernameEmail)
        passwordEditText = findViewById(R.id.password)
        val forgotPasswordText = findViewById<TextView>(R.id.forgotPassword)

        // Setup login button (removed duplicate listener)
        findViewById<Button>(R.id.signInButton).setOnClickListener {
            handleLogin()
        }

        forgotPasswordText.setOnClickListener {
            showForgotPasswordDialog()
        }
    }

    private fun handleLogin() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (!validateInput(email, password)) return

        authenticateUser(email, password)
    }

    private fun validateInput(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                showToast("Please enter your email")
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showToast("Please enter a valid email")
                false
            }
            password.isEmpty() -> {
                showToast("Please enter your password")
                false
            }
            password.length < 6 -> {
                showToast("Password must be at least 6 characters")
                false
            }
            else -> true
        }
    }

    private fun saveAuthToken(token: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("AUTH_TOKEN", token).apply()

        // for debugging
        val savedToken = sharedPreferences.getString("AUTH_TOKEN", null)
        showToast("Token saved: ${savedToken ?: "null"}")
    }

    private fun authenticateUser(email: String, password: String) {
        val credentials = mapOf("email" to email, "password" to password)

        AppModule.instance.login(credentials).enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(call: retrofit2.Call<LoginResponse>, response: retrofit2.Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val token = loginResponse?.token

                    if (token != null) {
                        Log.d("LoginActivity", "Login successful, token received") //debugging
                        saveAuthToken(token)
                        showToast("Login successful, redirecting...")
                        navigateToProfile(email, loginResponse.user.firstName ?: "User")
                    }
                    else {
                        Log.e("LoginActivity", "Token is null despite successful response")
                        showToast("Login error: No authentication token received")
                    }
                } else {
                    // debugging
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("LoginActivity", "Login failed: $errorBody")

                    showToast("Login failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                Log.e("LoginActivity", "Network error", t)
                showToast("Network Error: ${t.message}")
            }
        })
    }

    private fun navigateToProfile(email: String, name: String) {
        // Debugging
        Log.d("LoginActivity", "Starting navigation to ProfileActivity")
        showToast("Navigating to ProfileActivity...")

        startActivity(
            Intent(this, LandingActivity::class.java).apply {
                putExtra("EMAIL", email)
                putExtra("NAME", name)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        )
        finish()
    }

    private fun showForgotPasswordDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
        val dialogEmailEditText = view.findViewById<EditText>(R.id.forgot_password_email)

        AlertDialog.Builder(this)
            .setView(view)
            .setTitle("Password Reset")
            .setPositiveButton("Submit") { _, _ ->
                val email = dialogEmailEditText.text.toString().trim()
                if (validateForgotPasswordEmail(email)) {
                    sendPasswordResetEmail(email)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun validateForgotPasswordEmail(email: String): Boolean {
        if (email.isEmpty()) {
            showToast("Please enter your email")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter a valid email")
            return false
        }
        return true
    }

    private fun sendPasswordResetEmail(email: String) {
        // Simulate sending a password reset email
        // Replace this with your own logic to send an email if needed
        showToast("Password reset email sent to $email")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }
}
