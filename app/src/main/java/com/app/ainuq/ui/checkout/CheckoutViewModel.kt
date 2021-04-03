package com.app.ainuq.ui.checkout

import androidx.lifecycle.*
import com.app.ainuq.common.AuthStore
import com.app.ainuq.respsitory.CartRepository
import com.app.ainuq.respsitory.OrderRepository
import com.app.ainuq.respsitory.UserRepository
import com.app.ainuq.ui.cart.CartItemUiModel
import com.app.ainuq.ui.home.ProductItemUiModel
import com.app.ainuq.ui.orders.OrderItemUiModel
import com.app.ainuq.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authStore: AuthStore,
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _addOrderEvent = MutableLiveData<Event<Boolean>>()
    val addOrderEvent: LiveData<Event<Boolean>> = _addOrderEvent

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

    fun placeOrder(address: String) {
        val items = cartItems.value?.map { item ->
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
        }
        val order = OrderItemUiModel(
            orderItemId = System.currentTimeMillis().toString(),
            address = address,
            deliveryType = "Home Delivery",
            orderStatus = "In Progress",
            items = items ?: listOf()
        )

        viewModelScope.launch {
            orderRepository.addToOrder(order)
            cartRepository.deleteAllItems()
            _addOrderEvent.value = Event(true)
        }
    }


}