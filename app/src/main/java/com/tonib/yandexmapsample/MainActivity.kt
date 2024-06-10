package com.tonib.yandexmapsample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RawTile
import com.yandex.mapkit.TileId
import com.yandex.mapkit.Version
import com.yandex.mapkit.ZoomRange
import com.yandex.mapkit.geometry.LinearRing
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polygon
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.geometry.geo.Projection
import com.yandex.mapkit.geometry.geo.XYPoint
import com.yandex.mapkit.images.ImageDataDescriptor
import com.yandex.mapkit.images.ImageUrlProvider
import com.yandex.mapkit.layers.LayerOptions
import com.yandex.mapkit.layers.TileFormat
import com.yandex.mapkit.map.CreateTileDataSource
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectDragListener
import com.yandex.mapkit.map.MapType
import com.yandex.mapkit.map.PolygonMapObject
import com.yandex.mapkit.map.PolylineMapObject
import com.yandex.mapkit.map.TileDataSourceBuilder
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.tiles.TileProvider
import com.yandex.mapkit.tiles.UrlProvider
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var map: Map

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(BuildConfig.key)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        MapKitFactory.initialize(this)

        mapView = findViewById(R.id.map)
        map = mapView.mapWindow.map

        val urlTileProvider = UrlProvider { tileId, _, _ ->
            "https://tile.openstreetmap.org/${tileId.z}/${tileId.x}/${tileId.y}.png "
        }

        map.addTileLayer(
            "OpenStreetMap",
            LayerOptions().apply {
                transparent = false
                active = true
            }
        ) { builder ->
            builder.apply {
                setTileFormat(TileFormat.PNG)
                setTileUrlProvider(urlTileProvider)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}