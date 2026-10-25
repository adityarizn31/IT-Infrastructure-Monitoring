package com.example.opsmonitor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opsmonitor.adapter.ServerAdapter
import com.example.opsmonitor.databinding.ActivityMainBinding
import com.example.opsmonitor.model.Server

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dummyServers = listOf(
            Server("APP-SERVER-01", "192.168.93.78", "Online", 35,48,62),
            Server("APP-SERVER-02", "192.168.93.79", "Warning", 86,79,88),
            Server("DB-SERVER-01", "192.168.93.78", "Online", 40,39,65),
            Server("DB-SERVER-02", "192.168.93.78", "Warning", 90,85,88),
            Server("FILE-SERVER-01", "192.168.93.78", "Online", 51,64,70),
            Server("API-SERVER-01", "192.168.93.78", "Online", 50,48,62),
        )

        val tvTotalServer = binding.tvTotalServers
        val tvOnline = binding.tvOnline
        val tvWarning = binding.tvWarning
        val tvOffline = binding.tvOffline

        tvTotalServer.text = dummyServers.size.toString()
        tvOnline.text = dummyServers.count { it.status == "Online" }.toString()
        tvWarning.text = dummyServers.count { it.status == "Warning" }.toString()
        tvOffline.text = dummyServers.count {it.status == "Offline" }.toString()

        val rvServers = binding.rvServers
        rvServers.layoutManager = LinearLayoutManager(this)
        rvServers.adapter = ServerAdapter(dummyServers)
    }
}