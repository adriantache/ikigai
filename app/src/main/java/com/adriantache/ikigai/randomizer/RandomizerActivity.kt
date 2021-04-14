package com.adriantache.ikigai.randomizer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.adriantache.ikigai.IkigaiApplication
import com.adriantache.ikigai.R
import com.adriantache.ikigai.databinding.ActivityRandomizerBinding

class RandomizerActivity : AppCompatActivity() {
    private val viewModel: RandomizerViewModel by viewModels {
        RandomizerViewModelFactory((application as IkigaiApplication).repository)
    }

    private lateinit var binding: ActivityRandomizerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupDataBinding()

        viewModel.randomize()
        setupButton()
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_randomizer)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupButton() {
        binding.randomizeBtn.setOnClickListener {
            viewModel.randomize()
        }
    }
}
