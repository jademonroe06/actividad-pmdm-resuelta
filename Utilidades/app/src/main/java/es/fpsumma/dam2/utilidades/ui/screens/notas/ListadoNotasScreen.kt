package es.fpsumma.dam2.utilidades.ui.screens.notas

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import es.fpsumma.dam2.utilidades.model.Nota
import es.fpsumma.dam2.utilidades.ui.viewmodel.NotasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListadoNotasScreen(navController: NavController, vm: NotasViewModel, modifier: Modifier = Modifier) {

    val notas by vm.notas.collectAsState()

    //estos son los estados del formulario
    var asignatura by rememberSaveable { mutableStateOf("") }
    var valorNota by rememberSaveable { mutableStateOf("") }

    //aquí hago la lógica para los RadioButtons
    //con esto puedo hacer que haya varias opciones para seleccionar y que se guarden
    val trimestresOptions = listOf("1ºT", "2ºT", "3ºT")
    var selectedTrimestre by rememberSaveable { mutableStateOf(trimestresOptions[0]) }

    fun handleAddNota() {
        if (asignatura.isNotEmpty() && valorNota.isNotEmpty()) {
            vm.addNota(asignatura, selectedTrimestre, valorNota)
            asignatura = ""
            valorNota = ""
            //selectedTrimestre -> no lo reseteo/reinicio para facilitar meter varias notas del mismo trimestre
        }
    }

    fun handleDeleteNota(nota: Nota) {
        vm.deleteNota(nota)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Calificaciones") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            //sección del formulario
            Text("Nueva Nota", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = asignatura,
                onValueChange = { asignatura = it },
                label = { Text("Asignatura") },
                singleLine = true,
                //Restringe el texto a una sola línea que se desplaza
                //horizontalmente en lugar de permitir que se ajuste a varias líneas,
                //y avanza el foco en lugar de insertar una nueva línea cuando presiona
                //la tecla Intro (Enter).
                modifier = Modifier.fillMaxWidth()
            )

            //radioButtons (los trimestres)
            Text("Trimestre:", modifier = Modifier.padding(top = 8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                trimestresOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = (text == selectedTrimestre),
                                onClick = { selectedTrimestre = text }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedTrimestre),
                            onClick = { selectedTrimestre = text }
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

            OutlinedTextField(
                value = valorNota,
                onValueChange = { valorNota = it },
                label = { Text("Nota (ej: 8.5)") },
                singleLine = true,
                //Restringe el texto a una sola línea que se desplaza
                //horizontalmente en lugar de permitir que se ajuste a varias líneas,
                //y avanza el foco en lugar de insertar una nueva línea cuando presiona
                //la tecla Intro (Enter).
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = ::handleAddNota,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) { Text("Guardar") }

            HorizontalDivider(modifier.padding(vertical = 8.dp))

            //sección del listado de notas
            Text("Registro de Notas", style = MaterialTheme.typography.titleMedium)

            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = notas, key = { it.id }) { nota ->
                    Card(
                        modifier = modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            //columna con la asignatura y el trimestre
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = nota.asignatura,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = nota.trimestre,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }

                            //la nota (en grande para que se vea mejor)
                            Text(
                                text = nota.valor,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            //botón para borrar
                            IconButton(onClick = { handleDeleteNota(nota) }) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Borrar nota",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}