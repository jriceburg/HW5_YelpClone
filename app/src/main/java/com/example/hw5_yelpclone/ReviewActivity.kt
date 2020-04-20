package com.example.hw5_yelpclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_review.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReviewActivity : AppCompatActivity() {

    private val TAG = "ReviewActivity"
    private val BASE_URL = "https://api.yelp.com/v3/"
    private val API_KEY =
        "K06U41XThnXC4Z2YML7j_2BGx1ceL__GH-zyWdvQ4FvUX8bTUabmCd_tVkZTytXgnaY7xgCKDZ5WBoY0EvXVs2DvZ6pW0DEI_kEMm4FlkjVf4YuZ-Bf8hY94EnqZXnYx"

    private val Client_ID = "Yv-690SdYy2hTbkyo687wg"

    val reviews = ArrayList<ReviewInfo>()
    val reviewAdapter = ReviewAdapter(this,reviews)

    // Creating a Retrofit Instance
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    // Invoke API Call
    // create API call that will to call the interface
    val yelpUserAPI =retrofit.create(UserService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        // Access any extras passed in intent using methods like getIntExtra(), getStringExtra
        val receivedID       = intent.getStringExtra("reviewID")
        val businessName    = intent.getStringExtra("businessName")

        tv_review_title.text    = "$businessName Reviews"
        //tv_review_id.text       = receivedID?.toString()

        review_recyclerView.adapter = reviewAdapter

        // default vertical. the LayoutManager is responsible for how the items are shown
        review_recyclerView.layoutManager = LinearLayoutManager(this)
        // if you want, you can make the layout of the recyclerview horizontal as follows
        //recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        review_recyclerView.addItemDecoration(dividerItemDecoration)

        // full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()

        yelpUserAPI.getUserReviews("Bearer $API_KEY",receivedID).enqueue(object : Callback<ReviewSearchData>{
            override fun onFailure(call: Call<ReviewSearchData>, t: Throwable) {
                Log.d(TAG,": OnFailure $t")
            }
            override fun onResponse(
                call: Call<ReviewSearchData>, response: Response<ReviewSearchData>) {

                val body = response.body()
                if (body == null){
                    Log.w(TAG, "Valid response was not received")
                    return
                }
                reviews.addAll(body.reviews)
                reviewAdapter.notifyDataSetChanged()

            }


        })



    }


    fun finsh(view: View){
        finish()
    }
}
