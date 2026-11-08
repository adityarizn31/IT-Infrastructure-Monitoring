package com.example.opsmonitor.model

data class Alert(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val severity: String
)
