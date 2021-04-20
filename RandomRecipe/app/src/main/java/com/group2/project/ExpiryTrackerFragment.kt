import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
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

class ExpiryTrackerFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var expiry: ArrayList<ExpiryElement>
    private lateinit var rcExpiryList: RecyclerView
    private var currentUser: FirebaseUser? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var datePicker: DatePicker
    private lateinit var expiryInput:EditText
    private lateinit var expiryButton:Button
    var date = ""
    var input =""

    @RequiresApi(Build.VERSION_CODES.O)
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
                        /*if(expiry.size <1) {
                            expiry.add(expiryTing)
                        }else{

                            for (i in 0..expiry.size-1){

                            }

                        }*/
                    }
                expiry.sortBy { it.expiryDate }


                rcExpiryList.adapter?.notifyDataSetChanged()
            }


        }.addOnFailureListener{Log.d("test", "Error")}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_expiry, container, false)

    companion object {
        fun newInstance(): ExpiryTrackerFragment = ExpiryTrackerFragment()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datePicker = view!!.findViewById<DatePicker>(R.id.datePicker)
        datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->  changeDate()}

        expiryInput = view!!.findViewById(R.id.expiryInput)
        expiryButton = view!!.findViewById(R.id.button4)
        expiryButton.setOnClickListener{submitToDatabase()}
        rcExpiryList = view!!.findViewById(R.id.expiryList)
        rcExpiryList.layoutManager = LinearLayoutManager(context)
        rcExpiryList.adapter = AdapterExpiry(expiry)



        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH), { view, year, monthOfYear, dayOfMonth ->  changeDate()}

        )


    }

    private fun submitToDatabase(){

        input = expiryInput.text.toString()
        val expiryDB:ExpiryElement= ExpiryElement(input, date)
        var expiryPush: ArrayList<ExpiryElement>
        expiryPush = arrayListOf()



        if(input == "" || date == ""){
            Toast.makeText(
                context, "Enter item and date please",
                Toast.LENGTH_SHORT
            ).show()
        }
        if(input != "" && date != ""){
            expiry.add(expiryDB)
            database.child("expirydata").child(FirebaseAuth.getInstance().currentUser.uid).child("expiryItems").setValue(expiry)
            Toast.makeText(
                context, "Expiry date successfully added",
                Toast.LENGTH_SHORT
            ).show()
        }




    }

    private fun changeDate() {
        var month = datePicker.month.toInt() +1
        if(month <10){
            var monthwithzero = "0"+month
            date = datePicker.year.toString() + "-" + monthwithzero.toString()+"-"+ datePicker.dayOfMonth.toString()
        }else {
            date = datePicker.year.toString() + "-" + month.toString() + "-" + datePicker.dayOfMonth.toString()
        }
        Log.d("test", date.toString())
        var lel =1
    }

}


