package com.example.soundquiz.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.soundquiz.data.Communicator
import com.example.soundquiz.fragments.GameFragment
import com.example.soundquiz.fragments.PlayersAddFragment
import com.example.soundquiz.R


class GameActivity : AppCompatActivity(), Communicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        commitFragment(PlayersAddFragment())
    }

    /**
     * Put players list as argument to game fragment and commit new fragment
     * @param players - List with players names
     */
    override fun passData(players: ArrayList<String>) {
        val bundle = Bundle()
        bundle.putStringArrayList("players", players)

        val gameFragment = GameFragment()
        gameFragment.arguments = bundle
        commitFragment(gameFragment)
    }

    /**
     * Commit new fragment at Game Activity
     */
    private fun commitFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, fragment)
        ft.commit()
    }
}