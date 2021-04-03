package com.app.ainuq.db

import androidx.room.*
import com.app.ainuq.ui.cart.CartItemUiModel
import kotlinx.coroutines.flow.Flow


@Dao
interface CartDao {

    @Insert
    fun insertCartItem(item: CartItemUiModel)

    @Query("SELECT * FROM CartItemUiModel")
    fun getCartItems(): Flow<List<CartItemUiModel>>


//    @Query("UPDATE CartEntity cartItemId")
//    fun getCartItems(): List<CartEntity>

    @Delete
    fun deleteCartItem(item: CartItemUiModel)

    @Query("DELETE FROM CartItemUiModel WHERE cartItemId = :cartItemId")
    fun deleteByCartItemId(cartItemId: String)

    @Update
    fun updateCartItem(item: CartItemUiModel)

    @Query("DELETE FROM CartItemUiModel")
    fun deleteAllItems()
}