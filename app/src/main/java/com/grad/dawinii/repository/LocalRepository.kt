package com.grad.dawinii.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.grad.dawinii.datasource.local.DawiniiDao
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.model.entities.User
import com.grad.dawinii.model.relations.RoutineAndMedicine

class LocalRepository(private val dao: DawiniiDao) {

    var userMutableLiveData: MutableLiveData<User> = MutableLiveData()
    var routinesMutableLiveData = MutableLiveData<List<Routine>>()
    var medicinesMutableLiveData = MutableLiveData<List<Medicine>>()
    val medicinesForRoutineMutableLiveData = MutableLiveData<List<Medicine>>()

    suspend fun insertRoutine(routine: Routine, medicineList: MutableList<Medicine>) {
        dao.insertRoutine(routine)
        medicineList.forEach {
                medicine -> dao.insertMedicine(medicine)
            Log.d("TestInsertMedicine", "insertRoutine: $medicine")
        }
    }

    suspend fun getLocalUser(userId: String) {
        userMutableLiveData.postValue(dao.getUser(userId)[0])
    }

    suspend fun updateLocalUser(user: User) {
        dao.updateUser(user)
    }
    suspend fun getUserWithRoutines(userId: String) {
        val userWithRoutines = dao.getUserWithRoutines(userId)
        val routines = mutableListOf<Routine>()
        for (i in userWithRoutines) {
            routines.addAll(i.routines)

        }
        routinesMutableLiveData.postValue(routines)
    }

    suspend fun getUserWithMedicines(userId: String) {
        val userWithMedicines = dao.getUserWithMedicines(userId)
        val medicines = mutableListOf<Medicine>()
        for (i in userWithMedicines) {
            medicines.addAll(i.medicines)
        }
        medicinesMutableLiveData.postValue(medicines)
    }

    suspend fun getMedicinesWithRoutine(routineId: Int) {
        val medicinesWithRoutine = dao.getMedicinesWithRoutine(routineId)
        val medicines = mutableListOf<Medicine>()
        for (i in medicinesWithRoutine) {
            medicines.addAll(i.medicines)
            Log.d("TestMedicines", "getMedicinesForRoutine: $medicines")
        }
        medicinesForRoutineMutableLiveData.postValue(medicines)
    }


    suspend fun deleteRoutine(routine: Routine) {
        dao.deleteRoutine(routine)
        deleteMedicines(routine.routineName)

    }

    private suspend fun deleteMedicines(routineName: String) {
        dao.deleteMedicines(routineName)
    }

    suspend fun getMedicinesWithRoutineName(routineName: String) {
        val medicines = dao.getMedicinesWithRoutineName(routineName)
        medicinesForRoutineMutableLiveData.postValue(medicines)
        Log.d("TestMedicines", "getMedicinesForRoutine: $medicines")

    }
}