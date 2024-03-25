package com.dicoding.geotaggingjbg.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.geotaggingjbg.R
import com.dicoding.geotaggingjbg.data.database.Entity
import com.dicoding.geotaggingjbg.databinding.ItemDataBinding
import com.dicoding.geotaggingjbg.ui.detail.DetailActivity
import com.dicoding.geotaggingjbg.ui.helper.DiffCallback
import java.util.ArrayList

class HomeAdapter : ListAdapter<Entity, HomeAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback
//    private val listData = ArrayList<Entity>()
//
//    fun setListData(listNotes: List<Entity>) {
//        val diffCallback = DiffCallback(this.listData, listNotes)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        this.listData.clear()
//        this.listData.addAll(listNotes)
//        diffResult.dispatchUpdatesTo(this)
//    }
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

//    override fun getItemCount(): Int {
//        return listData.size
//    }
    class MyViewHolder(private val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Entity){
//            Glide.with(binding.root.context)
//                .load(data.image)
//                .into(binding.ivImage)
            binding.ivImage.setImageURI(data.image?.toUri())
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
//            binding.cvItem.setOnClickListener {
//                val intent = Intent(it.context, DetailActivity::class.java)
//                intent.putExtra(DetailActivity.extraData, data)
//                it.context.startActivity(intent)
//            }
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