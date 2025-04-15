package edu.nku.classapp

import android.animation.AnimatorSet
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.animation.ObjectAnimator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
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

        findViewById<TextView>(R.id.button11).setOnClickListener {
            startActivity(Intent(this, Guest::class.java))
        }
        findViewById<Button>(R.id.button9).setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val animatedMoney = findViewById<ImageView>(R.id.animatedMoney)

        val flyAnimation = ObjectAnimator.ofFloat(animatedMoney, "translationX", -500f, 1000f).apply {
            duration = 6000L
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.RESTART
            interpolator = LinearInterpolator()
        }

        // Vertical floating (gentle bobbing like a socially awkward balloon)
        val floatAnimation = ObjectAnimator.ofFloat(animatedMoney, "translationY", 0f, -20f, 0f, 20f, 0f).apply {
            duration = 3000L
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.RESTART
            interpolator = LinearInterpolator()
        }

        //For company animation
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(flyAnimation, floatAnimation)
        animatorSet.start()

        val company = findViewById<ImageView>(R.id.animatedCompany)
        val zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        company.startAnimation(zoomIn)
        zoomIn.repeatCount = Animation.INFINITE
        zoomIn.repeatMode = Animation.RESTART

        //For bar increase animation
        val barIncrease = findViewById<ImageView>(R.id.animatedBarIncrease)
        val animation = AnimationUtils.loadAnimation(this, R.anim.wiggle_zoom)
        barIncrease.startAnimation(animation)

        //Sparkle hour glass
        val hourGlass = findViewById<ImageView>(R.id.animatedHourGlass)
        val anim = AnimationUtils.loadAnimation(this, R.anim.sparkle)
        hourGlass.startAnimation(anim)

    }
}