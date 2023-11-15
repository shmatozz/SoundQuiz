package com.example.soundquiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.soundquiz.databinding.PlayerItemBinding


class PlayerAdapter: RecyclerView.Adapter<PlayerAdapter.PlayerHolder>() {

    val playersList = ArrayList<Player>()

    class PlayerHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = PlayerItemBinding.bind(item)
        fun bind(player: Player) = with(binding) {
            playerName.text = player.name
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
        holder.bind(playersList[position])
    }

    fun addPlayer(deposit: Player) {
        playersList.add(deposit)
        notifyDataSetChanged()
    }

    fun deletePlayer() {
        if (playersList.size > 0) {
            playersList.removeLast()
            notifyDataSetChanged()
        }
    }
}