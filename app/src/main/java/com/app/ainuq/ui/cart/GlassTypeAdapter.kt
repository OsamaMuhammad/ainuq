package com.app.ainuq.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.ainuq.R
import com.app.ainuq.databinding.ItemGlassTypeSelectionBinding

class GlassTypeAdapter(
    private val context: Context,
    private val onClick: (GlassItemUiModel) -> Unit,
) : ListAdapter<GlassItemUiModel, GlassTypeAdapter.GlassTypeViewHolder>(ColorItemDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlassTypeViewHolder {
        return GlassTypeViewHolder(
            binding = ItemGlassTypeSelectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context = context,
            onClick = onClick,
        )
    }

    override fun onBindViewHolder(holder: GlassTypeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class GlassTypeViewHolder(
        private val binding: ItemGlassTypeSelectionBinding,
        private val context: Context,
        private val onClick: (GlassItemUiModel) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemUiModel: GlassItemUiModel) {

            itemUiModel.apply {
                binding.checkbox.isChecked = isSelected
                if (isSelected) {
                    binding.tvPrice.setTextColor(ContextCompat.getColor(context, R.color.secondary))
                    binding.checkbox.setTextColor(ContextCompat.getColor(context, R.color.secondary))
                } else {
                    binding.tvPrice.setTextColor(ContextCompat.getColor(context, R.color.gray))
                    binding.checkbox.setTextColor(ContextCompat.getColor(context, R.color.gray))
                }

                binding.tvPrice.text = "+ Rs. $price"

                binding.checkbox.setOnClickListener {
                    onClick.invoke(this)
                }
            }

        }
    }
}

class ColorItemDiffUtil : DiffUtil.ItemCallback<GlassItemUiModel>() {
    override fun areItemsTheSame(
        oldItemUiModel: GlassItemUiModel,
        newItemUiModel: GlassItemUiModel
    ): Boolean {
        return oldItemUiModel == newItemUiModel
    }

    override fun areContentsTheSame(
        oldItemUiModel: GlassItemUiModel,
        newItemUiModel: GlassItemUiModel
    ): Boolean {
        return oldItemUiModel.id == newItemUiModel.id
    }
}