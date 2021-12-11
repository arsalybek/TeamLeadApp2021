package com.example.teamleadapp.view.customViews

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.teamleadapp.R

class ProgressDialog : DialogFragment() {

    var dimAmount: Float = 0.6F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val style = STYLE_NO_FRAME
        val theme = R.style.DialogTheme
        setStyle(style, theme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_progress_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun onStart() {
        super.onStart()

        val window: Window? = dialog?.window
        val params: WindowManager.LayoutParams? = window?.attributes
        params?.dimAmount = dimAmount
        window?.attributes = params
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}