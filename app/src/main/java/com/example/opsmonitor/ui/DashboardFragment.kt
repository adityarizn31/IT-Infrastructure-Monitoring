package com.example.opsmonitor.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.opsmonitor.R
import com.example.opsmonitor.databinding.FragmentDashboardBinding
import com.example.opsmonitor.model.Server
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

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
            Server("APP-SERVER-01", "192.168.93.78", "Online", 35, 48, 62),
            Server("APP-SERVER-02", "192.168.93.79", "Warning", 86, 79, 88),
            Server("DB-SERVER-01", "192.168.93.78", "Online", 40, 39, 65),
            Server("DB-SERVER-02", "192.168.93.78", "Warning", 90, 85, 88),
            Server("FILE-SERVER-01", "192.168.93.78", "Online", 51, 64, 70),
            Server("API-SERVER-01", "192.168.93.78", "Online", 50, 48, 62),
            Server("DB-DRC-SERVER-01", "192,168.18.165", "Offline", 0, 0, 0)
        )

        binding.tvTotalServers.text = dummyServers.size.toString()
        binding.tvOnline.text = dummyServers.count { it.status == "Online" }.toString()
        binding.tvWarning.text = dummyServers.count { it.status == "Warning" }.toString()
        binding.tvOffline.text = dummyServers.count { it.status == "Offline" }.toString()

        binding.btnViewAllServers.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment2_to_serverListFragment)
        }

        setupResourceChart()
    }

    private fun setupResourceChart() {
        val entriesCpu = listOf(
            Entry(1f, 35f), Entry(2f, 48f), Entry(3f, 40f),
            Entry(4f, 65f), Entry(5f, 52f), Entry(6f, 45f)
        )
        val entriesRam = listOf(
            Entry(1f, 50f), Entry(2f, 58f), Entry(3f, 62f),
            Entry(4f, 70f), Entry(5f, 64f), Entry(6f, 55f)
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

        val lineData = LineData(dataSetCpu, dataSetRam)
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
