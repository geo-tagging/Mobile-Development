package com.dicoding.geotaggingjbg.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.geotaggingjbg.R
import com.dicoding.geotaggingjbg.data.database.Entity
import com.dicoding.geotaggingjbg.databinding.ItemDataBinding

class HomeAdapter : ListAdapter<Entity, HomeAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(user)
        }
    }
    class MyViewHolder(val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Entity){
            Glide.with(binding.root.context)
                .load(data.image)
                .into(binding.ivImage)
            val jenTanId = data.jenTan
            val jenTanArray = binding.root.context.resources.getStringArray(R.array.array_jentan)
            var jenTanValue = ""
            for (item in jenTanArray) {
                val parts = item.split(", ")
                if (parts[0].toInt() == jenTanId) {
                    jenTanValue = parts[1]
                    break
                }
            }
            binding.tvJentan.text = jenTanValue
            binding.tvTanggal.text = data.tanggal
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Entity>() {
            override fun areItemsTheSame(oldItem: Entity, newItem: Entity): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Entity, newItem: Entity): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Entity)
    }
}