package es.fpsumma.dam2.utilidades.model

//lo primero que hacemos es crear la entidad para la base de datos

// Esta clase representa una "fila" de la tabla "notas" en la base de datos.
// Room la convierte en una tabla SQLite gracias a @Entity.

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notas")
data class Nota(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "asignatura")
    val asignatura: String,

    @ColumnInfo(name = "trimestre")
    val trimestre: String, //Guardo 1ºT", "2ºT", etc.

    @ColumnInfo(name = "valor")
    val valor: String //Uso String para evitar problemas al escribir "8.5", pero podría ser Double
)