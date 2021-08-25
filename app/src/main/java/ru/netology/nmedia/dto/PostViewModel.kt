package ru.netology.nmedia.dto

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImplementation

private val empty = Post(
        id = 0,
        author = "",
        content = "",
        published = "",
        likedByMe = false
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImplementation()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    fun save(){
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit (post: Post){
        edited.value = post
    }


    fun changeContent(content: String){
        edited.value?.let{
            val text = content.trim()
            if (it.content != text) {
                edited.value = it.copy(content = text)
            }
        }
    }

    fun cancelEditing() = edited.value?.let {
        repository.cancelEditing(it)
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun replyById(id: Long) = repository.replyById(id)
    fun viewsById(id: Long) = repository.viewsById(id)
    fun removeById(id: Long) = repository.removeById(id)

}