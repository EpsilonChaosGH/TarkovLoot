package com.example.tarkovloot.app.screen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.tarkovloot.R
import com.example.tarkovloot.app.model.Config
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

            viewModel.state.observe(viewLifecycleOwner) {
                adapter.itemList = it.items
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

    fun settingsDialog() {
        val config = viewModel.state.value!!.config

        val currentIndex = when(config.priceForOneCell){
            true -> 0
            false -> 1
        }
        val list = listOf(6450, 5320)
        val costItems = list
            .map { "value = $it" }
            .toTypedArray()

        val dialog2 = AlertDialog.Builder(requireContext())
            .setTitle("Sacu mass coast")
            .setSingleChoiceItems(costItems, currentIndex) { dialog, witch ->
                dialog.dismiss()
                viewModel.saveConfig(
                    Config(
                        keyConfig = config.keyConfig,
                        priceForOneCell = config.priceForOneCell,
                        priceWithMarket = config.priceWithMarket,
                        sortByPriceLowToHigh = config.sortByPriceLowToHigh
                    )
                )
            }
            .create()
        dialog2.show()
    }
}