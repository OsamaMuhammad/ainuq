package com.app.ainuq.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.ainuq.common.AuthStore
import com.app.ainuq.ui.cart.GlassItemUiModel
import com.app.ainuq.ui.home.ProductItemUiModel
import com.app.ainuq.ui.productDetail.ColorItemUiModel
import com.app.ainuq.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authStore: AuthStore
) : ViewModel() {

    private val _filteredProducts: MutableLiveData<List<ProductItemUiModel>> = MutableLiveData()
    val filteredProducts: LiveData<List<ProductItemUiModel>> = _filteredProducts

    private var allProducts: List<ProductItemUiModel> = listOf()

    private val _eventState: MutableLiveData<Result<Unit>> = MutableLiveData()
    val eventState: LiveData<Result<Unit>> = _eventState

    init {
        val tempItems = mutableListOf<ProductItemUiModel>()
        repeat(20) {
            tempItems.add(
                ProductItemUiModel(
                    name = "Item $it",
                    isFavourite = false,
                    price = 1500.0,
                    rating = "4.5/5",
                    colors = listOf(
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
                    ),

                    glasses = listOf(
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
                    ),
                    gender = "Male",
                    images = listOf(
                        "https://picsum.photos/id/0/300/200",
                        "https://picsum.photos/id/1/300/200",
                        "https://picsum.photos/id/10/300/200",
                    ),
                    material = "Metal",
                    productId = "$it",
                    thickness = "Normal",
                    weight = "Light",
                )
            )
        }
        allProducts = tempItems
        getProducts("")
    }


    fun getProducts(searchString: String) {
        if (searchString.trim().isEmpty() || searchString.trim().length < 2) {
            _filteredProducts.value = allProducts
            return
        }

        _filteredProducts.value = allProducts.filter {
            it.name.toLowerCase().contains(searchString.trim().toLowerCase())
        }
    }


}