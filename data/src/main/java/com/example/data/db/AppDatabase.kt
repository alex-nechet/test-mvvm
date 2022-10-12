package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.model.UserDb

@Database(entities = [UserDb::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao() : UsersDao
}