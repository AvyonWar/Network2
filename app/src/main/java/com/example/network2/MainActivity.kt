package com.example.network2

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//data class Repo(val id: String, val name: String)



interface ApiService {
    @GET("countries")
    suspend fun listCountries(): CountryResult
}
class Country: Any()
class CountryResult : Any()



const val API_AUTHORIZATION_HEADER = "X-RapidAPI-Key"

class AuthorizationInterceptor: Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader(API_AUTHORIZATION_HEADER, "c056c84ddbmshedd7538066827aep18b3d0jsn26df99f73270")
            .build()
        return  chain.proceed(newRequest)
    }

}

class MainActivity : AppCompatActivity() {
    val logging = HttpLoggingInterceptor()
    val authorization = AuthorizationInterceptor()
    val client = OkHttpClient.Builder()
        .addInterceptor(authorization)
        .addInterceptor(logging)
        .build()

    val retrofit = Retrofit.Builder().client(client).baseUrl("https://planets-info-by-newbapi.p.rapidapi.com")
        .addConverterFactory(GsonConverterFactory.create()).build()

    val apiService = retrofit.create(ApiService::class.java)





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logging.level = HttpLoggingInterceptor.Level.BASIC
        retriveRepos()

    }

        fun retriveRepos(){

            val progress = findViewById<ContentLoadingProgressBar>(R.id.progress_id)  // ??

            lifecycleScope.launch {
                try {
                    progress.show()
                    val countries = apiService.listCountries()
                    progress.hide()
                    showCountries(countries)
                } catch (e: Exception) {
                    progress.hide()
                    Snackbar.make(findViewById((R.id.main_id)), "Error", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry") {
                            retriveRepos()
                        }.show()
                }
            }
        }

    fun showCountries(countryResults: CountryResult){
        Toast.makeText(this, "showCoutries", Toast.LENGTH_SHORT).show()
    }






}