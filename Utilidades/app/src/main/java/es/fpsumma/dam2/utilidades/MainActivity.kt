package es.fpsumma.dam2.utilidades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import es.fpsumma.dam2.utilidades.ui.navigation.AppNavHost
import es.fpsumma.dam2.utilidades.ui.theme.UtilidadesTheme
//import es.fpsumma.dam2.utilidades.ui.viewmodel.TareasViewModel
import es.fpsumma.dam2.utilidades.ui.viewmodel.NotasViewModel
import es.fpsumma.dam2.utilidades.ui.viewmodel.TareasViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val tareasViewModel: TareasViewModel = viewModel()
    UtilidadesTheme {
        AppNavHost(navController = navController, tareasViewModel)
    }
}