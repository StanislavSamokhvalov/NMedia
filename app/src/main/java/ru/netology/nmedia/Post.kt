package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 999,
    var replyamount: Int = 900,
    var viewsamount: Int = 9000,
    var likedByMe: Boolean = false
)