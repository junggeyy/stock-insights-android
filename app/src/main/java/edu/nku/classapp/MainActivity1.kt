package edu.nku.classapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import edu.nku.classapp.model.Guest
import edu.nku.classapp.model.Login
import edu.nku.classapp.model.SignUp

class MainActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1) // Use your XML layout

        // Button click handlers
        findViewById<Button>(R.id.button9).setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

        findViewById<Button>(R.id.button10).setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }

        findViewById<Button>(R.id.button11).setOnClickListener {
            startActivity(Intent(this, Guest::class.java))
        }
        findViewById<Button>(R.id.button9).setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}