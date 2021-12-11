package com.example.teamleadapp.view.customViews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.teamleadapp.R
import com.example.teamleadapp.view.ui.empDetail.listeners.DeleteSkillDialogListener
import kotlinx.android.synthetic.main.delete_skill_dialog.view.*

class DeleteSkillAlertDialog : DialogFragment() {

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

        val view = inflater.inflate(R.layout.delete_skill_dialog, container, false)
        view.acceptButton.setOnClickListener {
            arguments?.getInt(
                KEY_POSITION
            )?.let { it1 ->
                (arguments?.getSerializable(KEY_LISTENER) as DeleteSkillDialogListener).positiveClick(
                    it1
                )
            }
            dismiss()
        }
        view.cancelButton.setOnClickListener {
            dismiss()
        }

        return view
    }

    companion object {
        const val KEY_LISTENER = "KEY_LISTENER"
        const val KEY_POSITION = "KEY_POSITION"

        fun newInstance(
            listener: DeleteSkillDialogListener,
            position: Int
        ): DeleteSkillAlertDialog {
            val dialog = DeleteSkillAlertDialog()

            val args = Bundle()
            args.putSerializable(KEY_LISTENER, listener)
            args.putInt(KEY_POSITION, position)
            dialog.arguments = args

            return dialog
        }
    }
}