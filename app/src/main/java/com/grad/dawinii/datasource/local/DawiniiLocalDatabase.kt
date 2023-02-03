package com.grad.dawinii.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.grad.dawinii.model.entities.*

@Database(entities = [User::class, Routine::class, Medicine::class, Appointment::class, RoutineMedicineCrossRef::class], version = 1)
abstract class DawiniiLocalDatabase: RoomDatabase() {

    abstract fun dawiniiDao(): DawiniiDao

    companion object {
        @Volatile
        private var dawiniiLocalDbInstance: DawiniiLocalDatabase? = null

        fun getUserDbInstance(context: Context): DawiniiLocalDatabase {
            if (dawiniiLocalDbInstance == null ) {
                synchronized(this) {
                    dawiniiLocalDbInstance = Room.databaseBuilder(context.applicationContext,
                        DawiniiLocalDatabase::class.java,
                        "dawinii_local_database").build()
                }
            }
            return dawiniiLocalDbInstance as DawiniiLocalDatabase
        }
    }
}