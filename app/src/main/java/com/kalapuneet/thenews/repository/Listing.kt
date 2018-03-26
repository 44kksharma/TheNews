package com.kalapuneet.thenews.repository

import android.arch.lifecycle.LiveData

data class Listing<T>(
        val articleList: LiveData<T>,
        val networkState: LiveData<NetworkState>
)