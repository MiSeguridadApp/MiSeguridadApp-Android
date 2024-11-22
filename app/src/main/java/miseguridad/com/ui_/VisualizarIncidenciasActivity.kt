package miseguridad.com.ui_

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.apisecurityapp.api.ApiClient
import com.example.apisecurityapp.model.IncidenciaResponse
import kotlinx.coroutines.launch
import miseguridad.com.R
import retrofit2.HttpException

class VisualizarIncidenciasActivity : AppCompatActivity() {

    private lateinit var listViewIncidencias: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_incidencias)

        listViewIncidencias = findViewById(R.id.listViewIncidencias)
        val btnBack: ImageView = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish() // Regresar a la actividad anterior
        }

        obtenerIncidencias()
    }

    private fun obtenerIncidencias() {
        lifecycleScope.launch {
            try {
                // Realizar la solicitud GET para obtener las incidencias
                val response = ApiClient.apiService.obtenerIncidencias()

                if (response.isSuccessful) {
                    val incidencias = response.body() ?: emptyList() // Si no hay incidencias, usar lista vacía

                    // Crear el adapter para mostrar las incidencias en el ListView
                    val incidenciasStrings = incidencias.map { incidencia ->
                        "Tipo: ${incidencia.tipo}\nUbicación: ${incidencia.ubicacion}\nEstado: ${getEstado(incidencia.estado)}"
                    }

                    val adapter = ArrayAdapter(
                        this@VisualizarIncidenciasActivity,
                        android.R.layout.simple_list_item_1,
                        incidenciasStrings
                    )
                    listViewIncidencias.adapter = adapter
                } else {
                    Toast.makeText(this@VisualizarIncidenciasActivity, "Error al obtener incidencias", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                // Manejar excepciones HTTP de Retrofit
                Toast.makeText(this@VisualizarIncidenciasActivity, "Error en la solicitud: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                // Manejar errores generales
                Toast.makeText(this@VisualizarIncidenciasActivity, "Error desconocido: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getEstado(estado: Int): String {
        return when (estado) {
            1 -> "Registrado"
            2 -> "Atendido"
            3 -> "Solucionado"
            else -> "Desconocido"
        }
    }
}
