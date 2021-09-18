package ru.netology.nmedia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.dto.PostViewModel
import ru.netology.nmedia.util.AndroidUtils

class NewPostFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(
                ownerProducer = ::requireParentFragment
        )

        val content = arguments?.getString("content")

        binding.edit.setText(content)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            val context = binding.edit.text.toString()
            viewModel.changeContent(context)
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        return binding.root
    }
}