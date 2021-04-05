package com.group2.project


import RecipesFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class  MyAdapter (private val myDataset: ArrayList<RecipeElement>): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.Title)
        val description: TextView = itemView.findViewById(R.id.description)
        //val image: TextView = itemView.findViewById(R.id.imageView) //make this imageview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_element,parent,false)
        return MyViewHolder(myView)
    }

    override fun getItemCount() = myDataset.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //holder.message.text = myDataset[position]
        holder.title.text = myDataset.get(position).title
        holder.description.text = myDataset.get(position).description
        //holder.image.text = myDataset.get(position).image
    }
}