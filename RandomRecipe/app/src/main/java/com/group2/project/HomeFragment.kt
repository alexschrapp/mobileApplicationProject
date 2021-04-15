import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.group2.project.R
import com.group2.project.RecipeElement
import com.group2.project.RecipeFragment
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class HomeFragment : Fragment() {

    private lateinit var recipes: ArrayList<RecipeElement>

    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater!!.inflate(R.layout.fragment_home, container, false)

        val randomRecipeButton = view.findViewById<Button>(R.id.button)
        val item1TextView = view.findViewById<TextView>(R.id.item1Text)
        val item1Date = view.findViewById<TextView>(R.id.item1Date)
        val item2TextView = view.findViewById<TextView>(R.id.item2Text)
        val item2Date = view.findViewById<TextView>(R.id.item2Date)
        val item3TextView = view.findViewById<TextView>(R.id.item3Text)
        val item3Date = view.findViewById<TextView>(R.id.item3Date)
        recipes = arrayListOf()
        database = Firebase.database.reference

        item1TextView.text = "Milk"
        item1Date.text = "14/5/2020"
        item2TextView.text = "Margaret Hamilton"
        item2Date.text = "N/A"
        item3TextView.text = "Sosij"
        item3Date.text = "Bruh Bruh"
        randomRecipeButton.setOnClickListener{ showRecipe() }
        return view


    }


    fun showRecipe(){
        val random: String = Random().nextInt(50).toString()
        //val randomRecipeTitle:String = recipes.get(random).title
        //val randomRecipeDescription:String? = recipes.get(random).description
        //val randomRecipeImage:String? = recipes.get(random).image
        //val currentIngredients = "200g Spaghetti "+System.getProperty ("line.separator")+"500g Minced Meat"+System.getProperty ("line.separator")+" 3 Tomatoes"+System.getProperty ("line.separator")+" 1 Onion"
        //val currentInstructions = "1. Cut the onions & tomatoes" + System.getProperty ("line.separator") +" 2. Fry the minced meat"


        //v.context.startActivity(Intent(v.context, RecipeFragment::class.java))
        val intent = Intent(context, RecipeFragment::class.java).apply {
            putExtra("id", random)
        }
        startActivity(intent)

    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}