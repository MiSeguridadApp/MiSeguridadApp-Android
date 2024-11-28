package miseguridad.com.ui_

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import miseguridad.com.R

class MapaActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_mapa)

        // Obtén la referencia al MapView
        mapView = findViewById(R.id.mapView)

        // Carga un estilo básico
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}