package com.example.myasus.footballKADE.entity.api

import org.junit.Test

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepoTest {

    @Test
    fun doRequest() {
        val apiRepo=mock(ApiRepo::class.java)

        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
        apiRepo.doRequest(url)
        verify(apiRepo).doRequest(url)
    }
}