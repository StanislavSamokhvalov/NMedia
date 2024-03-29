package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.counterNumber

interface PostCallback {
    fun like(post: Post)
    fun share(post: Post)
//    fun view(post: Post)
    fun remove(post: Post)
    fun edit(post: Post)
    fun video(post: Post)
    fun post(post: Post)
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
            reply.text = counterNumber(post.replyAmount.toDouble())
            views.text = counterNumber(post.viewsAmount.toDouble())
            like.text = counterNumber(post.likes.toDouble())
            like.isChecked = post.likedByMe

            if (post.video != "") View.VISIBLE

            content.setOnClickListener{
                postCallback.post(post)
            }

            like.setOnClickListener {
                postCallback.like(post)
            }

            reply.setOnClickListener {
                postCallback.share(post)
            }

            video.setOnClickListener {
                postCallback.video(post)
            }

//            views.setOnClickListener {
//                postCallback.view(post)
//            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_options)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.post_remove -> {
                                postCallback.remove(post)
                                true
                            }
                            R.id.post_edit -> {
                                postCallback.edit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}


