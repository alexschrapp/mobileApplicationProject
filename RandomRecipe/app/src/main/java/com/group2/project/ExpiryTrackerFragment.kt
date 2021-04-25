package com.group2.project
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
import androidx.core.view.get
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds
import kotlin.time.seconds

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
    var dateMilliSec = 0.0

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
                var id = 0

                    for (items in expFromDB){
                        val eFromDB = items as HashMap<String, Any>
                        val title = eFromDB.get("name").toString()
                        val date = eFromDB.get("expiryDate").toString()
                        val dateMilli = eFromDB.get("dateMilli").toString().toDouble()
                        val expiryTing = ExpiryElement(title, date, id, dateMilli)

                        expiry.add(expiryTing)
                        id +=1
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

    @ExperimentalTime
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
        val expiryDB:ExpiryElement= ExpiryElement(input, date, 0, dateMilliSec)
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

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalTime
    private fun changeDate() {
        var month = datePicker.month.toInt() +1
        var day = datePicker.dayOfMonth.toInt()

        if(month <10){
            var monthwithzero = "0" + month
            if(day <10) {

                var daywithzero = "0"+ day
                date =
                    datePicker.year.toString() + "-" + monthwithzero.toString() + "-" + daywithzero.toString()
            }else{
                date =
                    datePicker.year.toString() + "-" + monthwithzero.toString() + "-" + day.toString()
            }
        }else {
            if(day <10) {

                var daywithzero = "0"+ day
                date =
                    datePicker.year.toString() + "-" + month.toString() + "-" + daywithzero.toString()
            }
            date = datePicker.year.toString() + "-" + month.toString() + "-" + datePicker.dayOfMonth.toString()
        }

        val realDate = Date(datePicker.year.toString().toInt(), datePicker.month.toInt(), datePicker.dayOfMonth.toInt())
        val ah = realDate.time
        dateMilliSec = (ah - 59958144000000).toDouble()

        val fuk = LocalDate.now()
        val dateee = Date(LocalDate.now().year.toString().toInt(), LocalDate.now().monthValue.toString().toInt(), LocalDate.now().dayOfMonth.toString().toInt())
        val llall = dateee.time - 59958144000000
        val test = LocalDateTime.now()
        Log.d("date", date.toString())
        Log.d("dateee", realDate.toString())




        var lel =1
    }

}


