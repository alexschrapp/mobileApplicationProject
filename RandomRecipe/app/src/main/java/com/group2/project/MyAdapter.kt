package com.group2.project


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class  MyAdapter(
    private val myDataset: ArrayList<RecipeElement>
): RecyclerView.Adapter<MyAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val myView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_element, parent, false)

        return ViewHolder(myView)
    }

    override fun getItemCount() = myDataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = myDataset[position]
        holder.titleView.text = item.title
        holder.descriptionView.text = item.description

        Picasso.get().load(item.image).resize(350, 700).centerCrop().into(holder.imageView)
        holder.button.tag = item.id
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val titleView:TextView = view.findViewById(R.id.Title)
        val descriptionView:TextView = view.findViewById(R.id.date)

        val imageView:ImageView = view.findViewById(R.id.imageView)
        val button:Button = view.findViewById(R.id.showMore)

    }

}