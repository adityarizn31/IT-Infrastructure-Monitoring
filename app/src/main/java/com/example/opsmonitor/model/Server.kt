package com.example.opsmonitor.model

data class Server (
    val name : String,
    val ipAddress : String,
    val status : String,
    val cpuUsage : Int,
    val ramUsage : Int,
    val diskUsage : Int
)