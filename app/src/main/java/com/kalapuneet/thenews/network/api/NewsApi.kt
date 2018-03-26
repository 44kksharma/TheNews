package com.kalapuneet.thenews.network.api

import com.kalapuneet.thenews.network.models.Response
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/everything")
    fun getNewsArticles(
            @Query("q") q: String,
            @Query("sortBy") sortBy: String = "publishedAt",
            @Query("apiKey") apiKey: String
    ): Call<Response>

    companion object {
        private const val BASE_URL = "https://www.newsapi.org/"

        fun create(): NewsApi = create(HttpUrl.parse(BASE_URL)!!)

        private fun create(httpUrl: HttpUrl): NewsApi {
            return Retrofit.Builder()
                    .baseUrl(httpUrl)
                    .client(OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(NewsApi::class.java)
        }
    }
}