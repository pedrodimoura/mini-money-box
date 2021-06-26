package com.example.minimoneybox.features.account.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.minimoneybox.R
import com.example.minimoneybox.common.presentation.binding.viewBinding
import com.example.minimoneybox.databinding.ActivityAccountsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountsActivity : AppCompatActivity(R.layout.activity_accounts) {

    private val viewBinding: ActivityAccountsBinding by viewBinding(R.id.activityAccountsRoot)

    private val args: AccountsActivityArgs by navArgs()

    override fun onResume() {
        super.onResume()
        val string = viewBinding.accountsTextView.text
        viewBinding.accountsTextView.text = "$string: ${args.name}"
    }

}
