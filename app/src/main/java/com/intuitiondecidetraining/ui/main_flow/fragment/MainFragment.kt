package com.intuitiondecidetraining.ui.main_flow.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.intuitiondecidetraining.R
import com.intuitiondecidetraining.databinding.FragmentMainBinding
import com.intuitiondecidetraining.ui.main_flow.adapter.TestListAdapter
import com.intuitiondecidetraining.ui.main_flow.viewModel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), MenuProvider {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMainBinding.inflate(inflater, container, false).also {
        _binding = it
        requireActivity().addMenuProvider(
            this@MainFragment,
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TestListAdapter { testToOpen ->
            testToOpen.apply {
                findNavController().navigate(
                    R.id.action_mainFragment_to_testFragment,
                    bundleOf("id" to id, "name" to title, "tries" to tries, "variants" to variants)
                )
            }
        }
        binding.bindUI(adapter).subscribeUI(adapter)
    }

    private fun FragmentMainBinding.bindUI(adapter: TestListAdapter) = this.apply {
        testListRecyclerView.apply {
            this.adapter = adapter
        }
    }

    private fun FragmentMainBinding.subscribeUI(adapter: TestListAdapter) = this.apply {
        viewModel.apply {
            // Start a coroutine in the lifecycle scope
            lifecycleScope.launch {
                // repeatOnLifecycle launches the block in a new coroutine every time the
                // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    // Trigger the flow and start listening for values.
                    // Note that this happens when lifecycle is STARTED and stops
                    // collecting when the lifecycle is STOPPED
                    testsListFlow.collect { testsList ->
                        // New value received
                        adapter.submitList(testsList)
                    }
                }
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.options_fragment_main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_item_settings -> {
                findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
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

//private fun subscribeUi(adapter: LocalUserListAdapter) {
//    viewModel.apply {
//        storageSortOrder.observe(viewLifecycleOwner) {
//            updateList(adapter, getOrderedAllLocalUsers(idOfSort = it.ordinal))
//        }
//
//        allLocalUsers.observe(viewLifecycleOwner) {
//            updateList(adapter, getOrderedAllLocalUsers(allLocalUserList = it))
//        }
//
//        val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
//
//        storageDBMS.observe(viewLifecycleOwner) { dbms ->
//            Log.i(TAG, "StorageFragment observe dbms: ${dbms.name}")
//            //toolbar?.title = resources.getString(R.string.storage_name, it.name)
//            toolbar?.subtitle = dbms.name.lowercase()
//                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
//        }
//    }
//}