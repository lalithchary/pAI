package com.pai.personalai.domain.model

data class AIModel(
    val id: String,
    val name: String,
    val ownedBy: String = ""
)
