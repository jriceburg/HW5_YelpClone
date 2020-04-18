package com.example.hw5_yelpclone

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_items.view.*

class MyRecyclerAdapter(private val restaurantList: ArrayList<RestaurantInfo>)
    : RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>() {

    var count = 0
    private val TAG = "MsyRecyclerAdapter"

    // the ViewHolder class is required for any class that extends the RecyclerView class
    // e.i MyRecyclerAdapter has to have an inner ViewHolder class

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // the ViewHolder is where we produce the views for each of the custom row item layouts
        // we need to tell the adapter that we want to load the row_items.xml, and the adapter
        // will take care of the conversion for the RecyclerView
        // there are 8 views in our custom layout (row_items.xml file). this class needs to find
        // these items, then "catch" them and reuse them

        // we find the items by adding a constructor, they indicate that these are the
        // itemviews we want to use. thus we add the item view and use it as a reference

        // itemView represents one row, we use this data to initialize our variables
        // each MyViewHolder object keeps a reference to the 8 views in our row_item3.xml file
        val businessName    = itemView.tv_Business_Name
        val numReviews      = itemView.tv_num_reviews
        val ratingBar       = itemView.ratingBar
        val distance        = itemView.tv_distance
        val address         = itemView.tv_address
        val category        = itemView.tv_cat1
        val businessImg   = itemView.image_restaurant
        val price           = itemView.tv_price

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // this is a method to inflate a layout from our xml (row_item.xml) and returns the holder
        // similar to how we inflate a fragment. we have to use this method to create the views

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_items,parent,false)

        Log.d(TAG, "onCreateViewHolder: ${count++}")

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        // tells us how many rows will be in the list
        return restaurantList.size

    }
    //
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // get element from the restaurant list at this position replace the contents of the view
        // with that element items are binded. once you have the view, we have to add the data at the given position
        // get element from your dataset at this position, replace the contents of the view with that element
        /*  The layout manager binds the view holder to its data. It does this by calling the
            adapter's onBindViewHolder() method, and passing the view holder's position in the RecyclerView.
            The onBindViewHolder() method needs to fetch the appropriate data, and use it to fill
            in the view holder's layout. For example, if the RecyclerView is displaying a list of
            names, the method might find the appropriate name in the list, and fill in the view holder's TextView widget.
         */

        val currentItem = restaurantList[position]
        // we want to dynamically change the content
        holder.businessName.text    = currentItem.businessName
        holder.numReviews.text      = currentItem.numReviews.toString().plus(" Reviews  ")
        //holder.ratingBar.rating     = currentItem.ratingBar.toString()
        holder.distance.text        = currentItem.distance.toString().plus(" mi")
        holder.address.text         = currentItem.address
        holder.category.text        = currentItem.category
        holder.price.text           = currentItem.price
        holder.businessImg.setImageResource(currentItem.restaurantImg)

        Log.d(TAG, "onBindViewHolder: $position")


    }


}










