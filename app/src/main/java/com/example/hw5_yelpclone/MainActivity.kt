package com.example.hw5_yelpclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val restaurants = ArrayList<RestaurantInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recylcler_view.adapter = MyRecyclerAdapter(createData(10))

        // default vertical. the LayoutManager is responsible for how the items are shown
        recylcler_view.layoutManager = LinearLayoutManager(this)
        // if you want, you can make the layout of the recyclerview horizontal as follows
        //recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        /*
        You can decorate the items using various decorators attached to the recyclerview
        such as the DividerItemDecoration:
         */

        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recylcler_view.addItemDecoration(dividerItemDecoration)

    }

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
}
