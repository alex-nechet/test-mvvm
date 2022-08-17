package com.alex.android.git.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alex.android.git.data.model.UserDb

@Database(entities = [UserDb::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao() : UsersDao
}