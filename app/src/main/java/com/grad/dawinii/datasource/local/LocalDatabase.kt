package com.grad.dawinii.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.grad.dawinii.model.entities.*

@Database(entities = [
    User::class,
    Routine::class,
    Medicine::class,
    Appointment::class,
    RoutineMedicineCrossRef::class],
    version = 1)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun dawinniDao(): DawiniiDao

    companion object {
        private var instance: LocalDatabase? = null
        fun localDbInstance(context: Context): LocalDatabase? {
            if (instance == null) {
                synchronized(LocalDatabase::class) {
                    instance = buildRoomDb(context)
                }
            }
            return instance
        }

        private fun buildRoomDb(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            LocalDatabase::class.java,
            "local_db").build()
    }
}