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
        WordViewModelFactory((application as IkigaiApplication).repository)
    }

    private lateinit var binding: ActivityMainBinding

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
            viewModel.currentCategory = LOVE
            updateUi()
        }
    }

    private fun setupCurrentCategory() {
        binding.categoryTv.text = viewModel.currentCategory.string
    }

    private fun setupAddButton() {
        binding.submitBtn.setOnClickListener {
            viewModel.addAnswer(binding.answerEt.text.toString())

            clearInput()
            updateUi()
        }
    }

    private fun updateUi() {
        binding.categoryTv.text = viewModel.currentCategory.string
    }

    private fun setupNextCategoryButton() {
        binding.nextCategoryBtn.setOnClickListener {
            val nextCategory = viewModel.currentCategory.getNext()

            if (nextCategory != null) {
                viewModel.currentCategory = nextCategory
                updateUi()
                clearInput()
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

    private fun clearInput() {
        binding.answerEt.text = null
    }
}
