package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int,
    var replyAmount: Int,
    var viewsAmount: Int,
    var likedByMe: Boolean = false
)