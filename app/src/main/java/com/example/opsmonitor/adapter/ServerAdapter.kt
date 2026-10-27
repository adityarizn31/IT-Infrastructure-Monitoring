package com.example.opsmonitor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.opsmonitor.R
import com.example.opsmonitor.model.Server

class ServerAdapter(
    private var servers: List<Server>,
    private val onItemClick: ((Server) -> Unit)? = null
) : RecyclerView.Adapter<ServerAdapter.ServerViewHolder>() {

    class ServerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvServerName)
        val ipAddress: TextView = itemView.findViewById(R.id.tvServerIp)
        val status: TextView = itemView.findViewById(R.id.tvServerStatus)
        val cpu: TextView = itemView.findViewById(R.id.tvServerCpu)
        val ram: TextView = itemView.findViewById(R.id.tvServerRam)
        val disk: TextView = itemView.findViewById(R.id.tvServerDisk)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ServerAdapter.ServerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_server, parent, false)
        return ServerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServerAdapter.ServerViewHolder, position: Int) {
        val server = servers[position]

        holder.name.text = server.name
        holder.ipAddress.text = server.ipAddress
        holder.status.text = server.status
        holder.cpu.text = "CPU Usage: ${server.cpuUsage}%"
        holder.ram.text = "RAM Usage: ${server.ramUsage}%"
        holder.disk.text = "Disk Usage: ${server.diskUsage}%"

        val statusColor = when (server.status) {
            "Online" -> android.graphics.Color.rgb(52, 211, 153)
            "Warning" -> android.graphics.Color.rgb(251, 191, 36)
            else -> android.graphics.Color.rgb(248, 113, 113)
        }
        holder.status.setTextColor(statusColor)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(server)
        }
    }

    override fun getItemCount(): Int = servers.size

    fun updateData(newServers: List<Server>) {
        servers = newServers
        notifyDataSetChanged()
    }
}
