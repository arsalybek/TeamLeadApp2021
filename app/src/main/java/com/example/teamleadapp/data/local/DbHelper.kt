package com.example.teamleadapp.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.teamleadapp.data.local.DbContract.EmployeeEntry
import com.example.teamleadapp.data.local.DbContract.EmployeeWithSkillsEntry
import com.example.teamleadapp.data.local.DbContract.SkillEntry
import com.example.teamleadapp.data.remote.models.Employee
import com.example.teamleadapp.data.remote.models.EmployeeSkill
import com.example.teamleadapp.data.remote.models.EmployeeWithSkills
import com.example.teamleadapp.data.remote.models.Skill
import org.joda.time.LocalDate
import java.lang.String
import java.util.*

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_EMPLOYEES)
        db.execSQL(SQL_CREATE_SKILLS)
        db.execSQL(SQL_CREATE_EMPLOYEE_WITH_SKILLS)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL(SQL_DELETE_EMPLOYEES)
        db.execSQL(SQL_DELETE_SKILLS)
        db.execSQL(SQL_DELETE_EMPLOYEE_WITH_SKILLS)
        onCreate(db)
    }

    fun insertEmployees(employeeList: List<Employee>) {
        val db = writableDatabase
        var values: ContentValues
        employeeList.forEach{ employee ->
            values = ContentValues()
            values.put(EmployeeEntry.COLUMN_EMP_ID, employee.id)
            values.put(EmployeeEntry.COLUMN_FULL_NAME, employee.fullName)
            values.put(EmployeeEntry.COLUMN_POSITION, employee.position)
            values.put(EmployeeEntry.COLUMN_IMAGE, employee.image)
            values.put(EmployeeEntry.COLUMN_WORK_START, String.valueOf(employee.workStartDate))
            values.put(EmployeeEntry.COLUMN_AGE, employee.age)
            values.put(EmployeeEntry.COLUMN_EMAIL, employee.email)
            values.put(EmployeeEntry.COLUMN_PHONE_NUMBER, employee.phoneNumber)
            values.put(EmployeeEntry.COLUMN_GIT_USERNAME, employee.gitUsername)
            values.put(EmployeeEntry.COLUMN_GIT_REPO_NAME, employee.gitRepoName)
            db.insert(EmployeeEntry.TABLE_NAME, null, values)
        }
        db.close()
    }

    fun insertSkills(skillList: List<Skill>) {
        val db = writableDatabase
        var values: ContentValues
        skillList.forEach{ skill ->
            values = ContentValues()
            values.put(SkillEntry.COLUMN_SKILL_ID, skill.id)
            values.put(SkillEntry.COLUMN_NAME, skill.name)
            db.insert(SkillEntry.TABLE_NAME, null, values)
        }
        db.close()
    }

    fun insertEmployeeWithSkills(employeeWithSkillList: List<EmployeeWithSkills>) {
        val db = this.writableDatabase
        var values: ContentValues
        employeeWithSkillList.forEach{ empSkill ->
            values = ContentValues()
            values.put(EmployeeWithSkillsEntry.COLUMN_JOINED_ID, empSkill.id)
            values.put(EmployeeEntry.COLUMN_EMP_ID, empSkill.empId)
            values.put(SkillEntry.COLUMN_SKILL_ID, empSkill.skillId)
            values.put(EmployeeWithSkillsEntry.COLUMN_SCORE, empSkill.score)
            db.insert(EmployeeWithSkillsEntry.TABLE_NAME, null, values)
        }
        db.close()
    }

    fun readAllEmployees(): List<Employee> {
        val list: MutableList<Employee> = ArrayList()
        val selectQuery = "SELECT * FROM ${EmployeeEntry.TABLE_NAME}"
        val db = writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val employee = Employee(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(EmployeeEntry.COLUMN_EMP_ID)),
                    fullName = cursor.getString(cursor.getColumnIndexOrThrow(EmployeeEntry.COLUMN_FULL_NAME)),
                    age = cursor.getInt(cursor.getColumnIndexOrThrow(EmployeeEntry.COLUMN_AGE)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(EmployeeEntry.COLUMN_EMAIL)),
                    phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(EmployeeEntry.COLUMN_PHONE_NUMBER)),
                    position = cursor.getString(cursor.getColumnIndexOrThrow(EmployeeEntry.COLUMN_POSITION)),
                    image = cursor.getString(cursor.getColumnIndexOrThrow(EmployeeEntry.COLUMN_IMAGE)),
                    workStartDate = LocalDate.parse(
                        cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                EmployeeEntry.COLUMN_WORK_START
                            )
                        )
                    ),
                    gitUsername = cursor.getString(cursor.getColumnIndexOrThrow(EmployeeEntry.COLUMN_GIT_USERNAME)),
                    gitRepoName = cursor.getString(cursor.getColumnIndexOrThrow(EmployeeEntry.COLUMN_GIT_REPO_NAME)),
                    skills = readEmployeeSkills(cursor.getLong(cursor.getColumnIndexOrThrow(EmployeeEntry.COLUMN_EMP_ID)))
                )
                Log.e("HELPER", "id:" + cursor.getLong(cursor.getColumnIndexOrThrow(EmployeeEntry.COLUMN_EMP_ID)))
                list.add(employee)
            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }

    fun readAllSkills(): List<Skill> {
        val list: MutableList<Skill> = ArrayList()
        val selectQuery = "SELECT * FROM ${SkillEntry.TABLE_NAME}"
        val db = writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val skill = Skill(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(SkillEntry.COLUMN_SKILL_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(SkillEntry.COLUMN_NAME))
                )
                list.add(skill)
            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }

    private fun readEmployeeSkills(id: Long): MutableList<EmployeeSkill> {
        val skillList: MutableList<EmployeeSkill> = ArrayList()
        val selectQuery = ("SELECT " + SkillEntry.TABLE_NAME + "." + SkillEntry.COLUMN_NAME + ","
                + EmployeeWithSkillsEntry.TABLE_NAME + "." + EmployeeWithSkillsEntry.COLUMN_SCORE + " FROM "
                + EmployeeEntry.TABLE_NAME + "," + SkillEntry.TABLE_NAME + "," + EmployeeWithSkillsEntry.TABLE_NAME + " WHERE " + id + "=" +
                EmployeeEntry.TABLE_NAME + "." +  EmployeeEntry.COLUMN_EMP_ID + " AND " + EmployeeEntry.TABLE_NAME + "." + EmployeeEntry.COLUMN_EMP_ID
             + "=" + EmployeeWithSkillsEntry.TABLE_NAME + "." + EmployeeEntry.COLUMN_EMP_ID + " AND " +
                SkillEntry.TABLE_NAME + "." + SkillEntry.COLUMN_SKILL_ID + "=" + EmployeeWithSkillsEntry.TABLE_NAME + "." + SkillEntry.COLUMN_SKILL_ID)
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val skill = EmployeeSkill(
                    name = cursor.getString(cursor.getColumnIndexOrThrow(SkillEntry.COLUMN_NAME)),
                    score = cursor.getInt(cursor.getColumnIndexOrThrow(EmployeeWithSkillsEntry.COLUMN_SCORE))
                )
                skillList.add(skill)
            } while (cursor.moveToNext())
        }
        return skillList
    }

    fun updateSkillScore(empId: Long, skillId: Long, score: Int) {
        val updateQuery = "UPDATE " + EmployeeWithSkillsEntry.TABLE_NAME + " SET " +
                EmployeeWithSkillsEntry.COLUMN_SCORE + "=" + score.toString() + " WHERE " +
                EmployeeEntry.COLUMN_EMP_ID + "=" + empId.toString() + " AND " + SkillEntry.COLUMN_SKILL_ID + "=" + skillId.toString()
        val db = writableDatabase
        db.execSQL(updateQuery)
    }

    fun deleteSkill(empId: Long, skillId: Long) {
        val deleteQuery = "DELETE FROM " + EmployeeWithSkillsEntry.TABLE_NAME + " WHERE " +
                EmployeeEntry.COLUMN_EMP_ID + "=" + empId.toString() + " AND " + SkillEntry.COLUMN_SKILL_ID + "=" + skillId.toString()
        val db = writableDatabase
        db.execSQL(deleteQuery)
    }

    fun addSkill(employeeWithSkillsId: Long, empId: Long, skillId: Long, skillName: kotlin.String) {
        val db = this.writableDatabase
        val newSkill = ContentValues()
        newSkill.put(SkillEntry.COLUMN_SKILL_ID, skillId)
        newSkill.put(SkillEntry.COLUMN_NAME, skillName)
        db.insert(SkillEntry.TABLE_NAME, null, newSkill)
        val newEmployeeSkill = ContentValues()
        newEmployeeSkill.put(EmployeeWithSkillsEntry.COLUMN_JOINED_ID, employeeWithSkillsId)
        newEmployeeSkill.put(EmployeeEntry.COLUMN_EMP_ID, empId)
        newEmployeeSkill.put(SkillEntry.COLUMN_SKILL_ID, skillId)
        newEmployeeSkill.put(EmployeeWithSkillsEntry.COLUMN_SCORE, 1)
        db.insert(EmployeeWithSkillsEntry.TABLE_NAME, null, newEmployeeSkill)
    }

    fun removeAll() {
        val db = this.writableDatabase // helper is object extends SQLiteOpenHelper
        db.delete(EmployeeEntry.TABLE_NAME, null, null)
        db.delete(SkillEntry.TABLE_NAME, null, null)
        db.delete(EmployeeWithSkillsEntry.TABLE_NAME, null, null)
    }

    companion object {
        const val DATABASE_VERSION = 10
        const val DATABASE_NAME = "employee_db"

        private const val SQL_CREATE_EMPLOYEES =
            "CREATE TABLE " + EmployeeEntry.TABLE_NAME + " (" +
                    EmployeeEntry.COLUMN_EMP_ID + " INTEGER PRIMARY KEY," +
                    EmployeeEntry.COLUMN_FULL_NAME + " TEXT," +
                    EmployeeEntry.COLUMN_POSITION + " TEXT, " +
                    EmployeeEntry.COLUMN_IMAGE + " TEXT, " +
                    EmployeeEntry.COLUMN_WORK_START + " DATE, " +
                    EmployeeEntry.COLUMN_AGE + " INTEGER, " +
                    EmployeeEntry.COLUMN_EMAIL + " TEXT," +
                    EmployeeEntry.COLUMN_PHONE_NUMBER + " TEXT, " +
                    EmployeeEntry.COLUMN_GIT_USERNAME + " TEXT, " +
                    EmployeeEntry.COLUMN_GIT_REPO_NAME+ " TEXT)"

        private const val SQL_DELETE_EMPLOYEES = "DROP TABLE IF EXISTS " + EmployeeEntry.TABLE_NAME

        private const val SQL_CREATE_SKILLS =
            "CREATE TABLE " + SkillEntry.TABLE_NAME + " (" +
                    SkillEntry.COLUMN_SKILL_ID + " INTEGER PRIMARY KEY," +
                    SkillEntry.COLUMN_NAME + " TEXT)"

        private const val SQL_DELETE_SKILLS = "DROP TABLE IF EXISTS " + SkillEntry.TABLE_NAME

        private const val SQL_CREATE_EMPLOYEE_WITH_SKILLS =
            "CREATE TABLE " + EmployeeWithSkillsEntry.TABLE_NAME + " (" +
                    EmployeeWithSkillsEntry.COLUMN_JOINED_ID + " INTEGER PRIMARY KEY," +
                    EmployeeEntry.COLUMN_EMP_ID + " INTEGER REFERENCES TABLE_EMPLOYEE(EMP_ID) ON DELETE RESTRICT," +
                    SkillEntry.COLUMN_SKILL_ID + " INTEGER REFERENCES TABLE_SKILL(SKILL_ID) ON DELETE RESTRICT," +
                    EmployeeWithSkillsEntry.COLUMN_SCORE + " INTEGER)"

        private const val SQL_DELETE_EMPLOYEE_WITH_SKILLS = "DROP TABLE IF EXISTS " + EmployeeWithSkillsEntry.TABLE_NAME

    }
}
