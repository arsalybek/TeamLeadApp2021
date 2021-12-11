package com.example.teamleadapp.view.utils

import org.joda.time.Days
import org.joda.time.LocalDate
import org.joda.time.Months
import org.joda.time.Years
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object TimeIntervalConverter {
    fun getWorkExp(startDate: LocalDate?): String {
        val now = LocalDate()
        val monthsBetween = Months.monthsBetween(startDate, now).months
        val yearsBetween = Years.yearsBetween(startDate, now).years
        val daysBetween = Days.daysBetween(startDate, now).days
        var yearString = ""
        var monthString = ""
        var dayString = ""
        yearString = if (yearsBetween == 0) {
            "Year"
        } else "Years"
        monthString = if (monthsBetween == 0) {
            "Month"
        } else "Months"
        dayString = if (daysBetween == 0) {
            "Day"
        } else "Days"
        return yearString + ": " + yearsBetween + " " + monthString + ": " + monthsBetween % 12 + " " + dayString + ": " + daysBetween % 365 % 30
    }

    fun getUnixDate(date: String?, pattern: String?): Long {
        return try {
            val format: DateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            format.parse(date).time
        } catch (e: Exception) {
            Date().time
        }
    }

    fun getDateString(date: Long, pattern: String?): String? {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
    }
}