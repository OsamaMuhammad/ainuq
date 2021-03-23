package com.app.ainuq.ui.checkout

import androidx.lifecycle.*
import com.app.ainuq.common.AuthStore
import com.app.ainuq.respsitory.CartRepository
import com.app.ainuq.respsitory.UserRepository
import com.app.ainuq.ui.home.ProductItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
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
}