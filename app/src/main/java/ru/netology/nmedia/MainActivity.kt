package ru.netology.nmedia


import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostCallback
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding


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
            }
        })

        binding.list?.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.save?.setOnClickListener {
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

                setText("")
                clearFocus()
            }
        }
    }
}