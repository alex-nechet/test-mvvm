package com.example.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.model.UserDb
import kotlinx.coroutines.flow.Flow


@Dao
interface UsersDao {
    @Query("SELECT * FROM users ORDER BY id")
      fun getAll():  PagingSource<Int, UserDb>?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
     suspend fun getUser(id: Long) : UserDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertAll(users: List<UserDb>)

    @Query("DELETE from users")
    suspend fun deleteAll()
}