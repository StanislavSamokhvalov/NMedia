package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImplementation : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
            Post(
                    id = nextId++,
                    author = "Нетология. Университет интернет-профессий будущего",
                    content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                    published = "21 мая в 18:36",
                    likedByMe = false,
                    likes = 999,
                    replyAmount = 999,
                    viewsAmount = 999,
                    views = 222,
                    video = "https://www.youtube.com/watch?v=T385OriasaA"
            ),
            Post(
                    id = nextId++,
                    author = "Нетология. Университет интернет-профессий будущего",
                    content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                    published = "10 июня в 19:03",
                    likedByMe = false,
                    likes = 111,
                    replyAmount = 111,
                    viewsAmount = 111,
                    views = 45452,
                    video = "https://www.youtube.com/watch?v=T385OriasaA"
            ),
            Post(
                    id = nextId++,
                    author = "Нетология. Университет интернет-профессий будущего",
                    content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                    published = "19 июня в 13:21",
                    likedByMe = false,
                    likes = 666,
                    replyAmount = 666,
                    viewsAmount = 666,
                    views = 875552,
                    video = "https://www.youtube.com/watch?v=T385OriasaA"
            ),
            Post(
                    id = nextId++,
                    author = "Нетология. Университет интернет-профессий будущего",
                    content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                    published = "27 июня в 13:56",
                    likedByMe = false,
                    likes = 0,
                    replyAmount = 0,
                    viewsAmount = 0,
                    views = 0,
                    video = ""
            )
    ).reversed()

    private val data = MutableLiveData(posts)


    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                    likedByMe = !it.likedByMe,
                    likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun replyById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(replyAmount = it.replyAmount + 1)
        }
        data.value = posts
    }

    override fun viewsById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(viewsAmount = it.viewsAmount + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            listOf(post.copy(id = nextId++)) + posts
        } else posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts

    }

    override fun cancelEditing(post: Post) {
        posts.map { if (it.id != post.id) it}
    }
}