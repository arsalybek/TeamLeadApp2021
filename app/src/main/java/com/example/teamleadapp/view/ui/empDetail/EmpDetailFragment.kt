package com.example.teamleadapp.view.ui.empDetail

import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.teamleadapp.R
import com.example.teamleadapp.data.remote.models.Employee
import com.example.teamleadapp.data.remote.models.EmployeeSkill
import com.example.teamleadapp.data.remote.models.Skill
import com.example.teamleadapp.data.remote.models.Status
import com.example.teamleadapp.view.customViews.AddSkillAlertDialog
import com.example.teamleadapp.view.customViews.ProgressDialog
import com.example.teamleadapp.view.ui.GithubViewModel
import com.example.teamleadapp.view.ui.empDetail.listeners.AddSkillDialogListener
import com.example.teamleadapp.view.ui.empDetail.listeners.IChangeListener
import com.example.teamleadapp.view.ui.empList.EmpViewModel
import com.example.teamleadapp.view.utils.TimeIntervalConverter
import kotlinx.android.synthetic.main.fragment_emp_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

private const val KEY_EMPLOYEE = "employee"

class EmpDetailFragment : Fragment(R.layout.fragment_emp_detail), IChangeListener,
    AddSkillDialogListener {

    private val viewModel: EmpViewModel by viewModel()
    private val githubViewModel: GithubViewModel by viewModel()

    private var skillAdapter: EmpDetailSkillAdapter? = null
    private val progressBarDialog = ProgressDialog()
    private var employee: Employee? = null
    private val skillList: MutableList<Skill> = mutableListOf()
    private val employeeList: MutableList<Employee> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            employee = it.getSerializable(KEY_EMPLOYEE) as Employee
        }

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        githubViewModel.getEmployeeCommits(
            employee?.gitUsername.toString(),
            employee?.gitRepoName.toString()
        )
        bindViews()
        bindViewModels()
    }

    private fun bindViews() {
        initViews()
        initGestureDetector()

        saveButton.isEnabled = false
        saveButton.setOnClickListener {
            skillList.addAll(viewModel.getSkillList())
            employee?.skills?.forEach {
                for (skill in skillList) {
                    if (it.name == skill.name) {
                        viewModel.updateSkillScore(employee!!.id, skill.id, it.score)
                    }
                }
            }
            skillAdapter?.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Changes are applied", Toast.LENGTH_SHORT).show()
        }
        val addSkillDialog = AddSkillAlertDialog.newInstance(this)
        detailAddSkillButton.setOnClickListener {
            addSkillDialog.showNow(
                requireActivity().supportFragmentManager,
                "dialog"
            )
        }
        skillList.addAll(viewModel.getSkillList())
        employeeList.addAll(viewModel.getEmpList())
    }

    private fun initViews() {
        skillRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        skillAdapter = employee?.skills?.let { EmpDetailSkillAdapter(it, this) }
        skillRecyclerView.adapter = skillAdapter
        commitRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        Glide.with(requireContext())
            .load(employee?.image)
            .apply(RequestOptions().error(R.drawable.default_emp_img))
            .into(detailProfileImageView)

        detailNameTextView.text = employee?.fullName?.toUpperCase()
        detailPositionTextView.text = employee?.position?.toUpperCase()
        detailStartDateTextView.text = TimeIntervalConverter.getWorkExp(employee?.workStartDate)
        detailAgeTextView.text = employee?.age.toString()
        detailEmailTextView.text = employee?.email
        detailPhoneTextView.text = employee?.phoneNumber

        githubCommitsTextView.text = employee?.gitUsername + "/" + employee?.gitRepoName
    }

    private fun initGestureDetector() {
        val gesture = GestureDetector(requireContext(),
            object : SimpleOnGestureListener() {
                override fun onDown(e: MotionEvent): Boolean {
                    return true
                }

                override fun onFling(
                    e1: MotionEvent, e2: MotionEvent, velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    val SWIPE_MIN_DISTANCE = 200
                    val SWIPE_THRESHOLD_VELOCITY = 200
                    try {
                        if (e2.x - e1.x > SWIPE_MIN_DISTANCE
                            && abs(velocityX) > SWIPE_THRESHOLD_VELOCITY
                        ) {
                            activity?.supportFragmentManager?.popBackStack()
                        }
                    } catch (e: Exception) {
                    }
                    return super.onFling(e1, e2, velocityX, velocityY)
                }
            })
        nestedScrollView.setDetector(gesture)
    }

    private fun bindViewModels() {
        githubViewModel.statusLiveData.observe(viewLifecycleOwner, {
            when (it) {
                Status.SHOW_LOADING -> {
                    if (!progressBarDialog.isAdded) {
                        progressBarDialog.show(
                            childFragmentManager, "progressDialog"
                        )
                    }
                }
                Status.HIDE_LOADING -> {
                    progressBarDialog.dismiss()
                }
            }
        })

        githubViewModel.commitsLiveData.observe(viewLifecycleOwner, {
            commitRecyclerView.adapter = EmpDetailCommitAdapter(it)
        })
    }

    override fun positiveClick(skillName: String) {
        var cnt: Long = 0
        for (employee in employeeList) {
            cnt += employee.skills?.size!!
        }
        employee?.id?.let {
            viewModel.addSkill(
                cnt + 1,
                it, (skillList.size + 1).toLong(), skillName
            )
        }
        employee?.skills?.add(EmployeeSkill(skillName, 1))

        skillAdapter?.notifyDataSetChanged()
    }

    override fun onRateChanged() {
        saveButton.isEnabled = true
        saveButton.setBackgroundResource(R.color.colorBlue)
        saveButton.text = "Save changes"
    }

    override fun onDeclineClicked(empSkill: EmployeeSkill) {
        employee?.skills?.remove(empSkill)
        skillList.addAll(viewModel.getSkillList())
        var curSkillId: Long = 0
        for (skill in skillList) {
            if (skill.name == empSkill.name)
                curSkillId = skill.id
        }
        employee?.id?.let { viewModel.deleteSkill(it, curSkillId) }
        skillAdapter?.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(employee: Employee) =
            EmpDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_EMPLOYEE, employee)
                }
            }
    }
}