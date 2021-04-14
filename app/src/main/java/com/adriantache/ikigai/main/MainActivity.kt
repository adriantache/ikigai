package com.adriantache.ikigai.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.adriantache.ikigai.IkigaiApplication
import com.adriantache.ikigai.R
import com.adriantache.ikigai.databinding.ActivityMainBinding
import com.adriantache.ikigai.model.Category

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        WordViewModelFactory((application as IkigaiApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupDataBinding()

        setupCurrentCategory()
        setupAddButton()
        setupNextCategoryButton()
        setupDeleteAllButton()

        viewModel.refreshAnswers()
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupDeleteAllButton() {
        binding.deleteAllBtn.setOnClickListener {
            viewModel.deleteAll()
            clearInput()
        }
    }

    private fun setupCurrentCategory() {
        binding.categoryTv.text = viewModel.currentCategory.string
    }

    private fun setupAddButton() {
        binding.submitBtn.setOnClickListener {
            viewModel.addAnswer(binding.answerEt.text.toString())

            updateUi()
        }
    }

    private fun updateUi() {
        binding.categoryTv.text = viewModel.currentCategory.string
    }

    private fun setupNextCategoryButton() {
        binding.nextCategoryBtn.setOnClickListener {
            // TODO: 14/04/2021  improve this horribleness
            val categories = Category.values().sortedBy { it.order }
            val maxOrder = categories.maxOf { it.order }
            val nextOrder = if (viewModel.currentCategory.order + 1 > maxOrder) {
                1
            } else {
                viewModel.currentCategory.order + 1
            }
            viewModel.currentCategory = categories.find { it.order == nextOrder }!!
            updateUi()

            clearInput()
            viewModel.refreshAnswers()
        }
    }

    private fun clearInput() {
        binding.answerEt.text = null
    }
}
