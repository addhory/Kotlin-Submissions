package com.example.myasus.footballKADE.feature.team

import com.example.myasus.footballKADE.TestContextProvider
import com.example.myasus.footballKADE.entity.TeamItem
import com.example.myasus.footballKADE.entity.TeamResponse
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamPresenterTest {
    @Mock
    private lateinit var view: TeamView
    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepo
    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: TeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamList() {
        val teams: MutableList<TeamItem> = mutableListOf()
        val response = TeamResponse(teams)
        val t = "English%20Premier%20League"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(gson.fromJson("", TeamResponse::class.java))
                .thenReturn(response)

            presenter.getTeamList(t)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).showTeamList(teams)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getSearchTeam() {
        val teams: MutableList<TeamItem> = mutableListOf()
        val tResponse = TeamResponse(teams)
        val s ="Barcelona"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", TeamResponse::class.java))
                .thenReturn(tResponse)

            presenter.getSearchTeam(s)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).showTeamList(teams)
            Mockito.verify(view).hideLoading()
        }
    }
}