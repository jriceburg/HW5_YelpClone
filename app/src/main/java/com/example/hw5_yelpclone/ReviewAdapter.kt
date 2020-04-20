package com.example.hw5_yelpclone

import android.content.Context
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.review_items.view.*


class ReviewAdapter (val context: Context, private val reviewDataList : List<ReviewInfo>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){

    private val TAG = "ReviewAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_items,parent,false)

        Log.d(TAG, "onCreateViewHolder: ")
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviewDataList.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val currentItem = reviewDataList[position]
        Log.d(TAG, "onBindViewHolder: $position")
        holder.bind(currentItem)

        // animations
        //holder.itemView.image_restaurant.animation = AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation)
        //holder.itemView.row_items.animation = AnimationUtils.loadAnimation(context,R.anim.fade_scale_amination)
    }


    inner class ReviewViewHolder(itermView : View) : RecyclerView.ViewHolder(itermView){

        fun bind(reviews: ReviewInfo){
            itemView.tv_reviewUser.text         = reviews.user.reviewerName
            itemView.tv_review_descripton.text  = reviews.reviewDescription
            itemView.tv_days_ago.text           = reviews.daysAgo()

            itemView.reviewBar.rating           = reviews.reviewerRating.toFloat()
            if(reviews.user.reviewerURL.isNotEmpty()){
                Picasso.get().load(reviews.user.reviewerURL)
                    .placeholder(R.drawable.ic_account_box_black_24dp)
                    .fit().centerCrop()
                    .into(itemView.review_image)
            }else{
                itemView.review_image.setImageResource(R.drawable.ic_account_box_black_24dp)
            }
            val urlLink = Html.fromHtml(reviews.reviewURL)
            itemView.tv_review_URL.movementMethod = LinkMovementMethod.getInstance()
            itemView.tv_review_URL.text = urlLink

            //        = reviews.reviewURL


        }



    }


}