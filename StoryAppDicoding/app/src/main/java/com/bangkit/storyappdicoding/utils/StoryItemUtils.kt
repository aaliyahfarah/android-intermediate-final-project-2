package com.bangkit.storyappdicoding.utils

fun toDescriptionPreview(description: String): String {
    return if(description.length > 50)
        description.take(50) + "..."
    else
        description
}

fun toSnippetPreview(description: String): String {
    return if(description.length > 20)
        description.take(50) + "..."
    else
        description
}
