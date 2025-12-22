package es.fpsumma.dam2.utilidades.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.fpsumma.dam2.utilidades.model.Tarea
import es.fpsumma.dam2.utilidades.model.Nota

// Esto le dice a Room: “esta es mi base de datos”
// - entities: qué tablas tendrá (aquí, la tabla Tarea)
// - version: versión del esquema (si cambias campos/tabla, suele subir)
// - exportSchema=false: no guarda el “historial” del esquema en archivos

//lo tercero que hacemos es añadir la base de datos al archivo que la va a leer
//añado a este archivo la base de datos de notas porque no es necesario crear un nuevo AppDatabase
@Database(entities = [Tarea::class, Nota::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Room necesita que le digamos qué DAO vamos a usar para acceder a la BD
    // (el DAO contiene métodos como insertar, borrar, consultar, etc.)
    abstract fun tareaDao(): TareaDao
    abstract fun notaDao(): NotaDao

    companion object {
        // Guardamos aquí la única instancia de la BD (para no crear 20 bases de datos)
        @Volatile
        private var Instance: AppDatabase? = null

        // Función para obtener la BD desde cualquier parte de la app
        fun getDatabase(context: Context): AppDatabase {

            // Si ya existe, la devolvemos.
            // Si no existe, la creamos (una sola vez).
            return Instance ?: synchronized(this) {

                // synchronized: evita que 2 hilos creen la BD a la vez
                Room.databaseBuilder(
                                context,
                                AppDatabase::class.java,
                                "utilidades" // nombre del archivo de la base de datos en el móvil
                            ).fallbackToDestructiveMigration(false)
                    .build()
                    .also { Instance = it } // guardamos la BD creada para reutilizarla
            }
        }
    }
}