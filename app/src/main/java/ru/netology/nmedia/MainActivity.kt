package ru.netology.nmedia

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                avatar
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likesamount.text = counterNumber(post.likes.toDouble())
                replyamount.text = counterNumber(post.replyAmount.toDouble())
                viewscount.text = counterNumber(post.viewsAmount.toDouble())

                like.setImageResource(
                        if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                )

                like.setOnClickListener {
                    viewModel.like()
                }

                reply.setOnClickListener {
                    viewModel.reply()
                }

                views.setOnClickListener {
                    viewModel.views()
                }
            }
        }
    }

    fun counterNumber(amount: Double): String {
        var count = ""
        if (amount < 1000)
            count = String.format("%.0f", amount)
        else if (amount >= 1000 && amount < 10000)
            count = String.format("%.1f", (amount / 1000)) + "K"
        else if (amount >= 10000 && amount < 1000000)
            count = String.format("%.0f", (amount / 1000)) + "K"
        else if (amount >= 1000000 && amount < 1000000000)
            count = String.format("%.1f", (amount / 1000000)) + "M"
        return count

    }
}