package com.group2.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates

class RecipeFragment : AppCompatActivity() {


    private lateinit var currentRecipeTitle: TextView
    private lateinit var currentRecipeDescription: TextView
    private lateinit var currentRecipeIngredients: TextView
    private lateinit var currentRecipeInstructions: TextView
    private lateinit var currentRecipeImage: ImageView
    private var id by Delegates.notNull<Long>()
    private var title: String? = ""
    private var ingredients: String? = ""
    private var description: String? = ""
    private var instructions: String? = ""
    private var image: String? = ""

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_recipe)


        val currentRecipe = intent.getLongExtra("currentRecipeId", 0)
        val currentTitle = intent.getStringExtra("currentTitle")
        val currentImage = intent.getStringExtra("currentImage")
        val currentDescription = intent.getStringExtra("currentDescription")
        val currentIngredients = intent.getStringExtra("currentIngredients")
        val currentInstructions = intent.getStringExtra("currentInstructions")
        val recipeString = currentRecipe.toString()
        id = currentRecipe
        title = currentTitle
        description= currentDescription
        ingredients= currentIngredients
        instructions = currentInstructions
        image = currentImage
        Log.d("test", recipeString)

        currentRecipeTitle = findViewById(R.id.recipeTitle)
        currentRecipeDescription = findViewById(R.id.recipeDescription)
        currentRecipeIngredients = findViewById(R.id.ingredients)
        currentRecipeInstructions = findViewById(R.id.instructions)
        currentRecipeImage = findViewById(R.id.currentImage)

        supportActionBar?.apply {
            title="Recipe Detail"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val currentUser = intent.getParcelableExtra<FirebaseUser>("currentUser")
        currentRecipeTitle.text = title
        currentRecipeDescription.text = description
        currentRecipeIngredients.text = ingredients
        currentRecipeInstructions.text = instructions
        Picasso.get().load(image).resize(900, 600).centerCrop().into(currentRecipeImage);
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}