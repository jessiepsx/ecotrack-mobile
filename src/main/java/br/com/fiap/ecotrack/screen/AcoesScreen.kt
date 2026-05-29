package br.com.fiap.ecotrack.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun AcoesScreen(navController: NavController, viewModel: DashboardViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    val acoes = listOf(
        "Usei transporte sustentável" to 15,
        "Separei o lixo corretamente" to 10,
        "Economizei água hoje" to 20,
        "Apaguei luzes desnecessárias" to 5
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFF059669),
                    shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                )
                .statusBarsPadding()
                .padding(bottom = 24.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 8.dp, top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Text(text = "Pontuação Eco", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                    Text(text = "${uiState.pontos} pts", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Black)
                }
            }
        }

        Text(
            text = "Marque suas ações de hoje",
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1F2937)
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(acoes) { (titulo, pontos) ->
                var isChecked by rememberSaveable { mutableStateOf(false) }

                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isChecked) Color(0xFFF0FDF4) else Color.White
                    ),
                    border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked ->
                                isChecked = checked
                                if (checked) {
                                    viewModel.somarPontos(pontos)
                                } else {
                                    viewModel.somarPontos(-pontos)
                                }
                            },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF059669))
                        )

                        Text(text = titulo, modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)

                        Text(text = "+$pontos", color = Color(0xFF059669), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
