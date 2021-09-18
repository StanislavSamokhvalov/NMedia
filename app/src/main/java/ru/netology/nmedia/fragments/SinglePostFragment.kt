package ru.netology.nmedia.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.card_post.view.*
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentSinglePostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostViewModel
import ru.netology.nmedia.util.counterNumber

class SinglePostFragment : Fragment() {
    val viewModel: PostViewModel by viewModels(
            ownerProducer = ::requireParentFragment
    )
    var singlePost: Post? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSinglePostBinding.inflate(inflater, container, false)

        val id = arguments?.getLong("id")
        if (id == null) {
            Toast.makeText(context,
                    "Отсутствует id",
                    Toast.LENGTH_SHORT
            ).show()
            return binding.root
        }

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            posts.map { post ->
                if (post.id == id) {
                    singlePost = post
                    binding.singlepost.apply {
                        author.text = post.author
                        published.text = post.published
                        content.text = post.content
                        reply.text = counterNumber(post.replyAmount.toDouble())
                        views.text = counterNumber(post.viewsAmount.toDouble())
                        like.text = counterNumber(post.likes.toDouble())
                        like.isChecked = post.likedByMe
                        if (post.video != "") View.VISIBLE
                    }
                }
            }
        }

        binding.singlepost.like.setOnClickListener {
            singlePost?.id?.let { it -> viewModel.likeById(it) }
        }

        binding.singlepost.reply.setOnClickListener {
            singlePost?.let { it -> viewModel.replyById(it.id) }
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, singlePost?.content)
                type = "text/plain"
            }
            val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        binding.singlepost.menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.post_options)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.post_remove -> {
                            viewModel.removeById(id)
                            findNavController().navigate(R.id.action_singlePostFragment_to_feedFragment)
                            true
                        }
                        R.id.post_edit -> {
                            singlePost?.let { it -> viewModel.edit(it) }
                            val bundle = Bundle().apply { putString("content", singlePost?.content) }
                            findNavController().navigate(R.id.action_singlePostFragment_to_newPostFragment, bundle)
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }
        return binding.root
    }
}

