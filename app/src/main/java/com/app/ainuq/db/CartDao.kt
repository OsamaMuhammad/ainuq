package com.app.ainuq.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
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
}