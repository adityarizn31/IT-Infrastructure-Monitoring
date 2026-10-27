package com.example.opsmonitor.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opsmonitor.R
import com.example.opsmonitor.adapter.ServerAdapter
import com.example.opsmonitor.databinding.FragmentServerListBinding
import com.example.opsmonitor.model.Server

class ServerListFragment : Fragment() {

    private var _binding: FragmentServerListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ServerAdapter

    private val dummyServers = listOf(
        Server("APP-SERVER-01", "192.168.93.78", "Online", 35, 48, 62),
        Server("APP-SERVER-02", "192.168.93.79", "Warning", 86, 79, 88),
        Server("DB-SERVER-01", "192.168.93.78", "Online", 40, 39, 65),
        Server("DB-SERVER-02", "192.168.93.78", "Warning", 90, 85, 88),
        Server("FILE-SERVER-01", "192.168.93.78", "Online", 51, 64, 70),
        Server("API-SERVER-01", "192.168.93.78", "Online", 50, 48, 62)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ServerAdapter(dummyServers) { selectedServer ->
            val bundle = Bundle().apply {
                putSerializable("EXTRA_SERVER", selectedServer)
            }
            findNavController().navigate(
                R.id.action_serverListFragment_to_serverDetailFragment,
                bundle
            )
        }

        binding.rvServers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvServers.adapter = adapter

        binding.etSearchServer.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString()?.trim() ?: ""
                val filteredList = if (query.isEmpty()) {
                    dummyServers
                } else {
                    dummyServers.filter {
                        it.name.contains(query, ignoreCase = true) ||
                                it.ipAddress.contains(query, ignoreCase = true)
                    }
                }
                adapter.updateData(filteredList)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
