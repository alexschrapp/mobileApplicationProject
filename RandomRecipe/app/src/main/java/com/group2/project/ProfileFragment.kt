package com.group2.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates

class ProfileFragment : AppCompatActivity() {

    private lateinit var currentEmail: TextView
    private lateinit var currentName: TextView
    private lateinit var currentDiet: TextView
    private lateinit var currentAge: TextView
    private lateinit var currentSub: Button
    private lateinit var database: DatabaseReference
    private var isPremium by Delegates.notNull<Boolean>()

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)
        database = Firebase.database.reference

        currentEmail = findViewById(R.id.showEmail)
        currentName = findViewById(R.id.nameDisplay)
        currentAge = findViewById(R.id.showAge)
        currentSub = findViewById(R.id.premium)
        currentDiet = findViewById(R.id.dietView)

        val currentUser = intent.getParcelableExtra<FirebaseUser>("currentUser")

        supportActionBar?.apply {
            title="Profile"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        database.child("users").child(currentUser!!.uid).child("name").get().addOnSuccessListener {
            Log.d("IN SUCCESS LISTENER", "Got value ${it.value}")
            currentName.text = it.value.toString()
        }.addOnFailureListener{
            Log.d("IN FAIL LISTENER", "Error getting data", it)
        }

        database.child("users").child(currentUser!!.uid).child("age").get().addOnSuccessListener {
            Log.d("IN SUCCESS LISTENER", "Got value ${it.value}")
            currentAge.text = it.value.toString()
        }.addOnFailureListener{
            Log.d("IN FAIL LISTENER", "Error getting data", it)
        }

        database.child("users").child(currentUser!!.uid).child("subscription").get().addOnSuccessListener {
            Log.d("IN SUCCESS LISTENER", "Got value ${it.value}")
            if(it.value.toString() == "free") {
                currentSub.text = "UPGRADE TO PREMIUM"
                isPremium = false
            } else {
                currentSub.text = "PREMIUM MEMBER"
                isPremium = true
            }
            //currentSub.text = it.value.toString()
        }.addOnFailureListener{
            Log.d("IN FAIL LISTENER", "Error getting data", it)
        }

        database.child("users").child(currentUser!!.uid).child("diet").get().addOnSuccessListener {
            Log.d("IN SUCCESS LISTENER", "Got value ${it.value}")
            currentDiet.text = it.value.toString()
        }.addOnFailureListener{
            Log.d("IN FAIL LISTENER", "Error getting data", it)
        }

        currentEmail.text = currentUser?.email
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("RestrictedApi")
    fun clickSubscribe(view: View) {
        //Log.d("testing", FirebaseAuth.getInstance().currentUser.uid)
        if(isPremium) {
            Toast.makeText(this, "You have unsubscribed!", Toast.LENGTH_SHORT).show()
            database.child("users").child(FirebaseAuth.getInstance().currentUser.uid).child("subscription").setValue("free")
        } else {
            Toast.makeText(this, "You are now subscribed!", Toast.LENGTH_SHORT).show()
            database.child("users").child(FirebaseAuth.getInstance().currentUser.uid).child("subscription").setValue("premium")
        }
    }
}