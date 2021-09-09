package com.example.yabichat.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yabichat.Constants
import com.example.yabichat.R
import com.example.yabichat.model.Chat
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.item_chats.view.*
import kotlinx.android.synthetic.main.item_contacts.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChatsAdapter(private val dataSet: ArrayList<Chat>): RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {
//    var listener: View.OnClickListener = onDeleteChatListener

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
//            itemView.setOnClickListener{
//                // go to MsgActivity
//                val i = Intent(itemView.msg_info.context, MsgActivity::class.java)
//                i.putExtra(ContactsFragment.BUNDLE_KEY_CONTACT_UID, dataSet[position].memberUID)
//                i.putExtra(ContactsFragment.BUNDLE_KEY_CONTACT_NAME, dataSet[position].memberName)
//                itemView.context.startActivity(i)
//            }
            itemView.msg_info.setOnClickListener{
                val i = Intent(itemView.msg_info.context, MsgActivity::class.java)
                i.putExtra(ContactsFragment.BUNDLE_KEY_CONTACT_UID, dataSet[position].memberUID)
                i.putExtra(ContactsFragment.BUNDLE_KEY_CONTACT_NAME, dataSet[position].memberName)
                itemView.context.startActivity(i)
                // Toast.makeText(view.context, textView.text, Toast.LENGTH_SHORT).show()
            }
//            itemView.btn_delete_chat.setOnClickListener{
//                val dbRef_chats = Firebase.database.getReference(Constants.DATABASE_CHATS).child(MainActivity.user.uid)
//                dbRef_chats.child(dataSet[position].memberUID).removeValue()
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_chats, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.id = position
        holder.itemView.chat_photo.setImageResource(R.drawable.yabichat_g)
        holder.itemView.tv_chat_member.text = dataSet[position].memberName
        holder.itemView.tv_lastMsg.text = dataSet[position].lastMsg
        holder.itemView.tv_msgTime.text = getDateTime(dataSet[position].timestamp)
    }

    private fun getDateTime(time: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(time)
    }

    override fun getItemCount(): Int = dataSet.size
}