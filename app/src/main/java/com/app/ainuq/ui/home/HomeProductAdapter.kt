package com.app.ainuq.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.ainuq.databinding.ItemProductBinding
import com.app.ainuq.utils.extension.px

class HomeProductAdapter(
    private val context: Context,
    private val onClick: (ProductItemUiModel) -> Unit,
    private val onTryClick: (ProductItemUiModel) -> Unit,
    private val onFavouriteClick: (ProductItemUiModel) -> Unit,
) : ListAdapter<ProductItemUiModel, HomeProductAdapter.ProductViewHolder>(HomeProductDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            binding = ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context = context,
            onClick = onClick,
            onFavouriteClick = onFavouriteClick,
            onTryClick = onTryClick,
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val isLast = itemCount - 1 == position
        val isFirst = position == 0
        holder.bind(getItem(position), isLast, isFirst)
    }

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val context: Context,
        private val onClick: (ProductItemUiModel) -> Unit,
        private val onTryClick: (ProductItemUiModel) -> Unit,
        private val onFavouriteClick: (ProductItemUiModel) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductItemUiModel, isLast: Boolean, isFirst: Boolean) {


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
            item.apply {
                binding.tvFramePrice.text = "Rs. ${price}"
                binding.tvRating.text = rating

                binding.root.setOnClickListener {
                    onClick.invoke(this)
                }

                binding.imgFavourite.setOnClickListener {
                    onFavouriteClick.invoke(this)
                }
                binding.btnTryNow.setOnClickListener {
                    onTryClick.invoke(this)
                }
            }

        }
    }
}

class HomeProductDiffUtil : DiffUtil.ItemCallback<ProductItemUiModel>() {
    override fun areItemsTheSame(
        oldItem: ProductItemUiModel,
        newItem: ProductItemUiModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ProductItemUiModel,
        newItem: ProductItemUiModel
    ): Boolean {
        return oldItem.id == newItem.id
    }
}