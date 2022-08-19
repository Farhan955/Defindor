package com.techoship.defindor.Models

data class Exchange(
    val createdAt: String,
    val domain: String,
    val id: Int,
    val image: String,
    val isCentralized: Boolean,
    val name: String,
    val numPairs: Int,
    val numPairsUnmapped: Int,
    val rank: Int,
    val updatedAt: String,
    val websiteUrl: String
)