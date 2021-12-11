package com.example.teamleadapp.view.customViews

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.teamleadapp.R
import kotlinx.android.synthetic.main.view_drop_down.view.*

class DropDownView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var onClick: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.view_drop_down, this)

        editTextInput.setOnClickListener {
            if (alpha == 1.0F) {
                onClick?.invoke()
            }
        }
    }

    fun setEnable(enable: Boolean) {
        alpha = if (enable) {
            1.0F
        } else {
            0.4F
        }
        isEnabled = enable
    }

    fun setTitle(title: String?) {
        setDefault()
        editTextInput.setText(title)
    }

    fun setPlaceholder(placeholder: String) {
        textInputLayout.hint = placeholder
    }

    fun clear() {
        editTextInput.text = null
    }

    fun setDefault() {
        textInputLayout.hintTextColor =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.txt_subtl))
        textInputLayout.defaultHintTextColor =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.txt_subtl))
        editTextInput.setHintTextColor(ContextCompat.getColor(context, R.color.txt_subtl))
        editTextInput.setTextColor(ContextCompat.getColor(context, R.color.txt_subtl))
    }


    fun showProgress() {
        imageViewArrowDown.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        imageViewArrowDown.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }

    fun getPlaceHolder(): String = textInputLayout.hint.toString()

    fun getTitle(): String = editTextInput.text.toString()
}