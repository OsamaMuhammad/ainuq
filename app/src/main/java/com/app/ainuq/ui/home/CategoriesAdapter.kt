package com.app.ainuq.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.ainuq.databinding.ItemTopCategoriesBinding
import com.app.ainuq.utils.extension.px

class CategoriesAdapter(
    private val context: Context,
    private val onClick: (CategoryItemUiModel) -> Unit,
) : ListAdapter<CategoryItemUiModel, CategoriesAdapter.CategoryViewHolder>(CategoryItemDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            binding = ItemTopCategoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context = context,
            onClick = onClick,
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val isLast = itemCount - 1 == position
        val isFirst = position == 0
        holder.bind(getItem(position), isLast, isFirst)
    }

    class CategoryViewHolder(
        private val binding: ItemTopCategoriesBinding,
        private val context: Context,
        private val onClick: (CategoryItemUiModel) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemUiModel: CategoryItemUiModel, isLast: Boolean, isFirst: Boolean) {


            val marginLayoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams

            when {
                isFirst -> {
                    marginLayoutParams.marginStart = 20.px
                    marginLayoutParams.marginEnd = 0.px
                }
                isLast -> {
                    marginLayoutParams.marginStart = 8.px
                    marginLayoutParams.marginEnd = 20.px
                }
                else -> {
                    marginLayoutParams.marginStart = 8.px
                    marginLayoutParams.marginEnd = 0.px
                }
            }
            itemUiModel.apply {
                binding.tvCategoryName.text = name
                binding.root.setOnClickListener {
                    onClick.invoke(this)
                }
            }

        }
    }
}

class CategoryItemDiffUtil : DiffUtil.ItemCallback<CategoryItemUiModel>() {
    override fun areItemsTheSame(
        oldItemUiModel: CategoryItemUiModel,
        newItemUiModel: CategoryItemUiModel
    ): Boolean {
        return oldItemUiModel == newItemUiModel
    }

    override fun areContentsTheSame(
        oldItemUiModel: CategoryItemUiModel,
        newItemUiModel: CategoryItemUiModel
    ): Boolean {
        return oldItemUiModel.id == newItemUiModel.id
    }
}