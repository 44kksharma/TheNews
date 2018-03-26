package com.kalapuneet.thenews.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData

class ArticleViewModel(application: Application): AndroidViewModel(application) {
    val title = MutableLiveData<String>()
    val message = MutableLiveData<String>()
}