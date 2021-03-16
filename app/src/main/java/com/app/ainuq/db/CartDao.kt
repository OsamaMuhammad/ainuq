package com.app.ainuq.db

import androidx.room.Dao
import androidx.room.Insert
import com.app.ainuq.ui.home.ProductItemUiModel


@Dao
interface CartDao {


    @Insert
    fun insertCartItem(item: ProductItemUiModel)
}