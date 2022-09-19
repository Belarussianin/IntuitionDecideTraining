package com.intuitiondecidetraining.ui.main_flow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.intuitiondecidetraining.R
import com.intuitiondecidetraining.databinding.ActivityMainBinding
import com.intuitiondecidetraining.ui.main_flow.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    // TODO https://medium.com/android-news/login-and-main-activity-flow-a52b930f8351

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bindUI().subscribeUI()
    }

    private fun ActivityMainBinding.bindUI() = this.apply {
        val navHostFragment =
            supportFragmentManager.findFragmentById(navHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        toolbarLayout.toolbar.apply {
            setupWithNavController(navController, appBarConfiguration)
            setSupportActionBar(this)
            setNavigationOnClickListener {
                navController.navigateUp()
            }
            navController.addOnDestinationChangedListener { _, destination, arguments ->
                when (destination.id) {
                    R.id.mainFragment -> {
                        title = resources.getString(R.string.app_name)
                    }
                    R.id.testFragment -> {
                        arguments?.getString("name")?.let {
                            title = it
                        }
                    }
                }
            }
        }
    }

    private fun ActivityMainBinding.subscribeUI() = this.apply {

    }
}