package com.adriantache.ikigai.main

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.adriantache.ikigai.IkigaiApplication
import com.adriantache.ikigai.R
import com.adriantache.ikigai.model.AnswerEntity
import com.adriantache.ikigai.model.Category
import com.adriantache.ikigai.model.Category.LOVE

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels {
        WordViewModelFactory((application as IkigaiApplication).repository)
    }

    private var currentCategory: Category = LOVE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupCurrentCategory()
        setupAddButton()
        setupNextCategoryButton()

        setupObserver()
        viewModel.getAnswers()
    }

    private fun setupObserver() {
        viewModel.answers.observe(this) { answers ->
            val summaryTv = findViewById<TextView>(R.id.answers_tv)
            summaryTv.text = answers
                .filter { it.category == currentCategory }
                .joinToString("\n") { it.answer }
        }
    }

    private fun setupCurrentCategory() {
        // TODO: 14/04/2021 replace with DataBinding
        val categoryTv = findViewById<TextView>(R.id.category_tv)
        categoryTv.text = currentCategory.string
    }

    private fun setupAddButton() {
        val addButton = findViewById<Button>(R.id.submit_btn)
        addButton.setOnClickListener {
            val input = findViewById<EditText>(R.id.answer_et).text.toString()
            val answer = AnswerEntity(currentCategory, input)
            viewModel.addAnswer(answer)

            updateUi()
        }
    }

    private fun updateUi() {
        val categoryTv = findViewById<TextView>(R.id.category_tv)
        categoryTv.text = currentCategory.string
    }

    private fun setupNextCategoryButton() {
        val nextCategoryBtn = findViewById<Button>(R.id.next_category_btn)
        nextCategoryBtn.setOnClickListener {
            // TODO: 14/04/2021  improve this horribleness
            val categories = Category.values().sortedBy { it.order }
            val maxOrder = categories.maxOf { it.order }
            val nextOrder =
                if (currentCategory.order + 1 > maxOrder) 1 else currentCategory.order + 1
            currentCategory = categories.find { it.order == nextOrder }!!
            updateUi()

            val input = findViewById<EditText>(R.id.answer_et)
            input.text = null
            viewModel.getAnswers()
        }
    }
}
