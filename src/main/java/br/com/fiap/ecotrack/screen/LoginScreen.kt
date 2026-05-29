package br.com.fiap.ecotrack.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.ecotrack.database.AppDatabase
import br.com.fiap.ecotrack.ui.theme.EcoTrackTheme
import kotlinx.coroutines.launch

// Cores do Design
val Emerald600 = Color(0xFF059669)
val Emerald50 = Color(0xFFF0FDF4)
val Emerald900 = Color(0xFF064E3B)

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    val db = AppDatabase.getDatabase(context).usuarioDao()

    Box(modifier = Modifier.fillMaxSize().background(Emerald50)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .clip(RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp))
                .background(Emerald600)
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(40.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Surface(
                        modifier = Modifier.size(70.dp).offset(y = (-15).dp),
                        shape = CircleShape,
                        color = Color.White,
                        tonalElevation = 5.dp,
                        border = BorderStroke(4.dp, Emerald50)
                    ) {
                        Icon(
                            Icons.Default.Eco,
                            contentDescription = "Logo EcoTrack",
                            tint = Emerald600,
                            modifier = Modifier.padding(16.dp).size(40.dp)
                        )
                    }

                    Text("Bem-vindo!", fontSize = 28.sp, fontWeight = FontWeight.Black, color = Emerald900)
                    Text("EcoTrack: Sua jornada sustentável", color = Color.Gray, fontSize = 14.sp)

                    Spacer(Modifier.height(32.dp))

                    // Input E-mail
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("E-mail") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        leadingIcon = { Icon(Icons.Default.Mail, null, tint = Emerald600) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF9F9F9),
                            focusedContainerColor = Color.White,
                            focusedBorderColor = Emerald600
                        )
                    )

                    Spacer(Modifier.height(12.dp))

                    // Input Senha
                    OutlinedTextField(
                        value = senha,
                        onValueChange = { senha = it },
                        placeholder = { Text("Senha") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        leadingIcon = { Icon(Icons.Default.Lock, null, tint = Emerald600) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF9F9F9),
                            focusedContainerColor = Color.White,
                            focusedBorderColor = Emerald600
                        )
                    )

                    // Botão Entrar
                    Button(
                        onClick = {
                            if (email.isEmpty() || senha.isEmpty()) {
                                Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            scope.launch {
                                try {
                                    val user = db.buscarPorEmail(email)
                                    if (user != null && user.senha == senha) {
                                        // Navega para o Dashboard e limpa o histórico de login
                                        navController.navigate("dashboard") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    } else {
                                        Toast.makeText(context, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show()
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Erro ao acessar banco de dados", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp).height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Emerald600)
                    ) {
                        Text("Entrar", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }

                    // Botão para Tela de Cadastro
                    TextButton(
                        onClick = { navController.navigate("cadastro") },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Não tem conta? ", color = Color.Gray)
                        Text("Cadastre-se", color = Emerald600, fontWeight = FontWeight.Black)
                    }
                }
            }

            Text(
                "EcoTrack v2.0 • Compromisso ESG",
                modifier = Modifier.padding(top = 32.dp),
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    EcoTrackTheme {
        LoginScreen(navController = rememberNavController())
    }
}