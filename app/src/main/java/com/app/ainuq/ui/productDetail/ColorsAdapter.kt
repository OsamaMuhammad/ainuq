package com.app.ainuq.ui.productDetail

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.ainuq.databinding.ItemColorBinding
import com.app.ainuq.utils.extension.px

class ColorsAdapter(
    private val context: Context,
    private val onClick: (ColorItemUiModel) -> Unit,
) : ListAdapter<ColorItemUiModel, ColorsAdapter.ColorViewHolder>(ColorItemDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(
            binding = ItemColorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context = context,
            onClick = onClick,
        )
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ColorViewHolder(
        private val binding: ItemColorBinding,
        private val context: Context,
        private val onClick: (ColorItemUiModel) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemUiModel: ColorItemUiModel) {

            itemUiModel.apply {
                binding.root.setCardBackgroundColor(Color.parseColor(value))
                if (isSelected) {
                    binding.root.strokeWidth = 4.px
                } else {
                    binding.root.strokeWidth = 0.px
                }
                binding.root.setOnClickListener {
                    onClick.invoke(this)
                }
            }

        }
    }
}

class ColorItemDiffUtil : DiffUtil.ItemCallback<ColorItemUiModel>() {
    override fun areItemsTheSame(
        oldItemUiModel: ColorItemUiModel,
        newItemUiModel: ColorItemUiModel
    ): Boolean {
        return oldItemUiModel == newItemUiModel
    }

    override fun areContentsTheSame(
        oldItemUiModel: ColorItemUiModel,
        newItemUiModel: ColorItemUiModel
    ): Boolean {
        return oldItemUiModel.id == newItemUiModel.id
    }
}