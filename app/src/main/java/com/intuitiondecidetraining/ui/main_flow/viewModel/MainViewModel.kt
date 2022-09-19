package com.intuitiondecidetraining.ui.main_flow.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intuitiondecidetraining.data.db.test.Test
import com.intuitiondecidetraining.data.repository.TestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val testRepository: TestRepository
) : ViewModel() {

    private val _testListFlow = MutableStateFlow<List<Test>>(emptyList())
    val testsListFlow get() = _testListFlow.asStateFlow()

    init {
        getTestsList()
    }

    fun getTestsList() {
        viewModelScope.launch(Dispatchers.IO) {
            testRepository.getAll()
                .onEach(_testListFlow::emit)
                .catch { throwable ->
                    //TODO
                }
                .launchIn(viewModelScope)
        }
    }

    fun updateTest(test: Test) {
        viewModelScope.launch(Dispatchers.IO) {
            testRepository.insertOrUpdate(test)
        }
    }
}