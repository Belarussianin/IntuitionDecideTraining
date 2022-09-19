package com.intuitiondecidetraining.ui.main_flow.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {

    private val _testItemsListFlow = MutableStateFlow<List<Int>>(emptyList())
    val testItemsListFlow get() = _testItemsListFlow.asStateFlow()

    var triesLeft: Int? = null
        private set
    var rightOne: Int? = null
        private set

    fun getTestItemsList(size: Int, tries: Int) {
        viewModelScope.launch {
            rightOne = (0 until size).random()
            triesLeft = tries
            _testItemsListFlow.value = List(size) { it }
        }
    }

    fun onTestItemClick(testItem: Int) {
        triesLeft?.let {
            if (it <= 0) {
                //TODO
            } else {
                triesLeft = triesLeft?.dec()
                //TODO
            }
        }
    }

    fun isItemTheRightOne(testItem: Int): Boolean = testItem == rightOne
}