package com.example.teamleadapp.data.remote.models

import com.example.teamleadapp.R
import org.joda.time.LocalDate
import java.io.Serializable


data class Skill(
    val id: Long,
    val name: String
)

data class EmployeeSkill(
    val name: String,
    var score: Int
)

data class Employee(
    val id: Long,
    val fullName: String,
    val age: Int,
    val email: String,
    val phoneNumber: String,
    val position: String,
    val image: String,
    val workStartDate: LocalDate,
    val gitUsername: String,
    val gitRepoName: String,
    val skills: MutableList<EmployeeSkill>? = null
): Serializable {
    fun getBackImage(): Int {
        when(position){
            EmployeePositionType.ANDROID.code -> return R.drawable.card_background_android
            EmployeePositionType.IOS.code -> return R.drawable.card_backround_ios
            EmployeePositionType.BACKEND.code -> return R.drawable.card_background_back
            EmployeePositionType.FRONTEND.code -> return R.drawable.card_background_front
        }
            return 0
    }
}


data class EmployeeWithSkills(
    val id: Long,
    val empId: Long,
    val skillId: Long,
    val score: Int
)

enum class EmployeePositionType(val code: String){
    ANDROID("Android Developer"),
    IOS("Ios Developer"),
    BACKEND("Backend Developer"),
    FRONTEND("FrontEnd Developer"),
    PM("Product Manager"),
    QA("Tester")
}
