package com.kalapuneet.thenews.network.models

import com.google.gson.annotations.SerializedName

data class ArticlesItem(

	@SerializedName("publishedAt")
	val publishedAt: String? = null,

	@SerializedName("author")
	val author: Any? = null,

	@SerializedName("urlToImage")
	val urlToImage: Any? = null,

	@SerializedName("description")
	val description: String? = null,

	@SerializedName("source")
	val source: Source? = null,

	@SerializedName("title")
	val title: String? = null,

	@SerializedName("url")
	val url: String? = null
)