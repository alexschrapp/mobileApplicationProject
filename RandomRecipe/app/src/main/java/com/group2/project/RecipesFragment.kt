import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class RecipesFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var recipes: ArrayList<RecipeElement>
    private lateinit var rcRecipeList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        database = Firebase.database.reference
        recipes = arrayListOf<RecipeElement>()

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
        database.addValueEventListener(recipeListener)
        rcRecipeList.layoutManager = LinearLayoutManager(context)
        rcRecipeList.adapter = MyAdapter(recipes)

    }


    val recipeListener = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            if(snapshot.value != null){
                val recipesFromFirebase =
                    (snapshot.value as HashMap<*, ArrayList<RecipeElement>>).get("recipes")
                recipes.clear()

                if(recipesFromFirebase != null){
                    for (i in 0..recipesFromFirebase.size-1){
                        if(recipesFromFirebase.get(i) != null){
                            val recipe: RecipeElement = RecipeElement.from(recipesFromFirebase.get(i) as HashMap<String, String>)
                            recipes.add(recipe)
                        }
                    }
                }

                rcRecipeList.adapter?.notifyDataSetChanged()
                rcRecipeList.smoothScrollToPosition(rcRecipeList.adapter!!.itemCount - 1)
            }
        }
    }
}