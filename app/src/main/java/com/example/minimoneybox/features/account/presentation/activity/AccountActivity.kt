package com.example.minimoneybox.features.account.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import com.example.minimoneybox.R
import com.example.minimoneybox.common.presentation.binding.viewBinding
import com.example.minimoneybox.databinding.ActivityAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountActivity : AppCompatActivity(R.layout.activity_account) {

    private val viewBinding: ActivityAccountBinding by viewBinding(R.id.activityAccountsRoot)

    private val args: AccountActivityArgs by navArgs()

    override fun onResume() {
        super.onResume()
        findNavController(viewBinding.fragmentContainerViewAccountNavHost.id)
            .setGraph(R.navigation.account_nav_graph, args.toBundle())
    }

}
