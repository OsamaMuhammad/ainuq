package com.app.ainuq.utils

import com.app.ainuq.ui.cart.GlassItemUiModel
import com.app.ainuq.ui.home.ProductItemUiModel
import com.app.ainuq.ui.productDetail.ColorItemUiModel
import kotlin.random.Random

object Constants {

    //    val GITHUB_RAW_URL = "https://raw.githubusercontent.com/OsamaMuhammad/checking/master/frames/"
    private const val GITHUB_RAW_URL =
        "https://raw.githubusercontent.com/OsamaMuhammad/checking/master/newframes/"

    private val genderList = listOf(
        "Male",
        "Female",
        "Unisex"
    )

    private val materialsList = listOf(
        "Plastic",
        "Material"
    )


    private val thicknessList = listOf(
        "Thin",
        "Medium",
        "Thick"
    )

    private val weightList = listOf(
        "Ultra Light",
        "Light",
        "Normal"
    )


    private const val PNG = ".png"
    private const val GLTF = ".gltf"

    private val frameNames = mapOf(
        "harry-potter-silver-model" to "Harry Potter Silver",
        "funky-round-blue" to "Funky Round Blue",
        "pink-halfr-rimless" to "Pink Half Rimless",
        "golden-round" to "Golden Round",
        "harry-potter-red-model" to "Harry Potter Red",
        "aviators-black-model" to "Aviators Black",
        "jimmy-choo" to "JIMMY CHOO",
        "aviators-gold-model" to "Aviators Gold",
        "black-rectangle" to "Black Rectan",
        "cat-eye-black-model" to "Cat Eye Black",
        "diamond-aviator" to "Diamond Aviator",
        "funky-round-red" to "Funky Round Red",
        "aviators-silver-model" to "Aviators Silver",
        "harry-potter-black-model" to "Harry Potter Black",
        "funky-round-yellow" to "Funky Round Yellow",
        "cat-eye-mahroon-model" to "Cat Eye Mahroon",
        "rayban-blue" to "Ray-Ban Blue",
    )

    fun getAllProducts(): List<ProductItemUiModel> {
        return frameNames.map { item ->
            ProductItemUiModel(
                productId = item.key,
                gender = genderList.random(),
                images = getImageUrlsForFrame(name = item.key),
                name = item.value,
                colors = getColours(),
                glasses = getGlasses(),
                material = materialsList.random(),
                isFavourite = false,
                modelUrl = "$GITHUB_RAW_URL${item.key}/${item.key}$GLTF",
                prescription = null,
                price = Random.nextDouble(from = 1000.0, until = 3000.0).toLong().toDouble(),
                rating = "${Random.nextInt(3, 5)}/5",
                thickness = thicknessList.random(),
                weight = weightList.random(),
                cartItemId = null
            )
        }
    }

    private fun getImageUrlsForFrame(name: String): List<String> {
        return listOf(
            "$GITHUB_RAW_URL$name/$name-1$PNG",
            "$GITHUB_RAW_URL$name/$name-2$PNG",
            "$GITHUB_RAW_URL$name/$name-3$PNG"
        )
    }

    private fun getColours(): List<ColorItemUiModel> {
        return listOf(
            ColorItemUiModel(
                colorId = "sadsadsaf",
                name = "Blue",
                value = "#000C9C",
                isSelected = false
            ),
            ColorItemUiModel(
                colorId = "sadsdfdsfsadsaf",
                name = "Black",
                value = "#000000",
                isSelected = false
            ),
            ColorItemUiModel(
                colorId = "sadsadsadfsdfdsff",
                name = "Silver",
                value = "#EAE4E4",
                isSelected = false
            )
        )
    }

    private fun getGlasses(): List<GlassItemUiModel> {
        return listOf(
            GlassItemUiModel(
                name = "Glass",
                isSelected = false,
                price = 240.0,
                glassId = "SDfdsf"
            ),
            GlassItemUiModel(
                name = "Plastic",
                isSelected = false,
                price = 240.0,
                glassId = "asasfas"
            ),
            GlassItemUiModel(
                name = "Computer Glasses",
                isSelected = false,
                price = 435.0,
                glassId = "dfsdgsfhdssd"
            )
        )
    }

}