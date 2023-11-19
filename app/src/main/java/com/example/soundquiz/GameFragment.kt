package com.example.soundquiz

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soundquiz.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var players: ArrayList<Player>
    private var currentPlayer: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        val root = binding.root

        players = ArrayList()
        for (name in arguments?.getStringArrayList("players")!!) {
            players.add(Player(name, 0))
        }

        binding.currentPlayerTitle.text = getString(R.string.current_player, players[currentPlayer].name)

        init()

        return root
    }

    private fun init() = binding.apply {
        currentScoreButton.setOnClickListener {
            showPlayersScores()
        }

        transportCard.setOnClickListener {

        }

        sportCard.setOnClickListener {

        }

        professionsCard.setOnClickListener {

        }

        natureCard.setOnClickListener {

        }
    }

    private fun showPlayersScores() {
        val scoresDialog = Dialog(requireContext())
        scoresDialog.setContentView(R.layout.dialog_players_score)
        scoresDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        scoresDialog.setCancelable(true)

        val rc = scoresDialog.findViewById<RecyclerView>(R.id.players_rv)
        rc.layoutManager = LinearLayoutManager(context)
        val adapter = PlayerScoreAdapter()
        rc.adapter = adapter

        for (player in players) {
            adapter.addPlayer(player)
        }

        scoresDialog.show()
    }
}