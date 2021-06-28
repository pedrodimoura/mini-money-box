package com.example.minimoneybox.common.presentation.validation

import android.util.Patterns
import java.util.regex.Pattern
import javax.inject.Inject

class EmailValidator @Inject constructor() : Validator<String> {

    override fun validate(t: String): Boolean =
        Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), t)

}
