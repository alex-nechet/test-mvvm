package com.example.local.users

import androidx.paging.PagingSource
import com.example.local.users.db.AppDatabase
import com.example.local.users.model.UserDb

interface UserLocalDataSource {
    fun getAllUsers(): PagingSource<Int, UserDb>?
    suspend fun insertAll(users: List<UserDb>)
    suspend fun deleteAll()
    suspend fun getUserDetails(userId: Long): UserDb?
}

class UserLocalDataSourceImpl(appDatabase: AppDatabase) : UserLocalDataSource {
    private val dao = appDatabase.usersDao()

    override fun getAllUsers() = dao.getAll()

    override suspend fun deleteAll() = dao.deleteAll()

    override suspend fun insertAll(users: List<UserDb>) = dao.insertAll(users)

    override suspend fun getUserDetails(userId: Long) = dao.getUser(userId)
}
