package com.dicoding.geotaggingjbg.ui.save

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.dicoding.geotaggingjbg.R
import com.dicoding.geotaggingjbg.ViewModelFactory
import com.dicoding.geotaggingjbg.data.database.Entity
import com.dicoding.geotaggingjbg.databinding.FragmentSaveBinding

class SaveFragment : Fragment() {

    private var _binding: FragmentSaveBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
//    private lateinit var viewModel: SaveViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory: SaveViewModelFactory = SaveViewModelFactory.getInstance(requireContext().applicationContext)
        val viewModel: SaveViewModel by viewModels { factory }
        if (arguments != null) {
            currentImageUri = arguments?.getString(EXTRA_FILE)!!.toUri()
            binding.cvImagePreview.setImageURI(currentImageUri)
        }
        binding.btBatal.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.btSimpan.setOnClickListener {
            //yg ada dihapus, trus diisi buat upload room (insert)
            //toast + pindah ke cam
//            val option = "Image Save"
//            optionDialogListener?.onOptionChosen(option, currentImageUri.toString())
//            requireActivity().supportFragmentManager.popBackStack()
            val data = Entity(image = EXTRA_FILE)
            viewModel.saveImageLocal(data)
            showToast("Data telah disimpan")
            it.findNavController().navigate(R.id.action_navigation_save_to_navigation_home)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var EXTRA_FILE = "extra_file"
    }
}