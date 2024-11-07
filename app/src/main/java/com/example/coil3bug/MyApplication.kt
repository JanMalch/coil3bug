package com.example.coil3bug

import android.app.Application
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.network.ConnectivityChecker
import coil3.network.cachecontrol.CacheControlCacheStrategy
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.util.DebugLogger
import okhttp3.OkHttpClient

class MyApplication : Application() {

    @OptIn(ExperimentalCoilApi::class)
    override fun onCreate() {
        super.onCreate()
        SingletonImageLoader.setSafe { context ->
            ImageLoader.Builder(context)
                .logger(DebugLogger())
                .components {
                    add(
                        OkHttpNetworkFetcherFactory(
                            cacheStrategy = { CacheControlCacheStrategy() },
                            callFactory = {
                                OkHttpClient()
                            },
                            connectivityChecker = { ctx -> ConnectivityChecker(ctx) }
                        )
                    )
                }
                .build()
        }
    }

}