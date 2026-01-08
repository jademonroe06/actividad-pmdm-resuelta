package es.fpsumma.dam2.utilidades.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import es.fpsumma.dam2.utilidades.ui.screens.tareas.DetalleTareaScreen
import es.fpsumma.dam2.utilidades.ui.screens.tareas.ListadoTareasScreen
import es.fpsumma.dam2.utilidades.ui.screens.tareas.NuevaTareaScreen
import es.fpsumma.dam2.utilidades.ui.viewmodel.TareasViewModel

@Composable
fun AppNavHost(navController: NavHostController, tareasViewModel: TareasViewModel) {
    NavHost(navController = navController, startDestination = Routes.TAREA_LISTADO) {
        composable(Routes.TAREA_LISTADO) { ListadoTareasScreen(navController, tareasViewModel) }
        composable(Routes.TAREA_ADD) { NuevaTareaScreen(navController, tareasViewModel) }
        composable(
            route = Routes.TAREA_VIEW,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStage ->
            DetalleTareaScreen(
                id = backStage.arguments?.getInt("id") ?: 0,
                navController,
                tareasViewModel
            )
        }

    }
}

/*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.fpsumma.dam2.utilidades.ui.screens.home.HomeScreen
import es.fpsumma.dam2.utilidades.ui.screens.notas.ListadoNotasScreen
import es.fpsumma.dam2.utilidades.ui.screens.tareas.ListadoTareasScreen
import es.fpsumma.dam2.utilidades.ui.viewmodel.NotasViewModel
import es.fpsumma.dam2.utilidades.ui.viewmodel.TareasViewModel

@Composable
fun AppNavHost(navController: NavHostController, notasViewModel: NotasViewModel) {
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) { HomeScreen(navController)}
        //composable(Routes.LISTADO_TAREAS) { ListadoTareasScreen(navController,notasViewModel) }
        composable(Routes.NOTAS) { ListadoNotasScreen(navController,notasViewModel) }
    }
}
 */