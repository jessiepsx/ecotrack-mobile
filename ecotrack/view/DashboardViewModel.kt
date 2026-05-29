package br.com.fiap.ecotrack.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.ecotrack.screen.DashboardState
import br.com.fiap.ecotrack.api.RetrofitClient
import br.com.fiap.ecotrack.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class DashboardViewModel : ViewModel() {

    private val apiService = RetrofitClient.instance

    // Estado inicial com pontos em 0
    private val _uiState = MutableStateFlow(DashboardState(pontos = 0))
    val uiState: StateFlow<DashboardState> = _uiState.asStateFlow()

    init {
        println("API CHAMADA")
        // Carrega dados iniciais da API
        atualizarDados("Sao Paulo,BR")
    }

    fun somarPontos(valor: Int) {
        _uiState.update { currentState ->
            currentState.copy(pontos = currentState.pontos + valor)
        }
    }


    fun atualizarDados(cidade: String) {
        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true) }

            try {

                val climaCall = apiService.getClima(
                    cidade,
                    Constants.API_KEY,
                    Constants.UNITS,
                    Constants.LANGUAGE
                ).awaitResponse()

                println("CLIMA CODE: ${climaCall.code()}")

                if (climaCall.isSuccessful && climaCall.body() != null) {

                    val clima = climaCall.body()

                    val arCall = apiService.getAirQuality(
                        Constants.DEFAULT_LAT,
                        Constants.DEFAULT_LON,
                        Constants.API_KEY
                    ).awaitResponse()

                    println("AR CODE: ${arCall.code()}")

                    val ar = if (arCall.isSuccessful) arCall.body() else null

                    _uiState.update { currentState ->
                        currentState.copy(
                            cidade = clima?.name?.uppercase() ?: "DESCONHECIDO",
                            temperatura = "${clima?.main?.temp?.toInt() ?: "--"}°C",
                            qualidadeAr = ar?.list?.firstOrNull()?.main?.aqi ?: 0,
                            isLoading = false
                        )
                    }

                } else {

                    println("ERRO CLIMA API")

                    _uiState.update {
                        it.copy(cidade = "ERRO API", isLoading = false)
                    }

                }

            } catch (e: Exception) {

                println("ERRO CONEXAO: ${e.message}")

                _uiState.update {
                    it.copy(cidade = "ERRO DE CONEXÃO", isLoading = false)
                }

            }
        }
    }
}


