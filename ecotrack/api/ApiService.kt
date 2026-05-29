package br.com.fiap.ecotrack.api

import br.com.fiap.ecotrack.model.ClimaResponse
import br.com.fiap.ecotrack.model.QualidadeArResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    fun getClima(
        @Query("q") cidade: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "pt_br"
    ): Call<ClimaResponse>

    @GET("air_pollution")
    fun getAirQuality(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): Call<QualidadeArResponse>
}