package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding

interface PostCallback {
    fun onLikeListener(post: Post)
    fun onReplyListener(post: Post)
    fun onViewListener(post: Post)
}

class PostsAdapter(private val PostCallback: PostCallback) :
        ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, PostCallback)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(private val binding: CardPostBinding,
                     private val postCallback: PostCallback) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
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
                postCallback.onLikeListener(post)
            }

            reply.setOnClickListener {
                postCallback.onReplyListener(post)
            }

            views.setOnClickListener {
                postCallback.onViewListener(post)
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

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}


