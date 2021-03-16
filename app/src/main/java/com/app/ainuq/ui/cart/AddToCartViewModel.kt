package com.app.ainuq.ui.cart

import androidx.lifecycle.*
import com.app.ainuq.common.AuthStore
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
    private val userRepository: UserRepository
) : ViewModel() {

    private val _productDetail: MutableLiveData<ProductItemUiModel> = MutableLiveData()
    val productDetail: LiveData<ProductItemUiModel> = _productDetail

    val prescriptions: LiveData<List<PrescriptionItemUiModel>> =
        userRepository.getAllPrescription("Osamaid").asLiveData()

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
            colors =  tempColorList
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
            glasses =  tempGlassTypeList
        )
    }


    fun selectPrescription(item: PrescriptionItemUiModel) {
        viewModelScope.launch {
            userRepository.selectDeselectAllPrescription(isSelected = false)
            userRepository.selectDeselectPrescription(isSelected = true, prescriptionId = item.prescriptionId)
        }
    }

    fun addToCart(){
        _productDetail.value?.let { product ->
            if(!product.colors.any { it.isSelected }){
                _messageEvent.value = Event("Please select color")
                return@let
            }

            if(!product.glasses.any { it.isSelected }){
                _messageEvent.value = Event("Please select glass type")
                return@let
            }

            if(prescriptions.value?.any { it.isSelected } != true){
                _messageEvent.value = Event("Please select prescription")
                return@let
            }

            product.prescription = prescriptions.value?.firstOrNull { it.isSelected }


        }
    }

}