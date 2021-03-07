package com.app.ainuq.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.ainuq.common.AuthStore
import com.app.ainuq.ui.productDetail.ColorItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authStore: AuthStore
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
                    price = "Rs. 1,500",
                    rating = "4.5/5",
                    colors = listOf(
                        ColorItemUiModel(
                            name = "Blue",
                            value = "#000C9C",
                            isSelected = false
                        ),
                        ColorItemUiModel(
                            name = "Black",
                            value = "#000000",
                            isSelected = false
                        ),
                        ColorItemUiModel(
                            name = "Silver",
                            value = "#EAE4E4",
                            isSelected = false
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
                    weight = "Light"
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