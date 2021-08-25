package ru.netology.nmedia.activity


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostCallback
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostViewModel
import ru.netology.nmedia.util.AndroidUtils


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter(object : PostCallback {
            override fun like(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun reply(post: Post) {
                viewModel.replyById(post.id)
            }

            override fun view(post: Post) {
                viewModel.viewsById(post.id)
            }

            override fun remove(post: Post) {
                viewModel.removeById(post.id)
                groupCancelEdit.visibility = View.GONE
            }

            override fun edit(post: Post) {
                binding.groupCancelEdit.visibility = View.VISIBLE
                viewModel.edit(post)
            }
        })

        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L){
                return@observe
            }

            binding.content.setText(post.content)
            binding.content.requestFocus()
        }

        binding.save.setOnClickListener {
            with (binding.content) {
                if (text.isNullOrEmpty()) {
                    Toast.makeText(
                        this@MainActivity,
                            R.string.error_empty_content,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(text.toString())
                viewModel.save()
                binding.groupCancelEdit.visibility = View.GONE
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(it)
            }
        }


        binding.cancel.setOnClickListener {
            with(binding.content) {
                setText("")
                clearFocus()
            }
            binding.groupCancelEdit.visibility = View.GONE
            AndroidUtils.hideKeyboard(it)
    }

        binding.cancel.setOnClickListener {
            viewModel.cancelEditing()
            binding.content.setText("")
            groupCancelEdit.visibility = View.GONE
            AndroidUtils.hideKeyboard(it)
        }
}
}