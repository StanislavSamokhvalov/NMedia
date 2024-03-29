package ru.netology.nmedia.repository

import androidx.lifecycle.Transformations
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity


class PostRepositoryImpl(
    private val dao: PostDao,
) : PostRepository {
    override fun getAll() = Transformations.map(dao.getAll()) { list ->
        list.map {
            Post(
                it.id,
                it.author,
                it.content,
                it.published,
                it.video,
                it.views,
                it.likes,
                it.replyAmount,
                it.viewsAmount,
                it.likedByMe
            )
        }
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun replyById(id: Long) {
        dao.shareById(id)
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }
}

