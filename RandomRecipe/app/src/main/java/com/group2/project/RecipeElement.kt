package com.group2.project

data class RecipeElement(val title: String, val description: String?, val image:String){
    companion object{
        fun from(map: HashMap<String, String>) = object {
            val title by map
            val summary by map
            val image by map

            val data = RecipeElement(title, summary, image)
        }.data
    }
}