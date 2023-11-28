package com.example.soundquiz.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
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
import com.example.soundquiz.adapters.PlayerAdapter
import com.example.soundquiz.adapters.PlayerScoreAdapter
import com.example.soundquiz.databinding.FragmentGameBinding


class GameFragment : Fragment(), PlayerAdapter.Listener {

    private lateinit var binding: FragmentGameBinding
    private lateinit var players: ArrayList<Player>
    private lateinit var currentGuessers: ArrayList<Player>
    private var currentPlayer: Int = 0
    private var currentExplainer: Int = 0
    private lateinit var theme: Themes
    private lateinit var word: String

    // lists of words to each theme
    private lateinit var transportWords: MutableList<String>
    private lateinit var sportsWords: MutableList<String>
    private lateinit var professionsWords: MutableList<String>
    private lateinit var natureWords: MutableList<String>

    // dialog windows
    private lateinit var selectDialog: Dialog
    private lateinit var scoresDialog: Dialog
    private lateinit var cardDialog: Dialog

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
        currentGuessers = players.filter { it != players[currentPlayer] } as ArrayList<Player>

        transportWords = getString(R.string.transport_words).split(',').toMutableList()
        sportsWords = getString(R.string.sport_words).split(',').toMutableList()
        professionsWords = getString(R.string.professions_words).split(',').toMutableList()
        natureWords = getString(R.string.nature_words).split(',').toMutableList()

        selectDialog = buildDialog(R.layout.dialog_time_over, false)
        scoresDialog = buildDialog(R.layout.dialog_players_score)
        cardDialog = buildDialog(R.layout.dialog_card_selected, false)

        binding.currentPlayerTitle.text = getString(R.string.current_player, players[currentPlayer].name)

        init()

        return root
    }

    /**
     * Initializing listeners of game layout buttons and cards
     */
    private fun init() = binding.apply {
        currentScoreButton.setOnClickListener {
            showPlayersScores()
        }

        transportCard.setOnClickListener {
            theme = Themes.TRANSPORT
            word = getWord(theme)
            cardSelected()
        }

        sportCard.setOnClickListener {
            theme = Themes.SPORT
            word = getWord(theme)
            cardSelected()
        }

        professionsCard.setOnClickListener {
            theme = Themes.PROFESSION
            word = getWord(theme)
            cardSelected()
        }

        natureCard.setOnClickListener {
            theme = Themes.NATURE
            word = getWord(theme)
            cardSelected()
        }
    }

    /**
     * Open clicked card with selected theme and word, start timer
     */
    private fun cardSelected() {
        val stopButton = cardDialog.findViewById<Button>(R.id.stop_button)
        val timer = cardDialog.findViewById<Chronometer>(R.id.timer)
        val card = cardDialog.findViewById<ConstraintLayout>(R.id.background)
        val wordView = cardDialog.findViewById<TextView>(R.id.word)
        val player = MediaPlayer.create(requireContext(), R.raw.time_over)

        // set background according selected theme and generate word
        when (theme) {
            Themes.TRANSPORT -> {
                card.background = ContextCompat.getDrawable(requireContext(), R.drawable.card_blue)
                wordView.text = word
            }
            Themes.SPORT -> {
                card.background = ContextCompat.getDrawable(requireContext(), R.drawable.card_red)
                wordView.text = word
            }
            Themes.PROFESSION -> {
                card.background = ContextCompat.getDrawable(requireContext(), R.drawable.card_yellow)
                wordView.text = word
            }
            Themes.NATURE -> {
                card.background = ContextCompat.getDrawable(requireContext(), R.drawable.card_green)
                wordView.text = word
            }
        }

        val beginTime = SystemClock.elapsedRealtime() + 61000
        timer.base = beginTime
        timer.start()

        stopButton.setOnClickListener {
            cardDialog.dismiss()
            showWinnerSelectionWindow()
        }

        // when timer is over -> open results dialog
        timer.setOnChronometerTickListener {
            val elapsedMillis: Long = (SystemClock.elapsedRealtime() - timer.base)
            if (elapsedMillis in 0..1000) {
                player.start()
                cardDialog.dismiss()
                timer.stop()
                showWinnerSelectionWindow()
            }
        }

        cardDialog.show()
    }

    /**
     * Opens dialog window to select player who guess the word
     */
    private fun showWinnerSelectionWindow() {
        val playersRV = selectDialog.findViewById<RecyclerView>(R.id.players_rv)
        playersRV.layoutManager = LinearLayoutManager(context)
        val adapter = PlayerAdapter(this)
        playersRV.adapter = adapter

        adapter.addPlayer(Player(getString(R.string.nobody_guess)))
        for (player in currentGuessers) {
            adapter.addPlayer(player)
        }

        selectDialog.show()
    }

    /**
     * Open players score window
     */
    private fun showPlayersScores() {
        val rc = scoresDialog.findViewById<RecyclerView>(R.id.players_rv)
        rc.layoutManager = LinearLayoutManager(context)
        val adapter = PlayerScoreAdapter()
        rc.adapter = adapter

        for (player in players) {
            adapter.addPlayer(player)
        }

        scoresDialog.show()
    }

    /**
     * Construct dialog window
     * @param layout - resource id of dialog layout
     * @return Dialog window
     */
    private fun buildDialog(layout: Int, cancelable: Boolean = true) : Dialog {
        val dialog = Dialog(requireContext())
        dialog.setContentView(layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(cancelable)

        return dialog
    }

    /**
     * Generate random word from lists according to selected theme
     * @param theme - one of game themes (transport, sport, profession, nature)
     */
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

    /**
     * Checks if words in some theme is over and disable such card
     */
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

    /**
     * Realize selection of player who guess the word by clicking in the item of RecyclerView
     */
    override fun onClick(player: Player) {
        if (player.name == getString(R.string.nobody_guess)) {
            if (currentGuessers.size == 1) {
                stepCurrentPlayer()
            } else {
                openNextExplainWarning()
            }
        } else {
            for (i in players.indices) {
                if (players[i].name == player.name) {
                    players[i].score++
                    players[currentExplainer].score++
                    stepCurrentPlayer()
                    break
                }
            }
        }

        selectDialog.dismiss()
    }

    /**
     * Open dialog window to tell who is next to explain current word
     */
    private fun openNextExplainWarning() {
        val warningDialog = buildDialog(R.layout.dialog_new_player_step, false)
        val playersName = warningDialog.findViewById<TextView>(R.id.player_now_explain)
        val letsGoButton = warningDialog.findViewById<Button>(R.id.lets_go_button)

        playersName.text = currentGuessers.first().name
        currentGuessers.removeFirst()
        currentExplainer = (currentExplainer + 1) % players.size

        letsGoButton.setOnClickListener {
            warningDialog.dismiss()
            cardSelected()
        }

        warningDialog.show()
    }

    /**
     * Step current player who selects next card to play
     */
    private fun stepCurrentPlayer() {
        currentPlayer = (currentPlayer + 1) % players.size
        currentExplainer = currentPlayer
        currentGuessers = ArrayList()
        for (i in currentExplainer + 1 until players.size) {
            currentGuessers.add(players[i])
        }
        for (i in 0 until currentExplainer) {
            currentGuessers.add(players[i])
        }
        binding.currentPlayerTitle.text = getString(R.string.current_player, players[currentPlayer].name)
    }
}