package com.ejlim.data

import com.ejlim.data.service.FacilityService
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsAPITest {
    lateinit var mockWebServer: MockWebServer
    lateinit var productsAPI: FacilityService
    @Before
    fun setup(){
        productsAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(FacilityService::class.java)
    }

    fun testSearchFacility_returnFacility() = runTest{
        val mockResponse = MockResponse()
        val content = FileUtil.readFileResource("/Facilitys.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = productsAPI.searchFactility("")
        mockWebServer.takeRequest()

    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}