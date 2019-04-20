package com.example.myasus.kotlin_2.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormat {

    fun formatDateToMatch(date: Date): String {
        return SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(date)
    }
}