package com.example.minimoneybox.ui

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.minimoneybox.R
import com.example.minimoneybox.common.presentation.binding.viewBinding
import com.example.minimoneybox.databinding.FragmentErrorBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.parcelize.Parcelize

class ErrorBottomSheet : BottomSheetDialogFragment() {

    var test: String = ""

    private val viewBinding: FragmentErrorBinding by viewBinding()
    private val args: ErrorBottomSheetArgs by lazy {
        requireArguments().getParcelable(ERROR_BOTTOM_SHEET_ARGS) ?: ErrorBottomSheetArgs("")
    }

    private val bottomSheetBehavior by lazy {
        BottomSheetBehavior.from(getBottomSheetRootView().parent as View)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MoneyboxBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_error, container, false)

    override fun onResume() {
        super.onResume()
        setDefaultPeekHeight()
        enableFullScreenMode()
        inflateErrorMessageOnUi()
        viewBinding.backImageButton.setOnClickListener { dismissAllowingStateLoss() }
    }

    private fun setDefaultPeekHeight() {
        bottomSheetBehavior.peekHeight = getMaxHeightForCurrentDisplay()
    }

    private fun inflateErrorMessageOnUi() {
        viewBinding.errorMessageTextView.text = args.message
    }

    private fun getBottomSheetRootView() = viewBinding.root

    private fun getMaxHeightForCurrentDisplay(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = requireActivity().windowManager.currentWindowMetrics
            val insets = windowMetrics
                .windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() and WindowInsets.Type.displayCutout())
            windowMetrics.bounds.height() - insets.top - insets.bottom
        } else {
            val displayMetrics = DisplayMetrics()
            requireActivity().display?.getRealMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }

    private fun enableFullScreenMode() {
        getBottomSheetRootView().layoutParams.height = getMaxHeightForCurrentDisplay()
    }
}

private const val ERROR_BOTTOM_SHEET_ARGS = "error-bs-args"

@Parcelize
data class ErrorBottomSheetArgs(
    val message: String,
) : Parcelable

fun Fragment.errorBottomSheet(
    errorBottomSheetArgs: ErrorBottomSheetArgs,
) = ErrorBottomSheet().apply {
    arguments = Bundle().apply { putParcelable(ERROR_BOTTOM_SHEET_ARGS, errorBottomSheetArgs) }
}

fun ComponentActivity.errorBottomSheet(
    errorBottomSheetArgs: ErrorBottomSheetArgs,
) = ErrorBottomSheet().apply {
    arguments = Bundle().apply { putParcelable(ERROR_BOTTOM_SHEET_ARGS, errorBottomSheetArgs) }
}
