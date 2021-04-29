package com.group2.project

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class registerFragment : AppCompatActivity() {

    private lateinit var inputName: EditText
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var inputAge: EditText
    private var newUser: FirebaseUser? = null
    private lateinit var inputDiet: Spinner
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register)

        database = Firebase.database.reference

        supportActionBar?.apply {
            title="Register"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("RestrictedApi")
    fun clickRegister(view: View) {
        inputName = findViewById(R.id.editNameEntry)
        inputEmail = findViewById(R.id.editEmailEntry)
        inputPassword = findViewById(R.id.EditPasswordEntry)
        inputAge = findViewById(R.id.editAgeEntry)
        inputDiet = findViewById(R.id.spinner)
        var passed = 0

        Log.d("Name:", inputName.text.toString())
        Log.d("Email:", inputEmail.text.toString())
        Log.d("Password:", inputPassword.text.toString())
        Log.d("Age:", inputAge.text.toString())
        Log.d("Diet:", inputDiet.getSelectedItem().toString())

        if(inputName.text.toString() == "") {
            Toast.makeText(
                baseContext, "Enter a valid name please",
                Toast.LENGTH_SHORT
            ).show()
            passed = 0;
        } else {
            passed += 1
        }

        if(inputEmail.text.toString() == "") {
            Toast.makeText(
                baseContext, "Enter a valid Email Please",
                Toast.LENGTH_SHORT
            ).show()
            passed = 0;
        } else { passed += 1 }

        if(inputPassword.text.toString() == "") {
            Toast.makeText(
                baseContext, "Enter a Password Please",
                Toast.LENGTH_SHORT
            ).show()
            passed = 0;
        } else { passed += 1 }

        if(inputAge.text.toString() == "") {
            Toast.makeText(
                baseContext, "Enter a valid number for age",
                Toast.LENGTH_SHORT
            ).show()
            passed = 0;
        } else { passed += 1 }

        if(passed == 4) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(inputEmail.text.toString(),inputPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "Registration Successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        newUser = FirebaseAuth.getInstance().currentUser
                        val User: Users = Users(inputName.text.toString(),
                        inputAge.text.toString(),
                            inputDiet.getSelectedItem().toString(),
                        "free")
                        database.child("users").child(FirebaseAuth.getInstance().currentUser.uid).setValue(User)
                        finish()
                    } else {
                        Toast.makeText(
                            baseContext, "Registration Failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}