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
import com.example.opsmonitor.databinding.FragmentServerDetailBinding
import com.example.opsmonitor.model.Server
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

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

            val statusColor = when (s.status) {
                "Online" -> Color.rgb(52, 211, 153)
                "Warning" -> Color.rgb(251, 191, 36)
                else -> Color.rgb(248, 113, 113)
            }
            binding.tvDetailStatus.setTextColor(statusColor)

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

            setupChart(s)
        }
    }

    private fun setupChart(server: Server) {
        val cpu = server.cpuUsage.toFloat()
        val ram = server.ramUsage.toFloat()
        val disk = server.diskUsage.toFloat()

        val entriesCpu = listOf(
            Entry(1f, (cpu - 10f).coerceAtLeast(0f)),
            Entry(2f, (cpu - 5f).coerceAtLeast(0f)),
            Entry(3f, cpu),
            Entry(4f, (cpu + 5f).coerceAtMost(100f)),
            Entry(5f, cpu)
        )
        val entriesRam = listOf(
            Entry(1f, (ram - 5f).coerceAtLeast(0f)),
            Entry(2f, ram),
            Entry(3f, (ram + 2f).coerceAtMost(100f)),
            Entry(4f, (ram - 3f).coerceAtLeast(0f)),
            Entry(5f, ram)
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
