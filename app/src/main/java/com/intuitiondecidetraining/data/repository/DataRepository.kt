package com.intuitiondecidetraining.data.repository

import kotlinx.coroutines.flow.Flow

interface DataRepository<Key, Data> {

    fun getAll(): Flow<List<Data>>

    suspend fun getByKey(key: Key): Flow<Data?>

    suspend fun insertOrUpdate(data: Data): Key?

    suspend fun insert(data: Data): Key?

    suspend fun update(data: Data): Int

    suspend fun delete(data: Data): Int
}