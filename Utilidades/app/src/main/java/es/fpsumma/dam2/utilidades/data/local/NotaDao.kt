package es.fpsumma.dam2.utilidades.data.local

//lo segundo que hacemos es crear la base de datos

// @Dao indica que esta interfaz es un DAO de Room:
// aquí declaramos las operaciones típicas para la tabla "notas".

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.fpsumma.dam2.utilidades.model.Nota
import es.fpsumma.dam2.utilidades.model.Tarea
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {

    // Inserta una nota en la tabla.
    // onConflict = IGNORE significa:
    // si hay un conflicto (por ejemplo, mismo id), NO se inserta y no da error.
    // suspend => se llama desde una corrutina (para no bloquear la app).
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nota: Nota)

    //Actualiza una nota existente (mismo id). Cambia sus campos en la BD.
    @Update
    suspend fun update(nota: Nota)

    //Borra esa nota de la BD.
    @Delete
    suspend fun delete(nota: Nota)

    //Devuelve la nota cuyo id coincida con el parámetro.
    //:id es un parámetro que se sustituye por el valor que pasamos a la función.
    //Devuelve Flow => si esa nota cambia en la BD, la UI puede enterarse automáticamente.
    @Query("SELECT * from notas WHERE id = :id")
    fun getNota(id: Int): Flow<Nota>

    //Devuelve todas las notas ordenadas por título de la A a la Z.
    //Flow<List<Nota>> => la lista se actualiza sola cuando se insertan/borran/actualizan notas.
    @Query("SELECT * from notas ORDER BY asignatura ASC")
    fun getAllNotas(): Flow<List<Nota>>
}