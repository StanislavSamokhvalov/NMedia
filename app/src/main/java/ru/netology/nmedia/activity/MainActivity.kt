package ru.netology.nmedia.activity


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_post.*
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostCallback
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }
            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()) {
                Snackbar.make(binding.root, R.string.error_empty_content, LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok) {
                        finish()
                    }
                    .show()
                return@let
            }
            newPostLauncher.launch(text)
        }

        val adapter = PostsAdapter(object : PostCallback {
            override fun like(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun share(post: Post) {
                viewModel.replyById(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)

            }

            override fun view(post: Post) {
                viewModel.viewsById(post.id)
            }

            override fun remove(post: Post) {
                viewModel.removeById(post.id)
                //groupCancelEdit.visibility = View.GONE
            }

            override fun edit(post: Post) {
                viewModel.edit(post)
            }

            override fun video(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                val videoIntent = Intent.createChooser(intent, getString(R.string.chooser_video_player))
                startActivity(videoIntent)
            }
        })


        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) return@observe
            newPostLauncher.launch(post.content)

        //    binding.content.setText(post.content)
        //    binding.content.requestFocus()
        }
//
//        binding.save.setOnClickListener {
//            with(binding.content) {
//                if (text.isNullOrEmpty()) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        R.string.error_empty_content,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnClickListener
//                }
//                viewModel.changeContent(text.toString())
//                viewModel.save()
//                binding.groupCancelEdit.visibility = View.GONE
//                setText("")
//                clearFocus()
//                AndroidUtils.hideKeyboard(it)
//            }
//        }


//        binding.cancel.setOnClickListener {
//            with(binding.content) {
//                setText("")
//                clearFocus()
//            }
//            viewModel.cancelEditing()
//            binding.groupCancelEdit.visibility = View.GONE
//            AndroidUtils.hideKeyboard(it)
//        }

        binding.fab.setOnClickListener {
            newPostLauncher.launch("")
        }

    }
}