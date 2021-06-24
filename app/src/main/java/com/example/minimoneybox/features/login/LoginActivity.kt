package com.example.minimoneybox.features.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.minimoneybox.R
import com.example.minimoneybox.common.presentation.binding.viewBinding
import com.example.minimoneybox.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A login screen that offers login via email/password.
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val viewBinding: ActivityLoginBinding by viewBinding(R.id.activityMainRoot)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
