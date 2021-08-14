package ru.netology.nmedia

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImplementation

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImplementation()
    val data = repository.get()
    fun like(id: Long) = repository.like(id)
    fun reply(id: Long) = repository.reply(id)
    fun views(id: Long) = repository.views(id)
}