package com.group2.project


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class  MyAdapter(private val myDataset: ArrayList<RecipeElement>, private val context: Context): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    private var currentRecipe:Long? = null
    private var currentTitle:String? = null
    private var currentDescription:String? = null
    private var currentInstructions:String? = null
    var extras = Bundle()


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val title: TextView = itemView.findViewById(R.id.Title)
        val description: TextView = itemView.findViewById(R.id.description)
        //val image: TextView = itemView.findViewById(R.id.imageView) //make this imageview
        val showMore: Button = itemView.findViewById(R.id.showMore)
        var currentRecipeId:Long? = null
        var currentRecipeTitle:String? = null
        var currentRecipeDescription:String? = null
        var currentRecipeInstructions:String? = null

    }

    private fun onShowMoreClick() {

        showRecipe()



    }

    fun showRecipe(){
        extras?.putString("currentTitle", currentTitle);
        extras?.putString("currentDescription", currentDescription);
        extras?.putString("currentInstructions", currentInstructions);


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

        holder.currentRecipeId = myDataset.get(position).id
        holder.currentRecipeTitle = myDataset.get(position).title
        holder.currentRecipeDescription = myDataset.get(position).description



        currentRecipe = holder.currentRecipeId
        currentTitle = holder.currentRecipeTitle
        currentDescription = holder.currentRecipeDescription
        //currentInstructions = holder.currentRecipeInstructions
    }

}