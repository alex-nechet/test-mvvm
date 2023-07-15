package com.example.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.GetAllUsersUseCase
import com.example.domain.GetUserDetailsUseCase
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module


private val domainModule = module {
    factory { GetAllUsersUseCase(get()) }
}

class ListViewModel : ViewModel(), KoinComponent {

    private val getAllUsersUseCase: GetAllUsersUseCase by inject()

    init {
        loadKoinModules(domainModule)
        Log.e(this::class.simpleName, "LOADED")
    }

    val data = viewModelScope.async(start = CoroutineStart.LAZY) {
        getAllUsersUseCase().cachedIn(viewModelScope).stateIn(viewModelScope)
    }

    override fun onCleared() {
        unloadKoinModules(domainModule)
        Log.e(this::class.simpleName, "UNLOADED")
        super.onCleared()
    }
}
