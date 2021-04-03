package com.app.ainuq.db

import androidx.room.TypeConverter
import com.app.ainuq.ui.cart.CartItemUiModel
import com.app.ainuq.ui.cart.GlassItemUiModel
import com.app.ainuq.ui.productDetail.ColorItemUiModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type
import javax.inject.Inject


class Converters {

    var moshi: Moshi = Moshi.Builder().build()

    @TypeConverter
    fun listToJson(value: List<String>?): String {
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            String::class.java
        )
        val adapter: JsonAdapter<List<String>> = moshi.adapter(listMyData)
        return adapter.toJson(value) ?: ""
    }

    @TypeConverter
    fun jsonToList(value: String): List<String> {
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            String::class.java
        )
        val adapter: JsonAdapter<List<String>> = moshi.adapter(listMyData)
        return adapter.fromJson(value) ?: listOf()
    }


    @TypeConverter
    fun colorListToJson(value: List<ColorItemUiModel>): String {
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            ColorItemUiModel::class.java
        )
        val adapter: JsonAdapter<List<ColorItemUiModel>> = moshi.adapter(listMyData)
        return adapter.toJson(value) ?: ""
    }

    @TypeConverter
    fun jsonToColorList(value: String): List<ColorItemUiModel> {
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            ColorItemUiModel::class.java
        )
        val adapter: JsonAdapter<List<ColorItemUiModel>> = moshi.adapter(listMyData)
        return adapter.fromJson(value) ?: listOf()
    }

    @TypeConverter
    fun glassesListToJson(value: List<GlassItemUiModel>): String {
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            GlassItemUiModel::class.java
        )
        val adapter: JsonAdapter<List<GlassItemUiModel>> = moshi.adapter(listMyData)
        return adapter.toJson(value) ?: ""
    }

    @TypeConverter
    fun jsonToGlassesList(value: String): List<GlassItemUiModel> {
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            GlassItemUiModel::class.java
        )
        val adapter: JsonAdapter<List<GlassItemUiModel>> = moshi.adapter(listMyData)
        return adapter.fromJson(value) ?: listOf()
    }


    @TypeConverter
    fun cartListToJson(value: List<CartItemUiModel>): String {
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            CartItemUiModel::class.java
        )
        val adapter: JsonAdapter<List<CartItemUiModel>> = moshi.adapter(listMyData)
        return adapter.toJson(value) ?: ""
    }

    @TypeConverter
    fun jsonToCartList(value: String): List<CartItemUiModel> {
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            CartItemUiModel::class.java
        )
        val adapter: JsonAdapter<List<CartItemUiModel>> = moshi.adapter(listMyData)
        return adapter.fromJson(value) ?: listOf()
    }


}