package com.group2.project

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SettingsFragment : AppCompatActivity() {

    private lateinit var curEmail: TextView
    private lateinit var submitChanges: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_settings)

        curEmail = findViewById(R.id.emailField)

        supportActionBar?.apply {
            title="Settings"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        val currentUser = intent.getParcelableExtra<FirebaseUser>("currentUser")
        curEmail.text = currentUser?.email
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun signOut(view: View) {
        FirebaseAuth.getInstance().signOut()
        curEmail.text = ""
        Toast.makeText(applicationContext, "Sign Out Successful", Toast.LENGTH_LONG).show()
    }

    fun submitChanges(view: View) {
        Toast.makeText(this,"Changes Submitted Successfully!", Toast.LENGTH_SHORT).show()
    }
}