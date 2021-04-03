package com.app.ainuq.ui.orders

import androidx.lifecycle.*
import com.app.ainuq.common.AuthStore
import com.app.ainuq.respsitory.CartRepository
import com.app.ainuq.respsitory.OrderRepository
import com.app.ainuq.respsitory.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authStore: AuthStore,
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val orderItems: LiveData<List<OrderItemUiModel>> =
        orderRepository.getOrderItems().map { it.reversed() }.asLiveData(viewModelScope.coroutineContext)
}