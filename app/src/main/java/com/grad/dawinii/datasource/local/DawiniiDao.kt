package com.grad.dawinii.datasource.local

import androidx.room.*
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.model.entities.User
import com.grad.dawinii.model.relations.RoutineAndMedicine
import com.grad.dawinii.model.relations.UserAndAppointment
import com.grad.dawinii.model.relations.UserAndRoutine
import com.grad.dawinii.model.relations.UserAndRoutineAndMedicine

@Dao
interface DawiniiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: Routine)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: Medicine)

    @Transaction //To prevent multithreading issues
    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUserWithRoutines(userId: String): List<UserAndRoutine>

    @Transaction //To prevent multithreading issues
    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUserWithAppointments(userId: String): List<UserAndAppointment>

    @Transaction //To prevent multithreading issues
    @Query("SELECT * FROM routine WHERE id = :routineId")
    suspend fun getRoutineWithMedicines(routineId: String): List<RoutineAndMedicine>

    //To get the User and its Routines with its Medicines
    @Transaction
    @Query("SELECT * FROM User WHERE id = :userId")
    fun getUserWithRoutinesAndMedicines(userId: String): List<UserAndRoutineAndMedicine>

}