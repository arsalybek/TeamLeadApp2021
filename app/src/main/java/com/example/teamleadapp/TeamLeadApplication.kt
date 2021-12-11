package com.example.teamleadapp

import android.app.Application
import com.example.teamleadapp.data.local.DbHelper
import com.example.teamleadapp.data.remote.models.Employee
import com.example.teamleadapp.data.remote.models.EmployeePositionType
import com.example.teamleadapp.data.remote.models.EmployeeWithSkills
import com.example.teamleadapp.data.remote.models.Skill
import com.example.teamleadapp.di.*
import org.joda.time.LocalDate
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.*

class TeamLeadApplication: Application() {
    lateinit var dbHelper : DbHelper
    override fun onCreate() {
        super.onCreate()
        dbHelper = DbHelper(this)
        dbHelper.insertEmployees(getEmployees())
        dbHelper.insertSkills(getSkills())
        dbHelper.insertEmployeeWithSkills(getEmployeeWithSkills())
        startKoin {
            androidContext(this@TeamLeadApplication)
            modules(listOf(
                databaseModule,
                retrofitModule,
                repositoriesModule,
                viewModelsModule,
                appModule
            ))
        }

    }

    fun getSkills(): List<Skill> {
        val s1 = Skill(1, "UI/UX")
        val s2 = Skill(2, "Android Core")
        val s3 = Skill(3, "Presentation")
        val s4 = Skill(4, "IOS Core")
        val skillList: MutableList<Skill> = ArrayList<Skill>()
        skillList.add(s1)
        skillList.add(s2)
        skillList.add(s3)
        skillList.add(s4)
        return skillList
    }

    fun getEmployeeWithSkills(): List<EmployeeWithSkills> {
        val j1 = EmployeeWithSkills(1, 1, 1, 8)
        val j2 = EmployeeWithSkills(2, 1, 2, 5)
        val j3 = EmployeeWithSkills(3, 1, 3, 5)
        val j4 = EmployeeWithSkills(4, 2, 3, 8)
        val j5 = EmployeeWithSkills(5, 2, 1, 10)
        val j6 = EmployeeWithSkills(6, 2, 4, 6)
        val joinSkills: MutableList<EmployeeWithSkills> = ArrayList<EmployeeWithSkills>()
        joinSkills.add(j1)
        joinSkills.add(j2)
        joinSkills.add(j3)
        joinSkills.add(j4)
        joinSkills.add(j5)
        joinSkills.add(j6)
        return joinSkills
    }

    fun getEmployees(): List<Employee> {
        val employeeList: MutableList<Employee> = ArrayList<Employee>()
        val card1 = Employee(
            1,
            "Aleksandr Radlov",
            32,
            "sashaRadlov1@gmail.com",
            "+77057851992",
            EmployeePositionType.ANDROID.code,
           "https://www.resolutionfoundation.org/app/uploads/2020/02/George-300x300.jpg",
            LocalDate(2020, 1, 31),
            "Aldeshov",
            "AndroidAdvanced"
        )
        val card2 = Employee(
            2,
            "Anna Fedorova",
            23,
            "fedanne@gmail.com",
            "+77768234565",
            EmployeePositionType.IOS.code,
            "https://www.fineos.com/wp-content/uploads/2020/06/Return-to-work-post-Covid.jpg",
            LocalDate(2020, 1, 31),
            "arsalybek",
            "AndroidAdvanced2021"
        )
        employeeList.add(card1)
        employeeList.add(card2)
        return employeeList
    }
}