package id.ac.umn.stevenindriano.uts.retrofit

import id.ac.umn.stevenindriano.uts.MovieModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiEndpoint {

    @GET("/")
    fun getMovie(): Call<List<MovieModel>>
}