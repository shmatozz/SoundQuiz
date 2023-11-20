package com.example.soundquiz.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soundquiz.data.Player
import com.example.soundquiz.R
import com.example.soundquiz.adapters.PlayerScoreAdapter
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
            cardSelected(1) // transport
        }

        sportCard.setOnClickListener {
            cardSelected(2) // sport
        }

        professionsCard.setOnClickListener {
            cardSelected(3) // professions
        }

        natureCard.setOnClickListener {
            cardSelected(4) // nature
        }
    }

    private fun cardSelected(theme: Int) {
        val cardDialog = Dialog(requireContext())
        cardDialog.setContentView(R.layout.dialog_card_selected)
        cardDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        cardDialog.setCancelable(false)

        val stopButton = cardDialog.findViewById<Button>(R.id.stop_button)
        val timer = cardDialog.findViewById<Chronometer>(R.id.timer)
        val card = cardDialog.findViewById<ConstraintLayout>(R.id.background)

        // set background according selected theme and generate word
        when (theme) {
            1 -> {
                card.background = ContextCompat.getDrawable(requireContext(), R.drawable.card_blue)
            }
            2 -> {
                card.background = ContextCompat.getDrawable(requireContext(), R.drawable.card_red)
            }
            3 -> {
                card.background = ContextCompat.getDrawable(requireContext(),
                    R.drawable.card_yellow
                )
            }
            4 -> {
                card.background = ContextCompat.getDrawable(requireContext(), R.drawable.card_green)
            }
        }

        val beginTime = SystemClock.elapsedRealtime() + 61000
        timer.base = beginTime
        timer.start()

        stopButton.setOnClickListener {
            cardDialog.dismiss()
        }

        timer.setOnChronometerTickListener {
            if (beginTime - SystemClock.elapsedRealtime() < 1000) {
                timer.stop()
                // open result window
            }
        }

        cardDialog.show()
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