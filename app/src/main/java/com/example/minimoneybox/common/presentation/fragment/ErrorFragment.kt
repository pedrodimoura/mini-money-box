package com.example.minimoneybox.common.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.minimoneybox.R
import com.example.minimoneybox.common.presentation.binding.viewBinding
import com.example.minimoneybox.databinding.FragmentErrorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ErrorFragment : Fragment(R.layout.fragment_error) {

    private val viewBinding: FragmentErrorBinding by viewBinding()

    private val args: ErrorFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateErrorMessageOnUI()
    }

    private fun inflateErrorMessageOnUI() {
        viewBinding.errorMessageTextView.text = args.message
    }
}
