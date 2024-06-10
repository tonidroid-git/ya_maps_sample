package com.tonib.yandexmapsample

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.File

class MBTileLoader {

    private var database: SQLiteDatabase? = null

    fun initDatabase(file: File) {
        database = SQLiteDatabase
            .openDatabase(
                file.absolutePath,
                null,
                SQLiteDatabase.NO_LOCALIZED_COLLATORS or SQLiteDatabase.OPEN_READONLY
            )

        Log.d("DBT", "0")
    }

    fun getTile(x: Int, y: Int, zoom: Int): ByteArray? {
        if (database == null) {
            throw Exception("Database not initialized!")
        }
        try {
            val tile =
                arrayOf(COL_TILES_TILE_DATA)
            val xyz = arrayOf(
                x.toString(),
                y.toString(),
                zoom.toString()
            )

            val cursor = database!!.query(TABLE_TILES, tile, QUERY_SELECTION, xyz, null, null, null)


            Log.d("DBT", "1")

            if (cursor.count != 0) {
                cursor.moveToFirst()
                val bytes = cursor.getBlob(0)
                cursor.close()
                Log.d("DBT", "bytes: ${bytes.size}")
                return bytes
            }
        } catch (e: Exception) {
            Log.d("DBT", "2")
            Log.d(javaClass.name, "Unable to get tile: $e")
        }
        return null
    }

    fun dispose() {
        database?.close()
        database = null
    }

    companion object {
        private const val TABLE_TILES: String = "tiles"
        private const val COL_TILES_ZOOM_LEVEL: String = "zoom_level"
        private const val COL_TILES_TILE_COLUMN: String = "tile_column"
        private const val COL_TILES_TILE_ROW: String = "tile_row"
        private const val COL_TILES_TILE_DATA: String = "tile_data"
        private const val QUERY_SELECTION: String = "tile_column=? and tile_row=? and zoom_level=?"
    }

}