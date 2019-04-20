package com.example.myasus.kotlin_sub3.entity.api

import java.net.URL

class ApiRepo {

    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}