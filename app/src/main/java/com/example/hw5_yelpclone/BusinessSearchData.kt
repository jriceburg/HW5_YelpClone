package com.example.hw5_yelpclone

import com.google.gson.annotations.SerializedName

// define our data class for the Business search information. this is the class we are going to
// define to parse the JSON data from the API we write out data based on the JSON data structure,
// we have to get to the nested information

data class BusinessSearchData (
    val businesses : List<BusinessData>
)

data class BusinessData(
    val rating  : Double, // Rating for this business (value ranges from 1, 1.5, ... 4.5, 5).
    @SerializedName("name") val businessName : String, // Name of this business.
    val review_count : Int, // Number of reviews for this business
    val price : String, // Price level of the business. Value is one of $, $$, $$$ and $$$$.
    val distance : Double, // Distance in meters from the search location. This returns meters regardless of the locale.
    val location : Locale, // Location of this business, including address, city, state, zip code and country.
    val id : String, // Unique Yelp ID of this business.
    val categories : List<Category>, // List of category title and alias pairs associated with this business.
    val image_url : String // URL of photo for this business.
)

data class Category(
    val alias : String,// Alias of a category, when searching for business in certain categories,
    // use alias rather than the title.
    val title : String // Title of a category for display purpose.
)

data class Locale(
    val address1 : String,  // Street address of this business.
    val address2 : String,  // Street address of this business, continued.
    val address3 : String,  // Street address of this business, continued.
    val city : String,      // City of this business.
    val country : String,   // country code of this business.
    val state : String,     // state code of this business.
    val zip_code: String    // Zip code of this business.
)





