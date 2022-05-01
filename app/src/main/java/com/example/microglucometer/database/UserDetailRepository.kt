package com.example.microglucometer.database

import androidx.lifecycle.LiveData

class UserDetailRepository(private val userDetailDatabaseDao: UserDetailDatabaseDao) {

    val readAllData: LiveData<List<UserDetail>> = userDetailDatabaseDao.getAll()

    suspend fun addUserDetail(userDetail: UserDetail) {
        userDetailDatabaseDao.insert(userDetail)
    }
}
