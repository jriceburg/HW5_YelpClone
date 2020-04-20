package com.example.hw5_yelpclone

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://api.yelp.com/v3/"
    private val TAG = "MainActivity"
    private val API_KEY =
        "K06U41XThnXC4Z2YML7j_2BGx1ceL__GH-zyWdvQ4FvUX8bTUabmCd_tVkZTytXgnaY7xgCKDZ5WBoY0EvXVs2DvZ6pW0DEI_kEMm4FlkjVf4YuZ-Bf8hY94EnqZXnYx"

    private val Client_ID = "Yv-690SdYy2hTbkyo687wg"

    val restaurants = ArrayList<BusinessData>()
    val adapter = MyRecyclerAdapter(this,restaurants)

    // Creating a Retrofit Instance
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    // Invoke API Call
    // create API call that will to call the interface
    val yelpUserAPI =retrofit.create(UserService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.adapter = adapter

        // default vertical. the LayoutManager is responsible for how the items are shown
        recycler_view.layoutManager = LinearLayoutManager(this)
        // if you want, you can make the layout of the recyclerview horizontal as follows
        //recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recycler_view.addItemDecoration(dividerItemDecoration)

        // full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()

    }

    fun userSearch(view: View){

        restaurants.clear()
        adapter.notifyDataSetChanged()

        val foodTerm    = et_food_search.text.toString()
        val location    = et_location_search.text.toString()
        val radius      = et_radius.text.toString()

        Log.d(TAG,": Food search: $foodTerm, Location search: $location")

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Search term missing")
        dialog.setMessage("Search term & location cannot be empty. Please enter a search term & location")
        // Set an icon, optional
        dialog.setIcon(android.R.drawable.ic_delete)
        dialog.setNeutralButton("Okay") { dialog, which ->
            // code to run when Cancel is pressed
        }

        if(et_food_search.text.isBlank() || et_location_search.text.isBlank()){
            val dialogBox = dialog.create()
            dialogBox.show()
        }
        et_location_search.hideKeyboard()
        // Invoke API Call to endpoint
        if(radius.isBlank()){
            // user did not enter a radius input
            termLocationSearch( foodTerm,  location)
        }else{
            // user did enter a radius input
            val toMeter = 1609
            val radiusMiles = radius.toInt()*toMeter
            termLocationRadiusSearch(foodTerm,location,radiusMiles)
        }
    }

    private fun termLocationSearch( term :String,  location : String){
        //yelpUserAPI.searchRestraunts("Bearer $API_KEY","pizza","new britain")
        yelpUserAPI.searchRestraunts("Bearer $API_KEY",term,location)
            .enqueue(object : Callback<BusinessSearchData>{
                override fun onFailure(call: Call<BusinessSearchData>, t: Throwable) {
                    Log.d(TAG,": OnFailure $t")
                }
                override fun onResponse(call: Call<BusinessSearchData>,
                                        response: Response<BusinessSearchData>) {
                    Log.d(TAG,": OnResponse $response")
                    // do something with the data

                    val body = response.body()
                    if (body == null){
                        Log.w(TAG, "Valid response was not received")
                        return
                    }
                    restaurants.addAll(body.businesses)
                    adapter.notifyDataSetChanged()
                }
            })
    }

    private fun termLocationRadiusSearch( term :String,  location : String, radius : Int){
        yelpUserAPI.searchRestrauntsWitRadius("Bearer $API_KEY",term,location,radius )
            .enqueue(object : Callback<BusinessSearchData>{
                override fun onFailure(call: Call<BusinessSearchData>, t: Throwable) {
                    Log.d(TAG,": OnFailure $t")
                }
                override fun onResponse(call: Call<BusinessSearchData>,
                                        response: Response<BusinessSearchData>) {
                    Log.d(TAG,": OnResponse $response")
                    // do something with the data

                    val body = response.body()
                    if (body == null){
                        Log.w(TAG, "Valid response was not received")
                        return
                    }
                    restaurants.addAll(body.businesses)
                    adapter.notifyDataSetChanged()
                }
            })
    }

    private fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }



    /**
    // A helper function to create specified amount of dummy data
    private fun createData(size : Int): ArrayList<RestaurantInfo>{
        for (i in 0..size){
            val store = RestaurantInfo("Pizzaria",89,4.2,
                13.75,"1985 Wilson Road, Vermont","Gastro Pub",
                R.drawable.ic_free_breakfast_black_24dp,"$$")
            restaurants.add(store)
        }
        return restaurants
    }
    */
}
