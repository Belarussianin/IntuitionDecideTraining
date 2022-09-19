package com.intuitiondecidetraining.di

import com.intuitiondecidetraining.data.db.test.TestDatabase
import com.intuitiondecidetraining.data.repository.TestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val appModule = module {
    factory { CoroutineScope(SupervisorJob()) }

    // Test Room Database
    single { TestDatabase.getDatabase(get(), get()) }
    // TestRepository
    single { TestRepository(get<TestDatabase>().testDao()) }
}