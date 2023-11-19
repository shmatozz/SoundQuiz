package com.example.soundquiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.soundquiz.databinding.PlayerWithScoreItemBinding


class PlayerScoreAdapter: RecyclerView.Adapter<PlayerScoreAdapter.PlayerHolder>() {

    private val playersList = ArrayList<Player>()

    class PlayerHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = PlayerWithScoreItemBinding.bind(item)
        fun bind(player: Player) = with(binding) {
            playerName.text = player.name
            playerScore.text = player.score.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_with_score_item, parent, false)

        return PlayerHolder(view)
    }


    override fun getItemCount(): Int {
        return playersList.size
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.bind(playersList[position])
    }

    fun addPlayer(player: Player) {
        playersList.add(player)
        notifyDataSetChanged()
    }
}