package es.fpsumma.dam2.utilidades.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import es.fpsumma.dam2.utilidades.data.local.AppDatabase
import es.fpsumma.dam2.utilidades.model.Nota
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//lo siguiente que hacemos es crear el ViewModel que va a implementar las funciones

class NotasViewModel(app: Application) : AndroidViewModel(app) {

    // 1) Abrimos/creamos la base de datos donde se guardan las notas (archivo "notas.db")
    private val db = Room.databaseBuilder(
        app, AppDatabase::class.java, "notas.db"
    ).fallbackToDestructiveMigration(false).build()

    // 2) El DAO es quien hace las operaciones con la base de datos (guardar, borrar, leer...)
    private val dao = db.notaDao()

    // 3) Lista de tareas “observable”: cuando la BD cambia, esto se actualiza y la UI se entera
    val notas: StateFlow<List<Nota>> =
        dao.getAllNotas().stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    // 4) Añadir una tarea (se hace en segundo plano para no bloquear la app)
    fun addNota(asignatura: String, trimestre: String, valor: String) = viewModelScope.launch {
        dao.insert(Nota(asignatura = asignatura, trimestre = trimestre, valor = valor))
    }

    // 5) Borrar una tarea (también en segundo plano)
    fun deleteNota(nota: Nota) = viewModelScope.launch {
        dao.delete(nota)
    }
}
