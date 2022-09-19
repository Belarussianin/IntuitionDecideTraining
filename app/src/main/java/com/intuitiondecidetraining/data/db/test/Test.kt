package com.intuitiondecidetraining.data.db.test

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_NAME)
data class Test(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = COLUMN_NAME_ID)
    val id: Long = 0L,
    @ColumnInfo(name = COLUMN_NAME_TITLE)
    val title: String = "",
    @ColumnInfo(name = COLUMN_NAME_COUNT_OF_TRIES)
    val tries: Int = 1,
    @ColumnInfo(name = COLUMN_NAME_COUNT_OF_VARIANTS)
    val variants: Int = 2
)