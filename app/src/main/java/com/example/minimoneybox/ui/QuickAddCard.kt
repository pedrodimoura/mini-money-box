package com.example.minimoneybox.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.minimoneybox.R
import com.google.android.material.card.MaterialCardView
import java.text.NumberFormat

class QuickAddCard @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
) : MaterialCardView(context, attrs) {

    private val rootLinearLayout: LinearLayoutCompat = LinearLayoutCompat(context, attrs)

    private val quickAddAmountTextView = TextView(context)
    private val quickAddTitleTextView = TextView(context).apply {
        text = resources.getString(R.string.quick_add_title)
    }
    private val numberFormat by lazy {
        NumberFormat.getCurrencyInstance(java.util.Locale.UK).apply {
            maximumFractionDigits = 0
        }
    }

    var amount: Int = 0
        set(value) {
            if (value != field) {
                setAmountOnTextView(value)
                field = value
            }
        }

    private var quickAddClick: ((Int) -> Unit)? = null

    init {
        getAttrs()
        setMaterialCardConfiguration()
        setRootLayoutConfiguration()
        setQuickAddAmountViewConfiguration()
        setQuickAddTitleViewConfiguration()
        inflateAttrsOnUI()
    }

    fun quickAddClick(quickAddClick: (Int) -> Unit) {
        this.quickAddClick = quickAddClick
    }

    private fun getAttrs() {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.QuickAddCard)
        amount = typedArray.getInteger(R.styleable.QuickAddCard_amount, amount)
        typedArray.recycle()
    }

    private fun setMaterialCardConfiguration() {
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        isClickable = true
        isFocusable = true
        setOnClickListener { quickAddClick?.invoke(amount) }
    }

    private fun setRootLayoutConfiguration() {
        val defaultPaddingToRoot = resources.getDimensionPixelSize(R.dimen.margin_default)
        val minHeight = resources.getDimensionPixelSize(R.dimen.quick_add_min_height)
        val minWidth = resources.getDimensionPixelSize(R.dimen.quick_add_min_width)

        val layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

        rootLinearLayout.minimumHeight = minHeight
        rootLinearLayout.minimumWidth = minWidth
        rootLinearLayout.orientation = LinearLayoutCompat.VERTICAL
        rootLinearLayout.layoutParams = layoutParams
        rootLinearLayout.gravity = Gravity.CENTER
        rootLinearLayout.setPadding(
            defaultPaddingToRoot,
            defaultPaddingToRoot,
            defaultPaddingToRoot,
            defaultPaddingToRoot
        )

        addView(rootLinearLayout)
    }

    private fun setQuickAddAmountViewConfiguration() {
        val layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

        quickAddAmountTextView.layoutParams = layoutParams
        quickAddAmountTextView.typeface = ResourcesCompat.getFont(context, R.font.montserrat_bold)
        quickAddAmountTextView.textSize = 18f
        quickAddAmountTextView.setTextColor(
            ContextCompat.getColor(context, R.color.aqua_dark_100)
        )

        rootLinearLayout.addView(quickAddAmountTextView)
    }

    private fun setQuickAddTitleViewConfiguration() {
        val layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

        quickAddTitleTextView.layoutParams = layoutParams
        quickAddTitleTextView.typeface = ResourcesCompat.getFont(context, R.font.montserrat_bold)
        quickAddTitleTextView.textSize = 12f
        quickAddTitleTextView.setTextColor(
            ContextCompat.getColor(context, R.color.aqua_dark_100)
        )

        rootLinearLayout.addView(quickAddTitleTextView)
    }

    private fun inflateAttrsOnUI() {
        setAmountOnTextView(amount)
    }

    private fun setAmountOnTextView(amount: Int) {
        quickAddAmountTextView.text = numberFormat.format(amount)
    }
}
