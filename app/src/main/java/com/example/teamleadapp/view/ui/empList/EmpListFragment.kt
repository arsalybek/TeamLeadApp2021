package com.example.teamleadapp.view.ui.empList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.teamleadapp.R
import com.example.teamleadapp.view.ui.newEmp.NewEmpFragment
import kotlinx.android.synthetic.main.fragment_emp_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class EmpListFragment : Fragment(R.layout.fragment_emp_list) {

    private val viewModel: EmpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    private fun bindViews() {
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        LinearSnapHelper().attachToRecyclerView(recyclerView)
        recyclerView.adapter = EmpListAdapter(viewModel.getEmpList())
        pagerIndicator.attachToRecyclerView(recyclerView)

        addButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, NewEmpFragment.newInstance()).addToBackStack(null)
                .commit()
        }
    }

    companion object {
        fun newInstance() = EmpListFragment()
    }
}