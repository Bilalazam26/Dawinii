package com.grad.dawinii.repository

import androidx.lifecycle.MutableLiveData
import com.grad.dawinii.datasource.local.DawiniiDao
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.model.entities.User

class LocalRepository(private val dao: DawiniiDao) {

    var userMutableLiveData: MutableLiveData<User> = MutableLiveData()

    suspend fun insertRoutine(routine: Routine, medicineList: MutableList<Medicine>) {
        dao.insertRoutine(routine)
        medicineList.forEach { medicine -> dao.insertMedicine(medicine) }
    }

    suspend fun getLocalUser(userId: String) {
        userMutableLiveData.postValue(dao.getUser(userId)[0])
    }

    suspend fun updateLocalUser(user: User) {
        dao.updateUser(user)
    }
}