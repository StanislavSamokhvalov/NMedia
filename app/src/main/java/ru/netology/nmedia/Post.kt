package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 0,
    var replyAmount: Int = 0,
    var viewsAmount: Int = 0,
    var likedByMe: Boolean = false
)