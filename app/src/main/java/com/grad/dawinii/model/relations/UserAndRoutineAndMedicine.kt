package com.grad.dawinii.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.model.entities.User

data class UserAndRoutineAndMedicine(
    @Embedded val user: User,
    @Relation(
        entity = Routine::class,
        parentColumn = "id",
        entityColumn = "userId"
        )
    val Routines: List<RoutineAndMedicine>
)
