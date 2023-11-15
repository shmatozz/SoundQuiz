package com.example.soundquiz

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity


class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val playersFragment = PlayersAddFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, playersFragment)
        ft.commit()
    }
}