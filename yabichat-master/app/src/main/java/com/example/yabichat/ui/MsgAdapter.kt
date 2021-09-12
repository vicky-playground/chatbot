package com.example.yabichat.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yabichat.Constants.RECEIVE_ID
import com.example.yabichat.Constants.SEND_ID
import com.example.yabichat.R
import com.example.yabichat.model.Msg
import kotlinx.android.synthetic.main.item_message.view.*

class MsgAdapter(private val messagesList: ArrayList<Msg>): RecyclerView.Adapter<MsgAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        )
    }

    override fun getItemCount(): Int = messagesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage = messagesList[position]
        when (currentMessage.tag) {
            SEND_ID -> {
                holder.itemView.tv_send_message.apply {
                    text = currentMessage.msg
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_receive_message.visibility = View.INVISIBLE
            }
            RECEIVE_ID -> {
                holder.itemView.tv_receive_message.apply {
                    text = currentMessage.msg
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_send_message.visibility = View.INVISIBLE
            }
        }
    }

}