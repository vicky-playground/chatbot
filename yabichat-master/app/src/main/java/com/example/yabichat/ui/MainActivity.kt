package com.example.yabichat.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.yabichat.Constants
import com.example.yabichat.R
import com.example.yabichat.model.User
import com.firebase.ui.auth.AuthUI
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
//    val fragmentManager: FragmentManager = supportFragmentManager
//    val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
    private lateinit var dbRef_users: DatabaseReference
    private lateinit var dbRef_chatList: DatabaseReference
    private lateinit var dbRef_msgList: DatabaseReference
    private val fragmentContacts: ContactsFragment = ContactsFragment()
    private val fragmentChats: ChatsFragment = ChatsFragment()
    private lateinit var tabLayout: TabLayout

    companion object {
        lateinit var user: User
        const val BUNDLE_KEY_USER = "BUNDLE_KEY_USER"
        const val BUNDLE_KEY_CHAT_LIST = "BUNDLE_KEY_CHAT_LIST"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setUpFragments()
    }

    override fun onResume() {
        super.onResume()
        //setUpFragments()
    }

    private fun init(){
        user = intent.getParcelableExtra(LoginActivity.BUNDLE_KEY_USER)!!
        dbRef_users = Firebase.database.getReference(Constants.DATABASE_USERS)
        dbRef_chatList = Firebase.database.getReference(Constants.DATABASE_CHATS).child(user.uid)
        dbRef_msgList = Firebase.database.getReference(Constants.DATABASE_MESSAGES).child(user.uid)

    }

    private fun setUpFragments(){
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_fragment_container, fragmentContacts, "Contacts")
        fragmentTransaction.add(R.id.main_fragment_container, fragmentChats, "Chats")
        fragmentTransaction.hide(fragmentChats)
        fragmentTransaction.commit()

        val bundleContacts = Bundle()
        bundleContacts.putParcelable(BUNDLE_KEY_USER, user)
        fragmentContacts.arguments = bundleContacts

        // on tab selected
        tabLayout = findViewById(R.id.tabLayoutMain)
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                onFragmentChange(tab.position);
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun onFragmentChange(index: Int){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        when (index) {
            0 -> {
                fragmentTransaction.hide(fragmentChats)
                fragmentTransaction.show(fragmentContacts)
            }
            1 -> {
                fragmentTransaction.hide(fragmentContacts)
                fragmentTransaction.show(fragmentChats)
            }
        }
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_sign_out -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setCancelable(false)
                    .setTitle("登出")
                    .setMessage("確定要登出了嗎？")
                    .setPositiveButton(android.R.string.ok,
                        DialogInterface.OnClickListener { dialog, which ->
                            AuthUI.getInstance()
                                .signOut(this)
                                .addOnCompleteListener {
                                    Toast.makeText(this, "登出成功", Toast.LENGTH_LONG).show()
                                    finish()
                                }
                        }).setNegativeButton(android.R.string.no,
                        DialogInterface.OnClickListener { dialog, which -> }).create()
                builder.show()
            }
        }

        return true
    }


    private fun reload() {
        finish();
        startActivity(intent);
    }

}

private fun Bundle.putParcelable(bundleKeyChatList: String, dbRef: DatabaseReference) {

}
