package com.example.opsmonitor.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.opsmonitor.databinding.FragmentServerDetailBinding
import com.example.opsmonitor.model.Server

class ServerDetailFragment : Fragment() {

    private var _binding: FragmentServerDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val server = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("EXTRA_SERVER", Server::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable("EXTRA_SERVER") as? Server
        }

        server?.let { s ->
            binding.tvDetailServerName.text = s.name
            binding.tvDetailIpAddress.text = "IP: ${s.ipAddress}"
            binding.tvDetailStatus.text = "Status: ${s.status.uppercase()}"
            binding.tvDetailCpu.text = "CPU Usage: ${s.cpuUsage}%"
            binding.tvDetailRam.text = "RAM Usage: ${s.ramUsage}%"
            binding.tvDetailDisk.text = "Disk Usage: ${s.diskUsage}%"

            val statusColor = when (s.status) {
                "Online" -> android.graphics.Color.rgb(52, 211, 153)
                "Warning" -> android.graphics.Color.rgb(251, 191, 36)
                else -> android.graphics.Color.rgb(248, 113, 113)
            }
            binding.tvDetailStatus.setTextColor(statusColor)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
