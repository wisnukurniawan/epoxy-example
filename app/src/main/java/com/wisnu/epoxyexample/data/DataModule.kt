package com.wisnu.epoxyexample.data

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.wisnu.epoxyexample.BuildConfig
import com.wisnu.epoxyexample.data.repository.RemoteProfileRepository
import com.wisnu.epoxyexample.data.repository.RemoteProjectRepository
import com.wisnu.epoxyexample.data.source.server.GithubServerApi
import com.wisnu.epoxyexample.domain.repository.ProfileRepository
import com.wisnu.epoxyexample.domain.repository.ProjectRepository
import com.wisnu.epoxyexample.util.ServerModule
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module(override = true) {

    single(named(ServerModule.GITHUB_RETROFIT)) {
        buildRetrofitRxJava(
            buildOkHttp(androidContext()),
            BuildConfig.GITHUB_BASE_URL
        )
    }

    single(named(ServerModule.GITHUB_SERVICE)) {
        get<Retrofit>(named(ServerModule.GITHUB_RETROFIT)).create(GithubServerApi::class.java)
    }

    single<ProfileRepository> { RemoteProfileRepository(get(named(ServerModule.GITHUB_SERVICE))) }
    single<ProjectRepository> { RemoteProjectRepository(get(named(ServerModule.GITHUB_SERVICE))) }

}

fun buildOkHttp(
    context: Context
): OkHttpClient {
    return OkHttpClient.Builder().apply {
        addInterceptor(ChuckerInterceptor(context))
        addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        connectTimeout(60L, TimeUnit.SECONDS)
        readTimeout(60L, TimeUnit.SECONDS)
        writeTimeout(60L, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
    }.build()
}

fun buildRetrofitRxJava(
    okHttp: OkHttpClient,
    baseUrl: String
): Retrofit {
    return (Retrofit.Builder())
        .baseUrl(baseUrl)
        .client(okHttp)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
        .build()
}
