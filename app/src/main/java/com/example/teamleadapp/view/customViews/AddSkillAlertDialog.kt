package com.example.teamleadapp.view.customViews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import com.example.teamleadapp.R
import com.example.teamleadapp.view.ui.empDetail.listeners.AddSkillDialogListener
import kotlinx.android.synthetic.main.add_skill_dialog.*
import kotlinx.android.synthetic.main.add_skill_dialog.view.*

class AddSkillAlertDialog : DialogFragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.DialogTheme)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.add_skill_dialog, container, false)
        val listener =
            (arguments?.getSerializable(DeleteSkillAlertDialog.KEY_LISTENER) as AddSkillDialogListener)

        view.addButton.isEnabled = true
        view.addButton.setOnClickListener {
            listener.positiveClick(skillInputView.text.toString())
            dismiss()
        }
        view.closeButton.setOnClickListener {
            dismiss()
        }
        view.skillInputView.doAfterTextChanged {
            addButton.isEnabled = it.toString().trim().isNotEmpty()
        }

        return view
    }

    companion object {
        const val KEY_LISTENER = "KEY_LISTENER"

        fun newInstance(listener: AddSkillDialogListener): AddSkillAlertDialog {
            val dialog = AddSkillAlertDialog()
            val args = Bundle()
            args.putSerializable(KEY_LISTENER, listener)
            dialog.arguments = args

            return dialog
        }
    }
}