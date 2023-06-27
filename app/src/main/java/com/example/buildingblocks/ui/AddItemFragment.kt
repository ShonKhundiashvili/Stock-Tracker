package com.example.buildingblocks.ui
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.buildingblocks.data.model.models.Image
import com.example.buildingblocks.databinding.AddItemBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddItemFragment : Fragment() {
    private var _binding :AddItemBinding? = null
    private val binding get() = _binding!!
    private val viewModel : ItemsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddItemBinding.inflate(inflater, container, false)

        binding.finishBtn.setOnClickListener {
            val enteredSymbol = binding.itemTitle.text.toString().uppercase()

            val invalidCharsRegex = Regex("[\\.#_)(*&^%$@!]")

            if (binding.itemTitle.text.toString().isBlank() || binding.itemTitle.text.toString().matches(".*\\d.*".toRegex()) || enteredSymbol.contains(invalidCharsRegex)) {
                Toast.makeText(requireContext(), "Please enter a valid stock symbol", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val database = FirebaseDatabase.getInstance().getReference(enteredSymbol)
            database.get().addOnSuccessListener { snapshot ->
                snapshot.getValue(Image::class.java).apply {
                    viewModel.setImage(this?.B ?: "")
                    viewModel.setSymbol(enteredSymbol)
                    findNavController().previousBackStackEntry?.savedStateHandle?.set("symbol", enteredSymbol)
                    findNavController().popBackStack()
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Failed connecting to the server, please try again later", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}