package com.app.ainuq.ui.cart

import androidx.lifecycle.*
import com.app.ainuq.common.AuthStore
import com.app.ainuq.respsitory.CartRepository
import com.app.ainuq.respsitory.UserRepository
import com.app.ainuq.ui.home.ProductItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authStore: AuthStore,
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository
) : ViewModel() {


    val cartItems: LiveData<List<ProductItemUiModel>> = cartRepository.getCartItems().map {
        it.map { cartItem ->

            val prescription =
                userRepository.getPrescriptionByPrescriptionId(cartItem.prescriptionId ?: "")
                    .firstOrNull()?.let { prescription ->
                        prescription.copy(
                            isSelected = cartItem.prescriptionId == prescription.prescriptionId
                        )
                    }

            ProductItemUiModel(
                cartItemId = cartItem.cartItemId,
                prescription = prescription,
                isFavourite = cartItem.isFavourite,
                rating = cartItem.rating,
                colors = cartItem.colors,
                gender = cartItem.gender,
                images = cartItem.images,
                material = cartItem.material,
                thickness = cartItem.thickness,
                weight = cartItem.weight,
                name = cartItem.name,
                glasses = cartItem.glasses,
                price = cartItem.price,
                productId = cartItem.productId,
                modelUrl = cartItem.modelUrl
            )
        }
    }.asLiveData(viewModelScope.coroutineContext)


    fun deleteCartItemById(item: ProductItemUiModel) {
        viewModelScope.launch {
            cartRepository.deleteCartItemById(item.cartItemId ?: "")
        }
    }

    fun deleteCartItem(item: ProductItemUiModel) {
        viewModelScope.launch {
            cartRepository.deleteCartItem(
                CartItemUiModel(
                    cartItemId = item.cartItemId ?: "",
                    prescriptionId = item.prescription?.prescriptionId,
                    isFavourite = item.isFavourite,
                    rating = item.rating,
                    colors = item.colors,
                    gender = item.gender,
                    images = item.images,
                    material = item.material,
                    thickness = item.thickness,
                    weight = item.weight,
                    name = item.name,
                    glasses = item.glasses,
                    price = item.price,
                    productId = item.productId,
                    modelUrl = item.modelUrl
                )
            )
        }
    }


}

