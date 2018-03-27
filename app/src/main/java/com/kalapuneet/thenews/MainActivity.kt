package com.kalapuneet.thenews

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.widget.ProgressBar
import com.kalapuneet.thenews.network.api.NewsApi
import com.kalapuneet.thenews.network.models.Response
import com.kalapuneet.thenews.repository.NetworkState
import com.kalapuneet.thenews.repository.NewsArticlesRepository
import com.kalapuneet.thenews.ui.ArticleViewModel
import com.kalapuneet.thenews.ui.NewsAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var model: ArticleViewModel
    private lateinit var adapter: NewsAdapter

    private lateinit var dataLoadingProgressBar: ProgressBar
    private lateinit var newsItemRv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = getViewModel()
        initUi()
        initAdapter()
        refresh()
    }

    private fun initUi() {
        dataLoadingProgressBar = findViewById(R.id.data_loading_progress)
        newsItemRv = findViewById(R.id.news_item_rv)
    }

    private fun refresh() {
        val repo = NewsArticlesRepository(NewsApi.create(), ServiceLocator.instance(this).getNetworkExecutor())
        val response = repo.refresh()
        response.articleList.observe(this, Observer<Response> {
            it?.articles?.let {
                adapter.articleList = it
                adapter.notifyDataSetChanged()
            }
        })
        response.networkState.observe(this, Observer<NetworkState> {
            it?.let {
                adapter.setNetworkState(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun initAdapter() {
        adapter = NewsAdapter({
            refresh()
        })
        adapter.articleList = ArrayList()
        newsItemRv.adapter = adapter
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
