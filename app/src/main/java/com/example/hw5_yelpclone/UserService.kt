package com.example.hw5_yelpclone

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


// here we define our endpoints, we need to first look at the API documentation
// base URL: https://api.yelp.com/v3/

interface UserService {

    /**
    /businesses/search
    This endpoint returns up to 1000 businesses based on the provided search criteria. It has some
    basic information about the business. To get detailed information and reviews, please use the
    Business ID returned here and refer to /businesses/{id} and /businesses/{id}/reviews endpoints.
     */

    /**
    Parameters:
    term: Optional.Search term, for example "food" or "restaurants". The term may also be business names,
    such as "Starbucks". If term is not included the endpoint will default to searching across
    businesses from a small number of popular categories.

    location: Required if either latitude or longitude is not provided. This string indicates the
    geographic area to be used when searching for businesses. Examples: "New York City", "NYC",
    "350 5th Ave, New York, NY 10118". Businesses returned in the response may not be strictly
    within the specified location.
     */

    // https://api.yelp.com/v3/businesses/search
    @GET("businesses/search")
    fun searchRestraunts(@Header("Authorization") authHeader: String
                         , @Query("term") searchTerm: String,
                         @Query("location") location: String) : Call<Any>

    @GET("businesses/search")
    fun searchRestrauntsWitRadius(@Header("Authorization") authHeader: String
                                  , @Query("term") searchTerm: String
                                  ,@Query("location") location: String
                                  ,@Query("radius") radius: Int) : Call<Any>

    @GET("businesses/search")
    fun searchRestrauntsWitCat(@Header("Authorization") authHeader: String
                               , @Query("term") searchTerm: String
                               ,@Query("location") location: String
                               ,@Query("categories") categories: String) : Call<Any>

    // add price, and open now @GET calls later

    /**
    // https://api.yelp.com/v3/businesses/{id}/reviews
    @GET("businesses/{id}/reviews")
    fun getReviews(@Header("Authorization") authHeader: String
                         , @Query("id") reviewID: String) : Call<Any>
    */

}