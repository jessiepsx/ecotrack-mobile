package br.com.fiap.ecotrack.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.ecotrack.ui.theme.EcoTrackTheme
import br.com.fiap.ecotrack.view.DashboardViewModel

@Composable
fun ClimaScreen(navController: NavController, viewModel: DashboardViewModel) {
    // Conectando ao mesmo ViewModel do Dashboard para pegar os dados sincronizados
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF059669))
                .statusBarsPadding()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar para o Dashboard",
                    tint = Color.White
                )
            }
            Text(
                text = "Clima & Ar",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Card Principal de Clima
        Card(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF42A5F5), Color(0xFF1E88E5))
                        )
                    )
                    .padding(32.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.WbSunny,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(80.dp)
                    )
                    // DADO DA API vindo do ViewModel
                    Text(
                        text = uiState.temperatura,
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Text(
                        text = "Parcialmente Nublado",
                        fontSize = 20.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        Text(
            text = "Detalhes da Atmosfera",
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            fontWeight = FontWeight.Bold,
            color = Color(0xFF374151)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DetailSmallCard(
                label = "HUMIDADE",
                value = "65%",
                icon = Icons.Default.WaterDrop,
                iconColor = Color(0xFF42A5F5),
                modifier = Modifier.weight(1f)
            )
            DetailSmallCard(
                label = "ÍNDICE UV",
                value = "Alto",
                icon = Icons.Default.Warning,
                iconColor = Color(0xFFFFB74D),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Card de Qualidade do Ar
        Card(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4))
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color(0xFF059669), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.qualidadeAr.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    val statusAr = if (uiState.qualidadeAr <= 50) "Ótima" else "Moderada"
                    Text(
                        text = "Ar: $statusAr",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF065F46)
                    )
                    Text(
                        text = "O ar está limpo e seguro para atividades.",
                        fontSize = 12.sp,
                        color = Color(0xFF065F46).copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailSmallCard(
    label: String,
    value: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = label, fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ClimaPreview() {
    EcoTrackTheme {
        ClimaScreen(navController = rememberNavController(), viewModel = DashboardViewModel())
    }
}