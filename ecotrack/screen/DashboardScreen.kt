package br.com.fiap.ecotrack.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.ecotrack.ui.theme.EcoTrackTheme
import br.com.fiap.ecotrack.view.DashboardViewModel

data class DashboardState(
    val pontos: Int = 0,
    val cidade: String = "CARREGANDO...",
    val temperatura: String = "--°C",
    val qualidadeAr: Int = 0,
    val isLoading: Boolean = true
)

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        //  Cabeçalho
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFF00897B),
                    shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                )
                .padding(32.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Sua Pontuação Eco",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${uiState.pontos} pts",
                            color = Color.White,
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    Surface(
                        modifier = Modifier.size(60.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White.copy(alpha = 0.2f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Park,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFF00796B)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color(0xFF80CBC4),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = uiState.cidade,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        // Cards de Clima/Ar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoCard(
                label = "TEMPERATURA",
                value = uiState.temperatura,
                icon = Icons.Default.DeviceThermostat,
                iconColor = Color(0xFF448AFF),
                iconBg = Color(0xFFE3F2FD),
                modifier = Modifier.weight(1f)
            )
            InfoCard(
                label = "QUALIDADE DO AR",
                value = uiState.qualidadeAr.toString(),
                icon = Icons.Default.Air,
                iconColor = Color(0xFFFF8A65),
                iconBg = Color(0xFFFBE9E7),
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text = "Menu Principal",
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            color = Color(0xFF004D40),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp
        )

        MenuItem(
            titulo = "Ações Sustentáveis",
            subtitulo = "Registre seus hábitos diários",
            icon = Icons.Default.CheckCircle,
            onClick = { navController.navigate("acoes") }
        )

        MenuItem(
            titulo = "Dicas ESG",
            subtitulo = "Aprenda a ser mais sustentável",
            icon = Icons.Default.Info,
            onClick = { navController.navigate("dicas") }
        )

        MenuItem(
            titulo = "Clima & Ar",
            subtitulo = "Aprenda a ser mais sustentável",
            icon = Icons.Default.Info,
            onClick = { navController.navigate("clima") }
        )
    }
}

@Composable
fun InfoCard(
    label: String,
    value: String,
    icon: ImageVector,
    iconColor: Color,
    iconBg: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(32.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(16.dp),
                color = iconBg
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = label, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.LightGray)
            Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color.Black)
        }
    }
}

@Composable
fun MenuItem(
    titulo: String,
    subtitulo: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(90.dp),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = RoundedCornerShape(15.dp),
                color = Color(0xFFE8F5E9)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = titulo, fontWeight = FontWeight.Bold, color = Color(0xFF263238))
                Text(text = subtitulo, fontSize = 12.sp, color = Color.Gray)
            }
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}
