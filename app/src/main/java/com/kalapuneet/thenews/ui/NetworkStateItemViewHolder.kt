package com.kalapuneet.thenews.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.kalapuneet.thenews.R
import com.kalapuneet.thenews.repository.NetworkState
import com.kalapuneet.thenews.repository.Status.FAILED
import com.kalapuneet.thenews.repository.Status.RUNNING

class NetworkStateItemViewHolder(view: View, private val retryCallback: () -> Unit): RecyclerView.ViewHolder(view) {
    private val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
    private val retry = view.findViewById<Button>(R.id.retry_button)
    private val errorMsg = view.findViewById<TextView>(R.id.error_msg)

    init {
        retry.setOnClickListener {
            retryCallback()
        }
    }

    fun bindTo(networkState: NetworkState) {
        progressBar.visibility = toVisibility(networkState.status == RUNNING)
        retry.visibility = toVisibility(networkState.status == FAILED)
        errorMsg.visibility = toVisibility(!networkState.msg.isNullOrEmpty())
        errorMsg.text = networkState.msg ?: ""
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_network_state, parent, false)
            return NetworkStateItemViewHolder(view, retryCallback)
        }

        fun toVisibility(constraint: Boolean): Int {
            return if (constraint) View.VISIBLE else View.GONE
        }
    }
}