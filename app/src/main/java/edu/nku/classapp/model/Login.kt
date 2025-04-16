package edu.nku.classapp.model

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import edu.nku.classapp.LandingActivity
import edu.nku.classapp.R
import edu.nku.classapp.data.model.LoginResponse
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

    private fun authenticateUser(email: String, password: String) {
        val credentials = mapOf("email" to email, "password" to password)

        AppModule.instance.login(credentials).enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(
                call: retrofit2.Call<LoginResponse>,
                response: retrofit2.Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val token = loginResponse?.token

                    if (token != null) {
                        saveAuthToken(token)
                        showToast("Login successful!")
                        redirectToLanding(email, loginResponse.user.firstName ?: "User")
                    } else {
                        showToast("Login error: No token received")
                    }
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                    showToast("Login failed: $errorMsg")
                }
            }

            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                Log.e("Login", "Network error", t)
                showToast("Network error: ${t.localizedMessage}")
            }
        })
    }

    private fun saveAuthToken(token: String) {
        val prefs: SharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        prefs.edit().putString("AUTH_TOKEN", token).apply()
    }

    private fun redirectToLanding(email: String, name: String) {
        val intent = Intent(this, LandingActivity::class.java).apply {
            putExtra("EMAIL", email)
            putExtra("NAME", name)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }

    private fun showForgotPasswordDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
        val emailInput = view.findViewById<EditText>(R.id.forgot_password_email)

        AlertDialog.Builder(this)
            .setView(view)
            .setTitle("Reset Password")
            .setPositiveButton("Submit") { _, _ ->
                val email = emailInput.text.toString().trim()
                if (validateForgotPasswordEmail(email)) {
                    sendResetEmail(email)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun validateForgotPasswordEmail(email: String): Boolean {
        return when {
            email.isEmpty() -> {
                showToast("Please enter your email")
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showToast("Please enter a valid email")
                false
            }

            else -> true
        }
    }

    private fun sendResetEmail(email: String) {
        // will replace with our actual reset logic (if any)
        showToast("Reset link sent to $email")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }
}
