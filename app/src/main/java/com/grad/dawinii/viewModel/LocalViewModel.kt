package com.grad.dawinii.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.grad.dawinii.datasource.local.DawiniiDao
import com.grad.dawinii.datasource.local.LocalDatabase
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.model.entities.User
import com.grad.dawinii.repository.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalViewModel(application: Application): AndroidViewModel(application) {

    private val localRepository:LocalRepository
    var userMutableLiveData: MutableLiveData<User>
    var  routinesMutableLiveData: MutableLiveData<List<Routine>>

    init {
        val dao = LocalDatabase.localDbInstance(application)?.dawinniDao() as DawiniiDao
        localRepository = LocalRepository(dao)
        userMutableLiveData = localRepository.userMutableLiveData
        routinesMutableLiveData = localRepository.routinesMutableLiveData
    }

    fun insertRoutine(routine: Routine, medicineList: MutableList<Medicine>) = viewModelScope.launch(Dispatchers.IO) {
        localRepository.insertRoutine(routine, medicineList)
    }

    fun getLocalUser(userId: String)  = viewModelScope.launch(Dispatchers.IO) { localRepository.getLocalUser(userId) }

    fun updateLocalUser(user: User) = viewModelScope.launch(Dispatchers.IO) { localRepository.updateLocalUser(user) }

    fun getUserWithRoutines(userId: String) = viewModelScope.launch(Dispatchers.IO) { localRepository.getUserWithRoutines(userId) }
}