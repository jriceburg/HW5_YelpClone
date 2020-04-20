package com.example.hw5_yelpclone

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale
import java.util.concurrent.TimeUnit

data class ReviewSearchData(
    val reviews : List<ReviewInfo>
)

data class ReviewInfo(
    @SerializedName("rating")   val reviewerRating : Int,
    @SerializedName("text")     val reviewDescription : String,
    @SerializedName("url")      val reviewURL : String,
    val time_created : String,
    val user : Reviewer
){
    fun daysAgo() : String{
        // "time_created": "2016-08-10 07:56:44"
        var sdf2: SimpleDateFormat? = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val current = sdf2!!.parse(LocalDateTime.now().toString())
        val reviewDateNew = sdf2!!.parse(time_created)
        val diffInMillies = Math.abs(reviewDateNew.time - current.time)
        val diff: Long = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
        return "$diff days ago"
    }
}

data class Reviewer(
    @SerializedName("profile_url")  val reviewerURL : String,
    @SerializedName("name")         val reviewerName : String
)








