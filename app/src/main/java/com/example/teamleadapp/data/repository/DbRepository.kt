package com.example.teamleadapp.data.repository

import com.example.teamleadapp.data.remote.models.Employee
import com.example.teamleadapp.data.remote.models.Skill

interface DbRepository {
    fun getEmployees(): List<Employee>
    fun getSkills(): List<Skill>
    fun updateSkillScore(empId: Long, skillId: Long, score: Int)
    fun addSkill(employeeWithSkillsId: Long, empId: Long, skillId: Long, skillName: kotlin.String)
    fun deleteSkill(empId: Long, skillId: Long)
    fun addEmployee(employee: Employee)
}