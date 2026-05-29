package br.com.fiap.ecotrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.ecotrack.screen.*
import br.com.fiap.ecotrack.ui.theme.EcoTrackTheme
import br.com.fiap.ecotrack.view.DashboardViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EcoTrackTheme {
                val navController = rememberNavController()
                val dashboardViewModel: DashboardViewModel = viewModel()

                // Abre primeiro a de login
                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    // 1. Tela de Login
                    composable("login") {
                        LoginScreen(navController = navController)
                    }

                    // 2. Tela de Cadastro
                    composable("cadastro") {
                        CadastroScreen(navController = navController)
                    }

                    // 3. Painel Principal (Dashboard)
                    composable("dashboard") {
                        DashboardScreen(navController = navController, viewModel = dashboardViewModel
                        )
                    }

                    // 4. Tela de Clima e Qualidade do Ar
                    composable("clima") {
                        ClimaScreen(navController = navController,  viewModel = dashboardViewModel)
                    }

                    // 5. Tela de Ações Sustentáveis
                    composable("acoes") {
                        AcoesScreen(navController = navController, viewModel = dashboardViewModel
                        )
                    }

                    // 6. Tela de Dicas ESG
                    composable("dicas") {
                        DicasScreen(navController = navController)
                    }
                }
            }
        }
    }
}