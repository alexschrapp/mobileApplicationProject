import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.group2.project.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class HomeFragment : Fragment() {

    private lateinit var recipes: ArrayList<RecipeElement>

    private lateinit var database: DatabaseReference
    private lateinit var expiry: ArrayList<ExpiryElement>
    private lateinit var rcExpiryList: RecyclerView
    private var currentUser: FirebaseUser? = null
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater!!.inflate(R.layout.fragment_home, container, false)

        val randomRecipeButton = view.findViewById<Button>(R.id.button)

        recipes = arrayListOf()
        database = Firebase.database.reference

        randomRecipeButton.setOnClickListener{ showRecipe() }

        auth = Firebase.auth
        database = Firebase.database.reference
        expiry = arrayListOf()
        currentUser = auth.currentUser
        val id = currentUser!!.uid
        val lol =1
        database.child("expirydata").child(currentUser!!.uid).child("expiryItems").get().addOnSuccessListener {
            Log.d("test", "${it.value}")

            if (it.value != null) {
                val expFromDB = it.value as ArrayList<Any>
                expiry.clear()

                for (items in expFromDB){
                    val eFromDB = items as HashMap<String, Any>
                    val title = eFromDB.get("name").toString()
                    val date = eFromDB.get("expiryDate").toString()

                    val expiryTing = ExpiryElement(title, date)
                    expiry.add(expiryTing)
                }

                expiry.sortBy { it.expiryDate }





                rcExpiryList.adapter?.notifyDataSetChanged()
            }


        }.addOnFailureListener{Log.d("test", "Error")}
        return view




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcExpiryList = view!!.findViewById(R.id.expiryList)
        rcExpiryList.layoutManager = LinearLayoutManager(context)
        rcExpiryList.adapter = AdapterExpiry(expiry)

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