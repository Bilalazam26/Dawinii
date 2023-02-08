package com.grad.dawinii.repository

import com.grad.dawinii.datasource.local.DawiniiDao
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.model.entities.Routine

class RoutineRepository(private val dao: DawiniiDao) {

    /*suspend fun insertMedicine(medicine: Medicine) {
        dao.insertMedicine(medicine)
    }*/

    suspend fun insertRoutine(routine: Routine, medicineList: List<Medicine>) {
        dao.insertRoutine(routine)
        medicineList.forEach { medicine -> dao.insertMedicine(medicine) }
    }
}