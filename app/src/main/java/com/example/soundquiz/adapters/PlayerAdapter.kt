package com.example.soundquiz.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.soundquiz.data.Player
import com.example.soundquiz.R
import com.example.soundquiz.databinding.PlayerItemBinding


class PlayerAdapter(private val listener: Listener): RecyclerView.Adapter<PlayerAdapter.PlayerHolder>() {

    val playersList = ArrayList<Player>()

    class PlayerHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = PlayerItemBinding.bind(item)
        fun bind(player: Player, listener: Listener) = with(binding) {
            playerName.text = player.name

            playerCard.setOnClickListener {
                listener.onClick(player)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent, false)

        return PlayerHolder(view)
    }

    override fun getItemCount(): Int {
        return playersList.size
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.bind(playersList[position], listener)
    }

    fun addPlayer(player: Player) {
        playersList.add(player)
        notifyDataSetChanged()
    }

    interface Listener {
        fun onClick(player: Player)
    }
}