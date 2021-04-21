package com.group2.project

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar
    private lateinit var expiry: ArrayList<ExpiryElement>
    private val TAG: String = MainActivity::class.java.name
    private lateinit var auth: FirebaseAuth
    private var currentUser: FirebaseUser? = null
    private lateinit var database: DatabaseReference


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database.reference
        auth = Firebase.auth
        expiry = arrayListOf()
        currentUser = auth.currentUser

        database.child("expirydata").child(currentUser!!.uid).child("expiryItems").get().addOnSuccessListener {
            Log.d("test", "${it.value}")

            if (it.value != null) {
                val expFromDB = it.value as ArrayList<Any>
                expiry.clear()
                var id = 0

                for (items in expFromDB){
                    val eFromDB = items as HashMap<String, Any>
                    val title = eFromDB.get("name").toString()
                    val date = eFromDB.get("expiryDate").toString()
                    val expiryTing = ExpiryElement(title, date, id)

                    expiry.add(expiryTing)
                    id +=1
                }


            }


        }.addOnFailureListener{Log.d("test", "Error")}


        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        supportActionBar?.apply {
            title="Home"
        }


        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val homeFragment = HomeFragment.newInstance()
        openFragment(homeFragment)



    }



    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_expirytracker -> {
                    toolbar.title = "Expiry Tracker"
                    val songsFragment = ExpiryTrackerFragment.newInstance()
                    openFragment(songsFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_home -> {
                    toolbar.title = "Home"
                    val homeFragment = HomeFragment.newInstance()
                    openFragment(homeFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_recipes -> {
                    toolbar.title = "Recipes"
                    val recipesFragment = RecipesFragment.newInstance()
                    openFragment(recipesFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_top_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.action_profile -> {
            showProfile()
            true
        }
        R.id.action_settings -> {
            showSettings()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun showProfile() {
        val intent = Intent(this, ProfileFragment::class.java).apply {
            putExtra("currentUser",currentUser)
        }
        this?.startActivity(intent)
    }

    fun showSettings() {
        val intent = Intent(this, SettingsFragment::class.java).apply {
            putExtra("currentUser",currentUser)
        }
        this?.startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        currentUser = auth.currentUser
        if (currentUser == null) loginDialog()
    }

    fun loginDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setCancelable(false)

        with(builder) {
            setTitle("Login")
            val linearLayout: LinearLayout = LinearLayout(this@MainActivity)
            linearLayout.orientation = LinearLayout.VERTICAL

            val inputEmail: EditText = EditText(this@MainActivity)
            inputEmail.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            inputEmail.hint = "Enter email"
            linearLayout.addView(inputEmail)

            val inputPw: EditText = EditText(this@MainActivity)
            inputPw.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_PASSWORD
            inputPw.hint = "Enter password"
            linearLayout.addView(inputPw)
            builder.setView(linearLayout)

            builder.setNegativeButton("Register") { dialog, which ->
                register()
            }

            builder.setPositiveButton("OK") { dialog, which ->
                login(inputEmail.text.toString(), inputPw.text.toString())
            }.show()

        }
    }

    fun register() {
        val intent = Intent(this, registerFragment::class.java)
        this?.startActivity(intent)
    }



    fun login(email: String, password: String) {


        if (email != "" && password != "") {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        Toast.makeText(
                            baseContext, "Authentification successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        currentUser = auth.currentUser
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentification failed",
                            Toast.LENGTH_SHORT
                        ).show()
                        loginDialog()
                    }
                }

        }else{
            Toast.makeText(
                baseContext, "Please enter credentials",
                Toast.LENGTH_SHORT
            ).show()
            loginDialog()
        }
    }

    fun seeMore(view: View) {
        val id = view.tag.toString()
        val intent = Intent(this, RecipeFragment::class.java).apply {
            putExtra("id", view.tag.toString())
        }
        startActivity(intent)
    }

    fun delete(view: View) {


        val id2 = view.tag.toString().toInt()
        expiry.removeAt(id2)

        database.child("expirydata").child(FirebaseAuth.getInstance().currentUser.uid).child("expiryItems").setValue(expiry)
        Toast.makeText(
            this, "Item successfully deleted",
            Toast.LENGTH_SHORT
        ).show()
    }
}