package com.example.firebase_example

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class ContenedorMaps : AppCompatActivity(),
    OnMapReadyCallback,
    GoogleMap.OnCameraMoveListener,
    GoogleMap.OnCameraMoveStartedListener,
    GoogleMap.OnCameraIdleListener,
    GoogleMap.OnPolylineClickListener,
    GoogleMap.OnPolygonClickListener,
    GoogleMap.OnMarkerClickListener {
    private lateinit var mMap: GoogleMap
    var tienePermisos = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contenedor_maps)
        solicitarPermisos()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val btnCarolina = findViewById<Button>(R.id.btn_irCarolina)
        btnCarolina.setOnClickListener{
            val carolina = LatLng(-0.1819882619985097, -78.48386118871714)
            val zoom = 17f
            moverCamaraConZoom(carolina,zoom)
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            mMap = googleMap
            mMap
                .setOnPolygonClickListener {
                    Log.i("maps","OnPOlygonclick ${it}")
                }
            establecerConfiguracionMapa(googleMap)
            val quicentro = LatLng(-0.17617322571324992, -78.47921292858925)
            val titulo = "Quicentro"
            val zoom = 17f
            //LINEA
            val poliLineaUno = googleMap
                .addPolyline(
                    (
                            PolylineOptions()
                                .clickable(true)
                                .add(
                                    LatLng(-0.17582994015932804, -78.48329256030682),
                                    LatLng(-0.1766667853963633, -78.4781105329643),
                                    LatLng(-0.1770422928622918, -78.48174760805563)
                                )
                            )
                )


            //POLIGONO
            val poligonoUno = googleMap.addPolygon(
                PolygonOptions()
                    .clickable(true)
                    .add(
                        LatLng(-0.17880181345817522, -78.48282049156342),
                        LatLng(-0.17983177665568592, -78.48236988049015),
                        LatLng(-0.18001416596586567, -78.47907612812129)
                    )
            )
            poligonoUno.fillColor = -0xc771c4

            anadirMarcador(quicentro, titulo)
            moverCamaraConZoom(quicentro, zoom)
        }
    }

    fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 10f) {
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(latLng, zoom)
        )
    }

    fun anadirMarcador(latLng: LatLng, title: String) {
        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )
    }

    fun establecerConfiguracionMapa(mapa: GoogleMap) {
        val contexto = this.applicationContext
        with(mapa) {
            val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if (tienePermisos) {
                mapa.isMyLocationEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }
    }

    fun solicitarPermisos() {

        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )

        tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED

        if (tienePermisos) {
            Log.i("mapa", "Tiene permisos FINE LOCATION")
            this.tienePermisos = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        }
    }

    override fun onCameraMove() {
        Log.i("maps","OnCameraMove")
    }

    override fun onCameraMoveStarted(p0: Int) {
        Log.i("maps","OnCameraMoveStarted")
    }

    override fun onCameraIdle() {
        Log.i("maps","OnCameraIdle")
    }

    override fun onPolylineClick(p0: Polyline?) {
        Log.i("maps","OnPolylineClick")
    }

    override fun onPolygonClick(p0: Polygon?) {
        Log.i("maps","OnPolygonClick")
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        Log.i("maps","OnMarkerClick")
        return true
    }
}