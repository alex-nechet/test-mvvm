package com.example.remote.users

import com.example.network.GitApi
import com.example.network.mappers.ResultConverter
import com.example.remote.users.mappers.toDto
import com.example.remote.users.model.UserDto

interface UserRemoteDataSource {
    suspend fun getUsers(page: Long): Result<List<UserDto>?>

    suspend fun getDetails(userId: Long): Result<UserDto?>
}

class UserRemoteDataSourceImpl(
    private val api: GitApi,
    private val resultConverter: ResultConverter
) : UserRemoteDataSource {

    override suspend fun getUsers(page: Long): Result<List<UserDto>?> {
        return resultConverter { api.getUsers(page) }
            .mapCatching { list -> list?.map { it.toDto() } }
    }

    override suspend fun getDetails(userId: Long): Result<UserDto?> {
        return resultConverter { api.getDetails(userId) }
            .mapCatching { it?.toDto() }
    }
}
