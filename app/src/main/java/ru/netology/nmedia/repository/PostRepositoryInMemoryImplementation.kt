package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class PostRepositoryInMemoryImplementation : PostRepository {

    private var posts = listOf(
            Post(
                    id = 1,
                    author = "Нетология. Университет интернет-профессий будущего",
                    content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                    published = "21 мая в 18:36",
                    likedByMe = false,
                    likes = 999,
                    replyAmount = 999,
                    viewsAmount = 999
            ),
            Post(
                    id = 2,
                    author = "Нетология. Университет интернет-профессий будущего",
                    content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                    published = "21 мая в 18:36",
                    likedByMe = false,
                    likes = 111,
                    replyAmount = 111,
                    viewsAmount = 111
            ),
            Post(
                    id = 3,
                    author = "Нетология. Университет интернет-профессий будущего",
                    content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                    published = "21 мая в 18:36",
                    likedByMe = false,
                    likes = 666,
                    replyAmount = 666,
                    viewsAmount = 666
            ),
            Post(
                    id = 4,
                    author = "Нетология. Университет интернет-профессий будущего",
                    content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                    published = "21 мая в 18:36",
                    likedByMe = false,
                    likes = 0,
                    replyAmount = 0,
                    viewsAmount = 0
            )
    )


    private val data = MutableLiveData(posts)

    override fun get(): LiveData<List<Post>> = data

    override fun like(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                    likedByMe = !it.likedByMe,
                    likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun reply(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(replyAmount = it.replyAmount + 1)
        }
        data.value = posts
    }

    override fun views(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(viewsAmount = it.viewsAmount + 1)
        }
        data.value = posts
    }
}