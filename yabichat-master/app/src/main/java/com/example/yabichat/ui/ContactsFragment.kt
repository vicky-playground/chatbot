package com.example.yabichat.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yabichat.Constants
import com.example.yabichat.R
import com.example.yabichat.model.Chat
import com.example.yabichat.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_contacts.*
import java.util.*


class ContactsFragment : Fragment() {
    private lateinit var dbRef: DatabaseReference
    val listData = ArrayList<User>()
//
    companion object {
        lateinit var user: User
        const val BUNDLE_KEY_CONTACT_UID = "BUNDLE_KEY_CONTACT_UID"
        const val BUNDLE_KEY_CONTACT_NAME = "BUNDLE_KEY_CONTACT_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        getContactsData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        text_greeting.text = user.name
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    private fun init(){
        dbRef = Firebase.database.getReference(Constants.DATABASE_USERS) // Contacts
        Log.d(Constants.TAG_DEBUG, "--------arguments: " + arguments.toString())
        user = arguments?.get(MainActivity.BUNDLE_KEY_USER) as User //MainActivity.user

    }

    private fun getContactsData(){
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var userObj: User

                for(item in dataSnapshot.children){
                    userObj = User(
                        item.child("uid").value.toString(),
                        item.child("name").value.toString(),
                        item.child("email").value.toString(),
                        item.child("phone").value.toString(),
                        item.child("contacts").value as MutableList<String>?
                    )
                    if (userObj !in listData && userObj.uid != user.uid){
                        listData.add(userObj)
                    }
                }
                setContactsRecyclerView()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //Log.w(TAG, "onCancelled", databaseError.toException())
            }
        })
    }

    inner class ChatListener: View.OnClickListener{
        override fun onClick(v: View?) {
            val index = v?.id
            // Log.d(Constants.TAG_DEBUG, "index: "+ index)
            val contact = listData.get(index!!)
            chat(contact.uid, contact.name);
        }
    }

    private fun setContactsRecyclerView(){
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_contacts.layoutManager = layoutManager
        rv_contacts.adapter = ContactsAdapter(listData, ChatListener())
    }

    fun chat(contactUID: String, memberName: String) {

        // create new chatroom
        var dbRef_chatList = Firebase.database.getReference(Constants.DATABASE_CHATS).child(user.uid)

        dbRef_chatList.orderByKey().equalTo(contactUID).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    dbRef_chatList.child(contactUID).setValue(Chat(contactUID, memberName,"", Date().time))
                }
            }
        })

        // go to MsgActivity
        val i: Intent = Intent(activity, MsgActivity::class.java)
        i.putExtra(BUNDLE_KEY_CONTACT_UID, contactUID)
        i.putExtra(BUNDLE_KEY_CONTACT_NAME, memberName)
        startActivity(i)
    }

}

//class ContactsFragment : Fragment() {
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_contacts, container, false)
//    }
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ContactsFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ContactsFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
//}