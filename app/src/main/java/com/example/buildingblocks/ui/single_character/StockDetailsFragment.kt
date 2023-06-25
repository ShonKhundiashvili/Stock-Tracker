package com.example.buildingblocks.ui.single_character
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.buildingblocks.R
import com.example.buildingblocks.databinding.StockDetailsBinding
import com.example.buildingblocks.ui.ItemsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockDetailsFragment : Fragment()
{

    private val viewModel : ItemsViewModel by activityViewModels()
    var _binding : StockDetailsBinding? = null
    val binding get() = _binding!!
    var stockSymbol = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = StockDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("item")?.let { index ->
            val item = viewModel.items?.value?.get(index)
            binding.apply {
                item?.let {
                    itemTitle.text = "Title: ${it.symbol}"
                    price.text = "Price: ${it.close}"
                    volume.text = "Volume: ${it.volume}"
                    high.text = "High: ${it.high}"
                    low.text = "Low: ${it.low}"
                    stockSymbol = it.symbol
                }
            }
        }

        binding.stockNews.setOnClickListener {
            val bundle = bundleOf("item" to viewModel.specificStockUrl.value)
            findNavController().navigate(R.id.action_stockDetails_to_newsFragment2, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}