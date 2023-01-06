package com.ersubhadip.domains.dto.adapterModels

data class StoryModel(
    val id: Int,
    val storyTitle: String,
    val storyDesc: String,
    val storyTags: List<String>,
)