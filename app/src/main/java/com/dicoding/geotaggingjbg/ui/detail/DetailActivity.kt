package com.dicoding.geotaggingjbg.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.dicoding.geotaggingjbg.R
import com.dicoding.geotaggingjbg.data.database.Entity
import com.dicoding.geotaggingjbg.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    private lateinit var spinnerItemJenis: Array<String>
    private lateinit var spinnerItemLokasi: Array<String>
    private lateinit var spinnerItemKegiatan: Array<String>
    private lateinit var spinnerItemSk: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(extraData, 0)
        val factory = DetailViewModelFactory.createFactory(this, id)

        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        viewModel.getData.observe(this){
            showDetail(it)
        }
    }

//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }

    private fun showDetail(entity: Entity) {

        entity.apply {
            binding.apply {
                val img = entity.image?.toUri()
                val jenTanId = entity.jenTan
                val lokasiId = entity.lokasi
                val kegiatanId = entity.kegiatan
                val skId = entity.sk

                spinnerItemJenis = resources.getStringArray(R.array.array_jentan)
                val spinnerIdJenis = spinnerItemJenis.map{ it.split(",")}
                val adapterJenis = ArrayAdapter(this@DetailActivity, android.R.layout.simple_spinner_dropdown_item, spinnerIdJenis)
                binding.spinJentan.adapter = adapterJenis

                spinnerItemLokasi = resources.getStringArray(R.array.array_lokasi)
                val spinnerIdLokasi = spinnerItemLokasi.map{ it.split(",")}
                val adapterLokasi = ArrayAdapter(this@DetailActivity, android.R.layout.simple_spinner_dropdown_item, spinnerIdLokasi)
                binding.spinLokasi.adapter = adapterLokasi

                spinnerItemKegiatan = resources.getStringArray(R.array.array_kegiatan)
                val spinnerIdKegiatan = spinnerItemKegiatan.map{ it.split(",")}
                val adapterKegiatan = ArrayAdapter(this@DetailActivity, android.R.layout.simple_spinner_dropdown_item, spinnerIdKegiatan)
                binding.spinKegiatan.adapter = adapterKegiatan

                spinnerItemSk = resources.getStringArray(R.array.array_sk)
                val spinnerIdSk = spinnerItemSk.map{ it.split(",")}
                val adapterSk = ArrayAdapter(this@DetailActivity, android.R.layout.simple_spinner_dropdown_item, spinnerIdSk)
                binding.spinSk.adapter = adapterSk

                cvImagePreview.setImageURI(img)
                etLang.setText(entity.latitude)
                etLong.setText(entity.longitude)
                etElev.setText(entity.elevasi)
//                spinJentan.setSelection(entity.jenTan-1)
//                spinKegiatan.setSelection(entity.kegiatan-1)
//                spinLokasi.setSelection(entity.lokasi-1)
//                spinSk.setSelection(entity.sk-1)

                for ((index, item) in spinnerItemJenis.withIndex()) {
                    if (item.split(",")[0].toInt() == jenTanId) {
                        spinJentan.setSelection(index)
                        break
                    }
                }

                for ((index, item) in spinnerItemLokasi.withIndex()) {
                    if (item.split(",")[0].toInt() == lokasiId) {
                        spinLokasi.setSelection(index)
                        break
                    }
                }

                for ((index, item) in spinnerItemKegiatan.withIndex()) {
                    if (item.split(",")[0].toInt() == kegiatanId) {
                        spinKegiatan.setSelection(index)
                        break
                    }
                }

                for ((index, item) in spinnerItemSk.withIndex()) {
                    if (item.split(",")[0].toInt() == skId) {
                        spinSk.setSelection(index)
                        break
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val extraData = "Data"
        var username = "username"
        var ava = "avatarUrl"
    }
}