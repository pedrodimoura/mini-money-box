package com.example.minimoneybox.features.account.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.minimoneybox.R
import com.example.minimoneybox.common.presentation.binding.viewBinding
import com.example.minimoneybox.databinding.FragmentAccountItemBinding
import com.example.minimoneybox.features.account.domain.model.Product
import java.text.NumberFormat
import java.util.*

private val diffUtil = object : DiffUtil.ItemCallback<Product>() {
    // simplified verification
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem.id == newItem.id

    // simplified verification
    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem == newItem
}

class AccountRecyclerViewAdapter :
    ListAdapter<Product, AccountRecyclerViewAdapter.AccountRecyclerViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_account_item, parent, false)
        return AccountRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountRecyclerViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class AccountRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val viewBinding: FragmentAccountItemBinding by viewBinding()

        fun bind(product: Product) {
            viewBinding.textViewProductName.text = product.productDetail.friendlyName
            viewBinding.textViewPlanValue.text =
                NumberFormat.getCurrencyInstance(Locale.UK).format(product.planValue)
            viewBinding.textViewMoneyboxValue.text =
                NumberFormat.getCurrencyInstance(Locale.UK).format(product.moneybox)
        }
    }
}