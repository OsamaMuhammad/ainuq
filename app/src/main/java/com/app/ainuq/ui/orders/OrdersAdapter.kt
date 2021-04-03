package com.app.ainuq.ui.orders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.ainuq.databinding.ItemOrderBinding

class OrdersAdapter(
    private val context: Context,
    private val onClick: (OrderItemUiModel) -> Unit
) : ListAdapter<OrderItemUiModel, OrdersAdapter.CartViewHolder>((OrderItemDiffUtil())) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            binding = ItemOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context = context,
            onClick = onClick
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CartViewHolder(
        private val binding: ItemOrderBinding,
        private val context: Context,
        private val onClick: (OrderItemUiModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemUiModel: OrderItemUiModel) {

            itemUiModel.apply {

                var totalPrice = 0.0
                items.forEach {
                    totalPrice += it.price
                    totalPrice += it.glasses.firstOrNull { it.isSelected }?.price ?: 0.0
                }
                binding.tvPrice.text = "Rs. ${totalPrice}"

                binding.root.setOnClickListener {
                    onClick.invoke(this)
                }
                binding.tvAddress.text = address
                binding.tvStatus.text = orderStatus
                binding.tvDeliveryType.text = deliveryType
                binding.tvNumberFrames.text =
                    if (items.size > 1) "${items.size} Frames" else "${items.size} Frame"
            }

        }
    }
}

class OrderItemDiffUtil : DiffUtil.ItemCallback<OrderItemUiModel>() {
    override fun areItemsTheSame(
        oldItem: OrderItemUiModel,
        newItem: OrderItemUiModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: OrderItemUiModel,
        newItem: OrderItemUiModel
    ): Boolean {
        return oldItem.id == newItem.id
    }
}
