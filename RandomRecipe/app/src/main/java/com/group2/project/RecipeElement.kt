package com.group2.project
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.*


data class RecipeElement(val title: String, val description: String?, val image:String, val id: Int, val instructions:String, val ingredients:String){
}