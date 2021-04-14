package com.adriantache.ikigai.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adriantache.ikigai.model.AnswerEntity

@Entity(tableName = "answers")
data class AnswerRoomEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "answer") val answer: String
) {
    fun toEntity(): AnswerEntity {
        return AnswerEntity(enumValueOf(category), answer)
    }

    companion object {
        fun fromEntity(entity: AnswerEntity): AnswerRoomEntity {
            return AnswerRoomEntity(
                category = entity.category.name,
                answer = entity.answer
            )
        }
    }
}
