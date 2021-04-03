package com.app.ainuq.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.ainuq.databinding.ItemProduct2Binding
import com.app.ainuq.ui.home.HomeProductDiffUtil
import com.app.ainuq.ui.home.ProductItemUiModel

class SearchProductAdapter(
    private val context: Context,
    private val onClick: (ProductItemUiModel) -> Unit,
    private val onTryClick: (ProductItemUiModel) -> Unit,
    private val onFavouriteClick: (ProductItemUiModel) -> Unit,
) : ListAdapter<ProductItemUiModel, SearchProductAdapter.ProductViewHolder>(HomeProductDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            binding = ItemProduct2Binding.inflate(
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
        private val binding: ItemProduct2Binding,
        private val context: Context,
        private val onClick: (ProductItemUiModel) -> Unit,
        private val onTryClick: (ProductItemUiModel) -> Unit,
        private val onFavouriteClick: (ProductItemUiModel) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductItemUiModel, isLast: Boolean, isFirst: Boolean) {

            item.apply {
                binding.tvFramePrice.text = "Rs. ${price}"
                binding.tvRating.text = rating
                binding.tvFrameName.text = name
                binding.imgProduct.load(images.firstOrNull()){
                    crossfade(true)
                }

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
