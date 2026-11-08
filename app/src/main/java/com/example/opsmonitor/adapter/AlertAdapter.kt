package com.example.opsmonitor.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.opsmonitor.R
import com.example.opsmonitor.model.Alert

class AlertAdapter(private val alerts: List<Alert>) :
    RecyclerView.Adapter<AlertAdapter.AlertViewHolder>() {

    class AlertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val severity: TextView = itemView.findViewById(R.id.tvAlertSeverity)
        val title: TextView = itemView.findViewById(R.id.tvAlertTitle)
        val time: TextView = itemView.findViewById(R.id.tvAlertTime)
        val message: TextView = itemView.findViewById(R.id.tvAlertMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alert, parent, false)
        return AlertViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        val alert = alerts[position]

        holder.title.text = alert.title
        holder.time.text = alert.timestamp
        holder.message.text = alert.message
        holder.severity.text = alert.severity

        when (alert.severity.uppercase()) {
            "CRITICAL" -> {
                holder.severity.setBackgroundColor(Color.parseColor("#EF4444"))
            }
            "WARNING" -> {
                holder.severity.setBackgroundColor(Color.parseColor("#F59E0B"))
            }
            else -> {
                holder.severity.setBackgroundColor(Color.parseColor("#10B981"))
            }
        }
    }

    override fun getItemCount(): Int = alerts.size
}
