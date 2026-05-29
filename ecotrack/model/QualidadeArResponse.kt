package br.com.fiap.ecotrack.model

data class QualidadeArResponse(
    val list: List<AirItem>
)

data class AirItem(
    val main: AirMain
)

data class AirMain(
    val aqi: Int // Índice de 1 (Bom) a 5 (Péssimo)
)