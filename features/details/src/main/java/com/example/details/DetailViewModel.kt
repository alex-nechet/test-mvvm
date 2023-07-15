package com.example.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.details.mappers.toData
import com.example.domain.GetAllUsersUseCase
import com.example.domain.GetUserDetailsUseCase
import com.example.domain.common.model.map
import com.example.domain.entity.UserDetails
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module


private val domainModule = module {
    factory { GetUserDetailsUseCase(get()) }
}

class DetailViewModel(private val userId: Long) : ViewModel(), KoinComponent {

    private val getUserDetailsUseCase: GetUserDetailsUseCase by inject()

    init {
        loadKoinModules(domainModule)
    }

    val userDetails = viewModelScope.async(start = CoroutineStart.LAZY) { fetchData() }

    private suspend fun fetchData() = getUserDetailsUseCase(userId).map { state ->
        state.map { UserDetails(it, it.toData()) }
    }.stateIn(viewModelScope)

    override fun onCleared() {
        super.onCleared()
        unloadKoinModules(domainModule)
    }
}
