import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class ExpiryTrackerFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var expiry: ArrayList<ExpiryElement>
    private lateinit var rcExpiryList: RecyclerView
    private var currentUser: FirebaseUser? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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





                rcExpiryList.adapter?.notifyDataSetChanged()
            }


        }.addOnFailureListener{Log.d("test", "Error")}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_expiry, container, false)

    companion object {
        fun newInstance(): ExpiryTrackerFragment = ExpiryTrackerFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcExpiryList = view!!.findViewById(R.id.expiryList)
        rcExpiryList.layoutManager = LinearLayoutManager(context)
        rcExpiryList.adapter = AdapterExpiry(expiry)

    }

    }


