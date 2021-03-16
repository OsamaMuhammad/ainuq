package com.app.ainuq.ui.prescription

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.ainuq.databinding.ItemPrescriptionBinding
import com.app.ainuq.utils.extension.px

class PrescriptionAdapter (
    private val context: Context,
    private val onClick: (PrescriptionItemUiModel) -> Unit,
) : ListAdapter<PrescriptionItemUiModel, PrescriptionAdapter.PrescriptionViewHolder>(ColorItemDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionViewHolder {
        return PrescriptionViewHolder(
            binding = ItemPrescriptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context = context,
            onClick = onClick,
        )
    }

    override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PrescriptionViewHolder(
        private val binding: ItemPrescriptionBinding,
        private val context: Context,
        private val onClick: (PrescriptionItemUiModel) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemUiModel: PrescriptionItemUiModel) {

            itemUiModel.apply {
                if (isSelected) {
                    binding.root.strokeWidth = 1.px
                } else {
                    binding.root.strokeWidth = 0.px
                }
                binding.root.setOnClickListener {
                    onClick.invoke(this)
                }


                binding.spLeft.text = spLeft
                binding.spRight.text = spRight
                binding.cylLeft.text = cylLeft
                binding.cylRight.text = cylRight
                binding.baseLeft.text = baseLeft
                binding.baseRight.text = baseRight
                binding.axisLeft.text = axisLeft
                binding.axisRight.text = axisRight
                binding.prismLeft.text = prismLeft
                binding.prismRight.text = prismRight

                binding.tvName.text = userName
                binding.tvDate.text = date
            }

        }
    }
}

class ColorItemDiffUtil : DiffUtil.ItemCallback<PrescriptionItemUiModel>() {
    override fun areItemsTheSame(
        oldItemUiModel: PrescriptionItemUiModel,
        newItemUiModel: PrescriptionItemUiModel
    ): Boolean {
        return oldItemUiModel == newItemUiModel
    }

    override fun areContentsTheSame(
        oldItemUiModel: PrescriptionItemUiModel,
        newItemUiModel: PrescriptionItemUiModel
    ): Boolean {
        return oldItemUiModel.id == newItemUiModel.id
    }
}