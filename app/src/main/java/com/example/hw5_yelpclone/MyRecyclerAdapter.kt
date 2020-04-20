package com.example.hw5_yelpclone

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_review.view.*
import kotlinx.android.synthetic.main.row_items.view.*



class MyRecyclerAdapter(val context: Context, private val restaurantList: ArrayList<BusinessData>)
    : RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>() {

    var count = 0
    private val TAG = "MyRecyclerAdapter"




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // this is a method to inflate a layout from our xml (row_item.xml) and drop that inside
        // a ViewHolder (returns the holder) similar to how we inflate a fragment. we have to
        // use this method to create the views

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
        Log.d(TAG, "onBindViewHolder: $position")
        holder.bind(currentItem)

        // animations
        holder.itemView.image_restaurant.animation = AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation)
        holder.itemView.row_items.animation = AnimationUtils.loadAnimation(context,R.anim.fade_scale_amination)



    }
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


        fun bind(business: BusinessData){
            itemView.tv_Business_Name.text  = business.businessName
            itemView.tv_num_reviews.text    = "${business.review_count} Reviews"
            itemView.tv_address.text        = business.location.address1.plus(", ${business.location.city}")
            itemView.tv_price.text          = business.price
            itemView.ratingBar.rating       = business.rating.toFloat()
            itemView.tv_cat1.text           = business.categories[0].title
            itemView.tv_distance.text       = business.displayDistance()

            if(business.image_url.isNotEmpty()){
                Picasso.get().load(business.image_url)
                    .placeholder(R.drawable.ic_free_breakfast_black_24dp)
                    .fit().centerCrop()
                    .into(itemView.image_restaurant)
            }else{
                itemView.image_restaurant.setImageResource(R.drawable.ic_free_breakfast_black_24dp)
            }
        }

            // Set onClickListener to show a toast message for the selected row item in the list
            init {

                itemView.setOnClickListener{
                    val selectedItem = adapterPosition
                    Toast.makeText(itemView.context, "You clicked on ${restaurantList[selectedItem].businessName}",
                        Toast.LENGTH_SHORT).show()

                    val sendID      =  restaurantList[selectedItem].id
                    val sendName    =  restaurantList[selectedItem].businessName

                    //Create an Intent object with two parameters: 1) context, 2) class of the activity to launch
                    val myIntent = Intent(context, ReviewActivity::class.java)
                    myIntent.putExtra("businessName", sendName)
                    myIntent.putExtra("reviewID", sendID)
                    Log.d(TAG,": sending Name : $sendName. Review ID $sendID")
                    context.startActivity(myIntent)

                }

                /**
                // Set onLongClickListener to show a toast message and launch second activity
                itemView.setOnLongClickListener {
                    val selectedItem = adapterPosition
                    Toast.makeText(itemView.context, "Opening review for: ${itemView.tv_Business_Name.text}",
                        Toast.LENGTH_SHORT).show()
                    Log.d(TAG,": sending: $sendName")

                    // Start the new Activity with startActivity()
                    //context.startActivity(myIntent)



                    return@setOnLongClickListener true
                }

                */
            }

    }
}










