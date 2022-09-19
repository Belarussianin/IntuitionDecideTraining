package com.intuitiondecidetraining.data.repository

import com.intuitiondecidetraining.data.db.test.Test
import com.intuitiondecidetraining.data.db.test.TestDao
import kotlinx.coroutines.flow.Flow

class TestRepository(
    private val testDao: TestDao
) : DataRepository<Long, Test> {

    override fun getAll(): Flow<List<Test>> = testDao.getAll()

    override suspend fun getByKey(key: Long): Flow<Test?> = testDao.getById(key)

    override suspend fun insertOrUpdate(data: Test): Long? = testDao.insertOrUpdate(data)

    override suspend fun insert(data: Test): Long = testDao.insert(data)

    override suspend fun update(data: Test) = testDao.update(data)

    override suspend fun delete(data: Test) = testDao.delete(data)
}