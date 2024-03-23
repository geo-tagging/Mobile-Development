package com.dicoding.geotaggingjbg.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import androidx.camera.core.processing.SurfaceProcessorNode.In
import androidx.core.view.get
import androidx.lifecycle.LiveData
import com.dicoding.geotaggingjbg.data.database.Entity
import com.dicoding.geotaggingjbg.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    private var entity: Entity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("User", 0)
        val data = viewModel.getData(id)
        data.observe(this){
            showDetail(it)
        }
    }

//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }

    private fun showDetail(entity: Entity) {
        binding.apply {
            etLang.setText(entity.latitude)
            etLong.setText(entity.longitude)
            etElev.setText(entity.elevasi)
            spinJentan[entity.jenTan]
            spinKegiatan[entity.kegiatan]
            spinLokasi[entity.lokasi]
            spinSk[entity.sk]
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