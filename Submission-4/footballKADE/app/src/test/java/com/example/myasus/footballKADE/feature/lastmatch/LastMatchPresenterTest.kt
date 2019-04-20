package com.example.myasus.footballKADE.feature.lastmatch

import com.example.myasus.footballKADE.TestContextProvider
import com.example.myasus.footballKADE.entity.MatchEvents
import com.example.myasus.footballKADE.entity.MatchResponse
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.example.myasus.footballKADE.feature.upcomingmatch.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LastMatchPresenterTest {
    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepo

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: LastMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LastMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }


    @Test
    fun getMatchLastList() {
        val match: MutableList<MatchEvents> = mutableListOf()
        val response = MatchResponse(match)
        val league = "4328"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    MatchResponse::class.java
                )
            ).thenReturn(response)

            presenter.getMatchLastList(league)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventList(match)
            Mockito.verify(view).hideLoading()
        }



    }
}