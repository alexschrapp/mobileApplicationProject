package com.group2.project

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SettingsFragment : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var curEmail: TextView
    private lateinit var curAge: TextView
    private lateinit var curName: TextView
    private lateinit var curSpinner: Spinner
    private lateinit var submitChanges: Button
    private lateinit var spinnerText: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_settings)
        database = Firebase.database.reference

        curEmail = findViewById(R.id.emailField)
        curAge = findViewById(R.id.editAge)
        curSpinner = findViewById(R.id.SettingsSpinner)
        curName = findViewById(R.id.editPersonName)

        supportActionBar?.apply {
            title="Settings"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        val currentUser = intent.getParcelableExtra<FirebaseUser>("currentUser")
        curEmail.text = currentUser?.email

        database.child("users").child(currentUser!!.uid).child("name").get().addOnSuccessListener {
            Log.d("IN SUCCESS LISTENER", "Got value ${it.value}")
            curName.text = it.value.toString()
        }.addOnFailureListener{
            Log.d("IN FAIL LISTENER", "Error getting data", it)
        }

        database.child("users").child(currentUser!!.uid).child("age").get().addOnSuccessListener {
            Log.d("IN SUCCESS LISTENER", "Got value ${it.value}")
            curAge.text = it.value.toString()
        }.addOnFailureListener{
            Log.d("IN FAIL LISTENER", "Error getting data", it)
        }

        database.child("users").child(currentUser!!.uid).child("diet").get().addOnSuccessListener {
            Log.d("IN SUCCESS LISTENER", "Got value ${it.value}")
            spinnerText = getResources().getStringArray(R.array.entries_array)
            curSpinner.setSelection(spinnerText.indexOf(it.value.toString()))
        }.addOnFailureListener{
            Log.d("IN FAIL LISTENER", "Error getting data", it)
        }
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
        //THE VALUES TAKEN, IF NOT CHANGED ARE THE ONES TAKEN IN THE ONCREATE FROM THE DATABASE
        Log.d("Name:", curName.text.toString())
        Log.d("Email:", curEmail.text.toString())
        Log.d("Age:", curAge.text.toString())
        Log.d("Diet:", curSpinner.getSelectedItem().toString())

        //IMPLEMENT CHANGING DATA IN THE DATABASE
        if(curName.text.toString() != "") {
            database.child("users").child(FirebaseAuth.getInstance().currentUser.uid).child("name").setValue(curName.text.toString())
        } else {
            Toast.makeText(baseContext, "Null is not a valid Name", Toast.LENGTH_SHORT).show()
        }
        if(curAge.text.toString() != "") {
            database.child("users").child(FirebaseAuth.getInstance().currentUser.uid).child("age").setValue(curAge.text.toString())
        } else {
            Toast.makeText(baseContext, "Null is not a valid Age", Toast.LENGTH_SHORT).show()
        }
        database.child("users").child(FirebaseAuth.getInstance().currentUser.uid).child("diet").setValue(curSpinner.getSelectedItem().toString())
    }
}