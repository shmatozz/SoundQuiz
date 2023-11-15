package com.example.soundquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soundquiz.databinding.FragmentPlayersAddBinding

class PlayersAddFragment : Fragment() {

    private lateinit var binding: FragmentPlayersAddBinding
    private val adapter = PlayerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayersAddBinding.inflate(inflater, container, false)
        val root = binding.root

        init()

        return root
    }

    private fun init() {
        binding.apply {
            playersList.layoutManager = LinearLayoutManager(this@PlayersAddFragment.context)
            playersList.adapter = adapter


        }
    }
}