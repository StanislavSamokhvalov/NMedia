package ru.netology.nmedia.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositoryFileImpl(
        private val context: Context,
) : PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"
    private var nextId = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            try {
                context.openFileInput(filename).bufferedReader().use {
                    posts = gson.fromJson(it, type)
                    if (posts.isEmpty()) {
                        nextId = posts[0].id
                    } else nextId = posts[0].id + 1
                    data.value = posts
                }
            } catch (e: Exception) {
                val error = "$filename не может быть прочитан : ${e.message} >>> ${e.printStackTrace()} Создан пустой файл."
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                sync()
            }
        } else sync()
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                    likedByMe = !it.likedByMe,
                    likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
        sync()
    }

    override fun replyById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(replyAmount = it.replyAmount + 1)
        }
        data.value = posts
        sync()
    }

    override fun viewsById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(viewsAmount = it.viewsAmount + 1)
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            listOf(post.copy
            (id = nextId++, author = "Me", likedByMe = false, published = "now")) + posts
        } else posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }
}

