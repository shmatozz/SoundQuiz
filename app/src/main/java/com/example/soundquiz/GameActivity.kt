package com.example.soundquiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class GameActivity : AppCompatActivity(), Communicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        commitFragment(PlayersAddFragment())
    }

    override fun passData(players: ArrayList<String>) {
        val bundle = Bundle()
        bundle.putStringArrayList("players", players)

        val gameFragment = GameFragment()
        gameFragment.arguments = bundle
        commitFragment(gameFragment)
    }

    private fun commitFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, fragment)
        ft.commit()
    }
}