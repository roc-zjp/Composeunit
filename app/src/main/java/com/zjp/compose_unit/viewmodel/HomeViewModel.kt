package com.zjp.compose_unit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zjp.compose_unit.data.Result
import com.zjp.compose_unit.data.repository.ComposesRepository
import com.zjp.core_database.DBManager
import com.zjp.core_database.model.Compose
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessages: List<String?>
    val searchInput: String

    /**
     * There are no posts to render.
     *
     * This could either be because they are still loading or they failed to load, and we are
     * waiting to reload them.
     */
    data class NoComposes(
        override val isLoading: Boolean,
        override val errorMessages: List<String?>,
        override val searchInput: String
    ) : HomeUiState

    /**
     * There are posts to render, as contained in [postsFeed].
     *
     * There is guaranteed to be a [selectedPost], which is one of the posts from [postsFeed].
     */
    data class HasCompose(
        val composes: List<Compose>,
        override val isLoading: Boolean,
        override val errorMessages: List<String?>,
        override val searchInput: String
    ) : HomeUiState
}


data class HomeState(
    val composes: List<Compose> = listOf(),
    val isLoading: Boolean = false,
    val errorMessages: List<String?> = emptyList(),
    val searchInput: String = "",
) {

    fun toUiState(): HomeUiState {
        return if (composes.isEmpty()) {
            HomeUiState.NoComposes(isLoading, errorMessages, searchInput)
        } else {
            HomeUiState.HasCompose(composes, isLoading, errorMessages, searchInput)
        }
    }

}

class HomeViewModel(private val repository: ComposesRepository = ComposesRepository(DBManager.getInstance())) :
    ViewModel() {
    private val viewModelState = MutableLiveData<HomeState>(HomeState(isLoading = false))

    var uiState by mutableStateOf<HomeUiState>(viewModelState.value!!.toUiState())

    init {
        refreshComposes()
    }

    private fun refreshComposes() {
        viewModelScope.launch {
            viewModelState.value = HomeState(isLoading = true)
            delay(10000)
            when (val result = repository.getAllCompose()) {
                is Result.Success -> {
                    viewModelState.value = HomeState(
                        composes = result.data,
                        isLoading = false
                    )
                    uiState = viewModelState.value!!.toUiState()
                }
                is Result.Error -> {
                    viewModelState.value =
                        HomeState(
                            isLoading = false,
                            errorMessages = listOf(result.exception.message),
                        )

                    uiState = viewModelState.value!!.toUiState()
                }
            }

        }
    }

}