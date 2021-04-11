package com.group2.project


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class  MyAdapter(private val myDataset: ArrayList<RecipeElement>, private val context: Context): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    private var currentRecipe:Long? = null
    private var currentImage:String? = null
    private var currentTitle:String? = null
    private var currentDescription:String? = null
    private var currentIngredients:String? = null
    private var currentInstructions:String? = null
    var extras = Bundle()




    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val title: TextView = itemView.findViewById(R.id.Title)
        val description: TextView = itemView.findViewById(R.id.description)
        val image: ImageView = itemView.findViewById(R.id.imageView) //make this imageview
        val showMore: Button = itemView.findViewById(R.id.showMore)
        var currentRecipeId:Long? = null
        var currentRecipeImage:String? = null
        var currentRecipeTitle:String? = null
        var currentRecipeDescription:String? = null
        var currentRecipeIngredients:String? = null
        var currentRecipeInstructions:String? = null

    }

    private fun onShowMoreClick() {

        showRecipe()



    }

    fun showRecipe(){
        extras?.putString("currentTitle", currentTitle);
        extras?.putString("currentDescription", currentDescription);
        extras?.putString("currentIngredients", currentIngredients);
        extras?.putString("currentInstructions", currentInstructions);
        extras?.putString("currentImage", currentImage);


        val intent = Intent(context, RecipeFragment::class.java).apply {


            //putExtra("currentTitle", currentTitle)
            //putExtra("currentDescription", currentDescription)
            //putExtra("currentInstructions", currentInstructions)
        }
        intent.putExtras(extras)
        context.startActivity(intent)
        Log.d("test", "test")
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_element, parent, false)

        return MyViewHolder(myView)
    }

    override fun getItemCount() = myDataset.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //holder.message.text = myDataset[position]
        holder.title.text = myDataset.get(position).title
        holder.description.text = myDataset.get(position).description
        //holder.image.text = myDataset.get(position).image
        holder.showMore.setOnClickListener{ onShowMoreClick() }
        Picasso.get().load(myDataset.get(position).image).resize(350, 700).centerCrop().into(holder.image);
        holder.currentRecipeId = myDataset.get(position).id
        holder.currentRecipeImage = myDataset.get(position).image
        holder.currentRecipeTitle = myDataset.get(position).title
        holder.currentRecipeDescription = myDataset.get(position).description
        //holder.currentRecipeInstructions = myDataset.get(position).instructions
        //holder.currentRecipeIngredients = myDataset.get(position).ingredients


        currentImage = holder.currentRecipeImage
        currentRecipe = holder.currentRecipeId
        currentTitle = holder.currentRecipeTitle
        currentDescription = holder.currentRecipeDescription
        currentIngredients = "200g Spaghetti "+System.getProperty ("line.separator")+"500g Minced Meat"+System.getProperty ("line.separator")+" 3 Tomatoes"+System.getProperty ("line.separator")+" 1 Onion"
        currentInstructions = "1. Cut the onions & tomatoes" + System.getProperty ("line.separator") +" 2. Fry the minced meat"
    //currentInstructions = holder.currentRecipeInstructions
    }

}