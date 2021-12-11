package com.example.teamleadapp.view.ui.empList

import androidx.lifecycle.ViewModel
import com.example.teamleadapp.data.remote.models.Employee
import com.example.teamleadapp.data.remote.models.Skill
import com.example.teamleadapp.data.repository.DbRepository

class EmpViewModel(private val dbRepository: DbRepository): ViewModel() {

    fun getEmpList(): List<Employee>{
        return dbRepository.getEmployees()
    }

    fun getSkillList(): List<Skill>{
        return dbRepository.getSkills()
    }

    fun updateSkillScore(empId: Long, skillId: Long, score: Int){
        dbRepository.updateSkillScore(empId, skillId, score)
    }

    fun addSkill(employeeWithSkillsId: Long, empId: Long, skillId: Long, skillName: kotlin.String){
        dbRepository.addSkill(employeeWithSkillsId, empId, skillId, skillName)
    }

    fun deleteSkill(empId: Long, skillId: Long){
        dbRepository.deleteSkill(empId, skillId)
    }

    fun addEmployee(employee: Employee) {
        dbRepository.addEmployee(employee)
    }
}