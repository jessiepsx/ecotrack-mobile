package br.com.fiap.ecotrack.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import br.com.fiap.ecotrack.database.Usuario
import br.com.fiap.ecotrack.ui.theme.EcoTrackTheme
import kotlinx.coroutines.launch

@Composable
fun CadastroScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var carregando by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context).usuarioDao()
    val scope = rememberCoroutineScope()

    val emeraldGreen = Color(0xFF059669)

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(emeraldGreen)
                .padding(top = 12.dp, bottom = 12.dp, start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Voltar para Login",
                    tint = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.Eco,
                contentDescription = null,
                tint = emeraldGreen,
                modifier = Modifier.size(80.dp)
            )

            Text(
                "EcoTrack",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF064E3B) // Emerald 900
            )

            Text("Crie a sua conta sustentável", color = Color.Gray)

            Spacer(Modifier.height(32.dp))

            // Campo Nome
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome Completo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.Person, null, tint = emeraldGreen) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = emeraldGreen)
            )

            Spacer(Modifier.height(8.dp))

            // Campo E-mail
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.Email, null, tint = emeraldGreen) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = emeraldGreen)
            )

            Spacer(Modifier.height(8.dp))

            // Campo Senha
            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.Lock, null, tint = emeraldGreen) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = emeraldGreen)
            )

            Spacer(Modifier.height(24.dp))

            // Botão Criar Conta
            Button(
                onClick = {
                    if (email.isNotEmpty() && senha.isNotEmpty() && nome.isNotEmpty()) {
                        carregando = true
                        scope.launch {
                            try {
                                val existe = db.buscarPorEmail(email)
                                if (existe == null) {
                                    // Salva no Banco de Dados
                                    db.inserirUsuario(Usuario(email = email, nome = nome, senha = senha))

                                    Toast.makeText(
                                        context,
                                        "Conta criada com sucesso!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // Volta para a tela de Login
                                    navController.navigate("login") {
                                        popUpTo("cadastro") { inclusive = true }
                                    }
                                } else {
                                    Toast.makeText(context, "E-mail já registrado!", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Erro ao salvar: ${e.message}", Toast.LENGTH_LONG).show()
                            } finally {
                                carregando = false
                            }
                        }
                    } else {
                        Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = emeraldGreen),
                enabled = !carregando
            ) {
                if (carregando) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("Criar Conta", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CadastroPreview() {
    EcoTrackTheme {
        CadastroScreen(navController = rememberNavController())
    }
}