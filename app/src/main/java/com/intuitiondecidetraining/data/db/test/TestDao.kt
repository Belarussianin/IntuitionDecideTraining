package com.intuitiondecidetraining.data.db.test

import androidx.room.Dao
import androidx.room.Query
import com.intuitiondecidetraining.data.db.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TestDao : BaseDao<Test>() {

    @Query("SELECT * FROM $TABLE_NAME")
    abstract fun getAll(): Flow<List<Test>>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME_ID = :id")
    abstract override fun getById(id: Long): Flow<Test?>
}