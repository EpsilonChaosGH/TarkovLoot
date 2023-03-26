package com.example.tarkovloot.app.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarkovloot.app.model.Item
import com.example.tarkovloot.core_data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableLiveData<List<Item>>(listOf())
    val state: LiveData<List<Item>> = _state

//    private val _emptyListState = MutableLiveData<Boolean>()
//    val emptyListState = _emptyListState.share()

    init {
        listenCurrentState()
    }

    private fun listenCurrentState() {
        viewModelScope.launch {
            _state.value = repository.getItemList()
        }
    }
}