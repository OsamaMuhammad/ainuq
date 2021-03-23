package com.app.ainuq.ui.cart

import androidx.lifecycle.*
import com.app.ainuq.common.AuthStore
import com.app.ainuq.respsitory.CartRepository
import com.app.ainuq.respsitory.UserRepository
import com.app.ainuq.ui.home.ProductItemUiModel
import com.app.ainuq.ui.prescription.PrescriptionItemUiModel
import com.app.ainuq.ui.productDetail.ColorItemUiModel
import com.app.ainuq.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddToCartViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authStore: AuthStore,
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _productDetail: MutableLiveData<ProductItemUiModel> = MutableLiveData()
    val productDetail: LiveData<ProductItemUiModel> = _productDetail

    var isEdit = false

    private val _addToCartEvent = MutableLiveData<Event<Boolean>>()
    val addToCartEvent: LiveData<Event<Boolean>> = _addToCartEvent

    private val _updateItemEvent = MutableLiveData<Event<Boolean>>()
    val updateItemEvent: LiveData<Event<Boolean>> = _updateItemEvent

    val prescriptions: LiveData<List<PrescriptionItemUiModel>> =
        userRepository.getAllPrescription(authStore.user?.userId ?: "")
            .asLiveData(viewModelScope.coroutineContext)

    private val _messageEvent = MutableLiveData<Event<String>>()
    val messageEvent: LiveData<Event<String>> = _messageEvent


    fun setProduct(productDetail: ProductItemUiModel) {
        _productDetail.value = productDetail
    }


    fun selectColor(item: ColorItemUiModel) {
        var tempColorList = _productDetail.value?.colors?.toMutableList() ?: mutableListOf()
        tempColorList = tempColorList.map {
            it.copy(
                isSelected = it.colorId == item.colorId
            )
        }.toMutableList()
        _productDetail.value = _productDetail.value?.copy(
            colors = tempColorList
        )
    }


    fun selectGlassType(item: GlassItemUiModel) {
        var tempGlassTypeList = _productDetail.value?.glasses?.toMutableList() ?: mutableListOf()
        tempGlassTypeList = tempGlassTypeList.map {
            it.copy(
                isSelected = it.glassId == item.glassId
            )
        }.toMutableList()
        _productDetail.value = _productDetail.value?.copy(
            glasses = tempGlassTypeList
        )
    }


    fun selectPrescription(item: PrescriptionItemUiModel) {
        viewModelScope.launch {
            userRepository.selectDeselectAllPrescription(isSelected = false)
            userRepository.selectDeselectPrescription(
                isSelected = true,
                prescriptionId = item.prescriptionId
            )
        }
    }


    fun addToCart() {

        viewModelScope.launch {
            _productDetail.value?.let { product ->
                if (!product.colors.any { it.isSelected }) {
                    _messageEvent.value = Event("Please select color")
                    return@launch
                }

                if (!product.glasses.any { it.isSelected }) {
                    _messageEvent.value = Event("Please select glass type")
                    return@launch
                }

                if (prescriptions.value?.any { it.isSelected } != true) {
                    _messageEvent.value = Event("Please select prescription")
                    return@launch
                }

                product.prescription =
                    prescriptions.value?.firstOrNull { it.isSelected }

                cartRepository.addToCart(
                    CartItemUModel(
                        cartItemId = System.currentTimeMillis().toString(),
                        productId = product.productId,
                        price = product.price,
                        glasses = product.glasses,
                        name = product.name,
                        weight = product.weight,
                        thickness = product.thickness,
                        material = product.material,
                        images = product.images,
                        gender = product.gender,
                        colors = product.colors,
                        rating = product.rating,
                        isFavourite = product.isFavourite,
                        prescriptionId = product.prescription?.prescriptionId,
                        modelUrl = product.modelUrl
                    )
                )

                _addToCartEvent.value = Event(true)
            }

        }
    }


    fun updateItem() {

        viewModelScope.launch {
            _productDetail.value?.let { product ->
                if (!product.colors.any { it.isSelected }) {
                    _messageEvent.value = Event("Please select color")
                    return@launch
                }

                if (!product.glasses.any { it.isSelected }) {
                    _messageEvent.value = Event("Please select glass type")
                    return@launch
                }

                if (prescriptions.value?.any { it.isSelected } != true) {
                    _messageEvent.value = Event("Please select prescription")
                    return@launch
                }

                product.prescription =
                    prescriptions.value?.firstOrNull { it.isSelected }

                cartRepository.updateCartItem(
                    CartItemUModel(
                        cartItemId = product.cartItemId ?: "",
                        productId = product.productId,
                        price = product.price,
                        glasses = product.glasses,
                        name = product.name,
                        weight = product.weight,
                        thickness = product.thickness,
                        material = product.material,
                        images = product.images,
                        gender = product.gender,
                        colors = product.colors,
                        rating = product.rating,
                        isFavourite = product.isFavourite,
                        prescriptionId = product.prescription?.prescriptionId,
                        modelUrl = product.modelUrl
                    )
                )

                _messageEvent.value = Event("Item updated successfully")
                _updateItemEvent.value = Event(true)


            }

        }
    }


}