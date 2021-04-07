package com.group2.project

data class RecipeElement(val title: String, val description: String?, val image:String, val id: Long){
    companion object{
        fun from(map: HashMap<String, Any>) = object {
            val title by map
            val summary by map
            val image by map
            val id by map

            val data = RecipeElement(title as String, summary as String, image as String, id as Long)
        }.data
    }
}