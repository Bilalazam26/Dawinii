package com.grad.dawinii.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.model.entities.User

data class UserAndMedicine (
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val medicines : List<Medicine>
)
