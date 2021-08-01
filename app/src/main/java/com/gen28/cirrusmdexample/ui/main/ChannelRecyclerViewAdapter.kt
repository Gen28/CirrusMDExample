package com.gen28.cirrusmdexample.ui.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gen28.cirrusmdexample.R
import com.gen28.cirrusmdexample.entity.Channel

class ChannelRecyclerViewAdapter(
    private val values: List<Channel>
) : RecyclerView.Adapter<ChannelRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_channel_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = (position + 1).toString()
        holder.contentView.text = item.name
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.channel_id)
        val contentView: TextView = view.findViewById(R.id.channel_name)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}