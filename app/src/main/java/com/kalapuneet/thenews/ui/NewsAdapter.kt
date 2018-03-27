package com.kalapuneet.thenews.ui

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.kalapuneet.thenews.R
import com.kalapuneet.thenews.network.models.ArticlesItem
import com.kalapuneet.thenews.repository.NetworkState

class NewsAdapter (private val retryCallback: () -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var networkState: NetworkState? = null
    var articleList: List<ArticlesItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_network_state -> NetworkStateItemViewHolder.create(parent, retryCallback)
            R.layout.item_news_article -> NewsArticleItemViewHolder.create(parent)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_news_article
        }
    }

    override fun getItemCount() = articleList?.size ?: 0 + if (hasExtraRow()) 1 else 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_network_state -> (holder as NetworkStateItemViewHolder).bindTo(networkState!!)
            R.layout.item_news_article -> (holder as NewsArticleItemViewHolder).bindTo(articleList!![position])
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}