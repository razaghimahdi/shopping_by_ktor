package com.shoppingbyktor.database.models.comment

data class CommentReviewRequest(val product_id:Long, val comment:String, val rate:Double)
