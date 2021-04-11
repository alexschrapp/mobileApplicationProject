package com.group2.project
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.*

@Parcelize
data class RecipeElement(val title: String, val description: String?, val image:String, val id: Long) :
    Parcelable {
    companion object{
        fun from(map: HashMap<String, Any>) = object {
            val title by map
            val summary by map
            val image by map
            val id by map
            //val instructions by map

            val data = RecipeElement(title as String, summary as String, image as String, id as Long)
        }.data
    }
}