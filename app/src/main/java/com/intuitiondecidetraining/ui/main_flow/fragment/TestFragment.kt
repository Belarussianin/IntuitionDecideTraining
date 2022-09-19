package com.intuitiondecidetraining.ui.main_flow.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.intuitiondecidetraining.R
import com.intuitiondecidetraining.databinding.FragmentTestBinding
import com.intuitiondecidetraining.ui.main_flow.adapter.TestAdapter
import com.intuitiondecidetraining.ui.main_flow.viewModel.TestViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt
import kotlin.math.sqrt

class TestFragment : Fragment(), MenuProvider {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<TestViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentTestBinding.inflate(inflater, container, false).also {
        _binding = it
        requireActivity().addMenuProvider(
            this@TestFragment,
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TestAdapter(resources) { testItem ->
            viewModel.isItemTheRightOne(testItem)
        }

        binding.bindUI(adapter).subscribeUI(adapter)
    }

    private fun FragmentTestBinding.bindUI(adapter: TestAdapter) = this.apply {
        arguments?.let {
            val tries = it.getInt("tries", 1)
            val variants = it.getInt("variants", 2)
            viewModel.getTestItemsList(variants, tries)
        }
    }

    private fun FragmentTestBinding.subscribeUI(adapter: TestAdapter) = this.apply {
        viewModel.apply {
            // Start a coroutine in the lifecycle scope
            lifecycleScope.launch {
                // repeatOnLifecycle launches the block in a new coroutine every time the
                // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    // Trigger the flow and start listening for values.
                    // Note that this happens when lifecycle is STARTED and stops
                    // collecting when the lifecycle is STOPPED
                    testItemsListFlow.collect { testItemsList ->
                        testRecyclerView.apply {
                            layoutManager =
                                GridLayoutManager(
                                    requireContext(),
                                    sqrt(testItemsList.size.toFloat()).roundToInt()
                                )
                            this.adapter = adapter
                        }
                        adapter.submitList(testItemsList)
                    }
                }
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.options_fragment_test, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_item_sound -> {
                //TODO
                // menuItem.isChecked = viewModel.onSettingChange(!menuItem.isChecked)
                menuItem.isChecked = !menuItem.isChecked
                true
            }
            R.id.menu_item_vibration -> {
                //TODO
                // menuItem.isChecked = viewModel.onSettingChange(!menuItem.isChecked)
                menuItem.isChecked = !menuItem.isChecked
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}