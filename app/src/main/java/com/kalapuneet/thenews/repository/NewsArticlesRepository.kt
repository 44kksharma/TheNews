package com.kalapuneet.thenews.repository

import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import com.kalapuneet.thenews.BuildConfig
import com.kalapuneet.thenews.network.api.NewsApi
import com.kalapuneet.thenews.network.models.Response
import retrofit2.Call
import java.util.concurrent.Executor
import retrofit2.Callback

class NewsArticlesRepository(private val api: NewsApi, private val ioExecutor: Executor) {

    @MainThread
    private fun refresh(): Listing<Response> {
        val networkState = MutableLiveData<NetworkState>()
        val response = MutableLiveData<Response>()
        networkState.value = NetworkState.LOADING
        api.getNewsArticles("", "publishedAt", BuildConfig.NEWS_API_KEY)
                .enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        networkState.value = NetworkState.error(t?.message)
                    }

                    override fun onResponse(call: Call<Response>?, resp: retrofit2.Response<Response>?) {
                        ioExecutor.execute {
                            response.postValue(resp?.body())
                            networkState.postValue(NetworkState.LOADED)
                        }
                    }

                })
        return Listing(
                response,
                networkState
        )
    }
}