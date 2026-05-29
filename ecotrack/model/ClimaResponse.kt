package br.com.fiap.ecotrack.model

data class ClimaResponse(
    val name: String,
    val main: MainData,
    val weather: List<WeatherDescription>
)

data class MainData(
    val temp: Double,
    val humidity: Int
)

data class WeatherDescription(
    val description: String,
    val icon: String
)