package com.example.opsmonitor.model

import java.io.Serializable

data class Server (
    val name : String,
    val ipAddress : String,
    val status : String,
    val cpuUsage : Int,
    val ramUsage : Int,
    val diskUsage : Int
) : Serializable
