package com.dicoding.geotaggingjbg.ui.save

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
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

    private lateinit var spinnerItemJenis: Array<String>
    private lateinit var spinnerItemLokasi: Array<String>
    private lateinit var spinnerItemKegiatan: Array<String>
    private lateinit var spinnerItemSk: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerItemJenis = resources.getStringArray(R.array.array_jentan)
        val spinnerIdJenis = spinnerItemJenis.map{ it.split(",")}
        val adapterJenis = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerIdJenis)
        binding.spinJentan.adapter = adapterJenis

        spinnerItemLokasi = resources.getStringArray(R.array.array_lokasi)
        val spinnerIdLokasi = spinnerItemLokasi.map{ it.split(",")}
        val adapterLokasi = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerIdLokasi)
        binding.spinLokasi.adapter = adapterLokasi

        spinnerItemKegiatan = resources.getStringArray(R.array.array_kegiatan)
        val spinnerIdKegiatan = spinnerItemKegiatan.map{ it.split(",")}
        val adapterKegiatan = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerIdKegiatan)
        binding.spinKegiatan.adapter = adapterKegiatan

        spinnerItemSk = resources.getStringArray(R.array.array_sk)
        val spinnerIdSk = spinnerItemSk.map{ it.split(",")}
        val adapterSk = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerIdSk)
        binding.spinSk.adapter = adapterSk

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
            val data = Entity(
                image = currentImageUri.toString(),
                jenTan = binding.spinJentan.selectedItemId.toInt()+1,
                lokasi = binding.spinLokasi.selectedItemId.toInt()+1,
                kegiatan = binding.spinKegiatan.selectedItemId.toInt()+1,
                sk = binding.spinSk.selectedItemId.toInt()+1
            )
            viewModel.saveImageLocal(data)
            showToast("Data telah berhasil disimpan!")
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