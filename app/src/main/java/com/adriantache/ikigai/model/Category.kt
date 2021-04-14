package com.adriantache.ikigai.model

// TODO: 14/04/2021 replace string with stringRes
enum class Category(val string: String, val order: Int) {
    LOVE("What you love", 1),
    GOOD("What you're good at", 2),
    WORLD("What the world needs", 3)
}
