package com.wisnu.epoxyexample.di

import com.google.gson.GsonBuilder
import com.wisnu.epoxyexample.core.server.github.GithubServerApi
import com.wisnu.epoxyexample.util.ServerModule
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun serverModule(
    githubBaseUrl: String
) = module(override = true) {

  single {
    HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }
  }

  single {
    buildOkHttp(get())
  }

  single(named(ServerModule.GITHUB_RETROFIT)) {
    buildRetrofitRxJava(get(), githubBaseUrl)
  }

  single(named(ServerModule.GITHUB_SERVICE)) {
    get<Retrofit>(named(ServerModule.GITHUB_RETROFIT)).create(GithubServerApi::class.java)
  }

}

fun buildOkHttp(
    httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
  return OkHttpClient.Builder().apply {
    addInterceptor(httpLoggingInterceptor)
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
