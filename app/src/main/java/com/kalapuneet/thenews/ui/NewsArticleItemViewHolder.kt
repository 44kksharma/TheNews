package com.kalapuneet.thenews.ui

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kalapuneet.thenews.R
import com.kalapuneet.thenews.network.models.ArticlesItem

class NewsArticleItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val image = view.findViewById<ImageView>(R.id.image)
    private val title = view.findViewById<TextView>(R.id.title)
    private val description = view.findViewById<TextView>(R.id.description)
    private var articlesItem: ArticlesItem? = null

    init {
        view.setOnClickListener{
            articlesItem?.url?.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                view.context.startActivity(intent)
            }
        }
    }

    fun bindTo(articlesItem: ArticlesItem) {
        this.articlesItem = articlesItem
        title.text = articlesItem.title ?: "loading"
        description.text = articlesItem.description ?: "loading"
    }

    companion object {
        fun create(parent: ViewGroup): NewsArticleItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_article, parent, false)
            return NewsArticleItemViewHolder(view)
        }
    }
}