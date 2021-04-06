package com.app.ainuq.ui.cart

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.ainuq.databinding.ItemCartBinding
import com.app.ainuq.ui.home.HomeProductDiffUtil
import com.app.ainuq.ui.home.ProductItemUiModel

class CartAdapter(
    private val context: Context,
    private val onEditClick: (ProductItemUiModel) -> Unit,
    private val onRemoveClick: (ProductItemUiModel) -> Unit,
    private val isFromOrderDetail: Boolean = false
) : ListAdapter<ProductItemUiModel, CartAdapter.CartViewHolder>((HomeProductDiffUtil())) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            binding = ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context = context,
            onEditClick = onEditClick,
            onRemoveClick = onRemoveClick,
            isFromOrderDetail = isFromOrderDetail
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CartViewHolder(
        private val binding: ItemCartBinding,
        private val context: Context,
        private val onEditClick: (ProductItemUiModel) -> Unit,
        private val onRemoveClick: (ProductItemUiModel) -> Unit,
        private val isFromOrderDetail: Boolean
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemUiModel: ProductItemUiModel) {

            if(isFromOrderDetail){
                binding.btnDelete.visibility = View.GONE
                binding.btnEdit.visibility = View.GONE
            }else{
                binding.btnDelete.visibility = View.VISIBLE
                binding.btnEdit.visibility = View.VISIBLE
            }

            itemUiModel.apply {
                val color = colors.firstOrNull() { it.isSelected }
                binding.layoutColor.root.setCardBackgroundColor(
                    Color.parseColor(
                        color?.value ?: ""
                    )
                )

                binding.imgProduct.load(images.firstOrNull() ?: ""){
                    crossfade(true)
                }

                binding.tvFrameName.text = name
                binding.tvPrescriptionName.text = prescription?.userName ?: ""
                binding.tvPrescriptionDate.text = prescription?.date ?: ""

                var totalPrice = 0.0
                totalPrice += price
                totalPrice += glasses.firstOrNull { it.isSelected }?.price ?: 0.0
                binding.tvTotalPrice.text = "Rs. ${totalPrice}"

                binding.btnEdit.setOnClickListener {
                    onEditClick.invoke(this)
                }
                binding.btnDelete.setOnClickListener {
                    onRemoveClick.invoke(this)
                }
            }

        }
    }
}
