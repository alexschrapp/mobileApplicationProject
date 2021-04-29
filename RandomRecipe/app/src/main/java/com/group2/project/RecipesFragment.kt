import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.group2.project.MyAdapter
import com.group2.project.R
import com.group2.project.RecipeElement
import com.group2.project.RecipeFragment
import com.group2.project.Users.Companion.from


class RecipesFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var recipes: ArrayList<RecipeElement>
    private lateinit var rcRecipeList: RecyclerView
    private lateinit var showMore: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        database = Firebase.database.reference
        recipes = arrayListOf()

        database.child("recipes").get().addOnSuccessListener {
            if(it.value != null){
                val recipesFromDB = it.value as ArrayList<Any>
                recipes.clear()
                var key = 0
                for(item in recipesFromDB){

                    val recipeFromDB = item as HashMap<String, Any>
                    val title = recipeFromDB.get("title").toString()
                    val image = recipeFromDB.get("image").toString()
                    val id = recipeFromDB.get("id").toString().toLong()
                    val description = recipeFromDB.get("summary").toString()
                    var instructions = ""
                    if(recipeFromDB.get("instructions").toString() != null){
                        instructions = recipeFromDB.get("instructions").toString()
                    }else{
                        instructions = "No instructions available"
                    }

                    val recipe = RecipeElement(title, description, image, key, instructions, "0")

                    recipes.add(recipe)
                    key += 1
                }
                rcRecipeList.adapter?.notifyDataSetChanged()
            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_recipes, container, false)


    companion object {
        fun newInstance(): RecipesFragment = RecipesFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcRecipeList = view!!.findViewById(R.id.recipeList)
        rcRecipeList.layoutManager = LinearLayoutManager(context)
        rcRecipeList.adapter = MyAdapter(recipes)

    }


}