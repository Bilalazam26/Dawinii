package com.grad.dawinii.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.grad.dawinii.model.entities.Appointment
import com.grad.dawinii.model.entities.User

data class UserAndAppointment (
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val appointments : List<Appointment>
        )
