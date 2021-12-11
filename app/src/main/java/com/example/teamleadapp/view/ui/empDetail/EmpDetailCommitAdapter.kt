package com.example.teamleadapp.view.ui.empDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamleadapp.R
import com.example.teamleadapp.data.remote.models.GitHubCommitResponse
import kotlinx.android.synthetic.main.row_emp_detail_commit.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EmpDetailCommitAdapter(
    var commentList: List<GitHubCommitResponse>
) : RecyclerView.Adapter<EmpDetailCommitAdapter.EmpCommitViewHolder>() {

    val ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    val DATE_MONTH_FORMAT = "dd MMM HH:mm"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpCommitViewHolder {
        return EmpCommitViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_emp_detail_commit, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EmpCommitViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = commentList.size


    inner class EmpCommitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            val comment = commentList[position]

            itemView.commitTextView.text = comment.commit.commitMessage + "\n" + getDateString(
                getUnixDate(
                    comment.commit.committer.date,
                    ISO_DATE_TIME_FORMAT
                ),
                DATE_MONTH_FORMAT
            )
        }
    }

    fun getUnixDate(date: String?, pattern: String?): Long {
        return try {
            val format: DateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            format.parse(date).time
        } catch (e: Exception) {
            Date().time
        }
    }

    fun getDateString(date: Long, pattern: String?): String? {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
    }
}