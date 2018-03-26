package com.kalapuneet.thenews

import android.app.Application
import android.content.Context
import com.kalapuneet.thenews.network.api.NewsApi
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by kalapuneet on 19-03-2018.
 */
interface ServiceLocator {
    companion object {
        private val LOCK = Any()

        private var instance: ServiceLocator? = null
        fun instance(context: Context): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator(app = context.applicationContext as Application)
                }
                return instance!!
            }
        }
    }

    fun getNetworkExecutor(): Executor

    fun getNewsApi(): NewsApi
}

open class DefaultServiceLocator(val app: Application): ServiceLocator {

    private val api by lazy {
        NewsApi.create()
    }

    private val NETWORK_IO = Executors.newFixedThreadPool(2)

    override fun getNetworkExecutor(): Executor = NETWORK_IO

    override fun getNewsApi() = api
}