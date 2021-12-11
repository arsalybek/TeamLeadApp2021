package com.example.teamleadapp.data.repository

import com.example.teamleadapp.data.local.DbHelper
import com.example.teamleadapp.data.remote.models.Employee
import com.example.teamleadapp.data.remote.models.Skill

class DbRepositoryImpl(
    private val dbHelper: DbHelper
): DbRepository {
    override fun getEmployees(): List<Employee> =
        dbHelper.readAllEmployees()

    override fun getSkills(): List<Skill> =
        dbHelper.readAllSkills()

    override fun updateSkillScore(empId: Long, skillId: Long, score: Int) =
        dbHelper.updateSkillScore(empId, skillId, score)

    override fun addSkill(
        employeeWithSkillsId: Long,
        empId: Long,
        skillId: Long,
        skillName: String
    ) {
        dbHelper.addSkill(employeeWithSkillsId, empId, skillId, skillName)
    }

    override fun deleteSkill(empId: Long, skillId: Long) =
        dbHelper.deleteSkill(empId, skillId)

    override fun addEmployee(employee: Employee) {
        dbHelper.insertEmployees(mutableListOf(employee))
    }
}