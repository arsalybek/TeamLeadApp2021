package com.example.teamleadapp.view.ui.empList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.teamleadapp.R
import com.example.teamleadapp.data.remote.models.Employee
import com.example.teamleadapp.view.ui.activities.MainActivity
import com.example.teamleadapp.view.ui.empDetail.EmpDetailFragment
import kotlinx.android.synthetic.main.row_emp_card_data.view.*

class EmpListAdapter(var employeeList: List<Employee>) :
    RecyclerView.Adapter<EmpListAdapter.EmpListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpListViewHolder {
        return EmpListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_emp_card_data, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EmpListViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = employeeList.size


    inner class EmpListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            val employee = employeeList[position]

            Glide.with(itemView.context)
                .load(employee.image)
                .apply(RequestOptions().error(R.drawable.default_emp_img))
                .into(itemView.profileImageView)

            itemView.profileCard.setBackgroundResource(employee.getBackImage())
            itemView.nameTextView.text = employee.fullName
            itemView.positionTextView.text = employee.position
            if(employee.skills?.size!! >= 3) {
                itemView.skillLayout.visibility = View.VISIBLE
                itemView.skillRate1TextView.text = employee.skills?.get(0)?.score.toString()
                itemView.skillRate2TextView.text = employee.skills?.get(1)?.score.toString()
                itemView.skillRate3TextView.text = employee.skills?.get(2)?.score.toString()
                itemView.skillName1TextView.text = employee.skills?.get(0)?.name
                itemView.skillName2TextView.text = employee.skills?.get(1)?.name
                itemView.skillName3TextView.text = employee.skills?.get(2)?.name
            }

            itemView.setOnClickListener {
                MainActivity.fm.beginTransaction()
                    .replace(R.id.fragmentContainer, EmpDetailFragment.newInstance(employee)).addToBackStack(null)
                    .commit()
            }
        }
    }
}