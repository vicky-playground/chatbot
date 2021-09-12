package com.example.yabichat.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yabichat.R
import com.example.yabichat.model.User
import kotlinx.android.synthetic.main.item_contacts.view.*
import kotlin.collections.ArrayList


class ContactsAdapter(private val dataSet: ArrayList<User>, listener: View.OnClickListener): RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    var chatListener: View.OnClickListener = listener

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener(chatListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_contacts, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.id = position
        holder.itemView.item_textView.text = dataSet[position].name
        holder.itemView.item_imageView.setImageResource(R.drawable.yabichat_g)
        holder.itemView.setBackgroundColor((Color.parseColor("#ffffff")))
        holder.itemView.item_textView.setTextColor((Color.parseColor("#255000")))

//        if( position%2 == 0){
////            holder.itemView.setBackgroundColor((Color.parseColor("#673AB7")))
////            holder.itemView.item_textView.setTextColor((Color.parseColor("#ffffff")))
//
//            holder.itemView.setBackgroundColor((Color.parseColor("#457C15")))
//            holder.itemView.item_textView.setTextColor((Color.parseColor("#ffffff")))
//        }
//        else{
////            holder.itemView.setBackgroundColor((Color.parseColor("#FF9800")))
//
//            holder.itemView.setBackgroundColor((Color.parseColor("#ffffff")))
//            holder.itemView.item_textView.setTextColor((Color.parseColor("#457C15")))
//
//        }

    }

    override fun getItemCount(): Int = dataSet.size
}