package com.example.microglucometer.view_model

import android.app.Application
import androidx.lifecycle.*
import com.example.microglucometer.database.UserDetail
import com.example.microglucometer.database.UserDetailDatabase
import com.example.microglucometer.database.UserDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<UserDetail>>
    private val repository: UserDetailRepository

    init {
        val userDetailDao = UserDetailDatabase.getInstance(application).userDetailDao()
        repository = UserDetailRepository(userDetailDao)
        readAllData = repository.readAllData
    }

    fun addUserDetail(userDetail: UserDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserDetail(userDetail)
        }
    }

}
