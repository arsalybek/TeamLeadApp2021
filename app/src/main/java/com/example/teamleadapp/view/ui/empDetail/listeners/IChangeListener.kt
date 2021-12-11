package com.example.teamleadapp.view.ui.empDetail.listeners

import com.example.teamleadapp.data.remote.models.EmployeeSkill

interface IChangeListener {
    fun onRateChanged()
    fun onDeclineClicked(empSkill: EmployeeSkill)
}