package com.group2.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates

class RecipeFragment : AppCompatActivity() {

    private lateinit var database:DatabaseReference
    private lateinit var currentRecipeTitle: TextView
    private lateinit var currentRecipeDescription: TextView
    private lateinit var currentRecipeIngredients: TextView
    private lateinit var currentRecipeInstructions: TextView
    private lateinit var currentRecipeImage: ImageView

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_recipe)

        val id = intent.getStringExtra("id").toString()
        currentRecipeTitle = findViewById(R.id.recipeTitle)
        currentRecipeDescription = findViewById(R.id.recipeDescription)
        currentRecipeIngredients = findViewById(R.id.ingredients)
        currentRecipeInstructions = findViewById(R.id.instructions)
        currentRecipeImage = findViewById(R.id.currentImage)

        database = Firebase.database.reference

        database.child("recipes").child(id).get().addOnSuccessListener {
            if(it.value != null){
                val item = it.value as HashMap<String, Any>
                val title = item.get("title")
                val image = item.get("image")
                val description = item.get("summary")


                var instructions = item.get("instructions").toString()

                if (instructions == "null" || instructions == ""){
                    instructions = "No instructions available"
                }
                var ingredientsString = ""
                if(item.get("extendedIngredients").toString() != null){
                    val ingredients = item.get("extendedIngredients") as ArrayList<Any>


                    for(i in ingredients){
                        val test = i as HashMap<String, Any>
                        val lel = test.get("originalString")
                        ingredientsString = ingredientsString + System.getProperty ("line.separator") + lel.toString()
                    }

                }else {
                    ingredientsString = "No ingredients available "
                }

                currentRecipeTitle.text = title.toString()
                currentRecipeDescription.text = description.toString()
                currentRecipeInstructions.text = instructions
                currentRecipeIngredients.text = ingredientsString
                Picasso.get().load(image.toString()).resize(900, 600).centerCrop().into(currentRecipeImage)
            }
        }

        supportActionBar?.apply {
            title="Recipe Detail"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}