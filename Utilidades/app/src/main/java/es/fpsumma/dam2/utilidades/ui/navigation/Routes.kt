package es.fpsumma.dam2.utilidades.ui.navigation

object Routes {
    const val HOME = "home"
    const val TAREA_LISTADO = "tareas/listado"

    const val TAREA_ADD = "tareas/nueva"

    const val TAREA_VIEW = "tareas/detalle/{id}"

    fun tareaView(id: Int): String {
        return "tareas/detalle/$id"
    }

}

/*
object Routes {
    const val HOME = "home"

    //const val LISTADO_TAREAS = "tareas"

    const val NOTAS = "notas"
}
 */