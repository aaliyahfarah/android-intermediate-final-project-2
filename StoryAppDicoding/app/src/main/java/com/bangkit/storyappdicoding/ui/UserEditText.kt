package com.bangkit.storyappdicoding.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.bangkit.storyappdicoding.R
import com.bangkit.storyappdicoding.utils.UserUtils

class UserEditText : AppCompatEditText {
    private lateinit var validBackground: Drawable
    private lateinit var invalidBackground: Drawable
    private var isError = false

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        background = if(isError) invalidBackground else validBackground
    }

    private fun init() {
        isError = false
        validBackground = ContextCompat.getDrawable(context, R.drawable.bg_auth_input_24) as Drawable
        invalidBackground = ContextCompat.getDrawable(context, R.drawable.bg_auth_invalid_24) as Drawable

        background = validBackground

        validation(inputType)
    }

    private fun validation(inputType: Int) {
        val message: String
        val validator: (String) -> Boolean

        if(inputType - 1 == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            message = context.getString(R.string.invalid_password)
            validator = { it.length >= 8 }
        } else if(inputType - 1 == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) {
            message = context.getString(R.string.invalid_email)
            validator = { UserUtils.isEmailValid(it) }
        } else if(inputType - 1 == InputType.TYPE_TEXT_VARIATION_PERSON_NAME) {
            message = context.getString(R.string.invalid_name)
            validator = { it.length >= 3 }
        } else return

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                isError = !validator(s.toString())
                error = if(isError) message else null
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}