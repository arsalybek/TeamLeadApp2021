package com.example.teamleadapp.view.ui.empDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamleadapp.R
import com.example.teamleadapp.data.remote.models.EmployeeSkill
import com.example.teamleadapp.view.customViews.DeleteSkillAlertDialog
import com.example.teamleadapp.view.ui.activities.MainActivity
import com.example.teamleadapp.view.ui.empDetail.listeners.DeleteSkillDialogListener
import com.example.teamleadapp.view.ui.empDetail.listeners.IChangeListener
import kotlinx.android.synthetic.main.row_emp_detail_skill.view.*

class EmpDetailSkillAdapter(
    var skillList: List<EmployeeSkill>,
    var changeListener: IChangeListener
) :
    RecyclerView.Adapter<EmpDetailSkillAdapter.EmpSkillViewHolder>() {

    private val MAX_SCORE = "10"
    private val MIN_SCORE = "1"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpSkillViewHolder {
        return EmpSkillViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_emp_detail_skill, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EmpSkillViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = skillList.size


    inner class EmpSkillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        DeleteSkillDialogListener {
        fun bind(position: Int) {
            val skill = skillList[position]

            itemView.nameTextView.text = skill.name
            itemView.scoreTextView.text = skill.score.toString()

            itemView.scoreIncButton.setOnClickListener {
                if (itemView.scoreTextView.text.toString() != MAX_SCORE) {
                    val newScore: Int = itemView.scoreTextView.text.toString().toInt() + 1
                    itemView.scoreTextView.text = newScore.toString()
                    skill.score = newScore
                    changeListener.onRateChanged()
                }
            }
            val deleteSkillDialog = DeleteSkillAlertDialog.newInstance(this, position)
            itemView.scoreDecButton.setOnClickListener {
                if (itemView.scoreTextView.text.toString() != MIN_SCORE) {
                    val newScore: Int = itemView.scoreTextView.text.toString().toInt() - 1
                    itemView.scoreTextView.text = newScore.toString()
                    skill.score = newScore
                    changeListener.onRateChanged()
                } else {
                    deleteSkillDialog.showNow(MainActivity.fm, "delete")

                }
            }
        }

        override fun positiveClick(position: Int) {
            changeListener.onDeclineClicked(skillList[position])
        }
    }
}