package com.grad.dawinii.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.model.entities.RoutineMedicineCrossRef

data class RoutineAndMedicine (
    @Embedded val routine: Routine,
    @Relation(
            parentColumn = "routineId",
            entityColumn = "medicineName",
            associateBy = Junction(RoutineMedicineCrossRef::class)
    )
    val medicines: List<Medicine>
)
