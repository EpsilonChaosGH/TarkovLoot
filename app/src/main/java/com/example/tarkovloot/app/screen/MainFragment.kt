package com.example.tarkovloot.app.screen

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.tarkovloot.R
import com.example.tarkovloot.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel>()

    private val binding by viewBinding(FragmentMainBinding::bind)

    private val adapter by lazy(mode = LazyThreadSafetyMode.NONE) { MainAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            setupLayoutManager(binding, adapter)

            viewModel.itemsState.observe(viewLifecycleOwner) {
                adapter.itemList = it
            }
        }
        addMenu()
        observeRefresh()
    }

    private fun observeRefresh() {
        binding.refreshLayout.setColorSchemeResources(R.color.main_text_color)
        binding.refreshLayout.setProgressBackgroundColorSchemeResource(R.color.main_color)
        binding.refreshLayout.setOnRefreshListener {
            lifecycleScope.launchWhenStarted {
                viewModel.refreshCurrentItems()
                binding.refreshLayout.isRefreshing = false
            }
        }
    }

    private fun setupLayoutManager(binding: FragmentMainBinding, adapter: MainAdapter) {
        binding.recyclerView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width = binding.recyclerView.width
                val itemWidth = resources.getDimensionPixelSize(R.dimen.item_width)
                val columns = width / itemWidth
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), columns)
            }
        })
    }

    private fun addMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.settings_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                settingsDialog()
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    fun settingsDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_settings, null)

        val priceForOneCellSwitch = view.findViewById<SwitchCompat>(R.id.priceForOneCellSwitch)
        val priceWithMarket = view.findViewById<SwitchCompat>(R.id.priceWithMarketSwitch)
        val sortByPriceLowToHigh = view.findViewById<SwitchCompat>(R.id.sortByPriceLowToHighSwitch)

        viewModel.configState.observe(viewLifecycleOwner) { config ->
            priceForOneCellSwitch.isChecked = config.priceForOneCell
            priceWithMarket.isChecked = config.priceWithMarket
            sortByPriceLowToHigh.isChecked = config.sortByPriceLowToHigh

            priceForOneCellSwitch.setOnClickListener {
                viewModel.saveConfig(config.copy(priceForOneCell = priceForOneCellSwitch.isChecked))
            }
            priceWithMarket.setOnClickListener {
                viewModel.saveConfig(config.copy(priceWithMarket = priceWithMarket.isChecked))
            }
            sortByPriceLowToHigh.setOnClickListener {
                viewModel.saveConfig(config.copy(sortByPriceLowToHigh = sortByPriceLowToHigh.isChecked))
            }
        }

        val listener = DialogInterface.OnClickListener { _, _ -> }
        val builder = AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)
            .setPositiveButton(R.string.ok, listener)
            .create()
        builder.setView(view)
        builder.show()
    }
}