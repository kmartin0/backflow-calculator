package com.km.backflow.calculator.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.km.backflow.calculator.BuildConfig
import com.km.backflow.calculator.R
import com.km.backflow.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		initViewBinding()

	}

	private fun initViewBinding() {
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.tvVersion.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)
	}
}