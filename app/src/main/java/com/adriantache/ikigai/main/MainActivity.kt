package com.adriantache.ikigai.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.adriantache.ikigai.IkigaiApplication
import com.adriantache.ikigai.R
import com.adriantache.ikigai.databinding.ActivityMainBinding
import com.adriantache.ikigai.model.Category.Companion.getNext
import com.adriantache.ikigai.model.Category.LOVE
import com.adriantache.ikigai.randomizer.RandomizerActivity

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as IkigaiApplication).repository)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupDataBinding()

        setupCurrentCategory()
        setupButtons()
        viewModel.refreshAnswers()
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupCurrentCategory() {
        binding.categoryTv.text = viewModel.currentCategory.string
    }

    private fun setupButtons() {
        binding.deleteAllBtn.setOnClickListener {
            viewModel.deleteAll()
            viewModel.currentCategory = LOVE
            updateUi()
        }

        binding.submitBtn.setOnClickListener {
            viewModel.addAnswer(binding.answerEt.text.toString())
            updateUi()
        }

        binding.nextCategoryBtn.setOnClickListener {
            val nextCategory = viewModel.currentCategory.getNext()

            if (nextCategory != null) {
                viewModel.currentCategory = nextCategory
                updateUi()
                viewModel.refreshAnswers()
            } else {
                navigateToRandomizer()
            }
        }
    }

    private fun navigateToRandomizer() {
        val intent = Intent(this, RandomizerActivity::class.java)
        startActivity(intent)
    }

    private fun updateUi() {
        binding.categoryTv.text = viewModel.currentCategory.string
        clearInput()
    }

    private fun clearInput() {
        binding.answerEt.text = null
    }
}
