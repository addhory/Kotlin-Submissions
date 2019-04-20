package com.example.myasus.footballKADE.feature.upcomingmatch

import com.example.myasus.footballKADE.TestContextProvider
import com.example.myasus.footballKADE.entity.MatchEvents
import com.example.myasus.footballKADE.entity.MatchResponse
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UpcomingPresenterTest {
    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepo

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: UpcomingPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = UpcomingPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getMatchList() {
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

            presenter.getMatchList(league)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventList(match)
            Mockito.verify(view).hideLoading()
        }
    }

}