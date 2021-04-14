package com.adriantache.ikigai.model

// TODO: 14/04/2021 replace string with stringRes
enum class Category(val string: String, private val order: Int) {
    LOVE("What you love", 1),
    GOOD("What you're good at", 2),
    WORLD("What the world needs", 3);

    companion object {
        fun Category.getNext(): Category? {
            val categories = values().sortedBy { it.order }
            val maxOrder = categories.maxOf { it.order }
            val hasNext = order < maxOrder

            if (!hasNext) return null

            val newOrder = order + 1
            return categories.find { it.order == newOrder }
        }
    }
}
