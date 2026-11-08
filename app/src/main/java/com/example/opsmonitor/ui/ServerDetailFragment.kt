package com.example.opsmonitor.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.opsmonitor.R
import com.example.opsmonitor.databinding.FragmentServerDetailBinding
import com.example.opsmonitor.model.Server
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlin.math.min

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

        binding.btnBackDetail.setOnClickListener {
            findNavController().navigateUp()
        }

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

            val avgCpu = (s.cpuUsage * 0.85).toInt()
            val peakCpu = min(100, s.cpuUsage + 20)
            binding.tvCpuAvgPeak.text = "$avgCpu% / $peakCpu%"

            val avgRam = (s.ramUsage * 0.9).toInt()
            val peakRam = min(100, s.ramUsage + 15)
            binding.tvRamAvgPeak.text = "$avgRam% / $peakRam%"

            val avgDisk = s.diskUsage
            val peakDisk = min(100, s.diskUsage + 5)
            binding.tvDiskAvgPeak.text = "$avgDisk% / $peakDisk%"

            val statusColor = when (s.status) {
                "Online" -> Color.rgb(52, 211, 153)
                "Warning" -> Color.rgb(251, 191, 36)
                else -> Color.rgb(248, 113, 113)
            }
            binding.tvDetailStatus.setTextColor(statusColor)

            when (s.status) {
                "Offline" -> {
                    binding.tvMaintenanceStatus.text = "MAINTENANCE"
                    binding.tvMaintenanceStatus.setBackgroundColor(Color.parseColor("#F59E0B"))
                }
                "Warning" -> {
                    binding.tvMaintenanceStatus.text = "ATTENTION"
                    binding.tvMaintenanceStatus.setBackgroundColor(Color.parseColor("#F59E0B"))
                }
                else -> {
                    binding.tvMaintenanceStatus.text = "NORMAL"
                    binding.tvMaintenanceStatus.setBackgroundColor(Color.parseColor("#10B981"))
                }
            }

            binding.btnCopyIp.setOnClickListener {
                val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Server IP", s.ipAddress)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(requireContext(), "IP Address ${s.ipAddress} disalin!", Toast.LENGTH_SHORT).show()
            }

            binding.btnReboot.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("Reboot Server")
                    .setMessage("Apakah Anda yakin ingin melakukan reboot pada server ${s.name}?")
                    .setPositiveButton("Reboot") { _, _ ->
                        Toast.makeText(requireContext(), "Perintah reboot dikirim ke ${s.name}", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Batal", null)
                    .show()
            }

            binding.cgTimeRange.setOnCheckedStateChangeListener { _, _ ->
                val mode = when (binding.cgTimeRange.checkedChipId) {
                    R.id.chipDaily -> "daily"
                    R.id.chipWeekly -> "weekly"
                    else -> "24h"
                }
                setupChart(s, mode)
            }

            setupChart(s, "24h")
        }
    }

    private fun setupChart(server: Server, mode: String) {
        val cpu = server.cpuUsage.toFloat()
        val ram = server.ramUsage.toFloat()
        val disk = server.diskUsage.toFloat()

        val factor = when (mode) {
            "daily" -> 0.9f
            "weekly" -> 0.8f
            else -> 1.0f
        }

        val entriesCpu = listOf(
            Entry(1f, (cpu * 0.7f * factor).coerceIn(0f, 100f)),
            Entry(2f, (cpu * 0.9f * factor).coerceIn(0f, 100f)),
            Entry(3f, (cpu * factor).coerceIn(0f, 100f)),
            Entry(4f, (cpu * 1.1f * factor).coerceIn(0f, 100f)),
            Entry(5f, (cpu * factor).coerceIn(0f, 100f))
        )
        val entriesRam = listOf(
            Entry(1f, (ram * 0.8f * factor).coerceIn(0f, 100f)),
            Entry(2f, (ram * factor).coerceIn(0f, 100f)),
            Entry(3f, (ram * 1.05f * factor).coerceIn(0f, 100f)),
            Entry(4f, (ram * 0.95f * factor).coerceIn(0f, 100f)),
            Entry(5f, (ram * factor).coerceIn(0f, 100f))
        )
        val entriesDisk = listOf(
            Entry(1f, disk),
            Entry(2f, disk),
            Entry(3f, disk),
            Entry(4f, disk),
            Entry(5f, disk)
        )

        val dataSetCpu = LineDataSet(entriesCpu, "CPU (%)").apply {
            color = Color.parseColor("#60A5FA")
            valueTextColor = Color.parseColor("#F1F5F9")
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(Color.parseColor("#60A5FA"))
        }

        val dataSetRam = LineDataSet(entriesRam, "RAM (%)").apply {
            color = Color.parseColor("#34D399")
            valueTextColor = Color.parseColor("#F1F5F9")
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(Color.parseColor("#34D399"))
        }

        val dataSetDisk = LineDataSet(entriesDisk, "Disk (%)").apply {
            color = Color.parseColor("#FBBF24")
            valueTextColor = Color.parseColor("#F1F5F9")
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(Color.parseColor("#FBBF24"))
        }

        val lineData = LineData(dataSetCpu, dataSetRam, dataSetDisk)
        binding.lineChartResource.apply {
            data = lineData
            description.isEnabled = false
            legend.textColor = Color.parseColor("#94A3B8")
            xAxis.textColor = Color.parseColor("#94A3B8")
            axisLeft.textColor = Color.parseColor("#94A3B8")
            axisRight.isEnabled = false
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
