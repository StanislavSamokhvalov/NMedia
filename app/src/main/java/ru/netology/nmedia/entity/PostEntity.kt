package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
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
) {
    fun toDto() = Post(id, author, content, published, video, likes, replyAmount, viewsAmount, views, likedByMe)

    companion object {
        fun fromDto(post: Post) =
            PostEntity(
                post.id,
                post.author,
                post.content,
                post.published,
                post.video,
                post.views,
                post.likes,
                post.replyAmount,
                post.viewsAmount,
                post.likedByMe
            )
    }
}
