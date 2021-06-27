package com.example.minimoneybox.features.account.presentation.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.minimoneybox.R

class AccountItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val defaultMargin = parent.context.resources.getDimensionPixelSize(R.dimen.margin_default)
        val defaultHalfMargin =
            parent.context.resources.getDimensionPixelSize(R.dimen.margin_default_half)

        val viewPosition = parent.getChildAdapterPosition(view)

        val topMargin = if (viewPosition == 0) defaultMargin else defaultHalfMargin

        val bottomMargin = if (viewPosition == (state.itemCount - 1))
            defaultMargin
        else defaultHalfMargin

        outRect.set(defaultMargin, topMargin, defaultMargin, bottomMargin)
    }

}