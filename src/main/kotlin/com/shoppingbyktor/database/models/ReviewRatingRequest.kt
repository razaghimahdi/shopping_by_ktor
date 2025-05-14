package com.shoppingbyktor.database.models

data class ReviewRatingRequest(val productId:Long, val reviewText:String, val rating:Int)
