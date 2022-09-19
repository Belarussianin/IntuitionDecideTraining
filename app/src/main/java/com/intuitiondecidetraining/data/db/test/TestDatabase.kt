package com.intuitiondecidetraining.data.db.test

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Test::class], version = DATABASE_VERSION, exportSchema = false)
abstract class TestDatabase : RoomDatabase() {

    abstract fun testDao(): TestDao

    companion object {

        @Volatile
        private var INSTANCE: TestDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): TestDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TestDatabase::class.java,
                    DATABASE_NAME
                )
                    .addCallback(TestDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class TestDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase()
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                val cursor = db.query("SELECT * FROM $TABLE_NAME")
                if (!cursor.moveToFirst()) {
                    populateDatabase()
                }
            }

            private fun populateDatabase() {
                INSTANCE?.let { database ->
                    scope.launch {
                        getSampleList().forEach {
                            database.testDao().insert(it)
                        }
                    }
                }
            }
        }

        fun getSampleList(): List<Test> {
            return List(10) { Test(title = "$it in ${it + 2}", tries = it, variants = it + 2) }
        }
    }
}