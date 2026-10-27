package com.example.opsmonitor.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.opsmonitor.R
import com.example.opsmonitor.databinding.FragmentDashboardBinding
import com.example.opsmonitor.model.Server

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}