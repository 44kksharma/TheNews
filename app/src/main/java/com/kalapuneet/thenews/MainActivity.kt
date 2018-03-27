package com.kalapuneet.thenews

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kalapuneet.thenews.network.api.NewsApi
import com.kalapuneet.thenews.network.models.Response
import com.kalapuneet.thenews.repository.NetworkState
import com.kalapuneet.thenews.repository.NewsArticlesRepository
import com.kalapuneet.thenews.ui.ArticleViewModel
import com.kalapuneet.thenews.ui.NewsAdapter
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private lateinit var model: ArticleViewModel
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = getViewModel()
        initAdapter()
        refresh()
    }

    private fun refresh() {
        val repo = NewsArticlesRepository(NewsApi.create(), ServiceLocator.instance(this).getNetworkExecutor())
        val response = repo.refresh()
        response.articleList.observe(this, Observer<Response> {
            it?.articles?.let {
               adapter.articleList = it
            }
        })
        response.networkState.observe(this, Observer<NetworkState> {
            it?.let {
                adapter.setNetworkState(it)
            }
        })
    }

    private fun initAdapter() {
        adapter = NewsAdapter({
            refresh()
        })
        adapter.articleList = ArrayList()
    }

    private fun getViewModel(): ArticleViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ArticleViewModel(application) as T
            }
        })[ArticleViewModel::class.java]
    }
}
