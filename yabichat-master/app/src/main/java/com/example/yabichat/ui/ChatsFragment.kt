package com.example.yabichat.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yabichat.Constants
import com.example.yabichat.R
import com.example.yabichat.model.Chat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_chats.*
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.item_chats.view.*
import java.util.ArrayList


class ChatsFragment : Fragment() {
    private lateinit var dbRef_chats: DatabaseReference
    val listData = ArrayList<Chat>()

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    private fun init(){
        dbRef_chats = Firebase.database.getReference(Constants.DATABASE_CHATS).child(MainActivity.user.uid)
        getChatsData()

    }

    private fun getChatsData(){
        dbRef_chats.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var chatObj: Chat
//                listData.clear()
                for(item in dataSnapshot.children){
                    chatObj = Chat(
                        item.child("memberUID").value.toString(),
                        item.child("memberName").value.toString(),
                        item.child("lastMsg").value.toString(),
                        item.child("timestamp").value as Long
                    )
                    if (chatObj !in listData){
                        listData.add(chatObj)
                    }
//                    listData.add(chatObj)
                }
                setChatsRecyclerView()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //Log.w(TAG, "onCancelled", databaseError.toException())
            }
        })
    }

    private fun setChatsRecyclerView(){
//        Log.d(Constants.TAG_DEBUG, "listData: " + listData.size)
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_chats.layoutManager = layoutManager
//        val adapter = ChatsAdapter(listData)
//        adapter.notifyDataSetChanged()
        rv_chats.adapter =  ChatsAdapter(listData)

    }


    inner class onDeleteChatListener: View.OnClickListener{
        override fun onClick(v: View?) {
            val index = v?.id
            // val target = listData.get(index!!)
            Log.d(Constants.TAG_DEBUG, "target: "+ index + "--- " + listData.size)
            // dbRef_chats.child(target.memberUID).removeValue()
        }
    }

}