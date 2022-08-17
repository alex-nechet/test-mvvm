package com.alex.android.git.repository.db

import androidx.paging.PagingSource
import androidx.room.*
import com.alex.android.git.data.model.UserDb

@Dao
interface UsersDao {

    @Query("SELECT * FROM users ORDER BY id")
     fun getAll():  PagingSource<Int, UserDb>?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
     fun getUser(id: Long) : UserDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(users: List<UserDb>)

    @Query("DELETE from users")
     fun deleteAll()
}