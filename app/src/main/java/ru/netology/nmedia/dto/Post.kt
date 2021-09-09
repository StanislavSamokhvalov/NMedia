package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val video: String,
    var views: Int = 0,
    var likes: Int = 0,
    var replyAmount: Int = 0,
    var viewsAmount: Int = 0,
    var likedByMe: Boolean = false
)