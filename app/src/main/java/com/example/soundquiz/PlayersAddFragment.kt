package com.example.soundquiz

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

            addPlayerButton.setOnClickListener {
                addNewPlayer()
            }

            toPlayButton.setOnClickListener {
                // change fragment
            }
        }
    }

    private fun addNewPlayer() {
        val addDialog = Dialog(requireContext())
        addDialog.setContentView(R.layout.dialog_add_player)
        addDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        addDialog.setCancelable(true)

        val createButton: Button = addDialog.findViewById(R.id.create_button)
        val newPlayerInput: EditText = addDialog.findViewById(R.id.new_title)

        createButton.setOnClickListener {
            val newName = newPlayerInput.text.toString()
            if (newName.isEmpty()) {
                newPlayerInput.setHintTextColor(Color.argb(255, 255, 160, 160))
                newPlayerInput.setText("")
            } else if (isUnique(newName)) {
                val newPlayer = Player(newName)
                adapter.addPlayer(newPlayer)
                addDialog.dismiss()
            } else {
                newPlayerInput.setHintTextColor(Color.argb(255, 255, 160, 160))
                newPlayerInput.setHint(R.string.exist_player)
                newPlayerInput.setText("")
            }

            if (adapter.playersList.size >= 2) {
                binding.toPlayButton.isEnabled = true
            }
        }

        addDialog.show()
    }

    private fun isUnique(name: String) : Boolean {
        for (player in adapter.playersList) {
            if (player.name == name) {
                return false
            }
        }
        return true
    }
}