package com.grad.dawinii.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.grad.dawinii.model.entities.Note
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.model.entities.User

data class UserAndNote(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val notes : List<Note>
)