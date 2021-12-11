package com.example.teamleadapp.view.ui.newEmp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.teamleadapp.R
import com.example.teamleadapp.data.remote.models.Employee
import com.example.teamleadapp.data.remote.models.EmployeePositionType
import com.example.teamleadapp.view.adapter.PositionAdapter
import com.example.teamleadapp.view.ui.empList.EmpListFragment
import com.example.teamleadapp.view.ui.empList.EmpViewModel
import kotlinx.android.synthetic.main.fragment_new_emp.*
import org.joda.time.LocalDate
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewEmpFragment : Fragment(R.layout.fragment_new_emp) {

    var positionIndex: Int = 0
    val positionList: List<String> = mutableListOf(
        EmployeePositionType.ANDROID.code,
        EmployeePositionType.IOS.code,
        EmployeePositionType.BACKEND.code,
        EmployeePositionType.FRONTEND.code)

    private val viewModel: EmpViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()

        addButton.setOnClickListener {
            createNewEmp()
        }
    }

    private fun createNewEmp() {
        val date = startDateEditText.unMaskedText.orEmpty()
        val employee = Employee(
            id = (viewModel.getEmpList().size + 1).toLong(),
            fullName = fullNameEditText.text.toString(),
            age = ageEditText.text.toString().toInt(),
            email = emailEditText.text.toString(),
            phoneNumber = phoneNumberEditText.unMaskedText.orEmpty(),
            position = positionList[positionIndex],
            image = imageUrlEditText.text.toString(),
            workStartDate = LocalDate(date.substring(4).toInt(), date.substring(2, 4).toInt(), date.substring(0, 2).toInt()),
            gitUsername = githubUserNameEditText.text.trim().toString(),
            gitRepoName = githubRepoNameEditText.text.trim().toString()
        )

        viewModel.addEmployee(employee)
        launchListFragment()
    }

    private fun initSpinner(){
        val adapter: ArrayAdapter<String> =
            PositionAdapter(requireContext(), android.R.layout.simple_spinner_item, positionList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        positionSpinner.adapter = adapter
        positionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ) {
                when (position) {
                    0 -> positionIndex = 0
                    1 -> positionIndex = 1
                    2 -> positionIndex = 2
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun launchListFragment(){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, EmpListFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance() = NewEmpFragment()
    }
}