package com.example.buildingblocks.ui.all_characters
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buildingblocks.Item
import com.example.buildingblocks.R
import com.example.buildingblocks.databinding.AllItemsBinding
import com.example.buildingblocks.ui.ItemsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.finalkotlinproject.utils.Loading
import il.co.syntax.finalkotlinproject.utils.Success

@AndroidEntryPoint
class AllItemsFragment : Fragment() {

    private var _binding: AllItemsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ItemsViewModel by activityViewModels()
    private lateinit var itemAdapter: ItemAdapter
    private var filteredItems: List<Item> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
        setupAdapter()

        viewModel.items?.observe(viewLifecycleOwner) { items ->
            filteredItems = items ?: emptyList()
            filterItems(binding.searchEditText.text.toString())
        }

        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0,  ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.RIGHT) {
                    val context = binding.root.context
                    AlertDialog.Builder(context)
                        .setTitle("Delete Item")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Delete") { _, _ ->
                            val position = viewHolder.adapterPosition
                            val deletedItem = viewModel.getItemAtPosition(position)
                            val animator = DefaultItemAnimator()

                            animator.addDuration = 500
                            animator.removeDuration = 500
                            animator.supportsChangeAnimations = false
                            binding.recycler.itemAnimator = animator

                            viewModel.deleteItemFromRecycler(position)
                            itemAdapter.notifyItemRemoved(position)

                            binding.recycler.itemAnimator?.isRunning {
                                binding.recycler.itemAnimator = DefaultItemAnimator()
                            }

                            Snackbar.make(
                                binding.root,
                                "Item deleted",
                                Snackbar.LENGTH_LONG
                            ).setAction("Undo") {
                                deletedItem?.let {
                                    viewModel.addItem(it)
                                    itemAdapter.notifyItemInserted(position)
                                }
                            }.show()
                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                            itemAdapter.notifyItemChanged(viewHolder.adapterPosition)
                        }
                        .show()
                }
            }

        }).attachToRecyclerView(binding.recycler)
    }

    private fun setupAdapter() {
        itemAdapter = ItemAdapter(emptyList(), object : ItemAdapter.ItemListener {
            override fun onItemClicked(index: Int) {
                val bundle = bundleOf("item" to index)
                viewModel.getUrlForSpecifiedStock(viewModel.items?.value?.get(index)?.symbol?:"")
                findNavController().navigate(
                    R.id.action_allItemsFragment_to_stockDetails,
                    bundle
                )
            }

            override fun onItemLongClicked(index: Int) {}
        })

        binding.recycler.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun subscribeObservers() {
        viewModel.items?.observe(viewLifecycleOwner) { stockList ->
            stockList?.let {
                (binding.recycler.adapter as ItemAdapter).setItems(it)
            }
        }
        viewModel.symbol.observe(viewLifecycleOwner) {
            viewModel.printData()

            val status = it.status

            when (status) {
                is Loading -> {

                }

                is Success -> {
                    viewModel.getItems()
                }

                else -> {

                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AllItemsBinding.inflate(inflater, container, false)


        binding.addStock.setOnClickListener {
            findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment)
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                filterItems(searchText)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun filterItems(searchText: String) {
        val filteredList = if (searchText.isNotEmpty()) {
            filteredItems.filter { item ->
                item.symbol.contains(searchText, ignoreCase = true)
            }
        } else {
            filteredItems
        }
        itemAdapter.setItems(filteredList)
    }
}