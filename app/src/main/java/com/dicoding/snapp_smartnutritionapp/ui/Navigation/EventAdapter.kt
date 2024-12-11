package com.dicoding.snapp_smartnutritionapp.ui.Navigation

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.snapp_smartnutritionapp.data.response.Cobacoba.ListEventsItem
import com.dicoding.snapp_smartnutritionapp.databinding.ListItemBinding
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.DiffUtil
import com.dicoding.snapp_smartnutritionapp.ui.DetailData.DetailActivity

class EventAdapter : ListAdapter<ListEventsItem, EventAdapter.MyViewHolder>(DIFF_CALLBACK) {
    
    class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
                binding.titleTextView.text = event.name
                // Gunakan Glide untuk memuat gambar
                Glide.with(binding.imageView.context)
                    .load(event.imageLogo)
                    .into(binding.imageView)
                
                // Set nilai nutrisi
                // Asumsikan data ini ada di ListEventsItem
                // Sesuaikan dengan struktur data yang sebenarny
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
        holder.itemView.setOnClickListener{
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EVENT_ID, event.id)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}