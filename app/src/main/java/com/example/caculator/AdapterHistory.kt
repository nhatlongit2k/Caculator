package com.example.caculator

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterHistory(val listCacu: ArrayList<String>, val context: Context): RecyclerView.Adapter<AdapterHistory.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentItem = listCacu[position]
        val equalPosition = currentItem.indexOf("=")
//        Log.d("TAG", "onBindViewHolder: $equalPosition")
//        Log.d("TAG", "onBindViewHolder: ${currentItem.substring(0, equalPosition).toString()}")
        holder.tvWorking.setText(currentItem.substring(0, equalPosition).toString())
        holder.tvResult.setText(currentItem.substring(equalPosition, currentItem.length-1))
    }

    override fun getItemCount(): Int {
        return listCacu.size
    }

    inner class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvWorking: TextView = itemView.findViewById(R.id.tvWorking)
        val tvResult: TextView = itemView.findViewById(R.id.tvResult)
    }

}