package com.example.microglucometer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDetailDatabaseDao {
    @Query("SELECT * from user_detail_list")
    fun getAll(): LiveData<List<UserDetail>>

    @Insert
    suspend fun insert(item: UserDetail)
}
