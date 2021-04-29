package com.group2.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class  AdapterExpiry(
    private val myDataset: ArrayList<ExpiryElement>
): RecyclerView.Adapter<AdapterExpiry.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val myView = LayoutInflater.from(parent.context)
            .inflate(R.layout.expiry_element, parent, false)

        return ViewHolder(myView)
    }

    override fun getItemCount() = myDataset.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val titleView:TextView = view.findViewById(R.id.Title)
        val dateView:TextView = view.findViewById(R.id.date)
        val button: Button = view.findViewById(R.id.delete)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = myDataset[position]
        holder.titleView.text = item.name
        holder.dateView.text = item.expiryDate
        holder.button.tag = item.id


    }


}