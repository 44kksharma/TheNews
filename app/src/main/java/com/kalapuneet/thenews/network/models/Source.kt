package com.kalapuneet.thenews.network.models

import com.google.gson.annotations.SerializedName

data class Source(

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("id")
	val id: Any? = null
)