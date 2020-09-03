package com.dims.gads2020aadpracticeproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class LeaderItemsAdapter(private val leaders: List<Leader>): RecyclerView.Adapter<LeaderItemsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val nameTextView = itemView.findViewById<TextView>(R.id.name_text_view)
        private val detailTextView = itemView.findViewById<TextView>(R.id.detail_text_view)
        private val badgeImageView = itemView.findViewById<ImageView>(R.id.badge_imageView)

        fun setData(leader: Leader){
            nameTextView.text = leader.name
            detailTextView.text = if (leader is SkillLeader) "${leader.num} skill IQ score, ${leader.country}"
            else "${leader.num} learning hours, ${leader.country}"
            Picasso.get().load(leader.badgeUrl).into(badgeImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.leader_item, parent, false))
    }

    override fun getItemCount(): Int = leaders.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(leaders[position])
    }
}