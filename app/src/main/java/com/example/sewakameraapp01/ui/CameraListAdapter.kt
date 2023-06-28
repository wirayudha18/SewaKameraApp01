package com.example.sewakameraapp01.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sewakameraapp01.R
import com.example.sewakameraapp01.model.Camera

class CameraListAdapter(
    private val onItemClickListener: (Camera)  -> Unit
): ListAdapter<Camera, CameraListAdapter.CameraViewHolder>(WORDS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CameraViewHolder {
        return CameraViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CameraViewHolder, position: Int) {
        val camera = getItem(position)
        holder.bind(camera)
        holder.itemView.setOnClickListener {
            onItemClickListener(camera)
        }
    }

    class CameraViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val itemTextView: TextView = itemView.findViewById(R.id.itemTextView)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)

        fun bind(camera: Camera?) {
            nameTextView.text = camera?.name
            itemTextView.text = camera?.item
            timeTextView.text = camera?.time
        }

        companion object {
            fun create(parent: ViewGroup): CameraListAdapter.CameraViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_camera, parent, false)
                return CameraViewHolder(view)
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Camera>() {
            override fun areItemsTheSame(oldItem: Camera, newItem: Camera): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Camera, newItem: Camera): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}