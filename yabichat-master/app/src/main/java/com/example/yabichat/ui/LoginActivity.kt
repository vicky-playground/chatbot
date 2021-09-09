package com.example.yabichat.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.yabichat.Constants
import com.example.yabichat.R
import com.example.yabichat.model.User
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    companion object {
        const val RC_SIGN_IN = 123
        const val BUNDLE_KEY_USER = "BUNDLE_KEY_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = Firebase.auth
        dbRef = Firebase.database.getReference(Constants.DATABASE_USERS)

        checkIfLogin()
    }

    private fun checkIfLogin() {
        if (mAuth.currentUser == null) {
            createSignInIntent()
        } else {
            loginSuccess()
        }
    }

    private fun loginSuccess() {
        val mainIntent: Intent = Intent(this, MainActivity::class.java)
        val userObj = User(mAuth.currentUser!!.uid, getUserName(), mAuth.currentUser!!.email, mAuth.currentUser!!.phoneNumber)

        // check if the user exists
        // dbRef.orderByChild("TAG").equalTo("VALUE") -> WHERE TAG = "VALUE
        // dbRef.orderByChild(Constants.TAG_UID).equalTo(userObj.uid)
        dbRef.orderByKey().equalTo(userObj.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Log.i(Constants.TAG_DEBUG, "!dataSnapshot.exists()")
                    dbRef.child(userObj.uid).setValue(userObj)
                }
//                for (item in dataSnapshot.children) {
//                    val user = item.getValue(User::class.java)
//                    //list.add(user!!)
//                }
            }
        })

        mainIntent.putExtra(BUNDLE_KEY_USER, userObj)
        startActivity(mainIntent)
        finish()
    }

    private fun createSignInIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.yabichat)
                .setTheme(R.style.Theme_YabiChat)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                loginSuccess()
            } else {
                Toast.makeText(
                    baseContext,
                    R.string.loginFail,
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUserName(): String =
        when (true) {
            mAuth.currentUser!!.displayName != null -> mAuth.currentUser!!.displayName.toString()
            mAuth.currentUser!!.email != null -> mAuth.currentUser!!.email.toString()
            mAuth.currentUser!!.phoneNumber != null -> mAuth.currentUser!!.phoneNumber.toString()
            else -> "UserName"
        }
}