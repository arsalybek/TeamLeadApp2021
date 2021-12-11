package com.example.teamleadapp.data.local

import android.provider.BaseColumns

object DbContract {
    object EmployeeEntry : BaseColumns {
        const val TABLE_NAME = "employee"
        const val COLUMN_EMP_ID = "empId"
        const val COLUMN_FULL_NAME = "fullName"
        const val COLUMN_POSITION = "position"
        const val COLUMN_IMAGE = "imageUri"
        const val COLUMN_WORK_START = "workStart"
        const val COLUMN_AGE = "age"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PHONE_NUMBER = "phoneNumber"
        const val COLUMN_GIT_USERNAME = "gitUsername"
        const val COLUMN_GIT_REPO_NAME = "gitRepoName"
    }

    object SkillEntry : BaseColumns {
        const val TABLE_NAME = "skill"
        const val COLUMN_SKILL_ID = "skillId"
        const val COLUMN_NAME = "name"
    }

    object EmployeeWithSkillsEntry : BaseColumns {
        const val TABLE_NAME = "employee_skill"
        const val COLUMN_JOINED_ID = "joinedId"
        const val COLUMN_SCORE = "score"
    }
}