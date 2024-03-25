package com.dicoding.geotaggingjbg.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.geotaggingjbg.ViewModelFactory
import com.dicoding.geotaggingjbg.data.database.Entity
import com.dicoding.geotaggingjbg.databinding.FragmentHomeBinding
import com.dicoding.geotaggingjbg.ui.detail.DetailActivity
import kotlin.math.log

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvData.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvData.addItemDecoration(itemDecoration)

        viewModel.getData().observe(viewLifecycleOwner){data ->
            Log.d("Get data", data.toString())
            if (data.isEmpty()) {
                binding.tvNone.visibility = View.VISIBLE
            } else {
                binding.tvNone.visibility = View.GONE
            }
            setViewData(data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setViewData(userList: List<Entity>) {
        Log.d("Set data", userList.toString())
        val adapter = HomeAdapter()
        adapter.submitList(userList)
        binding.rvData.adapter = adapter

        adapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Entity) {
                val intentToDetail = Intent(requireActivity(), DetailActivity::class.java)
                intentToDetail.putExtra("Data", data.id)
                startActivity(intentToDetail)
            }
        })
    }
}