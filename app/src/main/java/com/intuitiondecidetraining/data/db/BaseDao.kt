package com.intuitiondecidetraining.data.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

abstract class BaseDao<Item> {

    abstract fun getById(id: Long): Flow<Item?>

    /**
     * Returns ID of the inserted item. -1 on conflict.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(item: Item): Long

    /**
     * Returns number of updated rows.
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(item: Item): Int

    /**
     * Returns number of deleted rows.
     */
    @Delete
    abstract suspend fun delete(item: Item): Int

    /**
     * Returns ID of inserted item or null if updated.
     */
    @Transaction
    open suspend fun insertOrUpdate(item: Item): Long? {
        return when (val result = insert(item)) {
            -1L -> {
                update(item)
                null
            }
            else -> {
                result
            }
        }
    }
}