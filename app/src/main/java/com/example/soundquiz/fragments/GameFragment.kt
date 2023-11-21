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
import android.widget.TextView
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

    private lateinit var transportWords: MutableList<String>
    private lateinit var sportsWords: MutableList<String>
    private lateinit var professionsWords: MutableList<String>
    private lateinit var natureWords: MutableList<String>

    private enum class Themes { TRANSPORT, SPORT, PROFESSION, NATURE }

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

        transportWords = getString(R.string.transport_words).split(',').toMutableList()
        sportsWords = getString(R.string.sport_words).split(',').toMutableList()
        professionsWords = getString(R.string.professions_words).split(',').toMutableList()
        natureWords = getString(R.string.nature_words).split(',').toMutableList()

        binding.currentPlayerTitle.text = getString(R.string.current_player, players[currentPlayer].name)

        init()

        return root
    }

    private fun init() = binding.apply {
        currentScoreButton.setOnClickListener {
            showPlayersScores()
        }

        transportCard.setOnClickListener {
            cardSelected(Themes.TRANSPORT)
        }

        sportCard.setOnClickListener {
            cardSelected(Themes.SPORT)
        }

        professionsCard.setOnClickListener {
            cardSelected(Themes.PROFESSION)
        }

        natureCard.setOnClickListener {
            cardSelected(Themes.NATURE)
        }
    }

    private fun cardSelected(theme: Themes) {
        val cardDialog = Dialog(requireContext())
        cardDialog.setContentView(R.layout.dialog_card_selected)
        cardDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        cardDialog.setCancelable(false)

        val stopButton = cardDialog.findViewById<Button>(R.id.stop_button)
        val timer = cardDialog.findViewById<Chronometer>(R.id.timer)
        val card = cardDialog.findViewById<ConstraintLayout>(R.id.background)
        val word = cardDialog.findViewById<TextView>(R.id.word)

        // set background according selected theme and generate word
        when (theme) {
            Themes.TRANSPORT -> {
                card.background = ContextCompat.getDrawable(requireContext(), R.drawable.card_blue)
                word.text = getWord(Themes.TRANSPORT)
            }
            Themes.SPORT -> {
                card.background = ContextCompat.getDrawable(requireContext(), R.drawable.card_red)
                word.text = getWord(Themes.SPORT)
            }
            Themes.PROFESSION -> {
                card.background = ContextCompat.getDrawable(requireContext(), R.drawable.card_yellow)
                word.text = getWord(Themes.PROFESSION)
            }
            Themes.NATURE -> {
                card.background = ContextCompat.getDrawable(requireContext(), R.drawable.card_green)
                word.text = getWord(Themes.NATURE)
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

    private fun getWord(theme: Themes) : String {
        val word: String
        when (theme) {
            Themes.TRANSPORT -> {
                word = transportWords.random()
                transportWords.remove(word)
            }
            Themes.SPORT -> {
                word = sportsWords.random()
                sportsWords.remove(word)
            }
            Themes.PROFESSION -> {
                word = professionsWords.random()
                professionsWords.remove(word)
            }
            Themes.NATURE -> {
                word = natureWords.random()
                natureWords.remove(word)
            }
        }

        checkAndDisableCard()

        return word
    }

    private fun checkAndDisableCard() {
        if (transportWords.size == 0) {
            binding.transportCard.isEnabled = false
            binding.transportCard.alpha = 0.4F
        }
        if (sportsWords.size == 0) {
            binding.sportCard.isEnabled = false
            binding.sportCard.alpha = 0.4F
        }
        if (professionsWords.size == 0) {
            binding.professionsCard.isEnabled = false
            binding.professionsCard.alpha = 0.4F
        }
        if (natureWords.size == 0) {
            binding.natureCard.isEnabled = false
            binding.natureCard.alpha = 0.4F
        }
    }
}