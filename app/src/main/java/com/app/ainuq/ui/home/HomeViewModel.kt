package com.app.ainuq.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.ainuq.common.AppConfig
import com.app.ainuq.common.AuthStore
import com.app.ainuq.ui.cart.GlassItemUiModel
import com.app.ainuq.ui.productDetail.ColorItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.app.ainuq.utils.Result

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authStore: AuthStore,
    private val appConfig: AppConfig,
) : ViewModel() {

    private val _homeData: MutableLiveData<HomeUiModel> = MutableLiveData()
    val homeData: LiveData<HomeUiModel> = _homeData

    private val _eventState: MutableLiveData<Result<Unit>> = MutableLiveData()
    val eventState: LiveData<Result<Unit>> = _eventState

    init {
        val tempItems = mutableListOf<ProductItemUiModel>()
        val categoryItems = mutableListOf<CategoryItemUiModel>()
        repeat(12) {
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
                        appConfig.gitRawUrl +"golden-round/golden-round-front.png",
                        appConfig.gitRawUrl +"golden-round/golden-round-side.png",
                        appConfig.gitRawUrl +"golden-round/golden-round-side2.png",
                        ),
                    material = "Metal",
                    productId = "$it",
                    thickness = "Normal",
                    weight = "Light",
                    modelUrl = appConfig.gitRawUrl +"golden-round/golden-round.gltf"
                )
            )
            categoryItems.add(
                CategoryItemUiModel(
                    name = "Category $it"
                )
            )
        }

        _homeData.value = HomeUiModel(
            listPopular = tempItems,
            listYouMayLike = tempItems,
            listCategory = categoryItems
        )
    }


}