package com.example.myasus.footballKADE.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormat {

    private fun formatDate(date: String, format: String, isDate: Boolean): String {
        var result = ""
        val old = SimpleDateFormat(if (isDate) "yyyy-MM-dd" else "HH:mm:ss", Locale.ENGLISH)

        try {
            val oldDate = old.parse(date)
            val newFormat = SimpleDateFormat(format)

            result = newFormat.format(oldDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return result
    }



    fun getLongDate(date: String?): String {
        return formatDate(date.toString(), "EEE, dd MMM yyyy", true)
    }


}
