package ru.netology.nmedia

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fun counterNumber(amount: Double): String {
            var count = ""
            if (amount < 1000)
                count = String.format("%.0f", amount)
            else if (amount >= 1000 && amount < 10000)
                count = String.format("%.1f", (amount / 1000)) + "K"
            else if (amount >= 10000 && amount < 1000000)
                count = String.format("%.0f", (amount / 1000)) + "K"
            else if (amount >=  1000000 && amount < 1000000000)
                count = String.format("%.1f", (amount / 1000000)) + "M"
            return count

        }

        val post = Post(
                id = 1,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                published = "21 мая в 18:36",
                likedByMe = false
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content

            like?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                        if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likesamount?.text = counterNumber(post.likes.toDouble())
            }

            reply?.setOnClickListener {
                post.replyamount = post.replyamount + 100
                replyamount?.text = counterNumber(post.replyamount.toDouble())
            }

            views?.setOnClickListener {
                post.viewsamount = post.viewsamount + 100
                viewscount?.text = counterNumber(post.viewsamount.toDouble())
            }
        }


    }
}