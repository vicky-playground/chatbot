package com.example.yabichat.ui
//
//import android.app.AlertDialog
//import android.content.DialogInterface
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.Menu
//import android.view.MenuItem
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.yabichat.Constants
//import com.example.yabichat.R
//import com.example.yabichat.model.Chat
//import com.example.yabichat.model.User
//import com.firebase.ui.auth.AuthUI
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.ValueEventListener
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
//import kotlinx.android.synthetic.main.activity_contacts.*
//import java.util.*
//
//class ContactsActivity : AppCompatActivity(){
//    private lateinit var dbRef: DatabaseReference
//    val listData = ArrayList<User>()
//
//    companion object {
//        lateinit var user: User
//        const val BUNDLE_KEY_CONTACT_UID = "BUNDLE_KEY_CONTACT_UID"
//        const val BUNDLE_KEY_CONTACT_NAME = "BUNDLE_KEY_CONTACT_NAME"
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_contacts)
//        init()
//
//        getContractsData()
//    }
//
//    private fun init(){
//        dbRef = Firebase.database.getReference(Constants.DATABASE_USERS) // Contacts
//        user = intent.getParcelableExtra(LoginActivity.BUNDLE_KEY_USER)!!
//
//        text_greeting.text = user.name
//    }
//
//    private fun getContractsData(){
//        dbRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                var userObj: User
//
//                for(item in dataSnapshot.children){
//                    userObj = User(
//                        item.child("uid").value.toString(),
//                        item.child("name").value.toString(),
//                        item.child("email").value.toString(),
//                        item.child("phone").value.toString(),
//                        item.child("contacts").value as MutableList<String>?
//                    )
//                    if (userObj !in listData && userObj.uid != user.uid){
//                        listData.add(userObj)
//                    }
//                }
//                setContactsRecyclerView()
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                //Log.w(TAG, "onCancelled", databaseError.toException())
//            }
//        })
//    }
//
//    inner class ChatListener: View.OnClickListener{
//        override fun onClick(v: View?) {
//            val id = v?.id
//            Log.d(Constants.TAG_DEBUG, "id: "+ id)
//            val user = listData.get(id!!)
//            chat(user.uid, user.name);
//        }
//    }
//
//    private fun setContactsRecyclerView(){
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        rv_contacts.layoutManager = layoutManager
//        rv_contacts.adapter = ContactsAdapter(listData, ChatListener())
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.menu_sign_out -> {
//                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
//                builder.setCancelable(false)
//                    .setTitle("登出")
//                    .setMessage("確定要登出了嗎？")
//                    .setPositiveButton(android.R.string.ok,
//                        DialogInterface.OnClickListener { dialog, which ->
//                            AuthUI.getInstance()
//                                .signOut(this)
//                                .addOnCompleteListener {
//                                    Toast.makeText(this, "登出成功", Toast.LENGTH_LONG).show()
//                                    finish()
//                                }
//                        }).setNegativeButton(android.R.string.no,
//                        DialogInterface.OnClickListener { dialog, which -> }).create()
//                builder.show()
//            }
//        }
//
//        return true
//    }
//
//    fun chat(contactUID: String, memberName: String) {
//        var dbRef_chatList = Firebase.database.getReference(Constants.DATABASE_CHATS).child(user.uid).child(
//            Constants.DATABASE_CHATS_NODE_CHAT_LIST)
//
//        dbRef_chatList.orderByKey().equalTo(contactUID).addListenerForSingleValueEvent(object :
//            ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {}
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (!dataSnapshot.exists()) {
//                    dbRef_chatList.child(contactUID).setValue(Chat(contactUID, memberName,"", Date().time))
//                }
//            }
//        })
//
//        val i: Intent = Intent(this, MsgActivity::class.java)
//        i.putExtra(BUNDLE_KEY_CONTACT_UID, contactUID)
//        i.putExtra(BUNDLE_KEY_CONTACT_NAME, memberName)
//        startActivity(i)
//    }
//
//    private fun reload() {
//        finish();
//        startActivity(getIntent());
//    }
//
//}