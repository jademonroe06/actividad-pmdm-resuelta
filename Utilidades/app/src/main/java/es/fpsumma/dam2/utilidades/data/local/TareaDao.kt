package es.fpsumma.dam2.utilidades.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.fpsumma.dam2.utilidades.model.Tarea
import kotlinx.coroutines.flow.Flow

// @Dao indica que esta interfaz es un DAO de Room:
// aquí declaramos las operaciones típicas para la tabla "tareas".
@Dao
interface TareaDao {

    // Inserta una tarea en la tabla.
    // onConflict = IGNORE significa:
    // si hay un conflicto (por ejemplo, mismo id), NO se inserta y no da error.
    // suspend => se llama desde una corrutina (para no bloquear la app).
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tarea: Tarea)

    // Actualiza una tarea existente (mismo id). Cambia sus campos en la BD.
    @Update
    suspend fun update(tarea: Tarea)

    // Borra esa tarea de la BD.
    @Delete
    suspend fun delete(tarea: Tarea)

    // Devuelve la tarea cuyo id coincida con el parámetro.
    // :id es un parámetro que se sustituye por el valor que pasamos a la función.
    // Devuelve Flow => si esa tarea cambia en la BD, la UI puede enterarse automáticamente.
    @Query("SELECT * from tareas WHERE id = :id")
    fun getTarea(id: Int): Flow<Tarea>

    // Devuelve todas las tareas ordenadas por título de la A a la Z.
    // Flow<List<Tarea>> => la lista se actualiza sola cuando se insertan/borran/actualizan tareas.
    @Query("SELECT * from tareas ORDER BY titulo ASC")
    fun getAllTareas(): Flow<List<Tarea>>
}