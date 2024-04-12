package bhuwan.example.mapsfinalproject.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "LocationDB"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Location"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_LATITUDE = "latitude"
        const val COLUMN_LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            " CREATE TABLE $TABLE_NAME($COLUMN_ID Integer Primary Key, $COLUMN_NAME Text, $COLUMN_DESCRIPTION Text, $COLUMN_LATITUDE  Real, $COLUMN_LONGITUDE Real)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(query)
        onCreate(db)
    }

    fun insert(data: DatabaseDataClass, context: Context) {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, data.name)
        contentValues.put(COLUMN_DESCRIPTION, data.description)
        contentValues.put(COLUMN_LATITUDE, data.latitude)
        contentValues.put(COLUMN_LONGITUDE, data.longitude)
        db.insert(TABLE_NAME, null, contentValues)
        Toast.makeText(context, "Data added successfully", Toast.LENGTH_SHORT).show()
    }

    fun read(): ArrayList<DatabaseDataClass> {
        val locationArray = ArrayList<DatabaseDataClass>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            val latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LATITUDE))
            val longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE))

            val locations = DatabaseDataClass(id, name, description, latitude, longitude)
            locationArray.add(locations)
        }
        cursor.close()
        return locationArray
    }
}
