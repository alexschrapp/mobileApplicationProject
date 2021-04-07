package com.group2.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import kotlin.properties.Delegates

class RecipeFragment : AppCompatActivity() {


    private lateinit var currentRecipeTitle: TextView
    private lateinit var currentRecipeDescription: TextView
    private lateinit var currentRecipeIngredients: TextView
    private var id by Delegates.notNull<Long>()
    private var title: String? = ""
    private var ingredients: String? = ""
    private var description: String? = ""

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_recipe)


        val currentRecipe = intent.getLongExtra("currentRecipeId", 0)
        val currentTitle = intent.getStringExtra("currentTitle")
        val currentDescription = intent.getStringExtra("currentDescription")
        val currentInstructions = intent.getStringExtra("currentInstructions")
        val recipeString = currentRecipe.toString()
        id = currentRecipe
        title = currentTitle
        description= currentDescription
        ingredients= currentInstructions
        Log.d("test", recipeString)

        currentRecipeTitle = findViewById(R.id.recipeTitle)
        currentRecipeDescription = findViewById(R.id.recipeDescription)
        currentRecipeIngredients = findViewById(R.id.ingredients)

        supportActionBar?.apply {
            title="Recipe Detail"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val currentUser = intent.getParcelableExtra<FirebaseUser>("currentUser")
        currentRecipeTitle.text = id.toString()
        currentRecipeDescription.text = description
        currentRecipeIngredients.text = ingredients
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}