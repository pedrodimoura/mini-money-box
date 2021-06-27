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
import com.example.minimoneybox.features.account.domain.model.Account
import java.text.NumberFormat
import java.util.*

private val diffUtil = object : DiffUtil.ItemCallback<Account>() {
    // simplified verification
    override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean =
        oldItem == newItem

    // simplified verification
    override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean =
        oldItem == newItem
}

class AccountRecyclerViewAdapter :
    ListAdapter<Account, AccountRecyclerViewAdapter.AccountRecyclerViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_account_item, parent, false)
        return AccountRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountRecyclerViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class AccountRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val viewBinding: FragmentAccountItemBinding by viewBinding()

        fun bind(account: Account) {
            viewBinding.textViewProductName.text = account.name
            viewBinding.textViewPlanValue.text =
                NumberFormat.getCurrencyInstance(Locale.UK).format(account.totalValue)
            viewBinding.textViewMoneyboxValue.text =
                NumberFormat.getCurrencyInstance(Locale.UK).format(account.totalContributions)
        }
    }
}