package com.app.ainuq.ui.productDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.ainuq.R
import com.app.ainuq.databinding.ItemSliderImageBinding

class ImageSliderAdapter(
    private val context: Context,
    var list: List<String>
) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(
            binding = ItemSliderImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context = context,
        )


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount() = list.size

    class ImageViewHolder(
        private val binding: ItemSliderImageBinding,
        private val context: Context,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {

            binding.imgProduct.load(R.drawable.glasses){
                crossfade(true)
            }

        }
    }


}
