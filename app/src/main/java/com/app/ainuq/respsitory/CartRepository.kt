package com.app.ainuq.respsitory

import com.app.ainuq.db.CartDao
import com.app.ainuq.ui.cart.CartItemUModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {

    suspend fun addToCart(item: CartItemUModel) = withContext(Dispatchers.IO) {
        cartDao.insertCartItem(item)
    }

    fun getCartItems() = cartDao.getCartItems()

    suspend fun deleteCartItemById(cartItemId: String) = withContext(Dispatchers.IO) {
        cartDao.deleteByCartItemId(cartItemId)
    }

    suspend fun deleteCartItem(cartItem: CartItemUModel) = withContext(Dispatchers.IO) {
        cartDao.deleteCartItem(item = cartItem)
    }
}
