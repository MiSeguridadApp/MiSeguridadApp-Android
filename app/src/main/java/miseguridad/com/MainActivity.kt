package miseguridad.com

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_incidencias) // Usamos el layout correspondiente

        // Inicializar ListView
        val listViewIncidencias: ListView = findViewById(R.id.listViewIncidencias)

        // Crear una lista de incidencias
        val incidencias = listOf("Incidencia 1", "Incidencia 2", "Incidencia 3", "Incidencia 4")

        // Crear un adapter para el ListView
        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, incidencias) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)

                // Obtener el texto y asignar el valor
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.text = getItem(position) // Asigna el texto de la incidencia

                // Crear el botón de edición
                val btnEdit = Button(context).apply {
                    text = "Editar"
                    setOnClickListener {
                        // Falta lógica al presionar
                    }
                }

                // Agregar el botón al final de cada ítem
                val linearLayout = view.findViewById<LinearLayout>(android.R.id.text2) // Este es el contenedor del layout
                linearLayout?.addView(btnEdit)

                return view
            }
        }

        // Configurar el adapter para el ListView
        listViewIncidencias.adapter = adapter
    }
}
