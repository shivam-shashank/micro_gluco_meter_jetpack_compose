package com.example.microglucometer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserDetail::class], version = 1, exportSchema = false)
abstract class UserDetailDatabase : RoomDatabase() {
    abstract fun userDetailDao(): UserDetailDatabaseDao

    companion object {

        private var INSTANCE: UserDetailDatabase? = null

        fun getInstance(context: Context): UserDetailDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDetailDatabase::class.java,
                        "user_detail_list_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
