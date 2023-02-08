package com.grad.dawinii.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.grad.dawinii.datasource.local.DawiniiDao
import com.grad.dawinii.datasource.local.LocalDatabase
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.repository.RoutineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoutineViewModel(application: Application): AndroidViewModel(application) {

    private val routineRepository:RoutineRepository

    init {
        val dao = LocalDatabase.localDbInstance(application)?.dawinniDao() as DawiniiDao
        routineRepository = RoutineRepository(dao)
    }

    fun insertRoutine(routine: Routine, medicineList: List<Medicine>) = viewModelScope.launch(Dispatchers.IO) {
        routineRepository.insertRoutine(routine, medicineList)
    }
}