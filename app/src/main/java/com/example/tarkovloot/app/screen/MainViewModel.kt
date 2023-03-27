package com.example.tarkovloot.app.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarkovloot.app.model.Config
import com.example.tarkovloot.app.model.MainState
import com.example.tarkovloot.core_data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> = _state

    init {
        listenCurrentState()
    }

    fun saveConfig(config: Config) {
        viewModelScope.launch {
            repository.saveConfig(config)
        }
    }

    private fun listenCurrentState() {
        viewModelScope.launch {
            repository.getItemsFlow().collect(){
                _state.value = it
            }
        }
    }
}