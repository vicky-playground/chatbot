package com.example.yabichat.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yabichat.Constants
import com.example.yabichat.R
import com.example.yabichat.model.Chat
import com.example.yabichat.model.Msg
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_msg.*
import java.util.*
import kotlin.collections.ArrayList

class MsgActivity : AppCompatActivity() {
    private lateinit var dbRef_chatList: DatabaseReference
    private lateinit var dbRef_msgList: DatabaseReference
    private lateinit var chatID: String
    private lateinit var memberName: String
    val listData = ArrayList<Msg>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg)

        init()
        setMsgRecyclerview()
    }

    private fun getDatabaseRef(uid: String, memberID: String, tag: String): DatabaseReference{
        val dbRef = Firebase.database.getReference(tag).child(uid)

        return dbRef.child(memberID)
    }

    private fun init(){
        chatID = intent.getStringExtra(ContactsFragment.BUNDLE_KEY_CONTACT_UID).toString()
        memberName = intent.getStringExtra(ContactsFragment.BUNDLE_KEY_CONTACT_NAME).toString()

        dbRef_chatList = getDatabaseRef(MainActivity.user.uid, chatID, Constants.DATABASE_CHATS)
        dbRef_msgList = getDatabaseRef(MainActivity.user.uid, chatID, Constants.DATABASE_MESSAGES)

        msg_userName.text = memberName

        btn_send.setOnClickListener {
            sendMessage()
        }

        btn_back.setOnClickListener{
            finish()
        }

    }

    private fun getMsgData(){
        dbRef_msgList.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var msgObj: Msg

                for(item in dataSnapshot.children){
//                    Log.d(Constants.TAG_DEBUG, "name: ${item.child(Constants.KEY_NAME).value}")
//                    Log.d(Constants.TAG_DEBUG, "msg: ${item.child("msg").value}")
//                    Log.d(Constants.TAG_DEBUG, "timestamp: ${item.child("timestamp").value}")

                    msgObj = Msg(
                        item.child("name").value.toString(),
                        item.child("msg").value.toString(),
                        item.child("timestamp").value as Long,
                        item.child("tag").value.toString()
                    )
                    if (msgObj !in listData){
                        listData.add(msgObj)
                    }
                }
                rv_message.scrollToPosition(listData.size - 1)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //Log.w(TAG, "onCancelled", databaseError.toException())
            }
        })
    }

    private fun setMsgRecyclerview(){
        getMsgData()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_message.layoutManager = layoutManager
        rv_message.adapter = MsgAdapter(listData)
    }


    private fun sendMessage() {
        val msg = et_message.text.toString()
        val timestamp = Date().time

        if (msg.isNotEmpty()) {
            val key: String = dbRef_msgList.push().key.toString()

            // send
            dbRef_msgList.child(key).setValue(Msg(MainActivity.user.name, msg, timestamp, Constants.SEND_ID))
            dbRef_chatList.setValue(Chat(chatID, memberName, msg, timestamp))

            // update member's chat data
            var dbRef_member_msgList = getDatabaseRef(chatID, MainActivity.user.uid, Constants.DATABASE_MESSAGES)
            var dbRef_member_chatList = getDatabaseRef(chatID, MainActivity.user.uid, Constants.DATABASE_CHATS)
            dbRef_member_msgList.child(key).setValue(Msg(MainActivity.user.name, msg, timestamp, Constants.RECEIVE_ID))
            dbRef_member_chatList.setValue(Chat(MainActivity.user.uid,
                MainActivity.user.name, msg, timestamp))

            et_message.setText("")
            //rv_message.scrollToPosition(listData.size - 1)

            getMsgData()
        }
    }

}