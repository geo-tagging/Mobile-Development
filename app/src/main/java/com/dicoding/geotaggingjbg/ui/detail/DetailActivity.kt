package com.dicoding.geotaggingjbg.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.dicoding.geotaggingjbg.R
import com.dicoding.geotaggingjbg.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val extraData = "Data"
        var username = "username"
        var ava = "avatarUrl"
    }
}