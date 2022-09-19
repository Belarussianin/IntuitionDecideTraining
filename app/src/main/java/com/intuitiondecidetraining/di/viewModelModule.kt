package com.intuitiondecidetraining.di

import com.intuitiondecidetraining.ui.main_flow.viewModel.MainViewModel
import com.intuitiondecidetraining.ui.main_flow.viewModel.SettingsViewModel
import com.intuitiondecidetraining.ui.main_flow.viewModel.TestResultViewModel
import com.intuitiondecidetraining.ui.main_flow.viewModel.TestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { SettingsViewModel() }
    viewModel { TestViewModel() }
    viewModel { TestResultViewModel() }
}