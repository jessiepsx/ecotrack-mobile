package br.com.fiap.ecotrack.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.Park
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.ecotrack.ui.theme.EcoTrackTheme

// Modelo para as Dicas Simples
data class DicaSimples(
    val titulo: String,
    val descricao: String,
    val icone: ImageVector,
    val corTema: Color,
    val corFundo: Color
)

@Composable
fun DicasScreen(navController: NavController) {

    val dicas = listOf(
        DicaSimples(
            "Transporte Consciente",
            "Prefira transporte público ou bicicleta. Além de reduzir a emissão de CO2, você melhora sua saúde física.",
            Icons.Default.DirectionsBike,
            Color(0xFF388E3C), // Verde
            Color(0xFFE8F5E9)  // Verde Claro
        ),
        DicaSimples(
            "Consumo de Plástico",
            "Reduza o uso de plástico descartável. Utilize garrafas reutilizáveis e evite canudos e sacolas de uso único.",
            Icons.Default.Block,
            Color(0xFFD32F2F), // Vermelho
            Color(0xFFFFEBEE)  // Vermelho Claro
        ),
        DicaSimples(
            "Gestão de Resíduos",
            "Separe o lixo reciclável. Papel, vidro, metal e plástico podem ser transformados em novos produtos se descartados corretamente.",
            Icons.Default.DeleteSweep,
            Color(0xFF1976D2), // Azul
            Color(0xFFE3F2FD)  // Azul Claro
        ),

        DicaSimples(
            "Reflorestamento",
            "Plante árvores sempre que possível. Elas ajudam a purificar o ar, oferecem sombra e combatem o aquecimento global.",
            Icons.Default.Park,
            Color(0xFF2E7D32), // Verde Escuro
            Color(0xFFDCEDC8)  // Verde Lima Claro
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // Cabeçalho
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    color = Color(0xFF059669),
                    shape = RoundedCornerShape(
                        bottomStart = 30.dp,
                        bottomEnd = 30.dp
                    )
                )
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 20.dp)
            ) {

                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Educação Ambiental",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Lista de Dicas
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            items(dicas) { dica ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = dica.corFundo
                    ),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = dica.icone,
                                contentDescription = null,
                                tint = dica.corTema,
                                modifier = Modifier.size(20.dp)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = dica.titulo,
                                color = dica.corTema,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = dica.descricao,
                            color = dica.corTema.copy(alpha = 0.8f),
                            fontSize = 15.sp,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DicasPreview() {
    EcoTrackTheme {
        DicasScreen(navController = rememberNavController())
    }
}