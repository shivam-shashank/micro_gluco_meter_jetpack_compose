package com.example.microglucometer.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.microglucometer.database.UserDetail
import com.example.microglucometer.database.UserDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val readAllData: LiveData<List<UserDetail>>
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

//class UserDetailViewModelFactory(
//    private val application: Application
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        @Suppress("UNCHECKED_CAST")
//        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
//            return TodoViewModel(application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
