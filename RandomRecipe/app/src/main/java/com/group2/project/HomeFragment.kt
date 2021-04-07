import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.group2.project.R
import com.group2.project.RecipeFragment


class HomeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_home, container, false)

        val randomRecipeButton = view.findViewById<Button>(R.id.button)
        val item1TextView = view.findViewById<TextView>(R.id.item1Text)
        val item1Date = view.findViewById<TextView>(R.id.item1Date)
        val item2TextView = view.findViewById<TextView>(R.id.item2Text)
        val item2Date = view.findViewById<TextView>(R.id.item2Date)
        val item3TextView = view.findViewById<TextView>(R.id.item3Text)
        val item3Date = view.findViewById<TextView>(R.id.item3Date)
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
        //v.context.startActivity(Intent(v.context, RecipeFragment::class.java))
        val intent = Intent(context, RecipeFragment::class.java)
        this.startActivity(intent)
        Log.d("test", "test")
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}