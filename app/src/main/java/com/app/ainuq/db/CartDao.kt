package com.app.ainuq.db

import androidx.room.*
import com.app.ainuq.ui.cart.CartItemUModel
import kotlinx.coroutines.flow.Flow


@Dao
interface CartDao {

    @Insert
    fun insertCartItem(item: CartItemUModel)

    @Query("SELECT * FROM CartItemUModel")
    fun getCartItems(): Flow<List<CartItemUModel>>


//    @Query("UPDATE CartEntity cartItemId")
//    fun getCartItems(): List<CartEntity>

    @Delete
    fun deleteCartItem(item: CartItemUModel)

    @Query("DELETE FROM CartItemUModel WHERE cartItemId = :cartItemId")
    fun deleteByCartItemId(cartItemId: String)

    @Update
    fun updateCartItem(item: CartItemUModel)

    @Query("DELETE FROM CartItemUModel")
    fun deleteAllItems()
}