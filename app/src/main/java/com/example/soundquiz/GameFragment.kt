package com.example.soundquiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.soundquiz.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var players: ArrayList<String>
    private var currentPlayer: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        val root = binding.root

        players = arguments?.getStringArrayList("players")!!

        binding.currentPlayerTitle.text = getString(R.string.current_player, players[currentPlayer])

        return root
    }
}