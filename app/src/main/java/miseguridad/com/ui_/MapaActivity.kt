package miseguridad.com.ui_
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.Plugin
import miseguridad.com.R

class MapaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // No es necesario llamar a Mapbox.getInstance() en las versiones más recientes.

        setContent {
            // Muestra el mapa con la configuración básica
            MapboxMap(
                modifier = Modifier.fillMaxSize(),
                mapViewportState = rememberMapViewportState {
                    setCameraOptions {
                        zoom(12.0) // Ajusta el nivel de zoom
                        center(Point.fromLngLat(-78.0628, -8.1080)) // Ubicación central (en latitud/longitud)
                        pitch(0.0) // Ángulo de inclinación
                        bearing(0.0) // Orientación
                    }
                }
            )
        }
    }
}