package pe.devpicon.android.codelab.pomodoro.data.remote.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pe.devpicon.android.codelab.pomodoro.data.remote.common.RandomActivityUrl.RANDOM_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitClient: Retrofit = buildRetrofit(getOkHttpClient(getLoggingInterceptor()))

fun getLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

fun buildRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(RANDOM_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

fun getOkHttpClient(logging: HttpLoggingInterceptor) =
    OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
