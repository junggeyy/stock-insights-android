package edu.nku.classapp

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import edu.nku.classapp.model.Guest
import edu.nku.classapp.ui.auth.LoginActivity
import edu.nku.classapp.ui.auth.SignupActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        findViewById<Button>(R.id.button9).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        findViewById<Button>(R.id.button10).setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        findViewById<TextView>(R.id.button11).setOnClickListener {
            startActivity(Intent(this, Guest::class.java))
        }
        val animatedMoney = findViewById<ImageView>(R.id.animatedMoney)
        val animation = AnimationUtils.loadAnimation(this, R.anim.wiggle_zoom)
        animatedMoney.startAnimation(animation)
    }
}
