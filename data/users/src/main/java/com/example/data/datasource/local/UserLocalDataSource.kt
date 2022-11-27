package com.example.data.datasource.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.example.data.db.AppDatabase
import com.example.data.db.model.UserDb

interface UserLocalDataSource {
    fun getAllUsers(): PagingSource<Int, UserDb>?
    suspend fun insertAll(users: List<UserDb>)
    suspend fun deleteAll()
    suspend fun getUserDetails(userId: Long): UserDb?
}

class UserLocalDataSourceImpl(private val appDatabase: AppDatabase) : UserLocalDataSource {
    private val dao = appDatabase.usersDao()

    override fun getAllUsers() = dao.getAll()

    override suspend fun deleteAll() = appDatabase.withTransaction {
        appDatabase.usersDao().deleteAll()
    }

    override suspend fun insertAll(users: List<UserDb>) = appDatabase.withTransaction {
        dao.insertAll(users)
    }

    override suspend fun getUserDetails(userId: Long) = dao.getUser(userId)
}