package com.example.soundquiz.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.soundquiz.R


class MainActivity : Activity() {

    private lateinit var playButton: Button
    private lateinit var rulesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playButton = findViewById(R.id.play_button)
        rulesButton = findViewById(R.id.rules_button)

        playButton.setOnClickListener {
            val intent = Intent(applicationContext, GameActivity::class.java)
            startActivity(intent)
        }

        rulesButton.setOnClickListener {
            val intent = Intent(applicationContext, RulesActivity::class.java)
            startActivity(intent)
        }
    }
}
