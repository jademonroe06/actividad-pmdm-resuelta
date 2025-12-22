package es.fpsumma.dam2.utilidades.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Esta clase representa una "fila" de la tabla "tareas" en la base de datos.
// Room la convierte en una tabla SQLite gracias a @Entity.
@Entity(tableName = "tareas")
data class Tarea(

    // Clave primaria (identificador único) de cada tarea.
    // autoGenerate = true => Room genera el id automáticamente al insertar.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Columna de la tabla donde guardamos el título.
    // name = nombre real de la columna en la BD (puede ser distinto al nombre del atributo).
    @ColumnInfo(name = "titulo")
    val titulo: String,

    // Columna de la tabla donde guardamos la descripción.
    @ColumnInfo(name = "descripcion")
    val descripcion: String,

    @ColumnInfo(name = "prioridad", defaultValue = "1")
    val prioridad: Int = 1,

    // columna opcional (puede ser null)
    @ColumnInfo(name = "categoria")
    val categoria: String? = null
)