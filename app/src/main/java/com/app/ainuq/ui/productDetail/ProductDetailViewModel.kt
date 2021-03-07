package com.app.ainuq.ui.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.ainuq.common.AuthStore
import com.app.ainuq.ui.home.HomeUiModel
import com.app.ainuq.ui.home.ProductItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authStore: AuthStore
) : ViewModel() {


    private val _productDetail: MutableLiveData<ProductItemUiModel> = MutableLiveData()
    val productDetail: LiveData<ProductItemUiModel> = _productDetail

    fun setProduct(productDetail: ProductItemUiModel){
        _productDetail.value = productDetail
    }
}