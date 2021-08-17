package com.freshworks.giff.model

data class Pagination(
    val count: Int,
    val offset: Int,
    val total_count: Int
)