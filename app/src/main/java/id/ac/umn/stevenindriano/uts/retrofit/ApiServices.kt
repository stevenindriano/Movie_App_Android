package id.ac.umn.stevenindriano.uts.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import id.ac.umn.stevenindriano.uts.BuildConfig

object ApiServices {

    val BASE_URL: String = "https://imdb-top-100-movies.p.rapidapi.com/"
    val endpoint: ApiEndpoint
        get() {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val apiKey = BuildConfig.API_KEY
            val apiHost = BuildConfig.API_HOST


            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("X-RapidAPI-Key", apiKey)
                        .addHeader("X-RapidAPI-Host", apiHost)
                        .build()
                    chain.proceed(request)
                }
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            return retrofit.create(ApiEndpoint::class.java)
        }
}